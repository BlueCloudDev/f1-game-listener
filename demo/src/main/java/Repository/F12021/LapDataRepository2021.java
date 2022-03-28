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
        stmt.setFloat(3, lapData.LastLapTime);
        stmt.setFloat(4, lapData.CurrentLapTime);
        stmt.setInt(5, lapData.Sector1TimeInMS);
        stmt.setInt(6, lapData.Sector2TimeInMS);
        stmt.setFloat(7, lapData.LapDistance);
        stmt.setFloat(8, lapData.TotalDistance);
        stmt.setFloat(9, lapData.SafetyCarDelta);
        stmt.setInt(10, lapData.CarPosition);
        stmt.setInt(11, lapData.CurrentLapNum);
        stmt.setInt(12, lapData.PitStatus);
        stmt.setInt(13, lapData.NumPitStops);
        stmt.setInt(14, lapData.Sector);
        stmt.setInt(15, lapData.CurrentLapInvalid);
        stmt.setInt(16, lapData.Penalties);
        stmt.setInt(17, lapData.Warnings);
        stmt.setInt(18, lapData.NumUnservedDriveThroughPens);
        stmt.setInt(19, lapData.NumUnservedStopGoPens);
        stmt.setInt(20, lapData.GridPosition);
        stmt.setString(21, gdc.DriverStatus(lapData.DriverStatus));
        stmt.setString(22, gdc.ResultStatus(lapData.ResultStatus));
        stmt.setInt(23, lapData.PitLaneTimerActive);
        stmt.setInt(24, lapData.PitLaneTimeInLaneInMS);
        stmt.setInt(25, lapData.PitStopTimerInMS);
        stmt.setInt(26, lapData.PitStopShouldServePen);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }
}
