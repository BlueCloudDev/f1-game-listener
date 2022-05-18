package Repository.UDPServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Configuration.Configuration;
import UDPServerModel.PlayerBays;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UDPServerRepository {
  private static final Logger logger = LogManager.getLogger(UDPServerRepository.class);
  private String SQL_FOLDER = Configuration.EnvVars.get("SQL_FOLDER");

  public void InitDB() throws IOException {
    Connection connection = null;
    try
    {
      // create a database connection
      var dbpath = Paths.get(SQL_FOLDER, "UDPServer/UDPServer.db");
      connection = DriverManager.getConnection("jdbc:sqlite:" + dbpath);
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.
      var path = Paths.get(SQL_FOLDER, "UDPServer/initSqliteDb.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      statement.execute(query);
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      logger.info(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        logger.info(e.getMessage());
      }
    }
  }

  public List<PlayerBays> GetPlayerBays() throws IOException {
    Connection connection = null;
    List<PlayerBays> playerBays = new ArrayList<PlayerBays>();
    try
    {
      // create a database connection
      var dbpath = Paths.get(SQL_FOLDER, "UDPServer/UDPServer.db");
      connection = DriverManager.getConnection("jdbc:sqlite:" + dbpath);
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // set timeout to 30 sec.
      var path = Paths.get(SQL_FOLDER, "UDPServer/SelectPlayerBay.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      ResultSet rs = statement.executeQuery(query);
      while (rs.next()) {
        PlayerBays playerBay = new PlayerBays();
        playerBay.Id = rs.getInt("id");
        playerBay.PlayerName = rs.getString("m_player_name");
        playerBay.Port = rs.getInt("m_port");
        playerBay.LastPacketReceived = rs.getDate("m_last_packet_received");
        playerBays.add(playerBay);
      }
      
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      logger.info(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null) {
          connection.close();
        }
      }
      catch(SQLException e)
      {
        // connection close failed.
        logger.info(e.getMessage());
      }
    }
    return playerBays;
  }

  public String GetPlayerNameByPort(int port) throws IOException {
    Connection connection = null;
    String name = "";
    try
    {
      // create a database connection
      var dbpath = Paths.get(SQL_FOLDER, "UDPServer/UDPServer.db");
      connection = DriverManager.getConnection("jdbc:sqlite:" + dbpath);
      var path = Paths.get(SQL_FOLDER, "UDPServer/SelectPlayerNameByPort.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setQueryTimeout(30);  // set timeout to 30 sec.
      statement.setInt(1, port);
      var rs = statement.executeQuery();
      while (rs.next()) {
        name = rs.getString("m_player_name");
      }
      
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      logger.info(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null) {
          connection.close();
        }
      }
      catch(SQLException e)
      {
        // connection close failed.
        logger.info(e.getMessage());
      }
    }
    return name;
  }

  public void InsertPlayerBay(int port) throws IOException {
    Connection connection = null;
    try
    {
      // create a database connection
      var dbpath = Paths.get(SQL_FOLDER, "UDPServer/UDPServer.db");
      connection = DriverManager.getConnection("jdbc:sqlite:" + dbpath);
      var path = Paths.get(SQL_FOLDER, "UDPServer/InsertPlayerBay.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, port);
      statement.executeUpdate();
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      String stackstrace = ExceptionUtils.getStackTrace(e);
      logger.info(stackstrace);
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        logger.info(e.getMessage());
      }
    }
  }

  public void UpdatePlayerBaysLastPacket(int port) throws IOException {
    Connection connection = null;
    try
    {
      // create a database connection
      var dbpath = Paths.get(SQL_FOLDER, "UDPServer/UDPServer.db");
      connection = DriverManager.getConnection("jdbc:sqlite:" + dbpath);
      var path = Paths.get(SQL_FOLDER, "UDPServer/UpdatePlayerBayLastPacket.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, port);
      statement.executeUpdate();
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      logger.info(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        logger.info(e.getMessage());
      }
    }
  }

  public void DeletePlayerBay(int port) throws IOException {
    Connection connection = null;
    try
    {
      // create a database connection
      var dbpath = Paths.get(SQL_FOLDER, "UDPServer/UDPServer.db");
      connection = DriverManager.getConnection("jdbc:sqlite:" + dbpath);
      var path = Paths.get(SQL_FOLDER, "UDPServer/DeletePlayerBay.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setInt(1, port);
      statement.executeUpdate();
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      logger.info(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        logger.info(e.getMessage());
      }
    }
  }

  public void UpdatePlayerBaysName(int port, String name) throws IOException {
    Connection connection = null;
    try
    {
      // create a database connection
      var dbpath = Paths.get(SQL_FOLDER, "UDPServer/UDPServer.db");
      connection = DriverManager.getConnection("jdbc:sqlite:" + dbpath);
      var path = Paths.get(SQL_FOLDER, "UDPServer/UpdatePlayerBayName.sql");
      String query = new String(Files.readAllBytes(path.toAbsolutePath()));
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setString(1, name);
      statement.setInt(2, port);
      statement.executeUpdate();
    }
    catch(SQLException e)
    {
      // if the error message is "out of memory",
      // it probably means no database file is found
      logger.info(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        logger.info(e.getMessage());
      }
    }
  }
}
