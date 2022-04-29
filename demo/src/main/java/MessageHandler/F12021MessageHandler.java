package MessageHandler;
import Repository.F12021.CarDamageRepository2021;
import Repository.F12021.CarSetupDataRepository2021;
import Repository.F12021.CarStatusDataRepository2021;
import Repository.F12021.CarTelemetryRepository2021;
import Repository.F12021.FinalClassificationRepository2021;
import Repository.F12021.LapDataRepository2021;
import Repository.F12021.MotionDataRepository2021;
import Repository.F12021.ParticipantDataRepository2021;
import Repository.F12021.SessionDataRepository2021;
import Repository.F12021.SessionHistoryDataRepository2021;
import Repository.F12021.SessionLookupRepository2021;
import oracle.ucp.jdbc.PoolDataSource;
import F12021Packet.F12021PacketCarDamageData;
import F12021Packet.F12021PacketCarSetupData;
import F12021Packet.F12021PacketCarStatusData;
import F12021Packet.F12021PacketCarTelemetryData;
import F12021Packet.F12021PacketFinalClassificationData;
import F12021Packet.F12021PacketHeader;
import F12021Packet.F12021PacketLapData;
import F12021Packet.F12021PacketMotionData;
import F12021Packet.F12021PacketParticipantData;
import F12021Packet.F12021PacketSessionData;
import F12021Packet.F12021PacketSessionHistoryData;
import F12021Packet.F12021SessionLookup;

import com.google.gson.Gson;

import org.json.JSONArray;


public class F12021MessageHandler {
  public void ProcessMessage(F12021PacketHeader header, long packetHeaderId, JSONArray ja, PoolDataSource pds) {
    Gson gson = new Gson();
    F12021SessionLookup lookup = new F12021SessionLookup();
    lookup.PlayerName = header.PlayerName;
    lookup.SessionUID = header.SessionUID;
    lookup.FrameIdentifier = header.FrameIdentifier;
    lookup.SessionTime = header.SessionTime;
    lookup.PlayerCarIndex = header.PlayerCarIndex;
    SessionLookupRepository2021 lkrepo = new SessionLookupRepository2021();
    int lookupID = 0;
    lookupID = lkrepo.SelectSessionLookupIDBySessionUIDAndFrameIdentifier(lookup.SessionUID, lookup.FrameIdentifier, pds);
    if (lookupID == 0) {
      lookupID = lkrepo.InsertSessionLookup(lookup, pds);
    }
    lookup.Id = lookupID;
    switch (header.PacketId) {
      case 0:
        F12021PacketMotionData p = gson.fromJson(ja.get(1).toString(), F12021PacketMotionData.class);
        MotionDataRepository2021 mrepo = new MotionDataRepository2021();
        for(int i = 0; i < p.CarMotionData.length; i++) {
          p.CarMotionData[i].SessionLookupID = lookup.Id;
          long mdid = mrepo.InsertMotionData(packetHeaderId, p.CarMotionData[i], pds);
          if (i == (int)header.PlayerCarIndex){
            mrepo.InsertMotionDataPlayer(mdid, p, pds);
          }
        }
        break;
      case 1:
        F12021PacketSessionData p1 = gson.fromJson(ja.get(1).toString(), F12021PacketSessionData.class);
        SessionDataRepository2021 repo = new SessionDataRepository2021();
        p1.SessionLookupID = lookup.Id;
        repo.InsertSessionData(packetHeaderId, p1, pds);
        break;
      case 2:
        F12021PacketLapData p2 = gson.fromJson(ja.get(1).toString(), F12021PacketLapData.class);
        LapDataRepository2021 repo2 = new LapDataRepository2021();
        for(int i = 0; i < p2.LapData.length; i++) {
          if (p2.LapData[i] != null) {
            p2.LapData[i].SessionLookupID = lookup.Id;
            repo2.InsertLapData(packetHeaderId, p2.LapData[i], pds);
          }
        }
        break;
      case 3:
        //F12020PacketEventData p3 = gson.fromJson(ja.get(1).toString(), F12020PacketEventData.class);
        break;
      case 4:
        F12021PacketParticipantData p4 = gson.fromJson(ja.get(1).toString(), F12021PacketParticipantData.class);
        ParticipantDataRepository2021 repo3 = new ParticipantDataRepository2021();
        for(int i = 0; i < p4.NumActiveCars; i++) {
          if (p4.ParticipantData[i] != null) {
            p4.ParticipantData[i].SessionLookupID = lookup.Id;
            repo3.InsertParticipantData(packetHeaderId, p4.ParticipantData[i], pds, p4.NumActiveCars);
          }
        }
        break;
      case 5:
        F12021PacketCarSetupData p5 = gson.fromJson(ja.get(1).toString(), F12021PacketCarSetupData.class);
        CarSetupDataRepository2021 repo4 = new CarSetupDataRepository2021();
        for(int i = 0; i < p5.CarSetups.length; i++) {
          if (p5.CarSetups[i] != null) {
            p5.CarSetups[i].SessionLookupID = lookup.Id;
            repo4.InsertCarSetupData(packetHeaderId, p5.CarSetups[i], pds);
          }
        }
        break;
      case 6:
        F12021PacketCarTelemetryData p6 = gson.fromJson(ja.get(1).toString(), F12021PacketCarTelemetryData.class);
        CarTelemetryRepository2021 repo5 = new CarTelemetryRepository2021();
        for(int i = 0; i < p6.CarTelemetryData.length; i++) {
          if (p6.CarTelemetryData[i] != null) {
            p6.CarTelemetryData[i].SessionLookupID = lookup.Id;
            repo5.InsertCarTelemetryData(packetHeaderId, p6.CarTelemetryData[i], pds);
          }
        }
        break;
      case 7:
        F12021PacketCarStatusData p7 = gson.fromJson(ja.get(1).toString(), F12021PacketCarStatusData.class);
        CarStatusDataRepository2021 repo6 = new CarStatusDataRepository2021();
        for(int i = 0; i < p7.CarStatusData.length; i++) {
          if (p7.CarStatusData[i] != null) {
            p7.CarStatusData[i].SessionLookupID = lookup.Id;
            repo6.InsertCarStatusData(packetHeaderId, p7.CarStatusData[i], pds);
          }
        }
        break;  
      case 8:
        F12021PacketFinalClassificationData p8 = gson.fromJson(ja.get(1).toString(), F12021PacketFinalClassificationData.class);
        FinalClassificationRepository2021 repo7 = new FinalClassificationRepository2021();
        for(int i = 0; i < p8.ClassificationData.length; i++) {
          if (p8.ClassificationData[i] != null) {
            p8.ClassificationData[i].SessionLookupID = lookup.Id;
            repo7.InsertFinalClassification(packetHeaderId, p8.ClassificationData[i], pds);
          }
        }
        break;
      case 9:
        //F12020LobbyInfoData p9 = gson.fromJson(ja.get(1).toString(), F12020LobbyInfoData.class);
        break;
      case 10:
        F12021PacketCarDamageData p10 = gson.fromJson(ja.get(1).toString(), F12021PacketCarDamageData.class);
        CarDamageRepository2021 repo9 = new CarDamageRepository2021();
        for(int i = 0; i < p10.CarDamageData.length; i++) {
          if (p10.CarDamageData != null) {
            p10.CarDamageData[i].SessionLookupID = lookup.Id;
            repo9.InsertCarDamage(packetHeaderId, p10.CarDamageData[i], pds);
          }
        }
      case 11:
        F12021PacketSessionHistoryData p11 = gson.fromJson(ja.get(1).toString(), F12021PacketSessionHistoryData.class);
        SessionHistoryDataRepository2021 repo10 = new SessionHistoryDataRepository2021();
        p11.SessionLookupID = lookup.Id;
        repo10.InsertSessionData(header.SessionUID, p11, pds);
    }
  }
}
