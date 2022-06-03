package Repository.F12021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import Configuration.Configuration;
import Converter.GameDataConverter2021;
import F12021Packet.F12021LapHistoryData;
import F12021Packet.F12021PacketSessionHistoryData;
import F12021Packet.F12021TyreStintHistoryData;
import oracle.ucp.jdbc.PoolDataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SessionHistoryDataRepository2021 {
  private static final Logger logger = LogManager.getLogger(SessionHistoryDataRepository2021.class);
  private GameDataConverter2021 gdc = new GameDataConverter2021();
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public void UpsertSessionData(String sessionUID, F12021PacketSessionHistoryData sessionData, PoolDataSource dataSource) {
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      var selectPath = Paths.get(path.toString(), "SelectSessionHistoryDataBySessionUID");
      String selectQuery = new String(Files.readAllBytes(selectPath.toAbsolutePath()));
      int historyID = 0;
      try (PreparedStatement selectStmt = con.prepareStatement(selectQuery)) {
        selectStmt.setString(1, sessionUID);
        ResultSet rs = selectStmt.executeQuery();
        if (rs.next()) {
          historyID = rs.getInt(1);
        }
      }
      
      if (historyID != 0) {
        var insertPath = Paths.get(path.toString(), "InsertSessionHistoryData2021.sql");
        String query = new String(Files.readAllBytes(insertPath.toAbsolutePath()));
        String returnCols[] = { "id" };
        try (PreparedStatement stmt = con.prepareStatement(query, returnCols)) {
          stmt.setString(1, sessionUID);
          stmt.setInt(2, sessionData.Index);
          stmt.setInt(3, sessionData.SessionLookupID);
          stmt.setInt(4, sessionData.BestLapTimeLapNum);
          stmt.setInt(5, sessionData.BestSector1LapNum);
          stmt.setInt(6, sessionData.BestSector2LapNum);
          stmt.setInt(7, sessionData.BestSector3LapNum);
          historyID = 0;
          if (stmt.executeUpdate() > 0) {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (null != generatedKeys && generatedKeys.next()) {
              historyID = generatedKeys.getInt(1);
            }
          }
        }
      } else {
        var updatePath = Paths.get(path.toString(), "InsertSessionHistoryData2021.sql");
        String updateQuery = new String(Files.readAllBytes(updatePath.toAbsolutePath()));
        try (PreparedStatement stmt = con.prepareStatement(updateQuery)) {
          stmt.setInt(1, sessionData.BestLapTimeLapNum);
          stmt.setInt(2, sessionData.BestSector1LapNum);
          stmt.setInt(3, sessionData.BestSector2LapNum);
          stmt.setInt(4, sessionData.BestSector3LapNum);
          stmt.setInt(5, historyID);
          stmt.executeUpdate();
        }
      }

      upsertLapHistoryData(sessionData, historyID, con);
      upsertTyreStintHistoryData(sessionData, historyID, con);
      
    } catch (SQLIntegrityConstraintViolationException ex) {
      return;
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }

  public void upsertLapHistoryData(F12021PacketSessionHistoryData sessionData, int historyID, Connection con) throws IOException, SQLException {
    //NOTE: It may be unnecessary to update Tyre Stint, not 100 percent sure
    if (historyID > 0 && sessionData.LapHistoryData.length > 0) {
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      var selectPath = Paths.get(path.toString(), "SelectLapHistoryData.sql");
      String selectQuery = new String(Files.readAllBytes(selectPath.toAbsolutePath()));
      for (int i = 0; i < sessionData.NumTyreStints; i++) {
        int lapHistoryID = 0;
        try (PreparedStatement selectStmt = con.prepareStatement(selectQuery)) {
          selectStmt.setInt(1, historyID);
          selectStmt.setInt(2, i);
          ResultSet rs = selectStmt.executeQuery();
          if (rs.next()) {
            lapHistoryID = rs.getInt(1);
          }
        }

        if (lapHistoryID == 0) {
          var insertPath = Paths.get(path.toString(), "InsertLapHistoryData2021.sql");
          String insertQuery = new String(Files.readAllBytes(insertPath.toAbsolutePath()));
          try (PreparedStatement stmt = con.prepareStatement(insertQuery)) {
            stmt.setLong(1, historyID);
            stmt.setLong(2, sessionData.LapHistoryData[i].LapTimeInMS);
            stmt.setLong(3, sessionData.LapHistoryData[i].Sector1TimeInMS);
            stmt.setLong(4, sessionData.LapHistoryData[i].Sector2TimeInMS);
            stmt.setLong(5, sessionData.LapHistoryData[i].Sector3TimeInMS);
            stmt.setInt(6, sessionData.LapHistoryData[i].LapValidBitFlags);
            stmt.setInt(7, i);
            stmt.execute();
          }
        } else {
          var updatePath = Paths.get(path.toString(), "UpdateLapHistoryData.sql");
          String updateQuery = new String(Files.readAllBytes(updatePath.toAbsolutePath())); 
          try (PreparedStatement stmt = con.prepareStatement(updateQuery)) {
            stmt.setLong(1, sessionData.LapHistoryData[i].LapTimeInMS);
            stmt.setLong(2, sessionData.LapHistoryData[i].Sector1TimeInMS);
            stmt.setLong(3, sessionData.LapHistoryData[i].Sector2TimeInMS);
            stmt.setLong(4, sessionData.LapHistoryData[i].Sector3TimeInMS);
            stmt.setInt(5, sessionData.LapHistoryData[i].LapValidBitFlags);
            stmt.executeUpdate();
          }
        }
      }
    }
  }

  public void upsertTyreStintHistoryData(F12021PacketSessionHistoryData sessionData, int historyID, Connection con) throws IOException, SQLException {
    //NOTE: It may be unnecessary to update Tyre Stint, not 100 percent sure
    if (historyID > 0 && sessionData.TyreStintsHistoryData.length > 0) {
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      var selectPath = Paths.get(path.toString(), "SelectTyreStintHistoryData.sql");
      String selectQuery = new String(Files.readAllBytes(selectPath.toAbsolutePath()));
      for (int i = 0; i < sessionData.NumTyreStints; i++) {
        int tyreStintID = 0;
        try (PreparedStatement selectStmt = con.prepareStatement(selectQuery)) {
          selectStmt.setInt(1, historyID);
          selectStmt.setInt(2, i);
          ResultSet rs = selectStmt.executeQuery();
          if (rs.next()) {
            tyreStintID = rs.getInt(1);
          }
        }

        if (tyreStintID == 0) {
          var insertPath = Paths.get(path.toString(), "InsertTyreStintHistoryData2021.sql");
          String insertQuery = new String(Files.readAllBytes(insertPath.toAbsolutePath()));
          try (PreparedStatement tsstmt = con.prepareStatement(insertQuery)) {
            tsstmt.setLong(1, historyID);
            tsstmt.setString(2, gdc.ActualTypeCompound(sessionData.TyreStintsHistoryData[i].TyreActualCompound));
            tsstmt.setString(3, gdc.VisualTyreCompound(sessionData.TyreStintsHistoryData[i].TyreVisualCompound));
            tsstmt.setInt(4, i);
            tsstmt.execute();
          }
        } else {
          var updatePath = Paths.get(path.toString(), "UpdateTyreStintHistoryData.sql");
          String updateQuery = new String(Files.readAllBytes(updatePath.toAbsolutePath())); 
          try (PreparedStatement tsstmt = con.prepareStatement(updateQuery)) {
            tsstmt.setString(1, gdc.ActualTypeCompound(sessionData.TyreStintsHistoryData[i].TyreActualCompound));
            tsstmt.setString(2, gdc.VisualTyreCompound(sessionData.TyreStintsHistoryData[i].TyreVisualCompound));
            tsstmt.setInt(3, tyreStintID);
            tsstmt.executeUpdate();
          }
        }
      }
    }
  }

  public F12021PacketSessionHistoryData SelectSessionHistoryData(String SessionUID, PoolDataSource dataSource) {
    F12021PacketSessionHistoryData result = new F12021PacketSessionHistoryData();
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(false);
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "SelectSessionHistoryDataBySessionUID2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setString(1, SessionUID);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
          if (result.Id == 0) {
            result.Id = rs.getInt(1);
            result.SessionUID = rs.getString(2);
            result.Index = rs.getInt(3);
            result.SessionLookupID = rs.getInt(4);
            result.BestLapTimeLapNum = rs.getInt(5);
            result.BestSector1LapNum = rs.getInt(6);
            result.BestSector2LapNum = rs.getInt(7);
            result.BestSector3LapNum = rs.getInt(8);
          }
          F12021LapHistoryData lap = new F12021LapHistoryData();
          lap.Id = rs.getInt(9);
          lap.LapNumber = rs.getInt(10);
          lap.LapTimeInMS = rs.getLong(11);
          lap.Sector1TimeInMS = rs.getInt(12);
          lap.Sector2TimeInMS = rs.getInt(13);
          lap.Sector3TimeInMS = rs.getInt(14);
          lap.LapValidBitFlags = rs.getInt(15);
          result.LapHistoryData[lap.LapNumber] = lap;
          F12021TyreStintHistoryData stint = new F12021TyreStintHistoryData();
          stint.Id = rs.getInt(16);
          stint.SessionHistoryId = rs.getInt(17);
          stint.TyreActualCompoundString = rs.getString(18);
          stint.TyreVisualCompoundString = rs.getString(19);
          stint.StintNumber = rs.getInt(20);
          result.TyreStintsHistoryData[stint.StintNumber] = stint;
        }
      }
    } catch (Exception ex ) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
    return result;
  }
}
