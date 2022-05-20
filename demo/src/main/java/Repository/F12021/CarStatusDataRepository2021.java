package Repository.F12021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Configuration.Configuration;
import Converter.GameDataConverter2021;
import F12021Packet.F12021CarStatusData;
import oracle.ucp.jdbc.PoolDataSource;

public class CarStatusDataRepository2021 {
  private static final Logger logger = LogManager.getLogger(CarStatusDataRepository2021.class);
  private GameDataConverter2021 gdc = new GameDataConverter2021();
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public void InsertCarStatusData(long packetHeaderID, F12021CarStatusData statusData, PoolDataSource dataSource) {
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "InsertCarStatusData2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setInt(2, statusData.Index);
        stmt.setInt(3, statusData.SessionLookupID);
        stmt.setInt(4,  statusData.TractionControl);
        stmt.setInt(5, statusData.AntiLockBrakes);
        stmt.setString(6, gdc.FuelMix(statusData.FuelMix));
        stmt.setInt(7, statusData.FrontBrakeBias);
        stmt.setInt(8, statusData.PitLimiterStatus);
        stmt.setFloat(9, statusData.FuelInTank);
        stmt.setFloat(10, statusData.FuelCapacity);
        stmt.setFloat(11, statusData.FuelRemainingLaps);
        stmt.setInt(12, statusData.MaxRPM);
        stmt.setInt(13, statusData.IdleRPM);
        stmt.setInt(14, statusData.MaxGears);
        stmt.setString(15, gdc.DRSAllowed(statusData.DRSAllowed));
        stmt.setInt(16, statusData.DRSActivationDistance);
        stmt.setString(17, gdc.ActualTypeCompound(statusData.ActualTyreCompound));
        stmt.setString(18, gdc.VisualTyreCompound(statusData.VisualTyreCompound));
        stmt.setInt(19, statusData.TyresAgeLaps);
        stmt.setString(20, gdc.VehicleFiaFlags(statusData.VehicleFiaFlags));
        stmt.setFloat(21, statusData.ERSStoreEnergy);
        stmt.setInt(22, statusData.ERSDeployMode);
        stmt.setFloat(23, statusData.ERSHarvestedThisLapMGUK);
        stmt.setFloat(24, statusData.ERSHarvestedThisLapMGUK);
        stmt.setFloat(25, statusData.ERSDeployedThisLap);
        stmt.setInt(26, statusData.NetworkPaused);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }

  
}
