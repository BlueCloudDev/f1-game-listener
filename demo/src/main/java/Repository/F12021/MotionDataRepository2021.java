package Repository.F12021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Configuration.Configuration;
import F12021Packet.F12021CarMotionData;
import F12021Packet.F12021PacketMotionData;
import oracle.ucp.jdbc.PoolDataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MotionDataRepository2021 {
  private static final Logger logger = LogManager.getLogger(MotionDataRepository2021.class);
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public Long InsertMotionData(long packetHeaderID, F12021CarMotionData motionData, PoolDataSource dataSource) {
    long id = 0;
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "F12021/InsertCarMotionData2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      String returnCols[] = { "id" };
      try (PreparedStatement stmt = con.prepareStatement(query, returnCols)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setInt(2, motionData.Index);
        stmt.setInt(3, motionData.SessionLookupID);
        stmt.setFloat(4, motionData.WorldPositionX);
        stmt.setFloat(5, motionData.WorldPositionY);
        stmt.setFloat(6, motionData.WorldPositionZ);
        stmt.setFloat(7, motionData.WorldVelocityX);
        stmt.setFloat(8, motionData.WorldVelocityY);
        stmt.setFloat(9, motionData.WorldVelocityZ);
        stmt.setFloat(10, motionData.WorldForwardDirX);
        stmt.setFloat(11, motionData.WorldForwardDirY);
        stmt.setFloat(12, motionData.WorldForwardDirZ);
        stmt.setFloat(13, motionData.WorldRightDirX);
        stmt.setFloat(14, motionData.WorldRightDirY);
        stmt.setFloat(15, motionData.WorldRightDirZ);
        stmt.setFloat(16, motionData.GForceLateral);
        stmt.setFloat(17, motionData.GForceLongitudinal);
        stmt.setFloat(18, motionData.Yaw);
        stmt.setFloat(19, motionData.Pitch);
        stmt.setFloat(20, motionData.Roll);
        
        if (stmt.executeUpdate() > 0) {
          ResultSet generatedKeys = stmt.getGeneratedKeys();
          if (null != generatedKeys && generatedKeys.next()) {
              id = generatedKeys.getLong(1);
          }
        }
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.fatal(stackTrace);
    }
    return id;
  }

  public void InsertMotionDataPlayer(long carMotionDataId, F12021PacketMotionData motionData, PoolDataSource dataSource) {
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "F12021/InsertCarMotionDataPlayer2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
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
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }
}
