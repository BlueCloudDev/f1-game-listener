package UDPListener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Base64;

import com.google.gson.Gson;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
import OCIStreaming.OCIStreamingMessage;

public class UDPListener {
  CloseableHttpClient httpClient;
  private boolean stop = false;
  private int port = 20777;
  private static int MAX_BUFFER = 2048;
  private OCIStreaming streaming;

  public UDPListener() throws OCIStreamingException, Exception{
    super();
    streaming = new OCIStreaming();

  }

  /*public void Listen() throws SocketException, IOException, Exception {
    DatagramSocket dsocket = new DatagramSocket(port);
    final byte[] buffer = new byte[MAX_BUFFER];
    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

    // Now loop forever, waiting to receive packets and printing them.
    int count = 0;
    while (true) {
      // Wait to receive a datagram
      dsocket.receive(packet);
      // Send contents to Controller
      ReadPacket(buffer);

      // Reset the length of the packet before reusing it.
      packet.setLength(buffer.length);
      count = count + 1;
      System.out.println(count);
    }
  }*/

  public void Listen() throws SocketException, IOException, Exception {
    DatagramSocket socket = new DatagramSocket(port);
    byte[] buf = new byte[MAX_BUFFER];
    int count = 0;
    while (true) {
      DatagramPacket packet 
        = new DatagramPacket(buf, buf.length);
      socket.receive(packet);
      
      InetAddress address = packet.getAddress();
      int port = packet.getPort();
      packet = new DatagramPacket(buf, buf.length, address, port);
      ReadPacket(packet.getData());
      count = count + 1;
      System.out.println(count);
    }
  }

  

  public void ReadPacket(byte[] bytes) throws IOException, ClientProtocolException, UnsupportedEncodingException, Exception {
    ByteBuffer bb = ByteBuffer.wrap(bytes);
    bb.order(ByteOrder.LITTLE_ENDIAN);
    Gson gson = new Gson();
    
    F12020PacketFactory factory = new F12020PacketFactory();
    F12020PacketHeader header = factory.CreatePacketHeader(bb);
  
    String body = "";
    String payload = "";
    OCIStreamingMessage osm = new OCIStreamingMessage();
    try {
      switch (header.PacketId) {
        case 0:
          F12020PacketMotionData motion = factory.CreatePacketMotionData(bb);
          body = gson.toJson(motion);
          payload = osm.Build(header.PacketId + "," + header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          return;
        case 1:
          F12020PacketSessionData session = factory.CreatePacksetSessionData(bb);
          body = gson.toJson(session);
          payload = osm.Build(header.PacketId + "," + header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          return;
        case 2:
          F12020PacketLapData lap = factory.CreatePacketLapData(bb);
          body = gson.toJson(lap);
          payload = osm.Build(header.PacketId + "," + header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          return;
        case 3:
          F12020PacketEventData event = factory.CreatePacketEventData(bb);
          body = gson.toJson(event);
          payload = osm.Build(header.PacketId + "," + header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          return;
        case 4:
          F12020PacketParticipantData participants = factory.CreatePacketParticipantData(bb);
          body = gson.toJson(participants);
          payload = osm.Build(header.PacketId + "," + header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          return;
        case 5:
          F12020PacketCarSetupData carsetup = factory.CreatePacketCarSetupData(bb);
          body = gson.toJson(carsetup);
          payload = osm.Build(header.PacketId + "," + header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          return;
        case 6:
          F12020PacketCarTelemetryData cartelemetry = factory.CreatePacketCarTelemetryData(bb);
          body = gson.toJson(cartelemetry);
          payload = osm.Build(header.PacketId + "," + header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          return;
        case 7:
          F12020PacketCarStatusData carstatus = factory.CreatePacketCarStatusData(bb);
          body = gson.toJson(carstatus);
          payload = osm.Build(header.PacketId + "," + header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          return;
        case 8:
          F12020PacketFinalClassificationData finalClassification = factory.CreatePacketFinalClassificationData(bb);
          body = gson.toJson(finalClassification);
          payload = osm.Build(header.PacketId + "," + header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          return;
        case 9:
          F12020PacketLobbyInfoData lobby = factory.CreatePacketLobbyInfoData(bb);
          body = gson.toJson(lobby);
          payload = osm.Build(header.PacketId + "," + header.SessionUID.toString(), body);
          streaming.SendMessage(payload);
          return;
        default:
          System.out.println("INVALID PACKET:");
          System.out.println(header.PacketId);
      }
    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    }
  }
}
