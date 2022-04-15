package Repository.F12021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;

import Configuration.Configuration;
import Converter.GameDataConverter2021;
import F12021Packet.F12021LapData;
import oracle.ucp.jdbc.PoolDataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LapDataRepository2021 {
  private static final Logger logger = LogManager.getLogger(LapDataRepository2021.class);
  private GameDataConverter2021 gdc = new GameDataConverter2021();
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public void InsertLapData(long packetHeaderID, F12021LapData lapData, PoolDataSource dataSource) {
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "F12021/InsertLapData2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setInt(1, lapData.Index);  
        stmt.setLong(2, packetHeaderID);
        stmt.setInt(3, lapData.SessionLookupID);
        stmt.setFloat(4, lapData.SessionTime);
        stmt.setLong(5, lapData.FrameIdentifier);
        stmt.setFloat(6, lapData.LastLapTime);
        stmt.setFloat(7, lapData.CurrentLapTime);
        stmt.setInt(8, lapData.Sector1TimeInMS);
        stmt.setInt(9, lapData.Sector2TimeInMS);
        stmt.setFloat(10, lapData.LapDistance);
        stmt.setFloat(11, lapData.TotalDistance);
        stmt.setFloat(12, lapData.SafetyCarDelta);
        stmt.setInt(13, lapData.CarPosition);
        stmt.setInt(14, lapData.CurrentLapNum);
        stmt.setInt(15, lapData.PitStatus);
        stmt.setInt(16, lapData.NumPitStops);
        stmt.setInt(17, lapData.Sector);
        stmt.setInt(18, lapData.CurrentLapInvalid);
        stmt.setInt(19, lapData.Penalties);
        stmt.setInt(20, lapData.Warnings);
        stmt.setInt(21, lapData.NumUnservedDriveThroughPens);
        stmt.setInt(22, lapData.NumUnservedStopGoPens);
        stmt.setInt(23, lapData.GridPosition);
        stmt.setString(24, gdc.DriverStatus(lapData.DriverStatus));
        stmt.setString(25, gdc.ResultStatus(lapData.ResultStatus));
        stmt.setInt(26, lapData.PitLaneTimerActive);
        stmt.setInt(27, lapData.PitLaneTimeInLaneInMS);
        stmt.setInt(28, lapData.PitStopTimerInMS);
        stmt.setInt(29, lapData.PitStopShouldServePen);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }
}
