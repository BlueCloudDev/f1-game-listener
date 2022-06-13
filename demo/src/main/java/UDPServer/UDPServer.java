package UDPServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import F12021Packet.F12021LapData;
import F12021Packet.F12021PacketLapData;
import F12021Packet.F12021PacketParticipantData;
import F12021Packet.F12021PacketSessionData;
import F12021Packet.F12021ParticipantData;
import Converter.GameDataConverter2021;

public class UDPServer implements Runnable {
  private static final Logger logger = LogManager.getLogger(UDPServer.class);
  private static DatagramSocket socket;
  private boolean running;
  private byte[] buf = new byte[256];
  public static HashMap<String, LocalTime> clients = new HashMap<String, LocalTime>();
  public static HashMap<Integer, UDPData> DriverData = new HashMap<Integer, UDPData>();

  public UDPServer()  throws SocketException {
    if (socket == null) {
      socket = new DatagramSocket(4445);  
    }
  }

  public synchronized void UpdateParticipants(F12021PacketParticipantData participantData) {
    for (F12021ParticipantData p : participantData.ParticipantData) {
      if (p == null) {
        continue;
      }
      var gdc = new GameDataConverter2021();
      if (!DriverData.containsKey(p.Index)) {
        var data = new UDPData();
        data.Name = p.Name.replace("\u0000", "");
        data.DriverID = gdc.DriverId(p.DriverId);
        data.TeamID = gdc.Team(p.TeamId);
        DriverData.put(p.Index, data);
      }
    }
  }

  public synchronized void UpdateLapData(F12021PacketLapData lapData) throws IOException {
    for (F12021LapData l : lapData.LapData) {
      if (lapData == null) {
        continue;
      }
      if (DriverData.containsKey(l.Index)) {
        var data = DriverData.get(l.Index);
        data.LapNumber = l.CurrentLapNum;
        data.LapDistance = l.LapDistance;
        data.LastLapInMS = l.LastLapTime;
        DriverData.put(l.Index, data);
      }
    }
  }

  public synchronized void UpdateSession(F12021PacketSessionData sessionData) {
    GameDataConverter2021 gdc = new GameDataConverter2021();
    for (Entry<Integer, UDPData> set : DriverData.entrySet()) {
      var data = set.getValue();
      if (data.TrackLength == sessionData.TrackLength) {
        break;
      }
      data.TrackLength = sessionData.TrackLength;
      data.TrackName = gdc.TrackIDs(sessionData.TrackID);
      DriverData.put(set.getKey(), data);
    }
  }

  public synchronized Collection<UDPData> GetValuesArray() {
    return DriverData.values();
  }

  @Override
  public void run() {
    try {
      running = true;
  
      while (running) {
        var gson = new Gson();
        var arr = GetValuesArray();
        var json = gson.toJson(arr);
        var jsonBytes = json.getBytes();
        for (Entry<String, LocalTime> set : clients.entrySet()) {
          var clientIp = set.getKey();
          InetAddress address = InetAddress.getByName(clientIp);
          var packet = new DatagramPacket(jsonBytes, jsonBytes.length, address, 4445);
          socket.send(packet);
        }
        Thread.sleep(16);
      }
    } catch (Exception ex) {
      String stackTrace = ExceptionUtils.getStackTrace(ex);
      logger.error(stackTrace);
    } finally {
      //socket.close();
    }
  }
}
