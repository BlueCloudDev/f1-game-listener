package Repository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Configuration.Configuration;
import Converter.GameDataConverter;
import F12020Packet.F12020MarshalZone;
import F12020Packet.F12020PacketSessionData;
import F12020Packet.F12020WeatherForecastSample;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SessionDataRepository {
  private static final Logger logger = LogManager.getLogger(SessionDataRepository.class);
  private GameDataConverter gdc = new GameDataConverter();
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public void InsertSessionData(long packetHeaderID, F12020PacketSessionData sessionData, OracleDataSource dataSource) {
    try (OracleConnection con = (OracleConnection) dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "InsertSessionData.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      String returnCols[] = { "id" };
      try (PreparedStatement stmt = con.prepareStatement(query, returnCols)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setString(2, gdc.Weather(sessionData.Weather));
        stmt.setInt(3, sessionData.TrackTemperature);
        stmt.setInt(4, sessionData.AirTemperature);
        stmt.setInt(5, sessionData.TotalLaps);
        stmt.setFloat(6, sessionData.TrackLength);
        stmt.setString(7, gdc.SessionType(sessionData.SessionType));
        stmt.setString(8, gdc.TrackIDs(sessionData.TrackID));
        stmt.setString(9, gdc.Formula(sessionData.Formula));
        stmt.setFloat(10, sessionData.SessionTimeLeft);
        stmt.setFloat(11, sessionData.SessionDuration);
        stmt.setInt(12, sessionData.PitSpeedLimit);
        stmt.setInt(13, sessionData.GamePaused);
        stmt.setInt(14, sessionData.IsSpectating);
        stmt.setInt(15, sessionData.SpectatorCarIndex);
        stmt.setInt(16, sessionData.SliProNativeSupport);
        stmt.setString(17, gdc.SafetyCarStatus(sessionData.SafetyCarStatus));
        stmt.setString(18, gdc.NetworkGame(sessionData.NetworkGame));
        long id = 0;
        if (stmt.executeUpdate() > 0) {
          ResultSet generatedKeys = stmt.getGeneratedKeys();
          if (null != generatedKeys && generatedKeys.next()) {
              id = generatedKeys.getLong(1);
          }
        }

        if (id > 0 && sessionData.MarshalZones.length > 0) {
          var path2 = Paths.get(SQL_FOLDER, "InsertMarshalZone.sql");
          String query2 = new String(Files.readAllBytes(path2.toAbsolutePath()));
          for (F12020MarshalZone mz : sessionData.MarshalZones) {
            if (mz != null) {
              try (PreparedStatement mzstmt = con.prepareStatement(query2)) {
                mzstmt.setLong(1, id);
                mzstmt.setFloat(2, mz.ZoneStart);
                mzstmt.setString(3, gdc.ZoneFlag(mz.ZoneFlag));
                mzstmt.execute();
              }
            }
          }
        }

        if (id > 0 && sessionData.WeatherForecastSamples.length > 0) {
          var path3 = Paths.get(SQL_FOLDER, "InsertWeatherForecast.sql");
          String query3 = new String(Files.readAllBytes(path3.toAbsolutePath()));
          for (F12020WeatherForecastSample wf : sessionData.WeatherForecastSamples) {
            if (wf != null) {
              try (PreparedStatement wfstmt = con.prepareStatement(query3)) {
                wfstmt.setLong(1, id);
                wfstmt.setString(2, gdc.SessionType(wf.SessionType));
                wfstmt.setInt(3, wf.TimeOffset);
                wfstmt.setString(4, gdc.Weather(wf.Weather));
                wfstmt.setInt(5, wf.TrackTemperature);
                wfstmt.setInt(6, wf.AirTemperature);
                wfstmt.execute();
              }
            }
          }
        }
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }
}
