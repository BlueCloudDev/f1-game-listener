package Repository.F12021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "InsertSessionData2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      String returnCols[] = { "id" };
      try (PreparedStatement stmt = con.prepareStatement(query, returnCols)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setInt(2, sessionData.SessionLookupID);
        stmt.setString(3, gdc.Weather(sessionData.Weather));
        stmt.setInt(4, sessionData.TrackTemperature);
        stmt.setInt(5, sessionData.AirTemperature);
        stmt.setInt(6, sessionData.TotalLaps);
        stmt.setFloat(7, sessionData.TrackLength);
        stmt.setString(8, gdc.SessionType(sessionData.SessionType));
        stmt.setString(9, gdc.TrackIDs(sessionData.TrackID));
        stmt.setString(10, gdc.Formula(sessionData.Formula));
        stmt.setFloat(11, sessionData.SessionTimeLeft);
        stmt.setFloat(12, sessionData.SessionDuration);
        stmt.setInt(13, sessionData.PitSpeedLimit);
        stmt.setInt(14, sessionData.GamePaused);
        stmt.setInt(15, sessionData.IsSpectating);
        stmt.setInt(16, sessionData.SpectatorCarIndex);
        stmt.setInt(17, sessionData.SliProNativeSupport);
        stmt.setString(18, gdc.SafetyCarStatus(sessionData.SafetyCarStatus));
        stmt.setString(19, gdc.NetworkGame(sessionData.NetworkGame));
        stmt.setInt(20, sessionData.ForecastAccuracy);
        stmt.setInt(21, sessionData.AIDifficulty);
        stmt.setLong(22, sessionData.SeasonLinkIdentifier);
        stmt.setLong(23, sessionData.WeekendLinkIdentifier);
        stmt.setLong(24, sessionData.SessionLinkIdentifier);
        stmt.setInt(25, sessionData.PitStopWindowIdealLap);
        stmt.setInt(26, sessionData.PitStopWindowLatestLap);
        stmt.setInt(27, sessionData.PitStopRejoinPosition);
        stmt.setInt(28, sessionData.SteeringAssist);
        stmt.setInt(29, sessionData.BrakingAssist);
        stmt.setInt(30, sessionData.GearboxAssist);
        stmt.setInt(31, sessionData.PitAssist);
        stmt.setInt(32, sessionData.PitReleaseAssist);
        stmt.setInt(33, sessionData.ERSAssist);
        stmt.setInt(34, sessionData.DRSAssist);
        stmt.setInt(35, sessionData.DynamicRacingLine);
        stmt.setInt(36, sessionData.DynamicRacingLineType);
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

  public F12021PacketSessionData SelectSessionData(String SessionUID, PoolDataSource dataSource) {
    F12021PacketSessionData result = new F12021PacketSessionData();
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(false);
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "SelectSessionDataBySessionUID.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setString(1, SessionUID);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
          result = convertResultSetToSessionData(rs);
        }
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
    return result;
  }

  public ArrayList<F12021PacketSessionData> SelectAllSessionData(String SessionUID, PoolDataSource dataSource) {
    ArrayList<F12021PacketSessionData> results = new ArrayList<F12021PacketSessionData>();
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(false);
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "SelectSessionDataBySessionUID.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setString(1, SessionUID);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
          var result = convertResultSetToSessionData(rs);
          results.add(result);
        }
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
    return results;
  }

  private F12021PacketSessionData convertResultSetToSessionData(ResultSet rs) throws SQLException {
    F12021PacketSessionData result = new F12021PacketSessionData();
    result.Id = rs.getInt(1);
    result.PacketId = rs.getInt(2);
    result.SessionLookupID = rs.getInt(3);
    result.WeatherString = rs.getString(4);
    result.TrackTemperature = rs.getInt(5);
    result.AirTemperature = rs.getInt(6);
    result.TotalLaps = rs.getInt(7);
    result.TrackLength = rs.getInt(8);
    result.SessionTypeString = rs.getString(9);
    result.TrackIDString = rs.getString(10);
    result.FormulaString = rs.getString(11);
    result.SessionTimeLeft = rs.getInt(12);
    result.SessionDuration = rs.getInt(13);
    result.PitSpeedLimit = rs.getInt(14);
    result.GamePaused = rs.getInt(15);
    result.IsSpectating = rs.getInt(16);
    result.SpectatorCarIndex = rs.getInt(17);
    result.SliProNativeSupport = rs.getInt(18);
    result.SafetyCarStatusString = rs.getString(19);
    result.NetworkGameString = rs.getString(20);
    result.ForecastAccuracy = rs.getInt(21);
    result.AIDifficulty = rs.getInt(22);
    result.SeasonLinkIdentifier = rs.getLong(23);
    result.WeekendLinkIdentifier = rs.getLong(24);
    result.SessionLinkIdentifier = rs.getLong(25);
    result.PitStopWindowIdealLap = rs.getInt(26);
    result.PitStopWindowLatestLap = rs.getInt(27);
    result.PitStopRejoinPosition = rs.getInt(28);
    result.SteeringAssist = rs.getInt(29);
    result.BrakingAssist = rs.getInt(30);
    result.GearboxAssist = rs.getInt(31);
    result.PitAssist = rs.getInt(32);
    result.PitReleaseAssist = rs.getInt(33);
    result.ERSAssist = rs.getInt(34);
    result.DRSAssist = rs.getInt(35);
    result.DynamicRacingLine = rs.getInt(36);
    result.DynamicRacingLineType = rs.getInt(37);
    result.SessionUID = rs.getString(38);
    return result;
  }
}
