package Repository.F12021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

import Configuration.Configuration;
import F12021Packet.F12021TrackCoordinates;
import oracle.ucp.jdbc.PoolDataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrackCoordinatesRepository {
  private static final Logger logger = LogManager.getLogger(TrackCoordinatesRepository.class);
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");

  public ArrayList<F12021TrackCoordinates> SelectPlayerSessions(String TrackIdString, PoolDataSource dataSource) {
    ArrayList<F12021TrackCoordinates> results = new ArrayList<F12021TrackCoordinates>();
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(false);
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "SelectPlayerSessions2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setString(1, TrackIdString);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
          F12021TrackCoordinates result = new F12021TrackCoordinates();
          result.Id = rs.getInt(1);
          result.TrackIdString = rs.getString(2);
          result.TrackType = rs.getString(3);
          result.TrackVolumeId = rs.getInt(4);
          result.Point2X = rs.getInt(5);
          result.Point2Y = rs.getInt(6);
          result.Point2Z = rs.getInt(7);
          result.Point3X = rs.getInt(8);
          result.Point3Y = rs.getInt(9);
          result.Point3Z = rs.getInt(10);

          results.add(result);
        }
      }
    } catch (Exception ex ) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
    return results;
  }

}
