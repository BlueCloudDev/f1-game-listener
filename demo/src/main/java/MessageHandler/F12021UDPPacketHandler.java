package MessageHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.Gson;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.client.ClientProtocolException;

import OCIStreaming.OCIStreaming;
import OCIStreaming.OCIStreamingException;
import OCIStreaming.OCIStreamingMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import F12021Packet.F12021PacketFactory;
import F12021Packet.F12021PacketFinalClassificationData;
import F12021Packet.F12021PacketHeader;
import MessageHandler.Runners.F12021PacketCarSetupDataRunner;
import MessageHandler.Runners.F12021PacketCarStatusDataRunner;
import MessageHandler.Runners.F12021PacketCarTelemetryDataRunner;
import MessageHandler.Runners.F12021PacketLapDataRunner;
import MessageHandler.Runners.F12021PacketMotionDataRunner;
import MessageHandler.Runners.F12021PacketParticipantDataRunner;
import MessageHandler.Runners.F12021PacketSessionDataRunner;
import MessageHandler.Runners.F12021PacketSessionHistoryDataRunner;
import Configuration.Configuration;

public class F12021UDPPacketHandler {
  private static final Logger logger = LogManager.getLogger(F12021UDPPacketHandler.class);
  private static Map<String, Integer> sessionParticipants = new HashMap<String, Integer>();
  private static List<OCIStreamingMessage> messageCache = new ArrayList<OCIStreamingMessage>();
  private static OCIStreaming streaming = null;
  private static Timer timer = new Timer();
  private Boolean isCached;

  public F12021UDPPacketHandler(Boolean _isCached) throws OCIStreamingException, Exception {
    super();
    isCached = _isCached;
    if (!isCached && streaming == null) {
      streaming = new OCIStreaming();
    }
    TimerTask sendCacheTask = SendCacheTask();
    timer.scheduleAtFixedRate(sendCacheTask, 1000, 500);
  }

  public TimerTask SendCacheTask() {
    return new TimerTask() {
      @Override
      public void run() {
        try {
          if (GetMessageCache().size() > 350) {
            sendCache();
          }
        } catch (Exception ex) {
          String stackTrace = ExceptionUtils.getStackTrace(ex);
          logger.fatal(stackTrace);
        }
      }
    };
  }

  public synchronized void ProcessMessage(OCIStreamingMessage message)
      throws IOException, ClientProtocolException, UnsupportedEncodingException, Exception {
    if (!isCached) {
      List<OCIStreamingMessage> messages = new ArrayList<OCIStreamingMessage>();
      messages.add(message);
      JSONObject jo = new JSONObject();
      jo.put("messages", messages);
      String body = jo.toString();
      body = body + "\n";
      streaming.SendMessage(body);
    } else {
      messageCache.add(message);
    }
  }

  public static synchronized void AddMessage(OCIStreamingMessage message) {
    messageCache.add(message);
  }

  public synchronized List<OCIStreamingMessage> GetMessageCache() {
    return messageCache;
  }

  public void sendCache() throws MalformedURLException, IOException {
    JSONObject jo = new JSONObject();
    var msgs = new Gson().toJson(GetMessageCache());
    JSONArray ja = new JSONArray(msgs);
    jo.put("messages", ja);
    String body = jo.toString();
    body = body + "\n";

    URL url = new URL(
        "http://" + Configuration.EnvVars.get("API_IP") + ":" + Configuration.EnvVars.get("API_PORT") + "/f12021");
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/json");
    // TODO add an API key for security
    /*
     * if (apikey.equals(Constants.APIKEY_PREPROD)) {
     * connection.setRequestProperty("Authorization", Constants.APIKEY_PREPROD);
     * }
     * if (apikey.equals(Constants.APIKEY_PROD)){
     * connection.setRequestProperty("Authorization", Constants.APIKEY_PROD);
     * }
     */
    con.setRequestProperty("Content-Length", Integer.toString(body.getBytes().length));
    con.setRequestProperty("Content-Language", "en-US");
    con.setUseCaches(false);
    con.setDoOutput(true);

    // Send request
    try {
      OutputStream os = con.getOutputStream();
      os.write(body.getBytes("UTF-8"));

      os.close();
      int res = con.getResponseCode();
      if (res != 200) {
        logger.info("Error sending message to API: " + res);
        return;
      }

      String msg = "Sending cached messages: " + messageCache.size() + " count";
      logger.info(msg);
      messageCache.clear();
    } catch (IOException ex) {
      var stacktrace = ExceptionUtils.getStackTrace(ex);
      logger.info(stacktrace);
    }
  }

  public void ReadPacket(F12021PacketHeader header, ByteBuffer bb)
      throws IOException, ClientProtocolException, UnsupportedEncodingException, Exception {
    /*if (GetMessageCache().size() > 350) {
      sendCache();
    }*/
    String body = "";
    Gson gson = new Gson();
    String key = "F12021";
    String headerJson = gson.toJson(header);
    F12021PacketFactory factory = new F12021PacketFactory();
    OCIStreamingMessage msg = null;
    try {
      switch (header.PacketId) {
        case 0:
          F12021PacketMotionDataRunner runner = new F12021PacketMotionDataRunner();
          runner.bb = bb;
          runner.header = header;
          runner.headerJson = headerJson;
          runner.key = key;
          runner.sessionParticipants = sessionParticipants;
          var t1 = new Thread(runner);
          t1.start();
          return;
        case 1:
          F12021PacketSessionDataRunner runner2 = new F12021PacketSessionDataRunner();
          runner2.bb = bb;
          runner2.header = header;
          runner2.headerJson = headerJson;
          runner2.key = key;
          var t2 = new Thread(runner2);
          t2.start();
          return;
        case 2:
          F12021PacketLapDataRunner runner3 = new F12021PacketLapDataRunner();
          runner3.bb = bb;
          runner3.header = header;
          runner3.headerJson = headerJson;
          runner3.key = key;
          runner3.sessionParticipants = sessionParticipants;
          var t3 = new Thread(runner3);
          t3.start();
          return;
        case 3:
          /*
           * F12021PacketEventData event = factory.CreatePacketEventData(bb);
           * body = gson.toJson(event);
           * msg = new OCIStreamingMessage(key, headerJson, body);
           * ProcessMessage(msg);
           */
          return;
        case 4:
          F12021PacketParticipantDataRunner runner4 = new F12021PacketParticipantDataRunner();
          runner4.bb = bb;
          runner4.header = header;
          runner4.headerJson = headerJson;
          runner4.key = key;
          runner4.sessionParticipants = sessionParticipants;
          var t4 = new Thread(runner4);
          t4.start();
          return;
        case 5:
          F12021PacketCarSetupDataRunner runner5 = new F12021PacketCarSetupDataRunner();
          runner5.bb = bb;
          runner5.header = header;
          runner5.headerJson = headerJson;
          runner5.key = key;
          runner5.sessionParticipants = sessionParticipants;
          var t5 = new Thread(runner5);
          t5.start();
          return;
        case 6:
          F12021PacketCarTelemetryDataRunner runner6 = new F12021PacketCarTelemetryDataRunner();
          runner6.bb = bb;
          runner6.header = header;
          runner6.headerJson = headerJson;
          runner6.key = key;
          runner6.sessionParticipants = sessionParticipants;
          var t6 = new Thread(runner6);
          t6.start();
          return;
        case 7:
          F12021PacketCarStatusDataRunner runner7 = new F12021PacketCarStatusDataRunner();
          runner7.bb = bb;
          runner7.header = header;
          runner7.headerJson = headerJson;
          runner7.key = key;
          runner7.sessionParticipants = sessionParticipants;
          var t7 = new Thread(runner7);
          t7.start();
          return;
        case 8:
          F12021PacketFinalClassificationData finalClassification = factory.CreatePacketFinalClassificationData(bb);
          body = gson.toJson(finalClassification);
          msg = new OCIStreamingMessage(key, headerJson, body);
          ProcessMessage(msg);
          return;
        case 9:
          return;
        case 10:
          /*
           * F12021PacketCarDamageData carDamage = factory.CreatePacketCarDamageData(bb);
           * if (!sessionParticipants.containsKey(header.SessionUID)) {
           * return;
           * }
           * numActiveCars = sessionParticipants.get(header.SessionUID);
           * if (numActiveCars <= 0) {
           * return;
           * }
           * F12021CarDamageData[] trunc5 = new F12021CarDamageData[numActiveCars];
           * for (int i = 0; i < numActiveCars; i++) {
           * trunc5[i] = carDamage.CarDamageData[i];
           * }
           * carDamage.CarDamageData = trunc5;
           * body = gson.toJson(carDamage);
           * msg = new OCIStreamingMessage(key, headerJson, body);
           * ProcessMessage(msg);
           */
          return;
        case 11:
          F12021PacketSessionHistoryDataRunner runner8 = new F12021PacketSessionHistoryDataRunner();
          runner8.bb = bb;
          runner8.header = header;
          runner8.headerJson = headerJson;
          runner8.key = key;
          var t8 = new Thread(runner8);
          t8.start();
          return;
        default:
          System.out.println("INVALID PACKET:");
          System.out.println(header.PacketId);
      }
    } catch (IllegalArgumentException ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.fatal(stackTrace);
    }
  }
}
