package Repository;

import java.io.File;
import java.nio.file.Files;
import java.sql.PreparedStatement;

import F12020Packet.F12020CarSetupData;
import F12020Packet.F12020CarTelemetryData;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class CarTelemetryRepository {

  public void InsertCarTelemetryData(long packetHeaderID, F12020CarTelemetryData telemetryData, OracleDataSource dataSource) {
    try (OracleConnection con = (OracleConnection) dataSource.getConnection()) {
      con.setAutoCommit(true);
      File file = new File("/home/opc/f1-game-listener/demo/src/InsertCarTelemetryData.sql");
      String query = new String(Files.readAllBytes(file.toPath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setInt(2,  telemetryData.Speed);
        stmt.setFloat(3, telemetryData.Throttle);
        stmt.setFloat(4, telemetryData.Steer);
        stmt.setFloat(5, telemetryData.Brake);
        stmt.setInt(6, telemetryData.Clutch);
        stmt.setInt(7, telemetryData.Gear);
        stmt.setInt(8, telemetryData.EngineRPM);
        stmt.setInt(9, telemetryData.DRS);
        stmt.setInt(10, telemetryData.RevLightsPercent);
        stmt.setInt(11, telemetryData.BrakesTemperature[0]);
        stmt.setInt(12, telemetryData.BrakesTemperature[1]);
        stmt.setInt(13, telemetryData.BrakesTemperature[2]);
        stmt.setInt(14, telemetryData.BrakesTemperature[3]);
        stmt.setInt(15, telemetryData.TyresSurfaceTemperature[0]);
        stmt.setInt(16, telemetryData.TyresSurfaceTemperature[1]);
        stmt.setInt(17, telemetryData.TyresSurfaceTemperature[2]);
        stmt.setInt(18, telemetryData.TyresSurfaceTemperature[3]);
        stmt.setInt(19, telemetryData.TyresInnerTemperature[0]);
        stmt.setInt(20, telemetryData.TyresInnerTemperature[1]);
        stmt.setInt(21, telemetryData.TyresInnerTemperature[2]);
        stmt.setInt(22, telemetryData.TyresInnerTemperature[3]);
        stmt.setInt(23, telemetryData.EngineTemperature);
        stmt.setFloat(24, telemetryData.TyresPressure[0]);
        stmt.setFloat(25, telemetryData.TyresPressure[1]);
        stmt.setFloat(26, telemetryData.TyresPressure[2]);
        stmt.setFloat(27, telemetryData.TyresPressure[3]);
        stmt.setInt(28, telemetryData.SurfaceType[0]);
        stmt.setInt(29, telemetryData.SurfaceType[1]);
        stmt.setInt(30, telemetryData.SurfaceType[2]);
        stmt.setInt(31, telemetryData.SurfaceType[3]);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  
}
