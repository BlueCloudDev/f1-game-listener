package Repository;

import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class OracleDataSourceProvider {
  public OracleDataSource GetOracleDataSource() throws SQLException {
    String DB_URL="jdbc:oracle:thin:@db202201261034_high?TNS_ADMIN=/home/opc/Wallet_DB202201261034";
    String DB_USER="admin";
    String DB_PASSWORD="OracleDataTx1!";

    Properties info = new Properties();     
    info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
    info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);          
    info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");    
  

    OracleDataSource ods = new OracleDataSource();
    ods.setURL(DB_URL);    
    ods.setConnectionProperties(info);
    return ods;
  }
}
