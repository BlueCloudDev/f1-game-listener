package Repository.F12021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Configuration.Configuration;
import F12021Packet.F12021Event;
import oracle.ucp.jdbc.PoolDataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventRepository2021 {
  private static final Logger logger = LogManager.getLogger(EventRepository2021.class);
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");

  public void InsertEvent(F12021Event event, PoolDataSource dataSource) {
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "InsertEvent.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setString(1, event.Name);
        stmt.setTimestamp(2, event.StartDate);
        stmt.setTimestamp(3, event.EndDate);  
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }

  public ArrayList<F12021Event> SelectEvents(PoolDataSource dataSource) {
    ArrayList<F12021Event> results = new ArrayList<F12021Event>();
    try (Connection con = dataSource.getConnection()) {
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "SelectEvents.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (Statement stmt = con.createStatement()) {
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
          var result = new F12021Event();
          result.Id = rs.getInt(1);
          result.Name = rs.getString(2);
          result.StartDate = rs.getTimestamp(3);
          result.EndDate = rs.getTimestamp(4);
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
