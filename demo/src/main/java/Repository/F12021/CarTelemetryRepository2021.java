package Repository.F12021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;

import Configuration.Configuration;
import F12021Packet.F12021CarTelemetryData;
import oracle.ucp.jdbc.PoolDataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarTelemetryRepository2021 {
  private static final Logger logger = LogManager.getLogger(CarTelemetryRepository2021.class);
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public void InsertCarTelemetryData(long packetHeaderID, F12021CarTelemetryData telemetryData, PoolDataSource dataSource) {
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "F12021/InsertCarTelemetryData2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setInt(2, telemetryData.Index);
        stmt.setInt(3, telemetryData.SessionLookupID);
        stmt.setInt(4,  telemetryData.Speed);
        stmt.setFloat(5, telemetryData.Throttle);
        stmt.setFloat(6, telemetryData.Steer);
        stmt.setFloat(7, telemetryData.Brake);
        stmt.setInt(8, telemetryData.Clutch);
        stmt.setInt(9, telemetryData.Gear);
        stmt.setInt(10, telemetryData.EngineRPM);
        stmt.setInt(11, telemetryData.DRS);
        stmt.setInt(12, telemetryData.RevLightsPercent);
        stmt.setInt(13, telemetryData.RevLightsBitValue);
        stmt.setInt(14, telemetryData.BrakesTemperature[0]);
        stmt.setInt(15, telemetryData.BrakesTemperature[1]);
        stmt.setInt(16, telemetryData.BrakesTemperature[2]);
        stmt.setInt(17, telemetryData.BrakesTemperature[3]);
        stmt.setInt(18, telemetryData.TyresSurfaceTemperature[0]);
        stmt.setInt(19, telemetryData.TyresSurfaceTemperature[1]);
        stmt.setInt(20, telemetryData.TyresSurfaceTemperature[2]);
        stmt.setInt(21, telemetryData.TyresSurfaceTemperature[3]);
        stmt.setInt(22, telemetryData.TyresInnerTemperature[0]);
        stmt.setInt(23, telemetryData.TyresInnerTemperature[1]);
        stmt.setInt(24, telemetryData.TyresInnerTemperature[2]);
        stmt.setInt(25, telemetryData.TyresInnerTemperature[3]);
        stmt.setInt(26, telemetryData.EngineTemperature);
        stmt.setFloat(27, telemetryData.TyresPressure[0]);
        stmt.setFloat(28, telemetryData.TyresPressure[1]);
        stmt.setFloat(29, telemetryData.TyresPressure[2]);
        stmt.setFloat(30, telemetryData.TyresPressure[3]);
        stmt.setInt(31, telemetryData.SurfaceType[0]);
        stmt.setInt(32, telemetryData.SurfaceType[1]);
        stmt.setInt(33, telemetryData.SurfaceType[2]);
        stmt.setInt(34, telemetryData.SurfaceType[3]);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }

  
}
