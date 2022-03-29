package MessageHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.client.ClientProtocolException;

import OCIStreaming.OCIStreaming;
import OCIStreaming.OCIStreamingMessage;
import oracle.security.crypto.cert.TrustedCAPolicy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import F12020Packet.F12020PacketHeader;
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

public class F12021UDPPacketHandler {
  private static final Logger logger = LogManager.getLogger(F12020UDPPacketHandler.class);
  private static Map<String, Integer> sessionParticipants = new HashMap<String, Integer>();

  public void ReadPacket(F12021PacketHeader header, ByteBuffer bb, OCIStreaming streaming) throws IOException, ClientProtocolException, UnsupportedEncodingException, Exception {
    String body = "";
    Gson gson = new Gson();
    OCIStreamingMessage osm = new OCIStreamingMessage();
    String key = "F12020";
    String headerJson = gson.toJson(header);
    String payload = "";
    F12021PacketFactory factory = new F12021PacketFactory();
    int numActiveCars = 0;
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
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
          return;
        case 1:
          F12021PacketSessionData session = factory.CreatePacksetSessionData(bb);
          body = gson.toJson(session);
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
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
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
          return;
        case 3:
          F12021PacketEventData event = factory.CreatePacketEventData(bb);
          body = gson.toJson(event);
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
          return;
        case 4:
          F12021PacketParticipantData participants = factory.CreatePacketParticipantData(bb);
          sessionParticipants.put(header.SessionUID, participants.NumActiveCars);
          body = gson.toJson(participants);
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
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
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
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
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
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
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
          return;
        case 8:
          F12021PacketFinalClassificationData finalClassification = factory.CreatePacketFinalClassificationData(bb);
          body = gson.toJson(finalClassification);
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
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
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
          return;
        case 11:
          F12021PacketSessionHistoryData sessionHistoryData = factory.CreatePacketSessionHistoryData(bb);
          body = gson.toJson(sessionHistoryData);
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
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
