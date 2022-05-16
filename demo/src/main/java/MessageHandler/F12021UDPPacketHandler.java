package MessageHandler;

import java.io.DataOutputStream;
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
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.catalina.webresources.Cache;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.client.ClientProtocolException;

import OCIStreaming.OCIStreaming;
import OCIStreaming.OCIStreamingException;
import OCIStreaming.OCIStreamingMessage;
import Repository.F12021.SessionLookupRepository2021;
import oracle.security.crypto.cert.TrustedCAPolicy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import F12021Packet.F12021CarDamageData;
import F12021Packet.F12021CarMotionData;
import F12021Packet.F12021CarSetupData;
import F12021Packet.F12021CarStatusData;
import F12021Packet.F12021CarTelemetryData;
import F12021Packet.F12021LapData;
import F12021Packet.F12021PacketCarDamageData;
import F12021Packet.F12021PacketCarSetupData;
import F12021Packet.F12021PacketCarStatusData;
import F12021Packet.F12021PacketCarTelemetryData;
import F12021Packet.F12021PacketEventData;
import F12021Packet.F12021PacketFactory;
import F12021Packet.F12021PacketFinalClassificationData;
import F12021Packet.F12021PacketHeader;
import F12021Packet.F12021PacketLapData;
import F12021Packet.F12021PacketMotionData;
import F12021Packet.F12021PacketParticipantData;
import F12021Packet.F12021PacketSessionData;
import F12021Packet.F12021PacketSessionHistoryData;

import Configuration.Configuration;

public class F12021UDPPacketHandler {
  private static final Logger logger = LogManager.getLogger(F12020UDPPacketHandler.class);
  private static Map<String, Integer> sessionParticipants = new HashMap<String, Integer>();
  private static List<OCIStreamingMessage> messageCache = new ArrayList<OCIStreamingMessage>();
  private static StopWatch stopwatch = new StopWatch();
  private static OCIStreaming streaming = null;
  private Boolean isCached;

  public F12021UDPPacketHandler(Boolean _isCached) throws OCIStreamingException, Exception {
    super();
    isCached = _isCached;
    if (!isCached && streaming == null) {
      streaming = new OCIStreaming();
    }
    if (stopwatch.isStopped()) {
      stopwatch.start();
    }
  }

  public void ProcessMessage(OCIStreamingMessage message) throws IOException, ClientProtocolException, UnsupportedEncodingException, Exception {
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

  public synchronized void sendCache() throws MalformedURLException, IOException {
    JSONObject jo = new JSONObject();
    var msgs = new Gson().toJson(messageCache);
    JSONArray ja = new JSONArray(msgs);
    jo.put("messages", ja);
    String body = jo.toString();
    body = body + "\n";

    URL url = new URL("http://" + Configuration.EnvVars.get("API_IP") + ":" + Configuration.EnvVars.get("API_PORT") + "/f12021");
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/json");
    //TODO add an API key for security
    /*if (apikey.equals(Constants.APIKEY_PREPROD)) {
        connection.setRequestProperty("Authorization", Constants.APIKEY_PREPROD);
    }
    if (apikey.equals(Constants.APIKEY_PROD)){
        connection.setRequestProperty("Authorization", Constants.APIKEY_PROD);
    }*/
    con.setRequestProperty("Content-Length", Integer.toString(body.getBytes().length));
    con.setRequestProperty("Content-Language", "en-US");  
    con.setUseCaches(false);
    con.setDoOutput(true);

    //Send request
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
    stopwatch.reset();
  }

  public void ReadPacket(F12021PacketHeader header, ByteBuffer bb) throws IOException, ClientProtocolException, UnsupportedEncodingException, Exception {
    long elapsed = stopwatch.getTime(TimeUnit.MILLISECONDS);
    if (elapsed > 1000) {
      sendCache();
    }
    String body = "";
    Gson gson = new Gson();
    String key = "F12020";
    String headerJson = gson.toJson(header);
    String payload = "";
    F12021PacketFactory factory = new F12021PacketFactory();
    int numActiveCars = 0;
    OCIStreamingMessage msg = null;
    try {
      switch (header.PacketId) {
        case 0:
          F12021PacketMotionData motion = factory.CreatePacketMotionData(bb);
          if (!sessionParticipants.containsKey(header.SessionUID)) {
            return;
          }
          numActiveCars = sessionParticipants.get(header.SessionUID);
          if (numActiveCars <= 0){
            return;
          }
          F12021CarMotionData[] trunc = new F12021CarMotionData[numActiveCars];
          for (int i = 0; i < numActiveCars; i++){
            trunc[i] = motion.CarMotionData[i]; 
          }
          motion.CarMotionData = trunc;
          body = gson.toJson(motion);
          msg = new OCIStreamingMessage(key, headerJson, body);
          ProcessMessage(msg);
          return;
        case 1:
          F12021PacketSessionData session = factory.CreatePacksetSessionData(bb);
          body = gson.toJson(session);
          msg = new OCIStreamingMessage(key, headerJson, body);
          ProcessMessage(msg);
          return;
        case 2:
          F12021PacketLapData lap = factory.CreatePacketLapData(bb);
          if (!sessionParticipants.containsKey(header.SessionUID)) {
            return;
          }
          numActiveCars = sessionParticipants.get(header.SessionUID);
          if (numActiveCars <= 0){
            return;
          }
          F12021LapData[] trunc1 = new F12021LapData[numActiveCars];
          for (int i = 0; i < numActiveCars; i++){
            trunc1[i] = lap.LapData[i]; 
          }
          lap.LapData = trunc1;
          body = gson.toJson(lap);
          msg = new OCIStreamingMessage(key, headerJson, body);
          ProcessMessage(msg);
          return;
        case 3:
          F12021PacketEventData event = factory.CreatePacketEventData(bb);
          body = gson.toJson(event);
          msg = new OCIStreamingMessage(key, headerJson, body);
          ProcessMessage(msg);
          return;
        case 4:
          F12021PacketParticipantData participants = factory.CreatePacketParticipantData(bb);
          sessionParticipants.put(header.SessionUID, participants.NumActiveCars);
          body = gson.toJson(participants);
          msg = new OCIStreamingMessage(key, headerJson, body);
          ProcessMessage(msg);
          return;
        case 5:
          F12021PacketCarSetupData carsetup = factory.CreatePacketCarSetupData(bb);
          if (!sessionParticipants.containsKey(header.SessionUID)) {
            return;
          }
          numActiveCars = sessionParticipants.get(header.SessionUID);
          if (numActiveCars <= 0){
            return;
          }
          F12021CarSetupData[] trunc2 = new F12021CarSetupData[numActiveCars];
          for (int i = 0; i < numActiveCars; i++){
            trunc2[i] = carsetup.CarSetups[i]; 
          }
          carsetup.CarSetups = trunc2;
          body = gson.toJson(carsetup);
          msg = new OCIStreamingMessage(key, headerJson, body);
          ProcessMessage(msg);
          return;
        case 6:
          F12021PacketCarTelemetryData cartelemetry = factory.CreatePacketCarTelemetryData(bb);
          if (!sessionParticipants.containsKey(header.SessionUID)) {
            return;
          }
          numActiveCars = sessionParticipants.get(header.SessionUID);
          if (numActiveCars <= 0){
            return;
          }
          F12021CarTelemetryData[] trunc3 = new F12021CarTelemetryData[numActiveCars];
          for (int i = 0; i < numActiveCars; i++){
            trunc3[i] = cartelemetry.CarTelemetryData[i]; 
          }
          cartelemetry.CarTelemetryData = trunc3;
          body = gson.toJson(cartelemetry);
          msg = new OCIStreamingMessage(key, headerJson, body);
          ProcessMessage(msg);
          return;
        case 7:
          F12021PacketCarStatusData carstatus = factory.CreatePacketCarStatusData(bb);
          if (!sessionParticipants.containsKey(header.SessionUID)) {
            return;
          }
          numActiveCars = sessionParticipants.get(header.SessionUID);
          if (numActiveCars <= 0){
            return;
          }
          F12021CarStatusData[] trunc4 = new F12021CarStatusData[numActiveCars];
          for (int i = 0; i < numActiveCars; i++){
            trunc4[i] = carstatus.CarStatusData[i]; 
          }
          body = gson.toJson(carstatus);
          carstatus.CarStatusData = trunc4;
          msg = new OCIStreamingMessage(key, headerJson, body);
          ProcessMessage(msg);
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
          F12021PacketCarDamageData carDamage = factory.CreatePacketCarDamageData(bb);
          if (!sessionParticipants.containsKey(header.SessionUID)) {
            return;
          }
          numActiveCars = sessionParticipants.get(header.SessionUID);
          if (numActiveCars <= 0){
            return;
          }
          F12021CarDamageData[] trunc5 = new F12021CarDamageData[numActiveCars];
          for (int i = 0; i < numActiveCars; i++){
            trunc5[i] = carDamage.CarDamageData[i]; 
          }
          carDamage.CarDamageData = trunc5;
          body = gson.toJson(carDamage);
          msg = new OCIStreamingMessage(key, headerJson, body);
          ProcessMessage(msg);
          return;
        case 11:
          F12021PacketSessionHistoryData sessionHistoryData = factory.CreatePacketSessionHistoryData(bb);
          body = gson.toJson(sessionHistoryData);
          msg = new OCIStreamingMessage(key, headerJson, body);
          ProcessMessage(msg);
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
