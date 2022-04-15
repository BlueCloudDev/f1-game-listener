package Repository.F12020;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Configuration.Configuration;
import Converter.GameDataConverter;
import F12020Packet.F12020CarStatusData;
import oracle.ucp.jdbc.PoolDataSource;

public class CarStatusDataRepository {
  private static final Logger logger = LogManager.getLogger(CarStatusDataRepository.class);
  private GameDataConverter gdc = new GameDataConverter();
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public void InsertCarStatusData(long packetHeaderID, F12020CarStatusData statusData, PoolDataSource dataSource) {
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "InsertCarStatusData.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setInt(2, statusData.Index);
        stmt.setInt(3,  statusData.TractionControl);
        stmt.setInt(4, statusData.AntiLockBrakes);
        stmt.setString(5, gdc.FuelMix(statusData.FuelMix));
        stmt.setInt(6, statusData.FrontBrakeBias);
        stmt.setInt(7, statusData.PitLimiterStatus);
        stmt.setFloat(8, statusData.FuelInTank);
        stmt.setFloat(9, statusData.FuelCapacity);
        stmt.setFloat(10, statusData.FuelRemainingLaps);
        stmt.setInt(11, statusData.MaxRPM);
        stmt.setInt(12, statusData.IdleRPM);
        stmt.setInt(13, statusData.MaxGears);
        stmt.setString(14, gdc.DRSAllowed(statusData.DRSAllowed));
        stmt.setInt(15, statusData.DRSActivationDistance);
        stmt.setInt(16, statusData.TyresWear[0]);
        stmt.setInt(17, statusData.TyresWear[1]);
        stmt.setInt(18, statusData.TyresWear[2]);
        stmt.setInt(19, statusData.TyresWear[3]);
        stmt.setString(20, gdc.ActualTypeCompound(statusData.ActualTyreCompound));
        stmt.setString(21, gdc.VisualTyreCompound(statusData.VisualTyreCompound));
        stmt.setInt(22, statusData.TyresAgeLaps);
        stmt.setInt(23, statusData.TyresDamage[0]);
        stmt.setInt(24, statusData.TyresDamage[1]);
        stmt.setInt(25, statusData.TyresDamage[2]);
        stmt.setInt(26, statusData.TyresDamage[3]);
        stmt.setInt(27, statusData.FrontLeftWingDamage);
        stmt.setInt(28, statusData.FrontRightWingDamage);
        stmt.setInt(29, statusData.RearWingDamage);
        stmt.setInt(30, statusData.DRSFault);
        stmt.setInt(31, statusData.EngineDamage);
        stmt.setInt(32, statusData.GearBoxDamage);
        stmt.setString(33, gdc.VehicleFiaFlags(statusData.VehicleFiaFlags));
        stmt.setFloat(34, statusData.ERSStoreEnergy);
        stmt.setInt(35, statusData.ERSDeployMode);
        stmt.setFloat(36, statusData.ERSHarvestedThisLapMGUK);
        stmt.setFloat(37, statusData.ERSHarvestedThisLapMGUK);
        stmt.setFloat(38, statusData.ERSDeployedThisLap);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }

  
}
