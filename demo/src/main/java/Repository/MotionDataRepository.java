package Repository;

import java.io.File;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import F12020Packet.F12020CarMotionData;
import F12020Packet.F12020PacketMotionData;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class MotionDataRepository {

  public Long InsertMotionData(long packetHeaderID, F12020CarMotionData motionData, OracleDataSource dataSource) {
    long id = 0;
    try (OracleConnection con = (OracleConnection) dataSource.getConnection()) {
      con.setAutoCommit(true);
      File file = new File("/home/opc/f1-game-listener/demo/src/InsertCarMotionData.sql");
      String query = new String(Files.readAllBytes(file.toPath()));
      String returnCols[] = { "id" };
      try (PreparedStatement stmt = con.prepareStatement(query, returnCols)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setInt(2, motionData.Index);
        stmt.setFloat(3, motionData.WorldPositionX);
        stmt.setFloat(4, motionData.WorldPositionY);
        stmt.setFloat(5, motionData.WorldPositionZ);
        stmt.setFloat(6, motionData.WorldVelocityX);
        stmt.setFloat(7, motionData.WorldVelocityY);
        stmt.setFloat(8, motionData.WorldVelocityZ);
        stmt.setFloat(9, motionData.WorldForwardDirX);
        stmt.setFloat(10, motionData.WorldForwardDirY);
        stmt.setFloat(11, motionData.WorldForwardDirZ);
        stmt.setFloat(12, motionData.WorldRightDirX);
        stmt.setFloat(13, motionData.WorldRightDirY);
        stmt.setFloat(14, motionData.WorldRightDirZ);
        stmt.setFloat(15, motionData.GForceLateral);
        stmt.setFloat(16, motionData.GForceLongitudinal);
        stmt.setFloat(17, motionData.Yaw);
        stmt.setFloat(18, motionData.Pitch);
        stmt.setFloat(19, motionData.Roll);
        
        if (stmt.executeUpdate() > 0) {
          ResultSet generatedKeys = stmt.getGeneratedKeys();
          if (null != generatedKeys && generatedKeys.next()) {
              id = generatedKeys.getLong(1);
          }
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return id;
  }

  public void InsertMotionDataPlayer(long carMotionDataId, F12020PacketMotionData motionData, OracleDataSource dataSource) {
    long id = 0;
    try (OracleConnection con = (OracleConnection) dataSource.getConnection()) {
      con.setAutoCommit(true);
      File file = new File("/home/opc/f1-game-listener/demo/src/InsertCarMotionDataPlayer.sql");
      String query = new String(Files.readAllBytes(file.toPath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, carMotionDataId);
        stmt.setFloat(2, motionData.SuspensionPosition[0]);
        stmt.setFloat(3, motionData.SuspensionPosition[1]);
        stmt.setFloat(4, motionData.SuspensionPosition[2]);
        stmt.setFloat(5, motionData.SuspensionPosition[3]);
        stmt.setFloat(6, motionData.SuspensionVelocity[0]);
        stmt.setFloat(7, motionData.SuspensionVelocity[1]);
        stmt.setFloat(8, motionData.SuspensionVelocity[2]);
        stmt.setFloat(9, motionData.SuspensionVelocity[3]);
        stmt.setFloat(10, motionData.SuspensionAcceleration[0]);
        stmt.setFloat(11, motionData.SuspensionAcceleration[1]);
        stmt.setFloat(12, motionData.SuspensionAcceleration[2]);
        stmt.setFloat(13, motionData.SuspensionAcceleration[3]);
        stmt.setFloat(14, motionData.WheelSpeed[0]);
        stmt.setFloat(15, motionData.WheelSpeed[1]);
        stmt.setFloat(16, motionData.WheelSpeed[2]);
        stmt.setFloat(17, motionData.WheelSpeed[3]);
        stmt.setFloat(18, motionData.WheelSlip[0]);
        stmt.setFloat(19, motionData.WheelSlip[1]);
        stmt.setFloat(20, motionData.WheelSlip[2]);
        stmt.setFloat(21, motionData.WheelSlip[3]);
        stmt.setFloat(22, motionData.LocalVelocityX);
        stmt.setFloat(23, motionData.LocalVelocityY);
        stmt.setFloat(24, motionData.LocalVelocityZ);
        stmt.setFloat(25, motionData.AngularVelocityX);
        stmt.setFloat(26, motionData.AngularVelocityY);
        stmt.setFloat(27, motionData.AngularVelocityZ);
        stmt.setFloat(28, motionData.AngularAccelerationX);
        stmt.setFloat(29, motionData.AngularAccelerationY);
        stmt.setFloat(30, motionData.AngularAccelerationZ);
        stmt.setFloat(31, motionData.FrontWheelsAngle);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
