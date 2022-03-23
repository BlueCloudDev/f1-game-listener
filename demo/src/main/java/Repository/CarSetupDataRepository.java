package Repository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;


import F12020Packet.F12020CarSetupData;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;
import Configuration.Configuration;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarSetupDataRepository {
  private static final Logger logger = LogManager.getLogger(CarSetupDataRepository.class);
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public void InsertCarSetupData(long packetHeaderID, F12020CarSetupData carSetupData, OracleDataSource dataSource) {
    try (OracleConnection con = (OracleConnection) dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "InsertCarSetupData.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setInt(2, carSetupData.Index);
        stmt.setInt(3, carSetupData.FrontWing);
        stmt.setInt(4, carSetupData.RearWing);
        stmt.setInt(5, carSetupData.OnThrottle);
        stmt.setInt(6, carSetupData.OffThrottle);
        stmt.setFloat(7, carSetupData.FrontCamber);
        stmt.setFloat(8, carSetupData.RearCamber);
        stmt.setFloat(9, carSetupData.FrontToe);
        stmt.setFloat(10, carSetupData.RearToe);
        stmt.setInt(11, carSetupData.FrontSuspension);
        stmt.setInt(12, carSetupData.RearSuspension);
        stmt.setInt(13, carSetupData.FrontAntiRollBar);
        stmt.setInt(14, carSetupData.RearAntiRollBar);
        stmt.setInt(15, carSetupData.FrontSuspensionHeight);
        stmt.setInt(16, carSetupData.RearSuspensionHeight);
        stmt.setInt(17, carSetupData.BrakePressure);
        stmt.setInt(18, carSetupData.BrakeBias);
        stmt.setFloat(19, carSetupData.RearLeftTyrePressure);
        stmt.setFloat(20, carSetupData.RearRightTyrePressure);
        stmt.setFloat(21, carSetupData.FrontLeftTyrePressure);
        stmt.setFloat(22, carSetupData.FrontRightTyrePressure);
        stmt.setInt(23, carSetupData.Ballast);
        stmt.setFloat(24, carSetupData.FuelLoad);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }

  
}
