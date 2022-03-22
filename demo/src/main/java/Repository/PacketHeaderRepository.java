package Repository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Configuration.Configuration;
import F12020Packet.F12020PacketHeader;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class PacketHeaderRepository {
  private static final Logger logger = LogManager.getLogger(PacketHeaderRepository.class);
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public long InsertPacketHeader(F12020PacketHeader packetHeader, OracleDataSource dataSource) {
    long id = 0;
    try (OracleConnection con = (OracleConnection) dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "InsertPacketHeader.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      String returnCols[] = { "id" };
      try (PreparedStatement stmt = con.prepareStatement(query, returnCols)) {
        stmt.setInt(1, packetHeader.PacketFormat);
        stmt.setString(2, packetHeader.PlayerName);
        stmt.setInt(3, packetHeader.GameMajorVersion);
        stmt.setInt(4, packetHeader.GameMinorVersion);
        stmt.setInt(5, packetHeader.PacketVersion);
        stmt.setInt(6, packetHeader.PacketId);
        stmt.setLong(7, packetHeader.SessionUID);
        stmt.setFloat(8, packetHeader.SessionTime);
        stmt.setLong(9, packetHeader.FrameIdentifier);
        stmt.setInt(10, packetHeader.PlayerCarIndex);
        stmt.setInt(11, packetHeader.SecondaryPlayerCarIndex);
        if (stmt.executeUpdate() > 0) {
          ResultSet generatedKeys = stmt.getGeneratedKeys();
          if (null != generatedKeys && generatedKeys.next()) {
              id = generatedKeys.getLong(1);
          }
        }
      }
    } catch (SQLIntegrityConstraintViolationException ex) {
      return id;
    } 
    catch (Exception ex) {
      logger.warn(ex.getMessage());
    }
    return id;
  }
}
