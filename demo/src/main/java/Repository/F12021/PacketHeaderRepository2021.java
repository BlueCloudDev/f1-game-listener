package Repository.F12021;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.Instant;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Configuration.Configuration;
import F12021Packet.F12021PacketHeader;
import oracle.ucp.jdbc.PoolDataSource;

public class PacketHeaderRepository2021 {
  private static final Logger logger = LogManager.getLogger(PacketHeaderRepository2021.class);
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");
  public long InsertPacketHeader(F12021PacketHeader packetHeader, PoolDataSource dataSource) {
    long id = 0;
    try (Connection con = dataSource.getConnection()) {
      con.setAutoCommit(true);
      var path = Paths.get(SQL_FOLDER, "F12021");
      path = Paths.get(path.toString(), Configuration.EnvVars.get("SCHEMA_NAME"));
      path = Paths.get(path.toString(), "InsertPacketHeader2021.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      String returnCols[] = { "id" };
      try (PreparedStatement stmt = con.prepareStatement(query, returnCols)) {
        stmt.setInt(1, packetHeader.PacketFormat);
        stmt.setString(2, packetHeader.PlayerName);
        stmt.setInt(3, packetHeader.GameMajorVersion);
        stmt.setInt(4, packetHeader.GameMinorVersion);
        stmt.setInt(5, packetHeader.PacketVersion);
        stmt.setInt(6, packetHeader.PacketId);
        stmt.setString(7, packetHeader.SessionUID);
        stmt.setFloat(8, packetHeader.SessionTime);
        stmt.setLong(9, packetHeader.FrameIdentifier);
        stmt.setInt(10, packetHeader.PlayerCarIndex);
        stmt.setInt(11, packetHeader.SecondaryPlayerCarIndex);
        stmt.setTimestamp(12, Timestamp.from(Instant.now()));
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
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.warn(stackTrace);
    }
    return id;
  }
}
