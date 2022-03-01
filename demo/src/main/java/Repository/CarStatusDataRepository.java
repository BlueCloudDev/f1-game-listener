package Repository;

import java.io.File;
import java.nio.file.Files;
import java.sql.PreparedStatement;

import Converter.GameDataConverter;
import F12020Packet.F12020CarStatusData;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class CarStatusDataRepository {
  private GameDataConverter gdc = new GameDataConverter();
  public void InsertCarStatusData(long packetHeaderID, F12020CarStatusData statusData, OracleDataSource dataSource) {
    try (OracleConnection con = (OracleConnection) dataSource.getConnection()) {
      con.setAutoCommit(true);
      File file = new File("/home/opc/f1-game-listener/demo/src/InsertCarStatusData.sql");
      String query = new String(Files.readAllBytes(file.toPath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setInt(2,  statusData.TractionControl);
        stmt.setInt(3, statusData.AntiLockBrakes);
        stmt.setString(4, gdc.FuelMix(statusData.FuelMix));
        stmt.setInt(5, statusData.FrontBrakeBias);
        stmt.setInt(6, statusData.PitLimiterStatus);
        stmt.setFloat(7, statusData.FuelInTank);
        stmt.setFloat(8, statusData.FuelCapacity);
        stmt.setFloat(9, statusData.FuelRemainingLaps);
        stmt.setInt(10, statusData.MaxRPM);
        stmt.setInt(11, statusData.IdleRPM);
        stmt.setInt(12, statusData.MaxGears);
        stmt.setString(13, gdc.DRSAllowed(statusData.DRSAllowed));
        stmt.setInt(14, statusData.DRSActivationDistance);
        stmt.setInt(15, statusData.TyresWear[0]);
        stmt.setInt(16, statusData.TyresWear[1]);
        stmt.setInt(17, statusData.TyresWear[2]);
        stmt.setInt(18, statusData.TyresWear[3]);
        stmt.setString(19, gdc.ActualTypeCompound(statusData.ActualTyreCompound));
        stmt.setString(20, gdc.VisualTyreCompound(statusData.VisualTyreCompound));
        stmt.setInt(21, statusData.TyresAgeLaps);
        stmt.setInt(22, statusData.TyresDamage[0]);
        stmt.setInt(23, statusData.TyresDamage[1]);
        stmt.setInt(24, statusData.TyresDamage[2]);
        stmt.setInt(25, statusData.TyresDamage[3]);
        stmt.setInt(26, statusData.FrontLeftWingDamage);
        stmt.setInt(27, statusData.FrontRightWingDamage);
        stmt.setInt(28, statusData.RearWingDamage);
        stmt.setInt(29, statusData.DRSFault);
        stmt.setInt(30, statusData.EngineDamage);
        stmt.setInt(31, statusData.GearBoxDamage);
        stmt.setString(32, gdc.VehicleFiaFlags(statusData.VehicleFiaFlags));
        stmt.setFloat(33, statusData.ERSStoreEnergy);
        stmt.setInt(34, statusData.ERSDeployMode);
        stmt.setFloat(35, statusData.ERSHarvestedThisLapMGUK);
        stmt.setFloat(36, statusData.ERSHarvestedThisLapMGUK);
        stmt.setFloat(37, statusData.ERSDeployedThisLap);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  
}
