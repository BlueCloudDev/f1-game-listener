package F12020Packet;

import Converter.Converter;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import Configuration.Configuration;

public class F12020PacketFactory {
  public Converter converter = new Converter();
  public final String PLAYER_NAME = Configuration.EnvVars.get("PLAYER_NAME");

  public F12020PacketHeader CreatePacketHeader(ByteBuffer bb){
    F12020PacketHeader p = new F12020PacketHeader();
    p.PacketFormat = converter.Uint16(bb.getShort());
    p.PlayerName = PLAYER_NAME;
    p.GameMajorVersion = converter.Uint8(bb.get());
    p.GameMinorVersion = converter.Uint8(bb.get());
    p.PacketVersion = converter.Uint8(bb.get());
    p.PacketId = converter.Uint8(bb.get());
    p.SessionUID = converter.Uint64(bb.getLong());
    p.SessionTime = bb.getFloat();
    p.FrameIdentifier = converter.Uint32(bb.getInt());
    p.PlayerCarIndex = converter.Uint8(bb.get());
    p.SecondaryPlayerCarIndex = converter.Uint8(bb.get());
    return p;
  }

  public F12020CarMotionData CreateCarMotionData(ByteBuffer bb) {
    F12020CarMotionData p = new F12020CarMotionData();
    p.WorldPositionX = bb.getFloat();
    p.WorldPositionY = bb.getFloat();
    p.WorldPositionZ = bb.getFloat();
    p.WorldVelocityX = bb.getFloat();
    p.WorldVelocityY = bb.getFloat();
    p.WorldVelocityZ = bb.getFloat();
    p.WorldForwardDirX = converter.NormalizedVector(bb.getShort());
    p.WorldForwardDirY = converter.NormalizedVector(bb.getShort());
    p.WorldForwardDirZ = converter.NormalizedVector(bb.getShort());
    p.WorldRightDirX = converter.NormalizedVector(bb.getShort());
    p.WorldRightDirY = converter.NormalizedVector(bb.getShort());
    p.WorldRightDirZ = converter.NormalizedVector(bb.getShort());
    p.GForceLateral = bb.getFloat();
    p.GForceLongitudinal = bb.getFloat();
    p.GForceVertical = bb.getFloat();
    p.Yaw = bb.getFloat();
    p.Pitch = bb.getFloat();
    p.Roll = bb.getFloat();
    return p;
  }

  public F12020PacketMotionData CreatePacketMotionData(ByteBuffer bb) {
    F12020PacketMotionData p = new F12020PacketMotionData();
    for(int i = 0; i < p.CarMotionData.length; i++){
      F12020CarMotionData c = CreateCarMotionData(bb);
      p.CarMotionData[i] = c;
      p.CarMotionData[i].Index = i;
    }
    p.SuspensionPosition = getFloatWheelData(bb);
    p.SuspensionVelocity = getFloatWheelData(bb);
    p.SuspensionAcceleration = getFloatWheelData(bb);
    p.WheelSpeed = getFloatWheelData(bb);
    p.WheelSlip = getFloatWheelData(bb);
    p.LocalVelocityX = bb.getFloat();
    p.LocalVelocityY = bb.getFloat();
    p.LocalVelocityZ = bb.getFloat();
    p.AngularVelocityX = bb.getFloat();
    p.AngularVelocityY = bb.getFloat();
    p.AngularVelocityZ = bb.getFloat();
    p.AngularAccelerationX = bb.getFloat();
    p.AngularAccelerationY = bb.getFloat();
    p.AngularAccelerationZ = bb.getFloat();
    p.FrontWheelsAngle = bb.getFloat();
    
    return p;
  }

  public F12020MarshalZone CreateMarshalZone(ByteBuffer bb) {
    F12020MarshalZone p = new F12020MarshalZone();
    p.ZoneStart = bb.getFloat();
    p.ZoneFlag = bb.get();
    return p;
  }

  public F12020WeatherForecastSample CreateWeatherForecastSample(ByteBuffer bb) {
    F12020WeatherForecastSample p = new F12020WeatherForecastSample();
    p.SessionType = converter.Uint8(bb.get());
    p.TimeOffset = converter.Uint8(bb.get());
    p.Weather = converter.Uint8(bb.get());
    p.TrackTemperature = bb.get();
    p.AirTemperature = bb.get();
    return p;
  }

  public F12020PacketSessionData CreatePacksetSessionData(ByteBuffer bb) {
    F12020PacketSessionData p = new F12020PacketSessionData();
    p.Weather = converter.Uint8(bb.get());
    p.TrackTemperature = bb.get();
    p.AirTemperature = bb.get();
    p.TotalLaps = converter.Uint8(bb.get());
    p.TrackLength = converter.Uint16(bb.getShort());
    p.SessionType = converter.Uint8(bb.get());
    p.TrackID = bb.get();
    p.Formula = converter.Uint8(bb.get());
    p.SessionTimeLeft = converter.Uint16(bb.getShort());
    p.SessionDuration = converter.Uint16(bb.getShort());
    p.PitSpeedLimit = converter.Uint8(bb.get());
    p.GamePaused = converter.Uint8(bb.get());
    p.IsSpectating = converter.Uint8(bb.get());
    p.SpectatorCarIndex = converter.Uint8(bb.get());
    p.SliProNativeSupport = converter.Uint8(bb.get());
    p.NumMarshalZones = converter.Uint8(bb.get());
    for (int i = 0; i < p.NumMarshalZones; i++) {
      p.MarshalZones[i] = CreateMarshalZone(bb);
    }
    p.SafetyCarStatus = converter.Uint8(bb.get());
    p.NetworkGame = converter.Uint8(bb.get());
    p.NumWeatherForecastSamples = converter.Uint8(bb.get());
    for (int i = 0; i < p.NumWeatherForecastSamples; i++) {
      p.WeatherForecastSamples[i] = CreateWeatherForecastSample(bb);
    }
    return p;
  }
  public F12020LapData CreateLapData(ByteBuffer bb) {
    F12020LapData p = new F12020LapData();
    p.LastLapTime = bb.getFloat();
    p.CurrentLapTime = bb.getFloat();
    p.Sector1TimeInMS = converter.Uint16(bb.getShort());
    p.Sector2TimeInMS = converter.Uint16(bb.getShort());
    p.BestLapTime = bb.getFloat();
    p.BestLapNum = converter.Uint8(bb.get());
    p.BestLapSector1TimeInMS = converter.Uint16(bb.getShort());
    p.BestLapSector2TimeInMS = converter.Uint16(bb.getShort());
    p.BestLapSector3TimeInMS = converter.Uint16(bb.getShort());
    p.BestOverallSector1TimeInMS = converter.Uint16(bb.getShort());
    p.BestOverallSector1LapNum = converter.Uint8(bb.get());
    p.BestOverallSector2TimeInMS = converter.Uint16(bb.getShort());
    p.BestOverallSector2LapNum = converter.Uint8(bb.get());
    p.BestOverallSector3TimeInMS = converter.Uint16(bb.getShort());
    p.BestOverallSector3LapNum = converter.Uint8(bb.get());
    p.LapDistance = bb.getFloat();
    p.TotalDistance = bb.getFloat();
    p.SafetyCarDelta = bb.getFloat();
    p.CarPosition = converter.Uint8(bb.get());
    p.CurrentLapNum = converter.Uint8(bb.get());
    p.PitStatus = converter.Uint8(bb.get());
    p.Sector = converter.Uint8(bb.get());
    p.CurrentLapInvalid = converter.Uint8(bb.get());
    p.Penalties = converter.Uint8(bb.get());
    p.GridPosition = converter.Uint8(bb.get());
    p.DriverStatus = converter.Uint8(bb.get());
    p.ResultStatus = converter.Uint8(bb.get());
    return p;
  }

  public F12020PacketLapData CreatePacketLapData(ByteBuffer bb) {
    F12020PacketLapData p = new F12020PacketLapData();
    for(int i = 0; i < p.LapData.length; i++) {
      p.LapData[i] = CreateLapData(bb);
      p.LapData[i].Index = i;
    }
    return p;
  }

  public F12020EventFastestLap CreateEventFastestLap(ByteBuffer bb) {
    F12020EventFastestLap p = new F12020EventFastestLap();
    p.VehicleIdx = converter.Uint8(bb.get());
    p.LapTime = bb.getFloat();
    return p;
  }

  public F12020EventRetirement CreateEventRetirement(ByteBuffer bb) {
    F12020EventRetirement p = new F12020EventRetirement();
    p.VehicleIdx = converter.Uint8(bb.get());
    return p;
  }

  public F12020EventTeamMateInPits CreateEventTeamMateInPits(ByteBuffer bb) {
    F12020EventTeamMateInPits p = new F12020EventTeamMateInPits();
    p.VehicleIdx = converter.Uint8(bb.get());
    return p;
  }

  public F12020EventRaceWinner CreateEventRaceWinner(ByteBuffer bb) {
    F12020EventRaceWinner p = new F12020EventRaceWinner();
    p.VehicleIdx = converter.Uint8(bb.get());
    return p;
  }

  public F12020EventPenalty CreateEventPenalty(ByteBuffer bb) {
    F12020EventPenalty p = new F12020EventPenalty();
    p.PenaltyType = converter.Uint8(bb.get());
    p.InfringementType = converter.Uint8(bb.get());
    p.VehicleIdx = converter.Uint8(bb.get());
    p.OtherVehicleIdx = converter.Uint8(bb.get());
    p.Time = converter.Uint8(bb.get());
    p.LapNum = converter.Uint8(bb.get());
    p.PlacesGained = converter.Uint8(bb.get());
    return p;
  }

  public F12020EventSpeedTrap CreateEventSpeedTrap(ByteBuffer bb) {
    F12020EventSpeedTrap p = new F12020EventSpeedTrap();
    p.VehicleIdx = converter.Uint8(bb.get());
    p.Speed = bb.getFloat();
    return p;
  }

  public F12020PacketEventData CreatePacketEventData(ByteBuffer bb) {
    F12020PacketEventData p = new F12020PacketEventData();
    byte[] bytes = new byte[4];
    for(int i = 0; i < bytes.length; i++) {
      bytes[i] = bb.get();
    }
    p.EventStringCode = new String(bytes, StandardCharsets.UTF_8);
    if (p.EventStringCode == "SSTA") {
      System.out.println("Session Started");
    } else if (p.EventStringCode == "SEND") {
      System.out.println("Session Ended");
    } else if (p.EventStringCode == "FTLP") {
      F12020EventFastestLap ftlp = CreateEventFastestLap(bb);
      System.out.println(ftlp);
    } else if (p.EventStringCode == "RTMT") {
      F12020EventRetirement rtmt = CreateEventRetirement(bb);
      System.out.println(rtmt);
    } else if (p.EventStringCode == "DRSE") {
      System.out.println("Race control enabled DRS");
    } else if (p.EventStringCode == "DRSD") {
      System.out.println("Race control disabled DRS");
    } else if (p.EventStringCode == "TMPT") {
      F12020EventTeamMateInPits tmpt = CreateEventTeamMateInPits(bb);
      System.out.println(tmpt);
    } else if (p.EventStringCode == "CHQF") {
      System.out.println("The chequered flag has been waved");
    } else if (p.EventStringCode == "RCWN"){
      F12020EventRaceWinner rcwn = CreateEventRaceWinner(bb);
      System.out.println(rcwn);
    } else if (p.EventStringCode == "PENA"){
      F12020EventPenalty pena = CreateEventPenalty(bb);
      System.out.println(pena);
    } else if (p.EventStringCode == "SPTP") {
      F12020EventSpeedTrap sptp = CreateEventSpeedTrap(bb);
      System.out.println(sptp);
    }
    return p;
  }

  public F12020ParticipantData CreateParticipantData(ByteBuffer bb) throws UnsupportedEncodingException {
    F12020ParticipantData p = new F12020ParticipantData();
    p.AiControlled = converter.Uint8(bb.get());
    p.DriverId = converter.Uint8(bb.get());
    p.TeamId = converter.Uint8(bb.get());
    p.RaceNumber = converter.Uint8(bb.get());
    p.Nationality = converter.Uint8(bb.get());
    p.Name = getName(bb);
    p.YourTelemetry = converter.Uint8(bb.get());
    return p;
  }

  public F12020PacketParticipantData CreatePacketParticipantData(ByteBuffer bb) throws UnsupportedEncodingException  {
    F12020PacketParticipantData p = new F12020PacketParticipantData();
    p.NumActiveCars = converter.Uint8(bb.get());
    for (int i = 0; i < p.NumActiveCars; i++) {
      p.ParticipantData[i] = CreateParticipantData(bb);
      p.ParticipantData[i].Index = i;
    }
    return p;
  }

  public F12020CarSetupData CreateCarSetupData(ByteBuffer bb) {
    F12020CarSetupData p = new F12020CarSetupData();
    p.FrontWing = converter.Uint8(bb.get());
    p.RearWing = converter.Uint8(bb.get());
    p.OnThrottle = converter.Uint8(bb.get());
    p.OffThrottle = converter.Uint8(bb.get());
    p.FrontCamber = bb.getFloat();
    p.RearCamber = bb.getFloat();
    p.FrontToe = bb.getFloat();
    p.RearToe = bb.getFloat();
    p.FrontSuspension = converter.Uint8(bb.get());
    p.RearSuspension = converter.Uint8(bb.get());
    p.FrontAntiRollBar = converter.Uint8(bb.get());
    p.RearAntiRollBar = converter.Uint8(bb.get());
    p.FrontSuspensionHeight = converter.Uint8(bb.get());
    p.RearSuspensionHeight = converter.Uint8(bb.get());
    p.BrakePressure = converter.Uint8(bb.get());
    p.BrakeBias = converter.Uint8(bb.get());
    p.RearLeftTyrePressure = bb.getFloat();
    p.RearRightTyrePressure = bb.getFloat();
    p.FrontLeftTyrePressure = bb.getFloat();
    p.FrontRightTyrePressure = bb.getFloat();
    p.Ballast = converter.Uint8(bb.get());
    p.FuelLoad = bb.getFloat();
    return p;
  }

  public F12020PacketCarSetupData CreatePacketCarSetupData(ByteBuffer bb) {
    F12020PacketCarSetupData p = new F12020PacketCarSetupData();
    for(int i = 0; i < p.CarSetups.length; i++) {
      p.CarSetups[i] = CreateCarSetupData(bb);
      p.CarSetups[i].Index = i;
    }
    return p;
  }

  public F12020CarTelemetryData CreateCarTelemetryData(ByteBuffer bb) {
    F12020CarTelemetryData p = new F12020CarTelemetryData();
    p.Speed = converter.Uint16(bb.getShort());
    p.Throttle = bb.getFloat();
    p.Steer = bb.getFloat();
    p.Brake = bb.getFloat();
    p.Clutch = converter.Uint8(bb.get());
    p.Gear = converter.Uint8(bb.get());
    p.EngineRPM = converter.Uint16(bb.getShort());
    p.DRS = converter.Uint8(bb.get());
    p.RevLightsPercent = converter.Uint8(bb.get());
    p.BrakesTemperature = getShortWheelDataUint16(bb);
    p.TyresSurfaceTemperature = getShortWheelDataUint8(bb);
    p.TyresInnerTemperature = getShortWheelDataUint8(bb);
    p.EngineTemperature = converter.Uint16(bb.get());
    p.TyresPressure = getFloatWheelData(bb);
    p.SurfaceType = getShortWheelDataUint8(bb);
    return p;
  }

  public F12020PacketCarTelemetryData CreatePacketCarTelemetryData(ByteBuffer bb) {
    F12020PacketCarTelemetryData p = new F12020PacketCarTelemetryData();
    for(int i = 0; i < p.CarTelemetryData.length; i++) {
      p.CarTelemetryData[i] = CreateCarTelemetryData(bb);
      p.CarTelemetryData[i].Index = i;
    }
    p.ButtonStatus = converter.Uint32(bb.getInt());
    p.MFDPanelIndex = converter.Uint8(bb.get());
    p.MFDPanelIndexSecondaryPlayer = converter.Uint8(bb.get());
    p.SuggestedGear = bb.getShort();
    return p;
  }

  public F12020CarStatusData CreateCarStatusData(ByteBuffer bb) {
    F12020CarStatusData p = new F12020CarStatusData();
    p.TractionControl = converter.Uint8(bb.get());
    p.AntiLockBrakes = converter.Uint8(bb.get());
    p.FuelMix = converter.Uint8(bb.get());
    p.FrontBrakeBias = converter.Uint8(bb.get());
    p.PitLimiterStatus = converter.Uint8(bb.get());
    p.FuelInTank = bb.getFloat();
    p.FuelCapacity = bb.getFloat();
    p.FuelRemainingLaps = bb.getFloat();
    p.MaxRPM = converter.Uint16(bb.getShort());
    p.IdleRPM = converter.Uint16(bb.getShort());
    p.MaxGears = converter.Uint8(bb.get());
    p.DRSAllowed = converter.Uint8(bb.get());
    p.DRSActivationDistance = converter.Uint16(bb.getShort());
    p.TyresWear = getShortWheelDataUint8(bb);
    p.ActualTyreCompound = converter.Uint8(bb.get());
    p.VisualTyreCompound = converter.Uint8(bb.get());
    p.TyresAgeLaps = converter.Uint8(bb.get());
    p.TyresDamage = getShortWheelDataUint8(bb);
    p.FrontLeftWingDamage = converter.Uint8(bb.get());
    p.FrontRightWingDamage = converter.Uint8(bb.get());
    p.RearWingDamage = converter.Uint8(bb.get());
    p.DRSFault = converter.Uint8(bb.get());
    p.EngineDamage = converter.Uint8(bb.get());
    p.GearBoxDamage = converter.Uint8(bb.get());
    p.VehicleFiaFlags = bb.getShort();
    p.ERSStoreEnergy = bb.getFloat();
    p.ERSDeployMode = converter.Uint8(bb.get());
    p.ERSHarvestedThisLapMGUK = bb.getFloat();
    p.ERSHarvestedThisLapMGUH = bb.getFloat();
    p.ERSDeployedThisLap = bb.getFloat();
    return p;
  }

  public F12020PacketCarStatusData CreatePacketCarStatusData(ByteBuffer bb) {
    F12020PacketCarStatusData p = new F12020PacketCarStatusData();
    for(int i = 0; i < p.CarStatusData.length; i++) {
      p.CarStatusData[i] = CreateCarStatusData(bb);
      p.CarStatusData[i].Index = i;
    }
    return p;
  }

  public F12020FinalClassificationData CreateFinalClassificationData(ByteBuffer bb) {
    F12020FinalClassificationData p = new F12020FinalClassificationData();
    p.Position = converter.Uint8(bb.get());
    p.NumLaps = converter.Uint8(bb.get());
    p.GridPosition = converter.Uint8(bb.get());
    p.Point = converter.Uint8(bb.get());
    p.NumPitStops = converter.Uint8(bb.get());
    p.ResultStatus = converter.Uint8(bb.get());
    p.BestLapTime = bb.getFloat();
    p.TotalRaceTime = bb.getDouble();
    p.PenaltiesTime = converter.Uint8(bb.get());
    p.NumPenalties = converter.Uint8(bb.get());
    p.NumTyreStints = converter.Uint8(bb.get());
    p.TyreStintsActual = getShortWheelDataUint8(bb, 8);
    p.TyreStintsVisual = getShortWheelDataUint8(bb, 8);
    return p;
  }

  public F12020PacketFinalClassificationData CreatePacketFinalClassificationData(ByteBuffer bb) {
    F12020PacketFinalClassificationData p = new F12020PacketFinalClassificationData();
    p.NumCars = converter.Uint8(bb.get());
    for(int i = 0; i < p.ClassificationData.length; i++) {
      p.ClassificationData[i] = CreateFinalClassificationData(bb);
    }
    return p;
  }

  public F12020LobbyInfoData CreateLobbyInfoData(ByteBuffer bb) throws UnsupportedEncodingException {
    F12020LobbyInfoData p = new F12020LobbyInfoData();
    p.AiControlled = converter.Uint8(bb.get());
    p.TeamId = converter.Uint8(bb.get());
    p.Nationality = converter.Uint8(bb.get());
    p.Name = getName(bb);
    p.ReadyStatus = converter.Uint8(bb.get());
    return p;
  }

  public F12020PacketLobbyInfoData CreatePacketLobbyInfoData(ByteBuffer bb) throws UnsupportedEncodingException{
    F12020PacketLobbyInfoData p = new F12020PacketLobbyInfoData();
    p.NumPlayers = converter.Uint8(bb.get());
    for(int i = 0; i < p.LobbyPlayers.length; i++) {
      p.LobbyPlayers[i] = CreateLobbyInfoData(bb);
    }
    return p;
  }

  private String getName(ByteBuffer bb) throws UnsupportedEncodingException {
    byte[] name = new byte[48];
    bb.get(name);
    String nameString = "";
    nameString = new String(name, "UTF-8");
    return nameString;
  }

  private float[] getFloatWheelData(ByteBuffer bb) {
    float[] a = new float[4];
    for(int i = 0; i < a.length; i++) {
      a[i] = bb.getFloat();
    }
    return a;
  }

  private short[] getShortWheelDataUint16(ByteBuffer bb) {
    short[] a = new short[4];
    for(int i = 0; i < a.length; i++) {
      a[i] = converter.Uint16(bb.getShort());
    }
    return a;
  }

  private short[] getShortWheelDataUint8(ByteBuffer bb) {
    short[] a = new short[4];
    for(int i = 0; i < a.length; i++) {
      a[i] = converter.Uint8(bb.get());
    }
    return a;
  }

  private short[] getShortWheelDataUint8(ByteBuffer bb, int count) {
    short[] a = new short[count];
    for(int i = 0; i < a.length; i++) {
      a[i] = converter.Uint8(bb.get());
    }
    return a;
  }
}
