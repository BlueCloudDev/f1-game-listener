package Repository;

import java.io.File;
import java.nio.file.Files;
import java.sql.PreparedStatement;

import Converter.GameDataConverter;
import F12020Packet.F12020LapData;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class LapDataRepository {
  private GameDataConverter gdc = new GameDataConverter();

  public void InsertLapData(long packetHeaderID, F12020LapData lapData, OracleDataSource dataSource) {
    try (OracleConnection con = (OracleConnection) dataSource.getConnection()) {
      con.setAutoCommit(true);
      File file = new File("/home/opc/f1-game-listener/demo/src/InsertLapData.sql");
      String query = new String(Files.readAllBytes(file.toPath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setFloat(2, lapData.LastLapTime);
        stmt.setFloat(3, lapData.CurrentLapTime);
        stmt.setInt(4, lapData.Sector1TimeInMS);
        stmt.setInt(5, lapData.Sector2TimeInMS);
        stmt.setFloat(6, lapData.BestLapTime);
        stmt.setInt(7, lapData.BestLapNum);
        stmt.setInt(8, lapData.BestLapSector1TimeInMS);
        stmt.setInt(9, lapData.BestLapSector2TimeInMS);
        stmt.setInt(10, lapData.BestLapSector3TimeInMS);
        stmt.setInt(11, lapData.BestOverallSector1TimeInMS);
        stmt.setInt(12, lapData.BestOverallSector1LapNum);
        stmt.setInt(13, lapData.BestOverallSector2TimeInMS);
        stmt.setInt(14, lapData.BestOverallSector2LapNum);
        stmt.setInt(15, lapData.BestOverallSector3TimeInMS);
        stmt.setInt(16, lapData.BestOverallSector3LapNum);
        stmt.setFloat(17, lapData.LapDistance);
        stmt.setFloat(18, lapData.TotalDistance);
        stmt.setFloat(19, lapData.SafetyCarDelta);
        stmt.setInt(20, lapData.CarPosition);
        stmt.setInt(21, lapData.CurrentLapNum);
        stmt.setInt(22, lapData.PitStatus);
        stmt.setInt(23, lapData.Sector);
        stmt.setInt(24, lapData.CurrentLapInvalid);
        stmt.setInt(25, lapData.Penalties);
        stmt.setInt(26, lapData.GridPosition);
        stmt.setString(27, gdc.DriverStatus(lapData.DriverStatus));
        stmt.setString(28, gdc.ResultStatus(lapData.ResultStatus));
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
