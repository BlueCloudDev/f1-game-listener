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
import F12021Packet.F12021LapHistoryData;
import F12021Packet.F12021PacketSessionHistoryData;
import F12021Packet.F12021SessionLookup;
import F12021Packet.F12021TyreStintHistoryData;
import oracle.ucp.jdbc.PoolDataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SessionLookupRepository2021 {
  private static final Logger logger = LogManager.getLogger(SessionLookupRepository2021.class);
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public int InsertSessionLookup(F12021SessionLookup lookup, PoolDataSource dataSource) {
    int id = 0;
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "InsertSessionLookup2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      String returnCols[] = { "id" };
      try (PreparedStatement stmt = con.prepareStatement(query, returnCols)) {
        stmt.setString(1, lookup.PlayerName);
        stmt.setString(2, lookup.SessionUID);
        stmt.setLong(3, lookup.FrameIdentifier);
        stmt.setFloat(4, lookup.SessionTime);
        stmt.setTimestamp(5, Timestamp.from(Instant.now()));
        stmt.setInt(6, lookup.PlayerCarIndex);
        if (lookup.EventId == 0) {
          lookup.EventId = 1;
        }
        stmt.setInt(7, lookup.EventId);
        if (stmt.executeUpdate() > 0) {
          ResultSet generatedKeys = stmt.getGeneratedKeys();
          if (null != generatedKeys && generatedKeys.next()) {
            id = generatedKeys.getInt(1);
          }
        }
      }
    } catch (SQLIntegrityConstraintViolationException ex) {
      id = SelectSessionLookupIDBySessionUIDAndFrameIdentifier(lookup.SessionUID, lookup.FrameIdentifier, dataSource);
      return id;
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
    return id;
  }

  public int SelectSessionLookupIDBySessionUID(String sessionUID, PoolDataSource dataSource) {
    int id = 0;
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(false);
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "SelectSessionLookupIDBySessionUID2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setString(1, sessionUID);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
          id = rs.getInt(1);
        }
      }
    } catch (Exception ex ) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
    return id;
  }

  public ArrayList<F12021SessionLookup> SelectPlayerSessions(PoolDataSource dataSource) {
    ArrayList<F12021SessionLookup> results = new ArrayList<F12021SessionLookup>();
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(false);
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "SelectPlayerSessions2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
          F12021SessionLookup result = new F12021SessionLookup();
          result.PlayerName = rs.getString(1);
          result.PlayerCarIndex = rs.getInt(2);
          result.SessionUID = rs.getString(3);
          result.CreatedOn = rs.getTimestamp(4);
          results.add(result);
        }
      }
    } catch (Exception ex ) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
    return results;
  }



  public int SelectSessionLookupIDBySessionUIDAndFrameIdentifier(String sessionUID, long frameIdentifier, PoolDataSource dataSource) {
    int id = 0;
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(false);
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "SelectSessionLookupIDBySessionUIDandFrameIdentifier2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setString(1, sessionUID);
        stmt.setLong(2, frameIdentifier);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
          id = rs.getInt(1);
        }
      }
    } catch (Exception ex ) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
    return id;
  }
}
