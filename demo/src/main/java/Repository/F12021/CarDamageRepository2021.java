package Repository.F12021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Configuration.Configuration;
import F12021Packet.F12021CarDamageData;
import oracle.ucp.jdbc.PoolDataSource;

public class CarDamageRepository2021 {
  private static final Logger logger = LogManager.getLogger(CarDamageRepository2021.class);
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public void InsertCarDamage(long packetHeaderId, F12021CarDamageData carDamage, PoolDataSource dataSource) {
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "InsertCarDamageData2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, packetHeaderId);
        stmt.setInt(2, carDamage.Index);
        stmt.setInt(3, carDamage.SessionLookupID);
        stmt.setFloat(4, carDamage.TyresWear[0]);
        stmt.setFloat(5, carDamage.TyresWear[1]);
        stmt.setFloat(6, carDamage.TyresWear[2]);
        stmt.setFloat(7, carDamage.TyresWear[3]);
        stmt.setInt(8, carDamage.TyresDamage[0]);
        stmt.setInt(9, carDamage.TyresDamage[1]);
        stmt.setInt(10, carDamage.TyresDamage[2]);
        stmt.setInt(11, carDamage.TyresDamage[3]);
        stmt.setInt(12, carDamage.BrakesDamage[0]);
        stmt.setInt(13, carDamage.BrakesDamage[1]);
        stmt.setInt(14, carDamage.BrakesDamage[2]);
        stmt.setInt(15, carDamage.BrakesDamage[3]);
        stmt.setInt(16, carDamage.FrontLeftWingDamage);
        stmt.setInt(17, carDamage.FrontRightWingDamage);
        stmt.setInt(18, carDamage.RearWingDamage);
        stmt.setInt(19, carDamage.FloorDamage);
        stmt.setInt(20, carDamage.DiffuserDamage);
        stmt.setInt(21, carDamage.SidepodDamage);
        stmt.setInt(22, carDamage.DRSFault);
        stmt.setInt(23, carDamage.GearBoxDamage);
        stmt.setInt(24, carDamage.EngineDamage);
        stmt.setInt(25, carDamage.EngineMGUHWear);
        stmt.setInt(26, carDamage.EngineESWear);
        stmt.setInt(27, carDamage.EngineCEWear);
        stmt.setInt(28, carDamage.EngineICEWear);
        stmt.setInt(29, carDamage.EngineMGUKWear);
        stmt.setInt(30, carDamage.EngineTCWear);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }
}
