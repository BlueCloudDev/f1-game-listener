package Repository.F12021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Configuration.Configuration;
import F12021Packet.Response.LeaderboardResponse2021;
import oracle.ucp.jdbc.PoolDataSource;

public class LeaderboardRepository2021 {
  private static final Logger logger = LogManager.getLogger(LeaderboardRepository2021.class);
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public ArrayList<LeaderboardResponse2021> SelectLeaderboard(int eventId, PoolDataSource dataSource) {
    ArrayList<LeaderboardResponse2021> results = new ArrayList<LeaderboardResponse2021>();
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "SelectLeaderboardData.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setInt(1, eventId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
          var result = new LeaderboardResponse2021();
          result.PlayerName = rs.getString(1);
          result.CreatedOn = rs.getTimestamp(2);
          result.TrackIdString = rs.getString(3);
          result.LapNumber = rs.getInt(4);
          result.LapTimeInMS = rs.getLong(5);
          result.Sector1TimeInMS = rs.getInt(6);
          result.Sector2TimeInMS = rs.getInt(7);
          result.Sector3TimeInMS = rs.getInt(8);
          result.LapValidBitFlags = rs.getInt(9);
          results.add(result);
        }
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
    return results;
  }
}