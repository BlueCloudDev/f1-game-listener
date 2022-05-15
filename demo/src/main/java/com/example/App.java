package com.example;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Configuration.Configuration;
import OCIStreaming.OCIStreaming;
import UDPListener.UDPListener;

import com.hellokaton.blade.Blade;


public class App {
  private static final Logger logger = LogManager.getLogger(App.class);
  private static boolean isConsumerRunning = false;
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    boolean ok = configuration.ReadEnvVars();
    System.out.println(Configuration.EnvVars.get("LISTEN_PORT"));
    if (!ok) {
      logger.info("Closing application");
      return;
    }
    String APPLICATION_MODE = Configuration.EnvVars.get("APPLICATION_MODE").toLowerCase();
    logger.info("Application Mode: " + APPLICATION_MODE);

    if(APPLICATION_MODE.equals("api")) {
      Blade.create().start(App.class, args);
    }

    if (APPLICATION_MODE.equals("both") || APPLICATION_MODE.equals("consumer")) {
      Timer timer = new Timer();
      TimerTask getMsgTask = GetMessagesTask();
      timer.scheduleAtFixedRate(getMsgTask, 5000, 5000);
      logger.info("Started consumer task");
    }
    
    if (APPLICATION_MODE.equals("both") || APPLICATION_MODE.equals("listener") || APPLICATION_MODE.equals("local-listener")) {
      try {
        logger.info("Started listener");
        UDPListener listener = new UDPListener();
        listener.Listen();
      } catch (Exception ex) {
        String stackTrace = ExceptionUtils.getStackTrace(ex);
        logger.fatal(stackTrace);
      }
    } else if (APPLICATION_MODE.equals("consumer")) {
      try {
        while (true) {
          Thread.sleep(1000);
        }
      } catch (InterruptedException ex) {
        logger.info("Interrupted, Shutting down...");
      }
    }

    logger.info("Closing application");
  }

  public static TimerTask GetMessagesTask() {
    return new TimerTask() {
      @Override
      public void run() {
        if (!isConsumerRunning) {
          isConsumerRunning = true;
          try {
            OCIStreaming stream = new OCIStreaming();
            stream.GetMessage();
          } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.fatal(stackTrace);
          }
          isConsumerRunning = false;
        }
      }
    };
  }

}
