package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Configuration.Configuration;
import OCIStreaming.OCIStreaming;
import Repository.UDPServer.UDPServerRepository;
import UDPListener.UDPListener;
import UDPServerModel.PlayerBays;

import com.hellokaton.blade.Blade;
import com.hellokaton.blade.mvc.http.HttpMethod;
import com.hellokaton.blade.options.CorsOptions;


public class App {
  private static final Logger logger = LogManager.getLogger(App.class);
  private static boolean isConsumerRunning = false;
  public static Map<Integer, Thread> udpListeners = new HashMap<Integer, Thread>();
  public static void main(String[] args) {
    Configuration configuration = new Configuration();
    boolean ok = configuration.ReadEnvVars();
    System.out.println(Configuration.EnvVars.get("LISTEN_PORT"));
    CorsOptions corsOptions = CorsOptions
      .forAnyOrigin()
      .allowedMethods(HttpMethod.PUT)
      .allowedMethods(HttpMethod.POST)
      .allowedMethods(HttpMethod.DELETE)
      .allowedMethods(HttpMethod.GET)
      .allowedHeaders("content-type")
      .allowNullOrigin()
      .allowCredentials();
    if (!ok) {
      logger.info("Closing application");
      return;
    }
    String APPLICATION_MODE = Configuration.EnvVars.get("APPLICATION_MODE").toLowerCase();
    logger.info("Application Mode: " + APPLICATION_MODE);

    if(APPLICATION_MODE.equals("api")) {
      Blade.create().cors(corsOptions).start(App.class, args);
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
        UDPListener listener = new UDPListener(0);
        listener.run();
      } catch (Exception ex) {
        String stackTrace = ExceptionUtils.getStackTrace(ex);
        logger.info(stackTrace);
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

    if (APPLICATION_MODE.equals("local-server")) {
      UDPServerRepository udprepo = new UDPServerRepository();
      try {
        udprepo.InitDB();
        Blade.create().cors(corsOptions).start(App.class, args);
        while (true) {
          var res = udprepo.GetPlayerBays();
          for (PlayerBays bay : res) {
            UDPListener udpListener = new UDPListener(bay.Port);
            if (!App.udpListeners.containsKey(bay.Port)) {
              udpListener.start();
              App.udpListeners.put(bay.Port, udpListener);
            }
          }
          Thread.sleep(1000);
        }
      } catch (Exception ex) {
        String stacktrace = ExceptionUtils.getStackTrace(ex);
        logger.info(stacktrace);
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
