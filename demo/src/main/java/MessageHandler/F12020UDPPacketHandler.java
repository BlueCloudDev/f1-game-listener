package MessageHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.google.gson.Gson;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.client.ClientProtocolException;

import F12020Packet.F12020PacketCarSetupData;
import F12020Packet.F12020PacketCarStatusData;
import F12020Packet.F12020PacketCarTelemetryData;
import F12020Packet.F12020PacketEventData;
import F12020Packet.F12020PacketFactory;
import F12020Packet.F12020PacketFinalClassificationData;
import F12020Packet.F12020PacketHeader;
import F12020Packet.F12020PacketLapData;
import F12020Packet.F12020PacketLobbyInfoData;
import F12020Packet.F12020PacketMotionData;
import F12020Packet.F12020PacketParticipantData;
import F12020Packet.F12020PacketSessionData;
import F12021Packet.F12021PacketHeader;
import OCIStreaming.OCIStreaming;
import OCIStreaming.OCIStreamingMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class F12020UDPPacketHandler {
  private static final Logger logger = LogManager.getLogger(F12020UDPPacketHandler.class);
  public void ReadPacket(F12021PacketHeader header, ByteBuffer bb, OCIStreaming streaming) throws IOException, ClientProtocolException, UnsupportedEncodingException, Exception {
    String body = "";
    Gson gson = new Gson();
    OCIStreamingMessage osm = new OCIStreamingMessage();
    String key = "F12020";
    String headerJson = gson.toJson(header);
    String payload = "";
    F12020PacketFactory factory = new F12020PacketFactory();
    try {
      switch (header.PacketId) {
        case 0:
          F12020PacketMotionData motion = factory.CreatePacketMotionData(bb);
          body = gson.toJson(motion);
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
          return;
        case 1:
          F12020PacketSessionData session = factory.CreatePacksetSessionData(bb);
          body = gson.toJson(session);
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
          return;
        case 2:
          F12020PacketLapData lap = factory.CreatePacketLapData(bb);
          body = gson.toJson(lap);
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
          return;
        case 3:
          F12020PacketEventData event = factory.CreatePacketEventData(bb);
          body = gson.toJson(event);
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
          return;
        case 4:
          F12020PacketParticipantData participants = factory.CreatePacketParticipantData(bb);
          body = gson.toJson(participants);
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
          return;
        case 5:
          F12020PacketCarSetupData carsetup = factory.CreatePacketCarSetupData(bb);
          body = gson.toJson(carsetup);
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
          return;
        case 6:
          F12020PacketCarTelemetryData cartelemetry = factory.CreatePacketCarTelemetryData(bb);
          body = gson.toJson(cartelemetry);
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
          return;
        case 7:
          F12020PacketCarStatusData carstatus = factory.CreatePacketCarStatusData(bb);
          body = gson.toJson(carstatus);
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
          return;
        case 8:
          F12020PacketFinalClassificationData finalClassification = factory.CreatePacketFinalClassificationData(bb);
          body = gson.toJson(finalClassification);
          payload = osm.Build(key, headerJson, body);
          streaming.SendMessage(payload);
          return;
        case 9:
          F12020PacketLobbyInfoData lobby = factory.CreatePacketLobbyInfoData(bb);
          body = gson.toJson(lobby);
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
