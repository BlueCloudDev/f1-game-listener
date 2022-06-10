package F12021Packet;

import Converter.ByteBufferReader;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import Configuration.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class F12021PacketFactory {
  private static final Logger logger = LogManager.getLogger(F12021PacketFactory.class);
  public ByteBufferReader reader = new ByteBufferReader();
  public final String PLAYER_NAME = Configuration.EnvVars.get("PLAYER_NAME");

  public F12021PacketHeader CreatePacketHeader(ByteBuffer bb){
    F12021PacketHeader p = new F12021PacketHeader();
    p.PlayerName = PLAYER_NAME;
    p.PacketFormat = reader.Uint16(bb);
    p.GameMajorVersion = reader.Uint8(bb);
    p.GameMinorVersion = reader.Uint8(bb);
    p.PacketVersion = reader.Uint8(bb);
    p.PacketId = reader.Uint8(bb);
    p.SessionUID = reader.Uint64(bb);
    p.SessionTime = reader.Float(bb);
    p.FrameIdentifier = reader.Uint32(bb);
    p.PlayerCarIndex = reader.Uint8(bb);
    p.SecondaryPlayerCarIndex = reader.Uint8(bb);
    return p;
  }

  public F12021PacketHeader CreatePacketHeader(ByteBuffer bb, String playerName, int EventId){
    F12021PacketHeader p = new F12021PacketHeader();
    p.PlayerName = playerName;
    p.EventId = EventId;
    p.PacketFormat = reader.Uint16(bb);
    p.GameMajorVersion = reader.Uint8(bb);
    p.GameMinorVersion = reader.Uint8(bb);
    p.PacketVersion = reader.Uint8(bb);
    p.PacketId = reader.Uint8(bb);
    p.SessionUID = reader.Uint64(bb);
    p.SessionTime = reader.Float(bb);
    p.FrameIdentifier = reader.Uint32(bb);
    p.PlayerCarIndex = reader.Uint8(bb);
    p.SecondaryPlayerCarIndex = reader.Uint8(bb);
    return p;
  }

  public F12021CarMotionData CreateCarMotionData(ByteBuffer bb) {
    F12021CarMotionData p = new F12021CarMotionData();
    p.WorldPositionX = reader.Float(bb);
    p.WorldPositionY = reader.Float(bb);
    p.WorldPositionZ = reader.Float(bb);
    p.WorldVelocityX = reader.Float(bb);
    p.WorldVelocityY = reader.Float(bb);
    p.WorldVelocityZ = reader.Float(bb);
    p.WorldForwardDirX = reader.NormalizedVector(bb);
    p.WorldForwardDirY = reader.NormalizedVector(bb);
    p.WorldForwardDirZ = reader.NormalizedVector(bb);
    p.WorldRightDirX = reader.NormalizedVector(bb);
    p.WorldRightDirY = reader.NormalizedVector(bb);
    p.WorldRightDirZ = reader.NormalizedVector(bb);
    p.GForceLateral = reader.Float(bb);
    p.GForceLongitudinal = reader.Float(bb);
    p.GForceVertical = reader.Float(bb);
    p.Yaw = reader.Float(bb);
    p.Pitch = reader.Float(bb);
    p.Roll = reader.Float(bb);
    return p;
  }

  public F12021PacketMotionData CreatePacketMotionData(ByteBuffer bb) {
    F12021PacketMotionData p = new F12021PacketMotionData();
    for(int i = 0; i < p.CarMotionData.length; i++){
      F12021CarMotionData c = CreateCarMotionData(bb);
      p.CarMotionData[i] = c;
      p.CarMotionData[i].Index = i;
    }
    p.SuspensionPosition = getFloatWheelData(bb);
    p.SuspensionVelocity = getFloatWheelData(bb);
    p.SuspensionAcceleration = getFloatWheelData(bb);
    p.WheelSpeed = getFloatWheelData(bb);
    p.WheelSlip = getFloatWheelData(bb);
    p.LocalVelocityX = reader.Float(bb);
    p.LocalVelocityY = reader.Float(bb);
    p.LocalVelocityZ = reader.Float(bb);
    p.AngularVelocityX = reader.Float(bb);
    p.AngularVelocityY = reader.Float(bb);
    p.AngularVelocityZ = reader.Float(bb);
    p.AngularAccelerationX = reader.Float(bb);
    p.AngularAccelerationY = reader.Float(bb);
    p.AngularAccelerationZ = reader.Float(bb);
    p.FrontWheelsAngle = reader.Float(bb);
    
    return p;
  }

  public F12021MarshalZone CreateMarshalZone(ByteBuffer bb) {
    F12021MarshalZone p = new F12021MarshalZone();
    p.ZoneStart = reader.Float(bb);
    p.ZoneFlag = reader.Int8(bb);
    return p;
  }

  public F12021WeatherForecastSample CreateWeatherForecastSample(ByteBuffer bb) {
    F12021WeatherForecastSample p = new F12021WeatherForecastSample();
    p.SessionType = reader.Uint8(bb);
    p.TimeOffset = reader.Uint8(bb);
    p.Weather = reader.Uint8(bb);
    p.TrackTemperature = reader.Int8(bb);
    p.TractTemperatureChange = reader.Int8(bb);
    p.AirTemperature = reader.Int8(bb);
    p.AirTemperatureChange = reader.Int8(bb);
    p.RainPercentage = reader.Uint8(bb);
    return p;
  }

  public F12021PacketSessionData CreatePacksetSessionData(ByteBuffer bb) {
    F12021PacketSessionData p = new F12021PacketSessionData();
    p.Weather = reader.Uint8(bb);
    p.TrackTemperature = reader.Int8(bb);
    p.AirTemperature = reader.Int8(bb);
    p.TotalLaps = reader.Uint8(bb);
    p.TrackLength = reader.Uint16(bb);
    p.SessionType = reader.Uint8(bb);
    p.TrackID = reader.Int8(bb);
    p.Formula = reader.Uint8(bb);
    p.SessionTimeLeft = reader.Uint16(bb);
    p.SessionDuration = reader.Uint16(bb);
    p.PitSpeedLimit = reader.Uint8(bb);
    p.GamePaused = reader.Uint8(bb);
    p.IsSpectating = reader.Uint8(bb);
    p.SpectatorCarIndex = reader.Uint8(bb);
    p.SliProNativeSupport = reader.Uint8(bb);
    p.NumMarshalZones = reader.Uint8(bb);
    for (int i = 0; i < p.NumMarshalZones; i++) {
      p.MarshalZones[i] = CreateMarshalZone(bb);
    }
    p.SafetyCarStatus = reader.Uint8(bb);
    p.NetworkGame = reader.Uint8(bb);
    p.NumWeatherForecastSamples = reader.Uint8(bb);
    for (int i = 0; i < p.NumWeatherForecastSamples; i++) {
      p.WeatherForecastSamples[i] = CreateWeatherForecastSample(bb);
    }
    p.ForecastAccuracy = reader.Uint8(bb);
    p.AIDifficulty = reader.Uint8(bb);
    p.SeasonLinkIdentifier = reader.Uint32(bb);
    p.WeekendLinkIdentifier = reader.Uint32(bb);
    p.SessionLinkIdentifier = reader.Uint32(bb);
    p.PitStopWindowIdealLap = reader.Uint8(bb);
    p.PitStopWindowLatestLap = reader.Uint8(bb);
    p.PitStopRejoinPosition = reader.Uint8(bb);
    p.SteeringAssist = reader.Uint8(bb);
    p.BrakingAssist = reader.Uint8(bb);
    p.GearboxAssist = reader.Uint8(bb);
    p.PitAssist = reader.Uint8(bb);
    p.PitReleaseAssist = reader.Uint8(bb);
    p.ERSAssist = reader.Uint8(bb);
    p.DRSAssist = reader.Uint8(bb);
    p.DynamicRacingLine = reader.Uint8(bb);
    p.DynamicRacingLineType = reader.Uint8(bb);
    return p;
  }
  public F12021LapData CreateLapData(ByteBuffer bb) {
    F12021LapData p = new F12021LapData();
    p.LastLapTime = reader.Uint32(bb);
    p.CurrentLapTime = reader.Uint32(bb);
    p.Sector1TimeInMS = reader.Uint16(bb);
    p.Sector2TimeInMS = reader.Uint16(bb);
    p.LapDistance = reader.Float(bb);
    p.TotalDistance = reader.Float(bb);
    p.SafetyCarDelta = reader.Float(bb);
    p.CarPosition = reader.Uint8(bb);
    p.CurrentLapNum = reader.Uint8(bb);
    p.PitStatus = reader.Uint8(bb);
    p.NumPitStops = reader.Uint8(bb);
    p.Sector = reader.Uint8(bb);
    p.CurrentLapInvalid = reader.Uint8(bb);
    p.Penalties = reader.Uint8(bb);
    p.Warnings = reader.Uint8(bb);
    p.NumUnservedDriveThroughPens = reader.Uint8(bb);
    p.NumUnservedStopGoPens = reader.Uint8(bb);
    p.GridPosition = reader.Uint8(bb);
    p.DriverStatus = reader.Uint8(bb);
    p.ResultStatus = reader.Uint8(bb);
    p.PitLaneTimerActive = reader.Uint8(bb);
    p.PitLaneTimeInLaneInMS = reader.Uint16(bb);
    p.PitStopTimerInMS = reader.Uint16(bb);
    p.PitStopShouldServePen = reader.Uint8(bb);
    return p;
  }

  public F12021PacketLapData CreatePacketLapData(ByteBuffer bb) {
    F12021PacketLapData p = new F12021PacketLapData();
    for(int i = 0; i < p.LapData.length; i++) {
      p.LapData[i] = CreateLapData(bb);
      p.LapData[i].Index = i;
    }
    return p;
  }

  public F12021EventFastestLap CreateEventFastestLap(ByteBuffer bb) {
    F12021EventFastestLap p = new F12021EventFastestLap();
    p.VehicleIdx = reader.Uint8(bb);
    p.LapTime = reader.Float(bb);
    return p;
  }

  public F12021EventRetirement CreateEventRetirement(ByteBuffer bb) {
    F12021EventRetirement p = new F12021EventRetirement();
    p.VehicleIdx = reader.Uint8(bb);
    return p;
  }

  public F12021EventTeamMateInPits CreateEventTeamMateInPits(ByteBuffer bb) {
    F12021EventTeamMateInPits p = new F12021EventTeamMateInPits();
    p.VehicleIdx = reader.Uint8(bb);
    return p;
  }

  public F12021EventRaceWinner CreateEventRaceWinner(ByteBuffer bb) {
    F12021EventRaceWinner p = new F12021EventRaceWinner();
    p.VehicleIdx = reader.Uint8(bb);
    return p;
  }

  public F12021EventPenalty CreateEventPenalty(ByteBuffer bb) {
    F12021EventPenalty p = new F12021EventPenalty();
    p.PenaltyType = reader.Uint8(bb);
    p.InfringementType = reader.Uint8(bb);
    p.VehicleIdx = reader.Uint8(bb);
    p.OtherVehicleIdx = reader.Uint8(bb);
    p.Time = reader.Uint8(bb);
    p.LapNum = reader.Uint8(bb);
    p.PlacesGained = reader.Uint8(bb);
    return p;
  }

  public F12021EventSpeedTrap CreateEventSpeedTrap(ByteBuffer bb) {
    F12021EventSpeedTrap p = new F12021EventSpeedTrap();
    p.VehicleIdx = reader.Uint8(bb);
    p.Speed = reader.Float(bb);
    p.OverallFastestInSession = reader.Uint8(bb);
    p.DriverFastestInSession = reader.Uint8(bb);
    return p;
  }

  public F12021PacketEventData CreatePacketEventData(ByteBuffer bb) {
    F12021PacketEventData p = new F12021PacketEventData();
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
      F12021EventFastestLap ftlp = CreateEventFastestLap(bb);
      System.out.println(ftlp);
    } else if (p.EventStringCode == "RTMT") {
      F12021EventRetirement rtmt = CreateEventRetirement(bb);
      System.out.println(rtmt);
    } else if (p.EventStringCode == "DRSE") {
      System.out.println("Race control enabled DRS");
    } else if (p.EventStringCode == "DRSD") {
      System.out.println("Race control disabled DRS");
    } else if (p.EventStringCode == "TMPT") {
      F12021EventTeamMateInPits tmpt = CreateEventTeamMateInPits(bb);
      System.out.println(tmpt);
    } else if (p.EventStringCode == "CHQF") {
      System.out.println("The chequered flag has been waved");
    } else if (p.EventStringCode == "RCWN"){
      F12021EventRaceWinner rcwn = CreateEventRaceWinner(bb);
      System.out.println(rcwn);
    } else if (p.EventStringCode == "PENA"){
      F12021EventPenalty pena = CreateEventPenalty(bb);
      System.out.println(pena);
    } else if (p.EventStringCode == "SPTP") {
      F12021EventSpeedTrap sptp = CreateEventSpeedTrap(bb);
      System.out.println(sptp);
    }
    return p;
  }

  public F12021ParticipantData CreateParticipantData(ByteBuffer bb) throws UnsupportedEncodingException {
    F12021ParticipantData p = new F12021ParticipantData();
    p.AiControlled = reader.Uint8(bb);
    p.DriverId = reader.Uint8(bb);
    p.NetworkId = reader.Uint8(bb);
    p.TeamId = reader.Uint8(bb);
    p.MyTeam = reader.Uint8(bb);
    p.RaceNumber = reader.Uint8(bb);
    p.Nationality = reader.Uint8(bb);
    p.Name = getName(bb);
    p.YourTelemetry = reader.Uint8(bb);

    return p;
  }

  public F12021PacketParticipantData CreatePacketParticipantData(ByteBuffer bb) throws UnsupportedEncodingException  {
    F12021PacketParticipantData p = new F12021PacketParticipantData();
    p.NumActiveCars = reader.Uint8(bb);
    for (int i = 0; i < p.NumActiveCars; i++) {
      p.ParticipantData[i] = CreateParticipantData(bb);
      p.ParticipantData[i].Index = i;
    }
    return p;
  }

  public F12021CarSetupData CreateCarSetupData(ByteBuffer bb) {
    F12021CarSetupData p = new F12021CarSetupData();
    p.FrontWing = reader.Uint8(bb);
    p.RearWing = reader.Uint8(bb);
    p.OnThrottle = reader.Uint8(bb);
    p.OffThrottle = reader.Uint8(bb);
    p.FrontCamber = reader.Float(bb);
    p.RearCamber = reader.Float(bb);
    p.FrontToe = reader.Float(bb);
    p.RearToe = reader.Float(bb);
    p.FrontSuspension = reader.Uint8(bb);
    p.RearSuspension = reader.Uint8(bb);
    p.FrontAntiRollBar = reader.Uint8(bb);
    p.RearAntiRollBar = reader.Uint8(bb);
    p.FrontSuspensionHeight = reader.Uint8(bb);
    p.RearSuspensionHeight = reader.Uint8(bb);
    p.BrakePressure = reader.Uint8(bb);
    p.BrakeBias = reader.Uint8(bb);
    p.RearLeftTyrePressure = reader.Float(bb);
    p.RearRightTyrePressure = reader.Float(bb);
    p.FrontLeftTyrePressure = reader.Float(bb);
    p.FrontRightTyrePressure = reader.Float(bb);
    p.Ballast = reader.Uint8(bb);
    p.FuelLoad = reader.Float(bb);
    return p;
  }

  public F12021PacketCarSetupData CreatePacketCarSetupData(ByteBuffer bb) {
    F12021PacketCarSetupData p = new F12021PacketCarSetupData();
    for(int i = 0; i < p.CarSetups.length; i++) {
      p.CarSetups[i] = CreateCarSetupData(bb);
      p.CarSetups[i].Index = i;
    }
    return p;
  }

  public F12021CarTelemetryData CreateCarTelemetryData(ByteBuffer bb) {
    F12021CarTelemetryData p = new F12021CarTelemetryData();
    p.Speed = reader.Uint16(bb);
    p.Throttle = reader.Float(bb);
    p.Steer = reader.Float(bb);
    p.Brake = reader.Float(bb);
    p.Clutch = reader.Uint8(bb);
    p.Gear = reader.Int8(bb);
    p.EngineRPM = reader.Uint16(bb);
    p.DRS = reader.Uint8(bb);
    p.RevLightsPercent = reader.Uint8(bb);
    p.RevLightsBitValue = reader.Uint16(bb);
    p.BrakesTemperature = getShortWheelDataUint16(bb);
    p.TyresSurfaceTemperature = getShortWheelDataUint8(bb);
    p.TyresInnerTemperature = getShortWheelDataUint8(bb);
    p.EngineTemperature = reader.Uint16(bb);
    p.TyresPressure = getFloatWheelData(bb);
    p.SurfaceType = getShortWheelDataUint8(bb);
    return p;
  }

  public F12021PacketCarTelemetryData CreatePacketCarTelemetryData(ByteBuffer bb) {
    F12021PacketCarTelemetryData p = new F12021PacketCarTelemetryData();
    for(int i = 0; i < p.CarTelemetryData.length; i++) {
      p.CarTelemetryData[i] = CreateCarTelemetryData(bb);
      p.CarTelemetryData[i].Index = i;
    }
    p.MFDPanelIndex = reader.Uint8(bb);
    p.MFDPanelIndexSecondaryPlayer = reader.Uint8(bb);
    p.SuggestedGear = reader.Int8(bb);
    return p;
  }

  public F12021CarStatusData CreateCarStatusData(ByteBuffer bb) {
    F12021CarStatusData p = new F12021CarStatusData();
    p.TractionControl = reader.Uint8(bb);
    p.AntiLockBrakes = reader.Uint8(bb);
    p.FuelMix = reader.Uint8(bb);
    p.FrontBrakeBias = reader.Uint8(bb);
    p.PitLimiterStatus = reader.Uint8(bb);
    p.FuelInTank = reader.Float(bb);
    p.FuelCapacity = reader.Float(bb);
    p.FuelRemainingLaps = reader.Float(bb);
    p.MaxRPM = reader.Uint16(bb);
    p.IdleRPM = reader.Uint16(bb);
    p.MaxGears = reader.Uint8(bb);
    p.DRSAllowed = reader.Uint8(bb);
    p.DRSActivationDistance = reader.Uint16(bb);
    p.ActualTyreCompound = reader.Uint8(bb);
    p.VisualTyreCompound = reader.Uint8(bb);
    p.TyresAgeLaps = reader.Uint8(bb);
    p.VehicleFiaFlags = reader.Int8(bb);
    p.ERSStoreEnergy = reader.Float(bb);
    p.ERSDeployMode = reader.Uint8(bb);
    p.ERSHarvestedThisLapMGUK = reader.Float(bb);
    p.ERSHarvestedThisLapMGUH = reader.Float(bb);
    p.ERSDeployedThisLap = bb.getFloat();
    p.NetworkPaused = reader.Uint8(bb);
    return p;
  }

  public F12021PacketCarStatusData CreatePacketCarStatusData(ByteBuffer bb) {
    F12021PacketCarStatusData p = new F12021PacketCarStatusData();
    for(int i = 0; i < p.CarStatusData.length; i++) {
      p.CarStatusData[i] = CreateCarStatusData(bb);
      p.CarStatusData[i].Index = i;
    }
    return p;
  }

  public F12021FinalClassificationData CreateFinalClassificationData(ByteBuffer bb) {
    F12021FinalClassificationData p = new F12021FinalClassificationData();
    p.Position = reader.Uint8(bb);
    p.NumLaps = reader.Uint8(bb);
    p.GridPosition = reader.Uint8(bb);
    p.Points = reader.Uint8(bb);
    p.NumPitStops = reader.Uint8(bb);
    p.ResultStatus = reader.Uint8(bb);
    p.BestLapTimeInMS = reader.Uint32(bb);
    p.TotalRaceTime = reader.Double(bb);
    p.PenaltiesTime = reader.Uint8(bb);
    p.NumPenalties = reader.Uint8(bb);
    p.NumTyreStints = reader.Uint8(bb);
    p.TyreStintsActual = getShortWheelDataUint8(bb, 8);
    p.TyreStintsVisual = getShortWheelDataUint8(bb, 8);
    return p;
  }

  public F12021PacketFinalClassificationData CreatePacketFinalClassificationData(ByteBuffer bb) {
    F12021PacketFinalClassificationData p = new F12021PacketFinalClassificationData();
    p.NumCars = reader.Uint8(bb);
    for(int i = 0; i < p.ClassificationData.length; i++) {
      p.ClassificationData[i] = CreateFinalClassificationData(bb);
    }
    return p;
  }

  public F12021LobbyInfoData CreateLobbyInfoData(ByteBuffer bb) throws UnsupportedEncodingException {
    F12021LobbyInfoData p = new F12021LobbyInfoData();
    p.AiControlled = reader.Uint8(bb);
    p.TeamId = reader.Uint8(bb);
    p.Nationality = reader.Uint8(bb);
    p.Name = getName(bb);
    p.CarNumber = reader.Uint8(bb);
    p.ReadyStatus = reader.Uint8(bb);
    return p;
  }

  public F12021PacketLobbyInfoData CreatePacketLobbyInfoData(ByteBuffer bb) throws UnsupportedEncodingException{
    F12021PacketLobbyInfoData p = new F12021PacketLobbyInfoData();
    p.NumPlayers = reader.Uint8(bb);
    for(int i = 0; i < p.LobbyPlayers.length; i++) {
      p.LobbyPlayers[i] = CreateLobbyInfoData(bb);
    }
    return p;
  }

  public F12021CarDamageData CreateCarDamageData(ByteBuffer bb) {
    F12021CarDamageData p = new F12021CarDamageData();
    p.TyresWear = getFloatWheelData(bb);
    p.TyresDamage = getShortWheelDataUint8(bb);
    p.BrakesDamage = getShortWheelDataUint8(bb);
    p.FrontLeftWingDamage = reader.Uint8(bb);
    p.FrontRightWingDamage = reader.Uint8(bb);
    p.RearWingDamage = reader.Uint8(bb);
    p.FloorDamage = reader.Uint8(bb);
    p.DiffuserDamage = reader.Uint8(bb);
    p.SidepodDamage = reader.Uint8(bb);
    p.DRSFault = reader.Uint8(bb);
    p.GearBoxDamage = reader.Uint8(bb);
    p.EngineDamage = reader.Uint8(bb);
    p.EngineMGUHWear = reader.Uint8(bb);
    p.EngineESWear = reader.Uint8(bb);
    p.EngineCEWear = reader.Uint8(bb);
    p.EngineICEWear = reader.Uint8(bb);
    p.EngineMGUKWear = reader.Uint8(bb);
    p.EngineTCWear = reader.Uint8(bb);
    return p;
  }

  public F12021PacketCarDamageData CreatePacketCarDamageData(ByteBuffer bb){
    F12021PacketCarDamageData p = new F12021PacketCarDamageData();
    for(int i = 0; i < p.CarDamageData.length; i++) {
      p.CarDamageData[i] = CreateCarDamageData(bb);
      p.CarDamageData[i].Index = reader.Uint8(bb);
    }
    return p;
  }

  public F12021LapHistoryData CreateLapHistoryData(ByteBuffer bb) {
    F12021LapHistoryData p = new F12021LapHistoryData();
    p.LapTimeInMS = reader.Uint32(bb);
    p.Sector1TimeInMS = reader.Uint16(bb);
    p.Sector2TimeInMS = reader.Uint16(bb);
    p.Sector3TimeInMS = reader.Uint16(bb);
    p.LapValidBitFlags = reader.Uint8(bb);
    return p;
  }

  public F12021TyreStintHistoryData CreateTyreStintHistoryData(ByteBuffer bb) {
    F12021TyreStintHistoryData p = new F12021TyreStintHistoryData();
    p.EndLap = reader.Uint8(bb);
    p.TyreActualCompound = reader.Uint8(bb);
    p.TyreVisualCompound = reader.Uint8(bb);
    return p;
  }

  public F12021PacketSessionHistoryData CreatePacketSessionHistoryData(ByteBuffer bb) {
    F12021PacketSessionHistoryData p = new F12021PacketSessionHistoryData();
    p.Index = reader.Uint8(bb);
    p.NumLaps = reader.Uint8(bb);
    p.NumTyreStints = reader.Uint8(bb);
    p.BestLapTimeLapNum = reader.Uint8(bb);
    p.BestSector1LapNum = reader.Uint8(bb);
    p.BestSector2LapNum = reader.Uint8(bb);
    p.BestSector3LapNum = reader.Uint8(bb);
    for(int i = 0; i < p.LapHistoryData.length; i++) {
      p.LapHistoryData[i] = CreateLapHistoryData(bb);
    }
    for(int i = 0; i < p.TyreStintsHistoryData.length; i++) {
      p.TyreStintsHistoryData[i] = CreateTyreStintHistoryData(bb);
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

  private int[] getShortWheelDataUint16(ByteBuffer bb) {
    int[] a = new int[4];
    for(int i = 0; i < a.length; i++) {
      a[i] = reader.Uint16(bb);
    }
    return a;
  }

  private int[] getShortWheelDataUint8(ByteBuffer bb) {
    int[] a = new int[4];
    for(int i = 0; i < a.length; i++) {
      a[i] = reader.Uint8(bb);
    }
    return a;
  }

  private int[] getShortWheelDataUint8(ByteBuffer bb, int count) {
    int[] a = new int[count];
    for(int i = 0; i < a.length; i++) {
      a[i] = reader.Uint8(bb);
    }
    return a;
  }
}
