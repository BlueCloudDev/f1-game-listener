package Repository.F12021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
  public void InsertSessionData(String sessionUID, F12021PacketSessionHistoryData sessionData, PoolDataSource dataSource) {
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "F12021/InsertSessionHistoryData2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      String returnCols[] = { "id" };
      try (PreparedStatement stmt = con.prepareStatement(query, returnCols)) {
        stmt.setString(1, sessionUID);
        stmt.setInt(2, sessionData.Index);
        stmt.setInt(3, sessionData.BestLapTimeLapNum);
        stmt.setInt(4, sessionData.BestSector1LapNum);
        stmt.setInt(5, sessionData.BestSector2LapNum);
        stmt.setInt(6, sessionData.BestSector3LapNum);
        long id = 0;
        if (stmt.executeUpdate() > 0) {
          ResultSet generatedKeys = stmt.getGeneratedKeys();
          if (null != generatedKeys && generatedKeys.next()) {
              id = generatedKeys.getLong(1);
          }
        }

        if (id > 0 && sessionData.TyreStintsHistoryData.length > 0) {
          var path2 = Paths.get(SQL_FOLDER, "F12021/InsertTyreStintHistoryData2021.sql");
          String query2 = new String(Files.readAllBytes(path2.toAbsolutePath()));
          for (F12021TyreStintHistoryData ts : sessionData.TyreStintsHistoryData) {
            if (ts != null) {
              try (PreparedStatement tsstmt = con.prepareStatement(query2)) {
                tsstmt.setLong(1, id);
                tsstmt.setString(2, gdc.ActualTypeCompound(ts.TyreActualCompound));
                tsstmt.setString(3, gdc.VisualTyreCompound(ts.TyreVisualCompound));
                tsstmt.execute();
              }
            }
          }
        }

        if (id > 0 && sessionData.LapHistoryData.length > 0) {
          var path3 = Paths.get(SQL_FOLDER, "F12021/InsertLapHistoryData2021.sql");
          String query3 = new String(Files.readAllBytes(path3.toAbsolutePath()));
          for (F12021LapHistoryData lh : sessionData.LapHistoryData) {
            if (lh != null) {
              try (PreparedStatement lhstmt = con.prepareStatement(query3)) {
                lhstmt.setLong(1, id);
                lhstmt.setLong(2, lh.LapTimeInMS);
                lhstmt.setLong(3, lh.Sector1TimeInMS);
                lhstmt.setLong(4, lh.Sector2TimeInMS);
                lhstmt.setLong(5, lh.Sector3TimeInMS);
                lhstmt.setInt(6, lh.LapValidBitFlags);
                lhstmt.execute();
              }
            }
          }
        }
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }
}
