package Repository;

import java.io.File;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import F12020Packet.F12020PacketHeader;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class PacketHeaderRepository {
  public long InsertPacketHeader(F12020PacketHeader packetHeader, OracleDataSource dataSource) {
    long id = 0;
    try (OracleConnection con = (OracleConnection) dataSource.getConnection()) {
      con.setAutoCommit(true);
      File file = new File("/home/opc/f1-game-listener/demo/src/InsertPacketHeader.sql");
      String query = new String(Files.readAllBytes(file.toPath()));
      String returnCols[] = { "id" };
      try (PreparedStatement stmt = con.prepareStatement(query, returnCols)) {
        stmt.setInt(1, packetHeader.PacketFormat);
        stmt.setInt(2, packetHeader.GameMajorVersion);
        stmt.setInt(3, packetHeader.GameMinorVersion);
        stmt.setInt(4, packetHeader.PacketVersion);
        stmt.setInt(5, packetHeader.PacketId);
        stmt.setLong(6, packetHeader.SessionUID);
        stmt.setFloat(7, packetHeader.SessionTime);
        stmt.setLong(8, packetHeader.FrameIdentifier);
        stmt.setInt(9, packetHeader.PlayerCarIndex);
        stmt.setInt(10, packetHeader.SecondaryPlayerCarIndex);
        if (stmt.executeUpdate() > 0) {
          ResultSet generatedKeys = stmt.getGeneratedKeys();
          if (null != generatedKeys && generatedKeys.next()) {
              id = generatedKeys.getLong(1);
          }
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return id;
  }
}
