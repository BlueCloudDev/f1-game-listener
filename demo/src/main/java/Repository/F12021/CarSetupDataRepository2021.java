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
      var path = Paths.get(SQL_FOLDER, "F12021/InsertCarSetupData2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setInt(2, carSetupData.Index);
        stmt.setInt(3, carSetupData.SessionLookupID);
        stmt.setFloat(4, carSetupData.SessionTime);
        stmt.setLong(5, carSetupData.FrameIdentifier);
        stmt.setInt(6, carSetupData.FrontWing);
        stmt.setInt(7, carSetupData.RearWing);
        stmt.setInt(8, carSetupData.OnThrottle);
        stmt.setInt(9, carSetupData.OffThrottle);
        stmt.setFloat(10, carSetupData.FrontCamber);
        stmt.setFloat(11, carSetupData.RearCamber);
        stmt.setFloat(12, carSetupData.FrontToe);
        stmt.setFloat(13, carSetupData.RearToe);
        stmt.setInt(14, carSetupData.FrontSuspension);
        stmt.setInt(15, carSetupData.RearSuspension);
        stmt.setInt(16, carSetupData.FrontAntiRollBar);
        stmt.setInt(17, carSetupData.RearAntiRollBar);
        stmt.setInt(18, carSetupData.FrontSuspensionHeight);
        stmt.setInt(19, carSetupData.RearSuspensionHeight);
        stmt.setInt(20, carSetupData.BrakePressure);
        stmt.setInt(21, carSetupData.BrakeBias);
        stmt.setFloat(22, carSetupData.RearLeftTyrePressure);
        stmt.setFloat(23, carSetupData.RearRightTyrePressure);
        stmt.setFloat(24, carSetupData.FrontLeftTyrePressure);
        stmt.setFloat(25, carSetupData.FrontRightTyrePressure);
        stmt.setInt(26, carSetupData.Ballast);
        stmt.setFloat(27, carSetupData.FuelLoad);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }

  
}
