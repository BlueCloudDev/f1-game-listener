package MessageHandler;

import com.google.gson.Gson;

import org.json.JSONArray;
import F12020Packet.F12020CarSetupData;
import F12020Packet.F12020CarStatusData;
import F12020Packet.F12020CarTelemetryData;
import F12020Packet.F12020LapData;
import F12020Packet.F12020LobbyInfoData;
import F12020Packet.F12020PacketCarSetupData;
import F12020Packet.F12020PacketCarStatusData;
import F12020Packet.F12020PacketCarTelemetryData;
import F12020Packet.F12020PacketEventData;
import F12020Packet.F12020PacketFinalClassificationData;
import F12020Packet.F12020PacketLapData;
import F12020Packet.F12020PacketMotionData;
import F12020Packet.F12020PacketParticipantData;
import F12020Packet.F12020PacketSessionData;
import F12020Packet.F12020ParticipantData;
import F12021Packet.F12021PacketHeader;
import Repository.F12020.CarSetupDataRepository;
import Repository.F12020.CarStatusDataRepository;
import Repository.F12020.CarTelemetryRepository;
import Repository.F12020.LapDataRepository;
import Repository.F12020.MotionDataRepository;
import Repository.F12020.ParticipantDataRepository;
import Repository.F12020.SessionDataRepository;
import oracle.ucp.jdbc.PoolDataSource;

public class F12020MessageHandler {
  public void ProcessMessage(F12021PacketHeader header, long packetHeaderId, JSONArray ja, PoolDataSource pds) {
    Gson gson = new Gson();
    switch (header.PacketId) {
      case 0:
        F12020PacketMotionData p = gson.fromJson(ja.get(1).toString(), F12020PacketMotionData.class);
        MotionDataRepository mrepo = new MotionDataRepository();
        for(int j = 0; j < p.CarMotionData.length; j++) {
          long mdid = mrepo.InsertMotionData(packetHeaderId, p.CarMotionData[j], pds);
          if (j == (int)header.PlayerCarIndex){
            mrepo.InsertMotionDataPlayer(mdid, p, pds);
          }
        }
        break;
      case 1:
        F12020PacketSessionData p1 = gson.fromJson(ja.get(1).toString(), F12020PacketSessionData.class);
        SessionDataRepository repo = new SessionDataRepository();
        repo.InsertSessionData(packetHeaderId, p1, pds);
        break;
      case 2:
        F12020PacketLapData p2 = gson.fromJson(ja.get(1).toString(), F12020PacketLapData.class);
        LapDataRepository repo2 = new LapDataRepository();
        for (F12020LapData data : p2.LapData) {
          repo2.InsertLapData(packetHeaderId, data, pds);
        }
        break;
      case 3:
        F12020PacketEventData p3 = gson.fromJson(ja.get(1).toString(), F12020PacketEventData.class);
        break;
      case 4:
        F12020PacketParticipantData p4 = gson.fromJson(ja.get(1).toString(), F12020PacketParticipantData.class);
        ParticipantDataRepository repo3 = new ParticipantDataRepository();
        for (F12020ParticipantData data : p4.ParticipantData) {
          if (data != null) {
            repo3.InsertParticipantData(packetHeaderId, data, pds);
          }
        }
        break;
      case 5:
        F12020PacketCarSetupData p5 = gson.fromJson(ja.get(1).toString(), F12020PacketCarSetupData.class);
        CarSetupDataRepository repo4 = new CarSetupDataRepository();
        for (F12020CarSetupData data : p5.CarSetups) {
          repo4.InsertCarSetupData(packetHeaderId, data, pds);
        }
        break;
      case 6:
        F12020PacketCarTelemetryData p6 = gson.fromJson(ja.get(1).toString(), F12020PacketCarTelemetryData.class);
        CarTelemetryRepository repo5 = new CarTelemetryRepository();
        for (F12020CarTelemetryData data : p6.CarTelemetryData) {
          if (data != null) {
            repo5.InsertCarTelemetryData(packetHeaderId, data, pds);
          }
        }
        break;
      case 7:
        F12020PacketCarStatusData p7 = gson.fromJson(ja.get(1).toString(), F12020PacketCarStatusData.class);
        CarStatusDataRepository repo6 = new CarStatusDataRepository();
        for (F12020CarStatusData data : p7.CarStatusData) {
          repo6.InsertCarStatusData(packetHeaderId, data, pds);
        }
        break;  
      case 8:
        F12020PacketFinalClassificationData p8 = gson.fromJson(ja.get(1).toString(), F12020PacketFinalClassificationData.class);
        break;
      case 9:
        F12020LobbyInfoData p9 = gson.fromJson(ja.get(1).toString(), F12020LobbyInfoData.class);
        break;
    }
  }
}
