package Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Configuration {
  public static Map<String, String> EnvVars = new HashMap<String, String>();
  private static final Logger logger = LogManager.getLogger(Configuration.class);
  private final String[] requiredKeys = {
    "APPLICATION_MODE",
    "STREAM_OCID",
    "OCI_PRIV_KEY_PATH", 
    "FINGERPRINT", 
    "TENANCY_OCID", 
    "USER_OCID",
    "API_HOST",
  };
  private final String[] consumerKeys = {
    "SQL_FOLDER",
    "DB_URL",
    "DB_USER",
    "DB_PASSWORD",
    "SCHEMA_NAME"
  };
  private final String[] listenerKeys = {
    "LISTEN_PORT",
    "PLAYER_NAME"
  };
  private final String[] localListenerKeys = {
    "LISTEN_PORT",
    "PLAYER_NAME",
    "API_IP",
    "API_PORT"
  };
  private final String[] localServerKeys = {
    "LISTEN_PORT",
    "PLAYER_NAME",
    "API_IP",
    "API_PORT",
    "SQL_FOLDER",
    "SCHEMA_NAME"
  };
  public boolean ReadEnvVars() {
    Map <String, String> envVarMap = System.getenv();
    List<String> missingKeys = new ArrayList<String>();
    for (String key : requiredKeys) {
      if(envVarMap.containsKey(key) && envVarMap.get(key) != "") {
        EnvVars.put(key, envVarMap.get(key));
      } else {
        missingKeys.add(key);
      }
    }

    if (missingKeys.size() == 0 && (EnvVars.get("APPLICATION_MODE").toLowerCase().equals("consumer") || EnvVars.get("APPLICATION_MODE").toLowerCase().equals("both"))) {
      for (String key : consumerKeys) {
        if(envVarMap.containsKey(key) && envVarMap.get(key) != "") {
          EnvVars.put(key, envVarMap.get(key));
        } else {
          missingKeys.add(key);
        }
      }
    }

    if (missingKeys.size() == 0 && (EnvVars.get("APPLICATION_MODE").toLowerCase().equals("listener") || EnvVars.get("APPLICATION_MODE").toLowerCase().equals("both"))) {
      for (String key : listenerKeys) {
        if(envVarMap.containsKey(key) && envVarMap.get(key) != "") {
          EnvVars.put(key, envVarMap.get(key));
        } else {
          missingKeys.add(key);
        }
      }
    }

    if (missingKeys.size() == 0 && (EnvVars.get("APPLICATION_MODE").toLowerCase().equals("local-listener"))) {
      for (String key : localListenerKeys) {
        if(envVarMap.containsKey(key) && envVarMap.get(key) != "") {
          EnvVars.put(key, envVarMap.get(key));
        } else {
          missingKeys.add(key);
        }
      }
    }

    if (missingKeys.size() == 0 && (EnvVars.get("APPLICATION_MODE").toLowerCase().equals("local-server"))) {
      for (String key : localServerKeys) {
        if(envVarMap.containsKey(key) && envVarMap.get(key) != "") {
          EnvVars.put(key, envVarMap.get(key));
        } else {
          missingKeys.add(key);
        }
      }
    }

    if (missingKeys.size() > 0) {
      var missing = Arrays.toString(missingKeys.toArray());
      logger.fatal("Missing required Environment Variables: " + missing);
      return false;
    }
    logger.info("Configuration OK");
    return true;
  }
}
