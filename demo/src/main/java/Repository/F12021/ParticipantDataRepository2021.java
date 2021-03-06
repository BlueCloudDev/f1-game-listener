package Repository.F12021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Configuration.Configuration;
import Converter.GameDataConverter2021;
import F12021Packet.F12021ParticipantData;
import oracle.ucp.jdbc.PoolDataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParticipantDataRepository2021 {
  private static final Logger logger = LogManager.getLogger(ParticipantDataRepository2021.class);
  private GameDataConverter2021 gdc = new GameDataConverter2021();
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public void InsertParticipantData(long packetHeaderID, F12021ParticipantData participantData, PoolDataSource dataSource, int numActiveCars) {
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "InsertParticipantData2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setInt(2, participantData.Index);
        stmt.setInt(3, participantData.SessionLookupID);
        stmt.setInt(4, participantData.AiControlled);
        stmt.setString(5, gdc.DriverId(participantData.DriverId));
        stmt.setInt(6, participantData.NetworkId);
        stmt.setString(7, gdc.Team(participantData.TeamId));
        stmt.setInt(8, participantData.RaceNumber);
        stmt.setString(9, gdc.Nationality(participantData.Nationality));
        stmt.setString(10, participantData.Name);
        stmt.setInt(11, numActiveCars);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }

  public int SelectNumActiveCarsBySessionUID(String sessionUID, PoolDataSource dataSource) {
    int numActiveCars = 0;
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(false);
      var path = Paths.get(SQL_FOLDER, "F12021/SelectNumActiveCarsBySessionUID.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setString(1, sessionUID);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
          numActiveCars = rs.getInt(1);
        }
      }
    } catch (Exception ex ) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
    return numActiveCars;
  }
}
