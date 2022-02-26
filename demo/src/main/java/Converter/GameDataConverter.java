package Converter;

import oracle.net.aso.l;

public class GameDataConverter {
  public String SessionType(short sessionType) {
    switch (sessionType) {
      case 0: return "Unknown";
      case 1: return "P1";
      case 2: return "P2";
      case 3: return "P3"; 
      case 4: return "Short P";
      case 5: return "Q1";
      case 6: return "Q2";
      case 7: return "Q3";
      case 8: return "Short Q";
      case 9: return "OSQ";
      case 10: return "R";
      case 11: return "R2";
      case 12: return "Time Trial";
      default: return "Default";
    }
  }

  public String Weather(short weather) {
    switch (weather) { 
      case 0: return "Clear";
      case 1: return "Light Clouds";
      case 2: return "Overcast";
      case 3: return "Light Rain";
      case 4: return "Heavy Rain";
      case 5: return "Storm";
      default: return "Default";
    }
  }

  public String Formula(short formula) {
    switch (formula) {
      case 0: return "F1 Modern";
      case 1: return "F1 Classic";
      case 2: return "F2";
      case 3: return "F1 Generic";  
      default: return "Default";
    }
  }

  public String SafetyCarStatus(short safetyCarStatus) {
    switch (safetyCarStatus) {
      case 0: return "No Safety Car";
      case 1: return "Full Safety Car";
      case 2: return "Virtual Safety Car";
      default: return "Default";
    }
  }

  public String NetworkGame(short networkGame) {
    switch (networkGame) {
      case 0: return "Offline";
      case 1: return "Online";
      default: return "Default";
    }
  }

  public String PitStatus(short pitStatus) {
    switch (pitStatus) {
      case 0: return "None";
      case 1: return "Pitting";
      case 2: return "In Pit Area";
      default: return "Default";
    }
  }

  public short Sector(short sector) {
    switch (sector) {
      case 0: return 1;
      case 1: return 2;
      case 2: return 3;
      default: return 0;
    }
  }

  public String CurrentLapValid(short currentLapValid) {
    switch (currentLapValid) {
      case 0: return "Valid";
      case 1: return "Invalid";
      default: return "Default";
    }
  }

  public String DriverStatus(short driverStatus) {
    switch (driverStatus) {
      case 0: return "In Garage";
      case 1: return "Flying Lap";
      case 2: return "In Lap";
      case 3: return "Out Lap";
      case 4: return "On Track";
      default: return "Default";
    }
  }

  public String ResultStatus(short resultStatus) {
    switch (resultStatus) {
      case 0: return "Invalid";
      case 1: return "Inactive";
      case 2: return "Active";
      case 3: return "Finished";
      case 4: return "Disqualified";
      case 5: return "Not Classified";
      case 6: return "Retired";
      default: return "Default";
    }
  }

  public String EventCodes(String codes) {
    switch (codes) {
      case "SSTA": return "Session Started";
      case "SEND": return "Session Ended";
      case "FTLP": return "Fastest Lap";
      case "RTMT": return "Retirement";
      case "DRSE": return "DRS Enabled";
      case "DRSD": return "DRS Disabled";
      case "TMPT": return "Teammate in Pits";
      case "CHQF": return "Chequered Flag";
      case "RCWN": return "Race Winner";
      case "PENA": return "Penalty Issued";
      case "SPTP": return "Speed Trap Triggered";
      default: return "Default";
    }
  }

  public String FuelMix(short FuelMix) {
    switch (FuelMix) {
      case 0: return "Lean";
      case 1: return "Standard";
      case 2: return "Rich";
      case 3: return "Max";
      default: return "Default";
    }
  }

  public String DRSAllowed(short drsAllowed) {
    switch (drsAllowed) {
      case 0: return "Not Allowed";
      case 1: return "Allowed";
      case -1: return "Unknown";
      default: return "Default";
    }
  }

  public String ActualTypeCompound(short actualTyreCompound) {
    switch (actualTyreCompound) {
      case 16: return "C5";
      case 17: return "C4";
      case 18: return "C3";
      case 19: return "C2";
      case 20: return "C1";
      case 7: return "Inter";
      case 8: return "Wet";
      case 9: return "Dry";
      case 10: return "Wet";
      case 11: return "Super Soft";
      case 12: return "Soft";
      case 13: return "Medium";
      case 14: return "Hard";
      case 15: return "Wet";
      default: return "Default";
    }
  }

  public String VisualTyreCompound(short visualTyreCompound) {
    switch (visualTyreCompound) {
      case 16: return "Soft";
      case 17: return "Medium";
      case 18: return "Hard";
      case 7: return "Inter";
      case 8: return "Wet";
      case 19: return "C2";
      case 20: return "C1";
      case 9: return "Dry";
      case 10: return "Wet";
      case 11: return "Super Soft";
      case 12: return "Soft";
      case 13: return "Medium";
      case 14: return "Hard";
      case 15: return "Wet";
      default: return "Default";
    }
  }

  public String DRSFault(short drsFault) {
    switch (drsFault) {
      case 0: return "OK";
      case 1: return "Fault";
      default: return "Default";
    }
  }

  public String VehicleFiaFlags(int vehicleFiaFlags) {
    switch (vehicleFiaFlags) {
      case -1: return "Invalid/Unknown";
      case 0: return "None";
      case 1: return "Green";
      case 2: return "Blue";
      case 3: return "Yellow";
      case 4: return "Red";
      default: return "Default";
    }
  }

  public String ErsDeployMode(short ersDeployMode) {
    switch (ersDeployMode) {
      case 0: return "None";
      case 1: return "Medium";
      case 2: return "Overtake";
      case 3: return "Hotlap";
      default: return "Default";
    }
  }

  public String TrackIDs (short trackID) {
    switch (trackID) {
      case 0: return "Melbourne";
      case 1: return "Paul Ricard";
      case 2: return "Shanghai";
      case 3: return "Sakhir (Bahrain)";
      case 4: return "Catalunya";
      case 5: return "Monaco";
      case 6: return "Montreal";
      case 7: return "Silverstone";
      case 8: return "Hockenheim";
      case 9: return "Hungaroring";
      case 10: return "Spa";
      case 11: return "Monza";
      case 12: return "Singapore";
      case 13: return "Suzuka";
      case 14: return "Abu Dhabi";
      case 15: return "Texas";
      case 16: return "Brazil";
      case 17: return "Austria";
      case 18: return "Sochi";
      case 19: return "Mexico";
      case 20: return "Baku (Azerbaijan)";
      case 21: return "Sakhir Short";
      case 22: return "Silverstone Short";
      case 23: return "Texas Short";
      case 24: return "Suzuka Short";
      case 25: return "Hanoi";
      case 26: return "Zandvoort";
      default: return "Default";
    }
  }

  public String Nationality (short nationalityID) {
    switch (nationalityID) {
      case 1: return "American";
      case 2: return "Argentinean";
      case 3: return "Australian";
      case 4: return "Austrian";
      case 5: return "Azerbaijani";
      case 6: return "Bahraini";
      case 7: return "Belgian";
      case 8: return "Bolivian";
      case 9: return "Brazilian";
      case 10: return "British";
      case 11: return "Bulgarian";
      case 12: return "Cameroonian";
      case 13: return "Canadian";
      case 14: return "Chilean";
      case 15: return "Chinese";
      case 16: return "Colombian";
      case 17: return "Costa Rican";
      case 18: return "Croatian";
      case 19: return "Cypriot";
      case 20: return "Czech";
      case 21: return "Danish";
      case 22: return "Dutch";
      case 23: return "Ecuadorian";
      case 24: return "English";
      case 25: return "Emirian";
      case 26: return "Estonian";
      case 27: return "Finnish";
      case 28: return "French";
      case 29: return "German";
      case 30: return "Ghanian";
      case 31: return "Greek";
      case 32: return "Guatemalan";
      case 33: return "Honduran";
      case 34: return "Hong Konger";
      case 35: return "Hungarian";
      case 36: return "Icelander";
      case 37: return "Indian";
      case 38: return "Indonesian";
      case 39: return "Irish";
      case 40: return "Israeli";
      case 41: return "Italian";
      case 42: return "Jamaican";
      case 43: return "Japanese";
      case 44: return "Jordanian";
      case 45: return "Kuwaiti";
      case 46: return "Latvian";
      case 47: return "Lebanese";
      case 48: return "Lituanian";
      case 49: return "Luxembourger";
      case 50: return "Malaysian";
      case 51: return "Maltese";
      case 52: return "Mexican";
      case 53: return "Mongasque";
      case 54: return "New Zealander";
      case 55: return "Nicaraguan";
      case 56: return "North Korean";
      case 57: return "Northern Irish";
      case 58: return "Norwegian";
      case 59: return "Omani";
      case 60: return "Pakistani";
      case 61: return "Panamanian";
      case 62: return "Paraguayan";
      case 63: return "Peruvian";
      case 64: return "Polish";
      case 65: return "Portuguese";
      case 66: return "Qatari";
      case 67: return "Romanian";
      case 68: return "Russian";
      case 69: return "Salvadoran";
      case 70: return "Saudi";
      case 71: return "Scottish";
      case 72: return "Serbian";
      case 73: return "Singaporean";
      case 74: return "Slovakian";
      case 75: return "Slovenian";
      case 76: return "South Korean";
      case 77: return "South African";
      case 78: return "Spanish";
      case 79: return "Swedish";
      case 80: return "Swiss";
      case 81: return "Thai";
      case 82: return "Turkish";
      case 83: return "Uruguayan";
      case 84: return "Ukrainian";
      case 85: return "Venezuelan";
      case 86: return "Welsh";
      case 87: return "Barbadian";
      case 88: return "Vietnamese";
      default: return "Default";
    }
  }

  public String SurfaceType(short surfaceType) {
    switch (surfaceType) {
      case 0: return "Tarmac";
      case 1: return "Rumble Strip";
      case 2: return "Concrete";
      case 3: return "Rock";
      case 4: return "Gravel";
      case 5: return "Mud";
      case 6: return "Sand";
      case 7: return "Grass";
      case 8: return "Water";
      case 9: return "Cobblestone";
      case 10: return "Metal";
      case 11: return "Ridged";
      default: return "Default";
    }
  }

  public String PenaltyType(short penaltyType) {
    switch (penaltyType) {
      case 0: return "Drive through";
      case 1: return "Stop Go";
      case 2: return "Grid Penalty";
      case 3: return "Penalty Reminder";
      case 4: return "Time Penalty";
      case 5: return "Warning";
      case 6: return "Disqualified";
      case 7: return "Removed from formation lap";
      case 8: return "Parked too long timer";
      case 9: return "Tyre regulations";
      case 10: return "This lap invalidated";
      case 11: return "This and next lap invalidated";
      case 12: return "This lap invalidated without reason";
      case 13: return "This and next lap invalidated without reason";
      case 14: return "This and previous lap invalidated";
      case 15: return "This and previous lap invalidated without reason";
      case 16: return "Retired";
      case 17: return "Black flag timer";
      default: return "Default";
    }
  }

  public String InfringementTypes(short infringementTypes) {
    switch (infringementTypes) {
      case 0: return "Blocking by slow driving";
      case 1: return "Blocking by wrong way driving";
      case 2: return "Reversing off the start line";
      case 3: return "Big Collision";
      case 4: return "Small Collision";
      case 5: return "Collision failed to hand back position angle";
      case 6: return "Collision failed to hand back position multiple";
      case 7: return "Corner cutting gained time";
      case 8: return "Corner cutting overtake single";
      case 9: return "Corner cutting overtake multiple";
      case 10: return "Crossed pit exit lane";
      case 11: return "Ignoring blue flags";
      case 12: return "Ignoring yellow flags";
      case 13: return "Ignoring drive through";
      case 14: return "Too many drive throughs";
      case 15: return "Drive through reminder serve within n laps";
      case 16: return "Drive through reminder serve this lap";
      case 17: return "Pit lane speeding";
      case 18: return "Parked for too long";
      case 19: return "Ignoring tyre regulations";
      case 20: return "Too many penalties";
      case 21: return "Multiple warnings";
      case 22: return "Approaching disqualification";
      case 23: return "Tyre regulations select single";
      case 24: return "Tyre regulations select multiple";
      case 25: return "Lap invalidated corner cutting";
      case 26: return "Lap invalidated running wide";
      case 27: return "Corner cutting ran wide gained time minor";
      case 28: return "Corner cutting ran wide gained time significant";
      case 29: return "Corner cutting ran wide gained time extreme";
      case 30: return "Lap invalidated wall riding";
      case 31: return "Lap invalidated flashback used";
      case 32: return "Lap invalidated reset to track";
      case 33: return "Blocking the pitlane";
      case 34: return "Jump start";
      case 35: return "Safety car to collision";
      case 36: return "safety car illegal overtake";
      case 37: return "Safety car exceeding allowed pace";
      case 38: return "Virtual safety car exceeding allowed pace";
      case 39: return "Formation lap below allowed speed";
      case 40: return "Retired mechanical failure";
      case 41: return "Retired terminally damaged";
      case 42: return "Safety car falling too far back";
      case 43: return "Black flag timer";
      case 44: return "Unserved stop go penalty";
      case 45: return "Unserved drive through penalty";
      case 46: return "Engine component change";
      case 47: return "Gearbox change";
      case 48: return "League grid penalty";
      case 49: return "Retry penalty";
      case 50: return "Illegal time gain";
      case 51: return "Mandatory pitstop";
      default: return "Default";
    }
  }

  public String Team(short teamID) {
    switch (teamID) {
      case 0: return "Mercedes";
      case 1: return "Ferrari";
      case 2: return "Red Bull Racing";
      case 3: return "Williams";
      case 4: return "Racing Point";
      case 5: return "Renault";
      case 6: return "Alpha Tauri";
      case 7: return "Haas";
      case 8: return "McLaren";
      case 9: return "Alpha Romeo";
      case 10: return "McLaren 1988";
      case 11: return "McLaren 1991";
      case 12: return "Williams 1992";
      case 13: return "Ferrari 1995";
      case 14: return "Williams 1996";
      case 15: return "McLaren 1998";
      case 16: return "Ferrari 2002";
      case 17: return "Ferrari 2004";
      case 18: return "Renault 2006";
      case 19: return "Ferrari 2007";
      case 20: return "McLaren 2008";
      case 21: return "Red Bull 2010";
      case 22: return "Ferrari 1976";
      case 23: return "ART Grand Prix";
      case 24: return "Campos Vexatec Racing";
      case 25: return "Carlin";
      case 26: return "Charouz Racing System";
      case 27: return "DMAS";
      case 28: return "Russian Time";
      case 29: return "MP Motorsport";
      case 30: return "Pertamina";
      case 31: return "McLaren 1990";
      case 32: return "Trident";
      case 33: return "BWT Arden";
      case 34: return "McLaren 1976";
      case 35: return "Lotus 1972";
      case 36: return "Ferrari 1979";
      case 37: return "McLaren 1982";
      case 38: return "Williams 2003";
      case 39: return "Brawn 2009";
      case 40: return "Lotus 1978";
      case 41: return "F1 Generic Car";
      case 42: return "Art GP '19";
      case 43: return "Campos '19";
      case 44: return "Carlin '19";
      case 45: return "Sauber Junior Charouz '19";
      case 46: return "Dams '19";
      case 47: return "Uni-Virtuosi '19";
      case 48: return "MP Motorsport '19";
      case 49: return "Prema '19";
      case 50: return "Trident '19";
      case 51: return "Arden '19";
      case 53: return "Benetton 1994";
      case 54: return "Benetton 1995";
      case 55: return "Ferrari 2000";
      case 56: return "Jordan 1991";
      case 255: return "My Team";
      default: return "Default";
    }
  }
}

