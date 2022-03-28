package Repository.F12021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Configuration.Configuration;
import Converter.GameDataConverter2021;
import F12021Packet.F12021FinalClassificationData;
import oracle.ucp.jdbc.PoolDataSource;

public class FinalClassificationRepository2021 {
  private static final Logger logger = LogManager.getLogger(FinalClassificationRepository2021.class);
  private GameDataConverter2021 gdc = new GameDataConverter2021();
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public void InsertFinalClassification(long packetHeaderId, F12021FinalClassificationData finalClassification, PoolDataSource dataSource) {
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "F12021/InsertFinalClassification2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, packetHeaderId);
        stmt.setInt(2, finalClassification.Position);
        stmt.setInt(3, finalClassification.NumLaps);
        stmt.setInt(4, finalClassification.GridPosition);
        stmt.setInt(5, finalClassification.Points);
        stmt.setInt(6, finalClassification.NumPitStops);
        stmt.setString(7, gdc.ResultStatus(finalClassification.ResultStatus));
        stmt.setLong(8, finalClassification.BestLapTimeInMS);
        stmt.setDouble(9, finalClassification.TotalRaceTime);
        stmt.setLong(10, finalClassification.PenaltiesTime);
        stmt.setInt(11, finalClassification.NumTyreStints);
        stmt.setInt(12, finalClassification.TyreStintsActual[0]);
        stmt.setInt(13, finalClassification.TyreStintsActual[1]); 
        stmt.setInt(14, finalClassification.TyreStintsActual[2]); 
        stmt.setInt(15, finalClassification.TyreStintsActual[3]);
        stmt.setInt(16, finalClassification.TyreStintsActual[4]);
        stmt.setInt(17, finalClassification.TyreStintsActual[5]);
        stmt.setInt(18, finalClassification.TyreStintsActual[6]);
        stmt.setInt(19, finalClassification.TyreStintsActual[7]);
        stmt.setInt(20, finalClassification.TyreStintsVisual[0]);
        stmt.setInt(21, finalClassification.TyreStintsVisual[1]);
        stmt.setInt(22, finalClassification.TyreStintsVisual[2]);
        stmt.setInt(23, finalClassification.TyreStintsVisual[3]);
        stmt.setInt(24, finalClassification.TyreStintsVisual[4]);
        stmt.setInt(25, finalClassification.TyreStintsVisual[5]);
        stmt.setInt(26, finalClassification.TyreStintsVisual[6]);
        stmt.setInt(27, finalClassification.TyreStintsVisual[7]);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }
}
