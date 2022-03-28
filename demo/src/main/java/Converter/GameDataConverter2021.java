package Converter;

public class GameDataConverter2021 {
  public String SessionType(int sessionType) {
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

  public String Weather(int weather) {
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

  public String TemperatureChange(int temperatureChange) {
    switch (temperatureChange) {
      case 0: return "Up";
      case 1: return "Down";
      case 2: return "No Change";
      default: return "Default";
    }
  }

  public String ZoneFlag(int zoneFlag) {
    switch (zoneFlag) {
      case -1: return "Invalid/Unknown";
      case 0: return "None";
      case 1: return "Green";
      case 2: return "Blue";
      case 3: return "Yellow";
      case 4: return "Red";
      default: return "Default";
    }
  }

  public String Formula(int formula) {
    switch (formula) {
      case 0: return "F1 Modern";
      case 1: return "F1 Classic";
      case 2: return "F2";
      case 3: return "F1 Generic";  
      default: return "Default";
    }
  }

  public String SafetyCarStatus(int safetyCarStatus) {
    switch (safetyCarStatus) {
      case 0: return "No Safety Car";
      case 1: return "Full Safety Car";
      case 2: return "Virtual Safety Car";
      default: return "Default";
    }
  }

  public String NetworkGame(int networkGame) {
    switch (networkGame) {
      case 0: return "Offline";
      case 1: return "Online";
      default: return "Default";
    }
  }

  public String PitStatus(int pitStatus) {
    switch (pitStatus) {
      case 0: return "None";
      case 1: return "Pitting";
      case 2: return "In Pit Area";
      default: return "Default";
    }
  }

  public short Sector(int sector) {
    switch (sector) {
      case 0: return 1;
      case 1: return 2;
      case 2: return 3;
      default: return 0;
    }
  }

  public String ForecastAccuracy(int forecastAccuracy) {
    switch (forecastAccuracy) {
      case 0: return "Perfect";
      case 1: return "Approximate";
      default: return "Default";
    }
  }

  public String BrakingAssist(int brakingAssist) {
    switch (brakingAssist) {
      case 0: return "Off";
      case 1: return "Low";
      case 2: return "Medium";
      case 3: return "High";
      default: return "Default";
    }
  }

  public String GearBoxAssist(int gearboxAssist) {
    switch (gearboxAssist) {
      case 1: return "Manual";
      case 2: return "Manual & Suggested Gear";
      case 3: return "Auto";
      default: return "Default"; 
    }
  }

  public String DynamicRacingLine(int dynamicRacingLine) {
    switch (dynamicRacingLine) {
      case 0: return "Off";
      case 1: return "Corners Only";
      case 2: return "Full";
      default: return "Default";
    }
  }

  public String DynamicRacingLineType(int dynamicRacingLineType) {
    switch (dynamicRacingLineType) {
      case 0: return "2D";
      case 1: return "3D";
      default: return "Default";
    }
  }

  public String CurrentLapValid(int currentLapValid) {
    switch (currentLapValid) {
      case 0: return "Valid";
      case 1: return "Invalid";
      default: return "Default";
    }
  }

  public String DriverStatus(int driverStatus) {
    switch (driverStatus) {
      case 0: return "In Garage";
      case 1: return "Flying Lap";
      case 2: return "In Lap";
      case 3: return "Out Lap";
      case 4: return "On Track";
      default: return "Default";
    }
  }

  public String ResultStatus(int resultStatus) {
    switch (resultStatus) {
      case 0: return "Invalid";
      case 1: return "Inactive";
      case 2: return "Active";
      case 3: return "Finished";
      case 4: return "Did Not Finish";
      case 5: return "Disqualified";
      case 6: return "Not Classified";
      case 7: return "Retired";
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

  public String FuelMix(int FuelMix) {
    switch (FuelMix) {
      case 0: return "Lean";
      case 1: return "Standard";
      case 2: return "Rich";
      case 3: return "Max";
      default: return "Default";
    }
  }

  public String DRSAllowed(int drsAllowed) {
    switch (drsAllowed) {
      case 0: return "Not Allowed";
      case 1: return "Allowed";
      case -1: return "Unknown";
      default: return "Default";
    }
  }

  public String ActualTypeCompound(int actualTyreCompound) {
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

  public String VisualTyreCompound(int visualTyreCompound) {
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

  public String DRSFault(int drsFault) {
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

  public String ErsDeployMode(int ersDeployMode) {
    switch (ersDeployMode) {
      case 0: return "None";
      case 1: return "Medium";
      case 2: return "Overtake";
      case 3: return "Hotlap";
      default: return "Default";
    }
  }

  public String ReadyStatus(int readyStatus) {
    switch (readyStatus) {
      case 0: return "Not Ready";
      case 1: return "Ready";
      case 2: return "Spectating";
      default: return "Default";
    }
  }
  public String TractionControl(int tractionControl) {
    switch (tractionControl) {
      case 0: return "Off";
      case 1: return "Medium";
      case 2: return "Full";
      default: return "Default";
    }
  }

  public String TrackIDs (int trackID) {
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
      case 27: return "Imola";
      case 28: return "Portimao";
      case 29: return "Jeddah";
      default: return "Default";
    }
  }

  public String Nationality (int nationalityID) {
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
      case 56: return "Northern Irish";
      case 57: return "Norwegian";
      case 58: return "Omani";
      case 59: return "Pakistani";
      case 60: return "Panamanian";
      case 61: return "Paraguayan";
      case 62: return "Peruvian";
      case 63: return "Polish";
      case 64: return "Portuguese";
      case 65: return "Qatari";
      case 66: return "Romanian";
      case 67: return "Russian";
      case 68: return "Salvadoran";
      case 69: return "Saudi";
      case 70: return "Scottish";
      case 71: return "Serbian";
      case 72: return "Singaporean";
      case 73: return "Slovakian";
      case 74: return "Slovenian";
      case 75: return "South Korean";
      case 76: return "South African";
      case 77: return "Spanish";
      case 78: return "Swedish";
      case 79: return "Swiss";
      case 80: return "Thai";
      case 81: return "Turkish";
      case 82: return "Uruguayan";
      case 83: return "Ukrainian";
      case 84: return "Venezuelan";
      case 85: return "Welsh";
      case 86: return "Barbadian";
      case 87: return "Vietnamese";
      default: return "Default";
    }
  }

  public String SurfaceType(int surfaceType) {
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

  public String PenaltyType(int penaltyType) {
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

  public String InfringementTypes(int infringementTypes) {
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

  public String DriverId(int driverId) {
    switch(driverId) {
      case 0: return "Carlos Sainz";
      case 1: return "Daniil Kvyat";
      case 2: return "Daniel Ricciardo";
      case 3: return "Fernando Alonso";
      case 4: return "Filipe Massa";
      case 6: return "Kimi Raikkonen";
      case 7: return "Lewis Hamilton";
      case 9: return "Max Verstappen";
      case 10: return "Nico Hulkenburg";
      case 11: return "Kevin Magnussen";
      case 12: return "Romain Grosjean";
      case 13: return "Sebastian Vettel";
      case 14: return "Sergio Vettel";
      case 15: return "Valtteri Bottas";
      case 17: return "Esteban Ocon";
      case 19: return "Lance Stroll";
      case 20: return "Arron Barnes";
      case 21: return "Martin Giles";
      case 22: return "Alex Murray";
      case 23: return "Lucas Roth";
      case 24: return "Igor Vorreia";
      case 25: return "Sophie Levasseur";
      case 26: return "Jonas Schiffer";
      case 27: return "Alain Forest";
      case 28: return "Jay letourneau";
      case 29: return "Esto Saari";
      case 30: return "Yasar Atiyeh";
      case 31: return "Callisto Calabresi";
      case 32: return "Naota Izum";
      case 33: return "Howard Clarke";
      case 34: return "Wilheim Kaufmann";
      case 35: return "Marie Laursen";
      case 36: return "Flavio Nieves";
      case 37: return "Peter Belousov";
      case 38: return "Klimek Michalski";
      case 39: return "Santiago Moreno";
      case 40: return "Benjamin Coppens";
      case 41: return "Noah Visser";
      case 42: return "Gert Waldmuller";
      case 43: return "Julian Quesada";
      case 44: return "Daniel Jones";
      case 45: return "Artem Markelov";
      case 46: return "Tadasuke Makino";
      case 47: return "Sean Galeal";
      case 48: return "Nyck De Vries";
      case 49: return "Jak Aitken";
      case 50: return "George Russell";
      case 51: return "Maximilian Gunther";
      case 52: return "Nirei Fukuzumi";
      case 53: return "Luca Ghiotto";
      case 54: return "Lando Norris";
      case 55: return "Sergio Sette Camara";
      case 56: return "Louis Deletraz";
      case 57: return "Antonio Fuoco";
      case 58: return "Charles Leclerc";
      case 59: return "Pierre Gasly";
      case 62: return "Alexander Albon";
      case 63: return "Nicholas Latifi";
      case 64: return "Dorian Boccolacci";
      case 65: return "Niko Kari";
      case 66: return "Roberto Merhi";
      case 67: return "Arjun Maini";
      case 68: return "Alessio Lorandi";
      case 69: return "Ruben Meijer";
      case 70: return "Rashid Nair";
      case 71: return "Jack Tremblay";
      case 72: return "Devon Butler";
      case 73: return "Lukas Weber";
      case 74: return "Antonio Giovinazzi";
      case 75: return "Robert Kubica";
      case 76: return "Alain Prost";
      case 77: return "Ayrton Senna";
      case 78: return "Nobuharu Matushita";
      case 79: return "Nikita Mazepin";
      case 80: return "Guanya Zhou";
      case 81: return "Mick Schumacher";
      case 82: return "Callum Ilott";
      case 83: return "Juan Manuel Correa";
      case 84: return "Jordan King";
      case 85: return "Mahaveer Raghunathan";
      case 86: return "Tatiana Calderon";
      case 87: return "Anthoine Hubert";
      case 88: return "Guiliano Alesi";
      case 89: return "Ralph Boschung";
      case 90: return "Michael Schumacher";
      case 91: return "Dan Ticktum";
      case 92: return "Marcus Armstrong";
      case 93: return "Christian Lundgaard";
      case 94: return "Yuki Tsunoda";
      case 95: return "Jehan Daruvala";
      case 96: return "Gulherme Samaia";
      case 97: return "Pedro Piquet";
      case 98: return "Felipe Drugovich";
      case 99: return "Robert Schwartzman";
      case 100: return "Roy Nissany";
      case 101: return "Marino Sato";
      case 102: return "Aidan Jackson";
      case 103: return "Casper Akkerman";
      case 109: return "Jenson Button";
      case 110: return "David Coulthard";
      case 111: return "Nico Rosbert";
      case 112: return "Oscar Piastri";
      case 113: return "Liam Lawson";
      case 114: return "Juri Vips";
      case 115: return "Theo Pourchaire";
      case 116: return "Richard Verschoor";
      case 117: return "Lirim Zendeli";
      case 118: return "David Beckmann";
      case 119: return "Gianluca Petecof";
      case 120: return "Matteo Nannini";
      case 121: return "Alessio Deledda";
      case 122: return "Bent Viscaal";
      case 123: return "enzo Fittipaldi";
      default: return "Default";
    }
  }

  public String Team(int teamID) {
    switch (teamID) {
      case 0: return "Mercedes";
      case 1: return "Ferrari";
      case 2: return "Red Bull Racing";
      case 3: return "Williams";
      case 4: return "Aston Martin";
      case 5: return "Alpine";
      case 6: return "Alpha Tauri";
      case 7: return "Haas";
      case 8: return "McLaren";
      case 9: return "Alpha Romeo";
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
      case 70: return "Art GP '20";
      case 71: return "Campos '20";
      case 72: return "Carlin '20";
      case 73: return "Charouz '20";
      case 74: return "Dams '20";
      case 75: return "Uni-Virtousi '20";
      case 76: return "MP Motorsport '20";
      case 77: return "Prema '20";
      case 78: return "Trident '20";
      case 79: return "BWT '20";
      case 80: return "Hitech '20";
      case 85: return "Mercedes 2020";
      case 86: return "Ferrari 2020";
      case 87: return "Red Bull 2020";
      case 88: return "Williams 2020";
      case 89: return "Racing Point 2020";
      case 90: return "Renault 2020";
      case 91: return "Alpha Tauri 2020";
      case 92: return "Haas 2020";
      case 93: return "McLaren 2020";
      case 94: return "Alfa Romeo 2020";
      case 106: return "Prema '21";
      case 107: return "Uni-Virtuosi '21";
      case 108: return "Carlin '21";
      case 109: return "Hitech '21";
      case 110: return "Art GP '21";
      case 111: return "MP Motorsport '21";
      case 112: return "Charouz '21";
      case 113: return "Dams '21";
      case 114: return "Campos '21";
      case 115: return "BWT '21";
      case 116: return "Trident '21";
      case 255: return "My Team";
      default: return "Default";
    }
  }
}

