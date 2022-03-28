package Repository.F12021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;

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
  public void InsertParticipantData(long packetHeaderID, F12021ParticipantData participantData, PoolDataSource dataSource) {
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "F12021/InsertParticipantData2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, packetHeaderID);
        stmt.setInt(2, participantData.Index);
        stmt.setInt(3, participantData.AiControlled);
        stmt.setString(4, gdc.DriverId(participantData.DriverId));
        stmt.setInt(5, participantData.NetworkId);
        stmt.setString(6, gdc.Team(participantData.TeamId));
        stmt.setInt(7, participantData.RaceNumber);
        stmt.setString(8, gdc.Nationality(participantData.Nationality));
        stmt.setString(9, participantData.Name);
        stmt.executeUpdate();
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
  }

  
}
