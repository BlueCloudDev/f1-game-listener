package Repository;

import java.sql.SQLException;
import java.util.Properties;

import Configuration.Configuration;
import oracle.jdbc.OracleConnection;
//import oracle.jdbc.pool.OracleDataSource;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

public class OracleDataSourceProvider {
  
  public PoolDataSource GetOraclePoolDataSource() throws SQLException {
    String DB_URL = Configuration.EnvVars.get("DB_URL");
    String DB_USER = Configuration.EnvVars.get("DB_USER");
    String DB_PASSWORD = Configuration.EnvVars.get("DB_PASSWORD");

    PoolDataSource  pds = PoolDataSourceFactory.getPoolDataSource();
    pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
    pds.setURL(DB_URL);
    pds.setUser(DB_USER);
    pds.setPassword(DB_PASSWORD);
    pds.setInitialPoolSize(30);

    Properties info = new Properties();     
    //info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
    //info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);          
    info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");   
    info.put(OracleConnection.CONNECTION_PROPERTY_FAN_ENABLED, "false"); 
    pds.setConnectionProperties(info);

    //OracleDataSource ods = new OracleDataSource();
    //ods.setURL(DB_URL);    
    //ods.setConnectionProperties(info);
    return pds;
  }
}
