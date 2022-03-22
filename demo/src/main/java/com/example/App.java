package com.example;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Configuration.Configuration;
import OCIStreaming.OCIStreaming;
import UDPListener.UDPListener;


public class App {
  private static final Logger logger = LogManager.getLogger(App.class);
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    boolean ok = configuration.ReadEnvVars();
    if (!ok) {
      logger.info("Closing application");
      return;
    }
    String APPLICATION_MODE = Configuration.EnvVars.get("APPLICATION_MODE").toLowerCase();
    logger.info("Application Mode: " + APPLICATION_MODE);

    if (APPLICATION_MODE.equals("both") || APPLICATION_MODE.equals("consumer")) {
      Timer timer = new Timer();
      TimerTask getMsgTask = GetMessagesTask();
      timer.scheduleAtFixedRate(getMsgTask, 5000, 5000);
      logger.info("Started consumer task");
    }
    
    if (APPLICATION_MODE.equals("both") || APPLICATION_MODE.equals("listener")) {
      try {
        UDPListener listener = new UDPListener();
        listener.Listen();
        logger.info("Started listener");
      } catch (Exception ex) {
        logger.fatal(ex.getMessage());
      }
    }
    logger.info("Closing application");
  }

  public static TimerTask GetMessagesTask() {
    return new TimerTask() {
      @Override
      public void run() {
        try {
          OCIStreaming stream = new OCIStreaming();
          stream.GetMessage();
        } catch (Exception ex) {
          logger.fatal(ex.getMessage());
        }
      }
    };
  }

}
