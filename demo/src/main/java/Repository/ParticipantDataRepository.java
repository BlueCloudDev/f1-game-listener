package Repository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;

import Configuration.Configuration;
import Converter.GameDataConverter;
import F12020Packet.F12020ParticipantData;
import oracle.ucp.jdbc.PoolDataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParticipantDataRepository {
  private static final Logger logger = LogManager.getLogger(ParticipantDataRepository.class);
  private GameDataConverter gdc = new GameDataConverter();
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public void InsertParticipantData(long packetHeaderID, F12020ParticipantData participantData, PoolDataSource dataSource) {
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "InsertParticipantData.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setInt(2, participantData.Index);
        stmt.setInt(3, participantData.AiControlled);
        stmt.setInt(4, participantData.DriverId);
        stmt.setString(5, gdc.Team(participantData.TeamId));
        stmt.setInt(6, participantData.RaceNumber);
        stmt.setString(7, gdc.Nationality(participantData.Nationality));
        stmt.setString(8, participantData.Name);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }

  
}
