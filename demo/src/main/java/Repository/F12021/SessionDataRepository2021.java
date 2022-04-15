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
        stmt.setInt(2, sessionData.SessionLookupID);
        stmt.setFloat(3, sessionData.SessionTime);
        stmt.setLong(4, sessionData.FrameIdentifier);
        stmt.setString(5, gdc.Weather(sessionData.Weather));
        stmt.setInt(6, sessionData.TrackTemperature);
        stmt.setInt(7, sessionData.AirTemperature);
        stmt.setInt(8, sessionData.TotalLaps);
        stmt.setFloat(9, sessionData.TrackLength);
        stmt.setString(10, gdc.SessionType(sessionData.SessionType));
        stmt.setString(11, gdc.TrackIDs(sessionData.TrackID));
        stmt.setString(12, gdc.Formula(sessionData.Formula));
        stmt.setFloat(13, sessionData.SessionTimeLeft);
        stmt.setFloat(14, sessionData.SessionDuration);
        stmt.setInt(15, sessionData.PitSpeedLimit);
        stmt.setInt(16, sessionData.GamePaused);
        stmt.setInt(17, sessionData.IsSpectating);
        stmt.setInt(18, sessionData.SpectatorCarIndex);
        stmt.setInt(19, sessionData.SliProNativeSupport);
        stmt.setString(20, gdc.SafetyCarStatus(sessionData.SafetyCarStatus));
        stmt.setString(21, gdc.NetworkGame(sessionData.NetworkGame));
        stmt.setInt(22, sessionData.ForecastAccuracy);
        stmt.setInt(23, sessionData.AIDifficulty);
        stmt.setLong(24, sessionData.SeasonLinkIdentifier);
        stmt.setLong(25, sessionData.WeekendLinkIdentifier);
        stmt.setLong(26, sessionData.SessionLinkIdentifier);
        stmt.setInt(27, sessionData.PitStopWindowIdealLap);
        stmt.setInt(28, sessionData.PitStopWindowLatestLap);
        stmt.setInt(29, sessionData.PitStopRejoinPosition);
        stmt.setInt(30, sessionData.SteeringAssist);
        stmt.setInt(31, sessionData.BrakingAssist);
        stmt.setInt(32, sessionData.GearboxAssist);
        stmt.setInt(33, sessionData.PitAssist);
        stmt.setInt(34, sessionData.PitReleaseAssist);
        stmt.setInt(35, sessionData.ERSAssist);
        stmt.setInt(36, sessionData.DRSAssist);
        stmt.setInt(37, sessionData.DynamicRacingLine);
        stmt.setInt(38, sessionData.DynamicRacingLineType);
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
          var path3 = Paths.get(SQL_FOLDER, "F12021/InsertWeatherForecastSamples2021.sql");
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
