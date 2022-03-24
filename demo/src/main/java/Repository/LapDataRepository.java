package Repository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;

import Configuration.Configuration;
import Converter.GameDataConverter;
import F12020Packet.F12020LapData;
import oracle.ucp.jdbc.PoolDataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LapDataRepository {
  private static final Logger logger = LogManager.getLogger(LapDataRepository.class);
  private GameDataConverter gdc = new GameDataConverter();
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public void InsertLapData(long packetHeaderID, F12020LapData lapData, PoolDataSource dataSource) {
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "InsertLapData.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setInt(1, lapData.Index);  
        stmt.setLong(2, packetHeaderID);
        stmt.setFloat(3, lapData.LastLapTime);
        stmt.setFloat(4, lapData.CurrentLapTime);
        stmt.setInt(5, lapData.Sector1TimeInMS);
        stmt.setInt(6, lapData.Sector2TimeInMS);
        stmt.setFloat(7, lapData.BestLapTime);
        stmt.setInt(8, lapData.BestLapNum);
        stmt.setInt(9, lapData.BestLapSector1TimeInMS);
        stmt.setInt(10, lapData.BestLapSector2TimeInMS);
        stmt.setInt(11, lapData.BestLapSector3TimeInMS);
        stmt.setInt(12, lapData.BestOverallSector1TimeInMS);
        stmt.setInt(13, lapData.BestOverallSector1LapNum);
        stmt.setInt(14, lapData.BestOverallSector2TimeInMS);
        stmt.setInt(15, lapData.BestOverallSector2LapNum);
        stmt.setInt(16, lapData.BestOverallSector3TimeInMS);
        stmt.setInt(17, lapData.BestOverallSector3LapNum);
        stmt.setFloat(18, lapData.LapDistance);
        stmt.setFloat(19, lapData.TotalDistance);
        stmt.setFloat(20, lapData.SafetyCarDelta);
        stmt.setInt(21, lapData.CarPosition);
        stmt.setInt(22, lapData.CurrentLapNum);
        stmt.setInt(23, lapData.PitStatus);
        stmt.setInt(24, lapData.Sector);
        stmt.setInt(25, lapData.CurrentLapInvalid);
        stmt.setInt(26, lapData.Penalties);
        stmt.setInt(27, lapData.GridPosition);
        stmt.setString(28, gdc.DriverStatus(lapData.DriverStatus));
        stmt.setString(29, gdc.ResultStatus(lapData.ResultStatus));
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }
}
