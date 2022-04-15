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
      var path = Paths.get(SQL_FOLDER, "F12021/InsertCarStatusData2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setInt(2, statusData.Index);
        stmt.setInt(3, statusData.SessionLookupID);
        stmt.setFloat(4, statusData.SessionTime);
        stmt.setLong(5, statusData.FrameIdentifier);
        stmt.setInt(6,  statusData.TractionControl);
        stmt.setInt(7, statusData.AntiLockBrakes);
        stmt.setString(8, gdc.FuelMix(statusData.FuelMix));
        stmt.setInt(9, statusData.FrontBrakeBias);
        stmt.setInt(10, statusData.PitLimiterStatus);
        stmt.setFloat(11, statusData.FuelInTank);
        stmt.setFloat(12, statusData.FuelCapacity);
        stmt.setFloat(13, statusData.FuelRemainingLaps);
        stmt.setInt(14, statusData.MaxRPM);
        stmt.setInt(15, statusData.IdleRPM);
        stmt.setInt(16, statusData.MaxGears);
        stmt.setString(17, gdc.DRSAllowed(statusData.DRSAllowed));
        stmt.setInt(18, statusData.DRSActivationDistance);
        stmt.setString(19, gdc.ActualTypeCompound(statusData.ActualTyreCompound));
        stmt.setString(20, gdc.VisualTyreCompound(statusData.VisualTyreCompound));
        stmt.setInt(21, statusData.TyresAgeLaps);
        stmt.setString(22, gdc.VehicleFiaFlags(statusData.VehicleFiaFlags));
        stmt.setFloat(23, statusData.ERSStoreEnergy);
        stmt.setInt(24, statusData.ERSDeployMode);
        stmt.setFloat(25, statusData.ERSHarvestedThisLapMGUK);
        stmt.setFloat(26, statusData.ERSHarvestedThisLapMGUK);
        stmt.setFloat(27, statusData.ERSDeployedThisLap);
        stmt.setInt(28, statusData.NetworkPaused);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }

  
}
