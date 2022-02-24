package UDPListener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Base64;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

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
import OCIStreaming.OCIStreaming;
import OCIStreaming.OCIStreamingException;

public class UDPListener {
  private boolean stop = false;
  private int port = 20777;
  private static int MAX_BUFFER = 2048;
  private OCIStreaming streaming;

  public UDPListener() throws OCIStreamingException{
    super();
    streaming = new OCIStreaming();
  }

  public void Listen() throws SocketException, IOException {
    try {
      DatagramSocket dsocket = new DatagramSocket(port);
      final byte[] buffer = new byte[MAX_BUFFER];
      DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

      // Now loop forever, waiting to receive packets and printing them.
      while (true) {
        // Wait to receive a datagram
        dsocket.receive(packet);
        // Send contents to Controller
        ReadPacket(buffer);

        // Reset the length of the packet before reusing it.
        packet.setLength(buffer.length);
      }
    } catch (SocketException se) {
      se.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  public String buildOCIStreamingMessage(String key, String json) throws UnsupportedEncodingException {
    JSONObject payload = new JSONObject();
    JSONArray msgs = new JSONArray();
    JSONObject msg = new JSONObject();
    msg.put("key", Base64.getEncoder().encodeToString(key.getBytes("UTF-8")));
    String enc = Base64.getEncoder().encodeToString(json.getBytes("UTF-8"));
    msg.put("value", enc);
    msgs.put(msg);
    payload.put("messages", msgs);
    return payload.toString();
  }

  public void ReadPacket(byte[] bytes) {
    ByteBuffer bb = ByteBuffer.wrap(bytes);
    bb.order(ByteOrder.LITTLE_ENDIAN);
    Gson gson = new Gson();

    F12020PacketFactory factory = new F12020PacketFactory();
    F12020PacketHeader header = factory.CreatePacketHeader(bb);
    
    try {
      
      String body = "";
      String payload = "";
      switch (header.PacketId) {
        case 0:
          F12020PacketMotionData motion = factory.CreatePacketMotionData(bb);
          body = gson.toJson(motion);
          payload = buildOCIStreamingMessage(header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          break;
        case 1:
          F12020PacketSessionData session = factory.CreatePacksetSessionData(bb);
          body = gson.toJson(session);
          payload = buildOCIStreamingMessage(header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          break;
        case 2:
          F12020PacketLapData lap = factory.CreatePacketLapData(bb);
          body = gson.toJson(lap);
          payload = buildOCIStreamingMessage(header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          break;
        case 3:
          F12020PacketEventData event = factory.CreatePacketEventData(bb);
          body = gson.toJson(event);
          payload = buildOCIStreamingMessage(header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          break;
        case 4:
          F12020PacketParticipantData participants = factory.CreatePacketParticipantData(bb);
          body = gson.toJson(participants);
          payload = buildOCIStreamingMessage(header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          break;
        case 5:
          F12020PacketCarSetupData carsetup = factory.CreatePacketCarSetupData(bb);
          body = gson.toJson(carsetup);
          payload = buildOCIStreamingMessage(header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          break;
        case 6:
          F12020PacketCarTelemetryData cartelemetry = factory.CreatePacketCarTelemetryData(bb);
          body = gson.toJson(cartelemetry);
          payload = buildOCIStreamingMessage(header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          break;
        case 7:
          F12020PacketCarStatusData carstatus = factory.CreatePacketCarStatusData(bb);
          body = gson.toJson(carstatus);
          payload = buildOCIStreamingMessage(header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          break;  
        case 8:
          F12020PacketFinalClassificationData finalClassification = factory.CreatePacketFinalClassificationData(bb);
          body = gson.toJson(finalClassification);
          payload = buildOCIStreamingMessage(header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
        case 9:
          F12020PacketLobbyInfoData lobby = factory.CreatePacketLobbyInfoData(bb);
          body = gson.toJson(lobby);
          payload = buildOCIStreamingMessage(header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
      }
    } catch (Exception ex){
      ex.printStackTrace();
    }
  }
}
