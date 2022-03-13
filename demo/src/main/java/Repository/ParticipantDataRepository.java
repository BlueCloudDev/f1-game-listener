package Repository;

import java.io.File;
import java.nio.file.Files;
import java.sql.PreparedStatement;

import Converter.GameDataConverter;
import F12020Packet.F12020ParticipantData;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class ParticipantDataRepository {
  private GameDataConverter gdc = new GameDataConverter();

  public void InsertParticipantData(long packetHeaderID, F12020ParticipantData participantData, OracleDataSource dataSource) {
    try (OracleConnection con = (OracleConnection) dataSource.getConnection()) {
      con.setAutoCommit(true);
      File file = new File("/home/opc/f1-game-listener/demo/src/InsertParticipantData.sql");
      String query = new String(Files.readAllBytes(file.toPath()));
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
      ex.printStackTrace();
    }
  }

  
}
