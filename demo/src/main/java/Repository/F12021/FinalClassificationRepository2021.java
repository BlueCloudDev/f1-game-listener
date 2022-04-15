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
      var path = Paths.get(SQL_FOLDER, "F12021/InsertFinalClassificationData2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, packetHeaderId);
        stmt.setInt(2, finalClassification.SessionLookupID);
        stmt.setFloat(3, finalClassification.SessionTime);
        stmt.setLong(4, finalClassification.FrameIdentifier);
        stmt.setInt(5, finalClassification.Position);
        stmt.setInt(6, finalClassification.NumLaps);
        stmt.setInt(7, finalClassification.GridPosition);
        stmt.setInt(8, finalClassification.Points);
        stmt.setInt(9, finalClassification.NumPitStops);
        stmt.setString(10, gdc.ResultStatus(finalClassification.ResultStatus));
        stmt.setLong(11, finalClassification.BestLapTimeInMS);
        stmt.setDouble(12, finalClassification.TotalRaceTime);
        stmt.setLong(13, finalClassification.PenaltiesTime);
        stmt.setInt(14, finalClassification.NumTyreStints);
        stmt.setInt(15, finalClassification.TyreStintsActual[0]);
        stmt.setInt(16, finalClassification.TyreStintsActual[1]); 
        stmt.setInt(17, finalClassification.TyreStintsActual[2]); 
        stmt.setInt(18, finalClassification.TyreStintsActual[3]);
        stmt.setInt(19, finalClassification.TyreStintsActual[4]);
        stmt.setInt(20, finalClassification.TyreStintsActual[5]);
        stmt.setInt(21, finalClassification.TyreStintsActual[6]);
        stmt.setInt(22, finalClassification.TyreStintsActual[7]);
        stmt.setInt(23, finalClassification.TyreStintsVisual[0]);
        stmt.setInt(24, finalClassification.TyreStintsVisual[1]);
        stmt.setInt(25, finalClassification.TyreStintsVisual[2]);
        stmt.setInt(26, finalClassification.TyreStintsVisual[3]);
        stmt.setInt(27, finalClassification.TyreStintsVisual[4]);
        stmt.setInt(28, finalClassification.TyreStintsVisual[5]);
        stmt.setInt(29, finalClassification.TyreStintsVisual[6]);
        stmt.setInt(30, finalClassification.TyreStintsVisual[7]);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }
}
