package Repository.F12021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Configuration.Configuration;
import Converter.GameDataConverter2021;
import F12021Packet.F12021MarshalZone;
import F12021Packet.F12021PacketSessionData;
import F12021Packet.F12021WeatherForecastSample;
import oracle.ucp.jdbc.PoolDataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SessionDataRepository2021 {
  private static final Logger logger = LogManager.getLogger(SessionDataRepository2021.class);
  private GameDataConverter2021 gdc = new GameDataConverter2021();
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public void InsertSessionData(long packetHeaderID, F12021PacketSessionData sessionData, PoolDataSource dataSource) {
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "F12021/InsertSessionData2021.sql");
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
        stmt.setInt(19, sessionData.ForecastAccuracy);
        stmt.setInt(20, sessionData.AIDifficulty);
        stmt.setLong(21, sessionData.SeasonLinkIdentifier);
        stmt.setLong(22, sessionData.WeekendLinkIdentifier);
        stmt.setLong(23, sessionData.SessionLinkIdentifier);
        stmt.setInt(24, sessionData.PitStopWindowIdealLap);
        stmt.setInt(25, sessionData.PitStopWindowLatestLap);
        stmt.setInt(26, sessionData.PitStopRejoinPosition);
        stmt.setInt(27, sessionData.SteeringAssist);
        stmt.setInt(28, sessionData.BrakingAssist);
        stmt.setInt(29, sessionData.GearboxAssist);
        stmt.setInt(30, sessionData.PitAssist);
        stmt.setInt(31, sessionData.PitReleaseAssist);
        stmt.setInt(32, sessionData.ERSAssist);
        stmt.setInt(33, sessionData.DRSAssist);
        stmt.setInt(34, sessionData.DynamicRacingLine);
        stmt.setInt(35, sessionData.DynamicRacingLineType);
        long id = 0;
        if (stmt.executeUpdate() > 0) {
          ResultSet generatedKeys = stmt.getGeneratedKeys();
          if (null != generatedKeys && generatedKeys.next()) {
              id = generatedKeys.getLong(1);
          }
        }

        if (id > 0 && sessionData.MarshalZones.length > 0) {
          var path2 = Paths.get(SQL_FOLDER, "F12021/InsertMarshalZone2021.sql");
          String query2 = new String(Files.readAllBytes(path2.toAbsolutePath()));
          for (F12021MarshalZone mz : sessionData.MarshalZones) {
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
          var path3 = Paths.get(SQL_FOLDER, "F12021/InsertWeatherForecast2021.sql");
          String query3 = new String(Files.readAllBytes(path3.toAbsolutePath()));
          for (F12021WeatherForecastSample wf : sessionData.WeatherForecastSamples) {
            if (wf != null) {
              try (PreparedStatement wfstmt = con.prepareStatement(query3)) {
                wfstmt.setLong(1, id);
                wfstmt.setString(2, gdc.SessionType(wf.SessionType));
                wfstmt.setInt(3, wf.TimeOffset);
                wfstmt.setString(4, gdc.Weather(wf.Weather));
                wfstmt.setInt(5, wf.TrackTemperature);
                wfstmt.setString(6, gdc.TemperatureChange(wf.TractTemperatureChange));
                wfstmt.setInt(7, wf.AirTemperature);
                wfstmt.setString(8, gdc.TemperatureChange(wf.AirTemperatureChange));
                wfstmt.setInt(9, wf.RainPercentage);
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
