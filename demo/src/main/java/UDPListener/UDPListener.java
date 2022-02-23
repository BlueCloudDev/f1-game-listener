package UDPListener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.google.gson.Gson;

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

public class UDPListener {
  private boolean stop = false;
  private int port = 20777;
  private static int MAX_BUFFER = 2048;

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

  public void ReadPacket(byte[] bytes) {
    ByteBuffer bb = ByteBuffer.wrap(bytes);
    bb.order(ByteOrder.LITTLE_ENDIAN);
    Gson gson = new Gson();

    F12020PacketFactory factory = new F12020PacketFactory();
    F12020PacketHeader header = factory.CreatePacketHeader(bb);

    switch (header.PacketId) {

      case 0:
        F12020PacketMotionData motion = factory.CreatePacketMotionData(bb);
        System.out.println(motion);
        try {
          OCIStreaming streaming = new OCIStreaming();
          streaming.SendMessage(gson.toJson(motion));
        } catch (Exception ex){
          ex.printStackTrace();
        }

        // System.out.println(packet.toString());
        // session.setMotionData((PacketMotionData) packet);
        break;
      case 1:
        F12020PacketSessionData session = factory.CreatePacksetSessionData(bb);
        System.out.println(session);
        // System.out.println(packet.toString());
        // session.setSessionData((PacketSessionData) packet);
        break;
      case 2:
        F12020PacketLapData lap = factory.CreatePacketLapData(bb);
        System.out.println(lap);
        // System.out.println(packet.toString());
        // session.setLapData((PacketLapData) packet);
        break;
      case 3:
        F12020PacketEventData event = factory.CreatePacketEventData(bb);
        System.out.println(event);
        // System.out.println(packet.toString());
        // session.setEventAction((PacketEventData) packet);
        break;
      case 4:
        F12020PacketParticipantData participants = factory.CreatePacketParticipantData(bb);
        System.out.println(participants);
        // System.out.println(packet.toString());
        // session.setParticipantsData((PacketParticipantsData) packet);
        break;
      case 5:
        F12020PacketCarSetupData carsetup = factory.CreatePacketCarSetupData(bb);
        System.out.println(carsetup);
        // System.out.println(packet.toString());
        // session.setCarSetupData((PacketCarSetupData) packet);
        break;
      case 6:
        F12020PacketCarTelemetryData cartelemetry = factory.CreatePacketCarTelemetryData(bb);
        System.out.println(cartelemetry);
        // System.out.println(packet.toString());
        // session.setCarTelemetryData((PacketCarTelemetryData) packet);
        break;
      case 7:
        F12020PacketCarStatusData carstatus = factory.CreatePacketCarStatusData(bb);
        System.out.println(carstatus);
        // System.out.println(packet.toString());
        // session.setCarStatusData((PacketCarStatusData) packet);
        break;  
      case 8:
        F12020PacketFinalClassificationData finalClassification = factory.CreatePacketFinalClassificationData(bb);
        System.out.println(finalClassification);
      case 9:
        F12020PacketLobbyInfoData lobby = factory.CreatePacketLobbyInfoData(bb);
        System.out.println(lobby);
    }
  }
}
