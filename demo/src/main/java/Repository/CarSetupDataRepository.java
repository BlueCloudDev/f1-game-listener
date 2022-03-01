package Repository;

import java.io.File;
import java.nio.file.Files;
import java.sql.PreparedStatement;

import F12020Packet.F12020CarSetupData;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class CarSetupDataRepository {

  public void InsertCarSetupData(long packetHeaderID, F12020CarSetupData carSetupData, OracleDataSource dataSource) {
    try (OracleConnection con = (OracleConnection) dataSource.getConnection()) {
      con.setAutoCommit(true);
      File file = new File("/home/opc/f1-game-listener/demo/src/InsertCarSetupData.sql");
      String query = new String(Files.readAllBytes(file.toPath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setInt(2, carSetupData.FrontWing);
        stmt.setInt(3, carSetupData.RearWing);
        stmt.setInt(4, carSetupData.OnThrottle);
        stmt.setInt(5, carSetupData.OffThrottle);
        stmt.setFloat(6, carSetupData.FrontCamber);
        stmt.setFloat(7, carSetupData.RearCamber);
        stmt.setFloat(8, carSetupData.FrontToe);
        stmt.setFloat(9, carSetupData.RearToe);
        stmt.setInt(10, carSetupData.FrontSuspension);
        stmt.setInt(11, carSetupData.RearSuspension);
        stmt.setInt(12, carSetupData.FrontAntiRollBar);
        stmt.setInt(13, carSetupData.RearAntiRollBar);
        stmt.setInt(14, carSetupData.FrontSuspensionHeight);
        stmt.setInt(15, carSetupData.RearSuspensionHeight);
        stmt.setInt(16, carSetupData.BrakePressure);
        stmt.setInt(17, carSetupData.BrakeBias);
        stmt.setFloat(18, carSetupData.RearLeftTyrePressure);
        stmt.setFloat(19, carSetupData.RearRightTyrePressure);
        stmt.setFloat(20, carSetupData.FrontLeftTyrePressure);
        stmt.setFloat(21, carSetupData.FrontRightTyrePressure);
        stmt.setInt(22, carSetupData.Ballast);
        stmt.setFloat(23, carSetupData.FuelLoad);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  
}
