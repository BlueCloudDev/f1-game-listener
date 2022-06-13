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
import F12021Packet.F12021TrackCorner;
import oracle.ucp.jdbc.PoolDataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrackCornerRepository {
  private static final Logger logger = LogManager.getLogger(TrackCornerRepository.class);
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");

  public ArrayList<F12021TrackCorner> SelectCorners(String TrackIdString, PoolDataSource dataSource) {
    ArrayList<F12021TrackCorner> results = new ArrayList<F12021TrackCorner>();
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(false);
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "SelectTrackCornerByTrackId.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setString(1, TrackIdString);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
          F12021TrackCorner result = new F12021TrackCorner();
          result.Id = rs.getInt(1);
          result.TrackIdString = rs.getString(2);
          result.Corner = rs.getInt(3);
          result.Apex1 = rs.getDouble(4);
          result.Apex2 = rs.getDouble(5);
          result.Apex3 = rs.getDouble(6);
          result.X = rs.getDouble(7);
          result.Y = rs.getDouble(8);

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
