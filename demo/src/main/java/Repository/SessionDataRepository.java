package Repository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

  public void InsertSessionData(long sessionUID, F12020PacketSessionData sessionData) {
    String DB_URL="jdbc:oracle:thin:@db202201261034_high?TNS_ADMIN=/home/opc/Wallet_DB202201261034";
    String DB_USER="admin";
    String DB_PASSWORD="OracleDataTx1!";
    try {
      Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
      con.setAutoCommit(true);
      String query = new String(Files.readAllBytes(Paths.get("src/main/java/Repository/Statements/InsertSessionData.sql")));
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
        int id = stmt.executeUpdate();

        if (sessionData.MarshalZones.length > 0) {
          String mzq = new String(Files.readAllBytes(Paths.get("src/main/java/Repository/Statements/InsertMarshalZone.sql")));
          for (F12020MarshalZone mz : sessionData.MarshalZones) {
            try (PreparedStatement mzstmt = con.prepareStatement(mzq)) {
              mzstmt.setInt(1, id);
              mzstmt.setFloat(2, mz.ZoneStart);
              mzstmt.setInt(3, mz.ZoneFlag);
              mzstmt.execute();
            }
          }
        }

        if (sessionData.WeatherForecastSamples.length > 0) {
          String wfq = new String(Files.readAllBytes(Paths.get("src/main/java/Repository/Statements/InsertWeatherForecast.sql")));
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
