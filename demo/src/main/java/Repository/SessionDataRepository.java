package Repository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Converter.GameDataConverter;
import F12020Packet.F12020MarshalZone;
import F12020Packet.F12020PacketSessionData;
import F12020Packet.F12020WeatherForecastSample;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class SessionDataRepository {
  //private OracleDataSource dataSource = null;
  private GameDataConverter gdc = new GameDataConverter();

  public SessionDataRepository() throws SQLException {
    super();
    //dataSource = ods;
  }

  public void InsertSessionData(long sessionUID, F12020PacketSessionData sessionData, OracleDataSource dataSource) {
    try (OracleConnection con = (OracleConnection) dataSource.getConnection()) {
      con.setAutoCommit(true);
      File file = new File("/home/opc/f1-game-listener/demo/src/InsertSessionData.sql");
      String query = new String(Files.readAllBytes(file.toPath()));
      try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setLong(1, sessionUID);
        stmt.setString(2, gdc.Weather(sessionData.Weather));
        stmt.setInt(3, sessionData.TrackTemperature);
        stmt.setInt(4, sessionData.AirTemperature);
        stmt.setInt(5, sessionData.TotalLaps);
        stmt.setFloat(6, sessionData.TrackLength);
        stmt.setString(7, gdc.SessionType(sessionData.SessionType));
        stmt.setString(8, gdc.TrackIDs(sessionData.TrackID));
        stmt.setString(9, gdc.Formula(sessionData.Formula));
        stmt.setFloat(10, sessionData.SessionTimeLeft);
        stmt.setFloat(11, sessionData.SessionDuration);
        stmt.setInt(12, sessionData.PitSpeedLimit);
        stmt.setInt(13, sessionData.GamePaused);
        stmt.setInt(14, sessionData.IsSpectating);
        stmt.setInt(15, sessionData.SpectatorCarIndex);
        stmt.setInt(16, sessionData.SliProNativeSupport);
        stmt.setString(17, gdc.SafetyCarStatus(sessionData.SafetyCarStatus));
        stmt.setString(18, gdc.NetworkGame(sessionData.NetworkGame));
        int id = 0;
        if (stmt.executeUpdate() > 0) {

          // getGeneratedKeys() returns result set of keys that were auto
          // generated
          // in our case student_id column
          ResultSet generatedKeys = stmt.getGeneratedKeys();
      
          // if resultset has data, get the primary key value
          // of last inserted record
          if (null != generatedKeys && generatedKeys.next()) {
      
              // voila! we got student id which was generated from sequence
              id = generatedKeys.getInt(1);
          }
      
        }

        if (id > 0 && sessionData.MarshalZones.length > 0) {
          File f2 = new File ("/home/opc/f1-game-listener/demo/src/InsertMarshalZone.sql");
          String mzq = new String(Files.readAllBytes(f2.toPath()));
          for (F12020MarshalZone mz : sessionData.MarshalZones) {
            try (PreparedStatement mzstmt = con.prepareStatement(mzq)) {
              mzstmt.setInt(1, id);
              mzstmt.setFloat(2, mz.ZoneStart);
              mzstmt.setInt(3, mz.ZoneFlag);
              mzstmt.execute();
            }
          }
        }

        if (id > 0 && sessionData.WeatherForecastSamples.length > 0) {
          File f3 = new File ("/home/opc/f1-game-listener/demo/src/InsertMarshalZone.sql");
          String wfq = new String(Files.readAllBytes(f3.toPath()));
          for (F12020WeatherForecastSample wf : sessionData.WeatherForecastSamples) {
            try (PreparedStatement wfstmt = con.prepareStatement(wfq)) {
              wfstmt.setInt(1, id);
              wfstmt.setString(2, gdc.SessionType(wf.SessionType));
              wfstmt.setInt(3, wf.TimeOffset);
              wfstmt.setString(4, gdc.Weather(wf.Weather));
              wfstmt.setInt(5, wf.TrackTemperature);
              wfstmt.setInt(6, wf.AirTemperature);
              wfstmt.execute();
            }
          }
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    System.out.println("BAD");
  }
}
