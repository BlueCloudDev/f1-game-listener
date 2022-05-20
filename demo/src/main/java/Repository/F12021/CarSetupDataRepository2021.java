package Repository.F12021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;


import F12021Packet.F12021CarSetupData;
import oracle.ucp.jdbc.PoolDataSource;
import Configuration.Configuration;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarSetupDataRepository2021 {
  private static final Logger logger = LogManager.getLogger(CarSetupDataRepository2021.class);
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public void InsertCarSetupData(long packetHeaderID, F12021CarSetupData carSetupData, PoolDataSource dataSource) {
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "InsertCarSetupData2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setInt(2, carSetupData.Index);
        stmt.setInt(3, carSetupData.SessionLookupID);
        stmt.setInt(4, carSetupData.FrontWing);
        stmt.setInt(5, carSetupData.RearWing);
        stmt.setInt(6, carSetupData.OnThrottle);
        stmt.setInt(7, carSetupData.OffThrottle);
        stmt.setFloat(8, carSetupData.FrontCamber);
        stmt.setFloat(9, carSetupData.RearCamber);
        stmt.setFloat(10, carSetupData.FrontToe);
        stmt.setFloat(11, carSetupData.RearToe);
        stmt.setInt(12, carSetupData.FrontSuspension);
        stmt.setInt(13, carSetupData.RearSuspension);
        stmt.setInt(14, carSetupData.FrontAntiRollBar);
        stmt.setInt(15, carSetupData.RearAntiRollBar);
        stmt.setInt(16, carSetupData.FrontSuspensionHeight);
        stmt.setInt(17, carSetupData.RearSuspensionHeight);
        stmt.setInt(18, carSetupData.BrakePressure);
        stmt.setInt(19, carSetupData.BrakeBias);
        stmt.setFloat(20, carSetupData.RearLeftTyrePressure);
        stmt.setFloat(21, carSetupData.RearRightTyrePressure);
        stmt.setFloat(22, carSetupData.FrontLeftTyrePressure);
        stmt.setFloat(23, carSetupData.FrontRightTyrePressure);
        stmt.setInt(24, carSetupData.Ballast);
        stmt.setFloat(25, carSetupData.FuelLoad);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }

  
}
