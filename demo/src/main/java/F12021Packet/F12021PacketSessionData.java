package F12021Packet;



public class F12021PacketSessionData {
  public int SessionLookupID;
  public Float SessionTime;
  public Long FrameIdentifier;
  /**
   * Weather - 0 = clear, 1 = light cloud, 2 = overcast
   * 3 = light rain, 4 = heavy rain, 5 = storm                             
   */
  public int Weather;
  /**
   * Track temp. in degrees celsius                               
   */
  public int TrackTemperature;
  /**
   * Air temp. in degrees celsius                               
   */
  public int AirTemperature;
  /**
   * Total number of laps in this race                             
   */
  public int TotalLaps;
  /**
   * Track length in meters                             
   */
  public int TrackLength;
  /**
   * 0 = unknown, 1 = P1, 2 = P2, 3 = P3, 4 = int P
   * 5 = Q1, 6 = Q2, 7 = Q3, 8 = int Q, 9 = OSQ
   * 10 = R, 11 = R2, 12 = Time Trial
   */
  public int SessionType;
  /**
   * -1 for unknown, 0-21 for tracks, see appendix                            
   */
  public int TrackID;
  /**
   * Formula, 0 = F1 Modern, 1 = F1 Classic, 2 = F2,
   * 3 = F1 Generic                   
   */
  public int Formula;
  /**
   * Time left in session in seconds                          
   */
  public int SessionTimeLeft;
  /**
   * Session duration in seconds                        
   */
  public int SessionDuration;
  /**
   * Pit speed limit in kilometers per hour                        
   */
  public int PitSpeedLimit;
  /**
   * Whether the game is paused                        
   */
  public int GamePaused;
  /**
   * Whether the player is spectating          
   */
  public int IsSpectating;
  /**
   * Index of the car being spectated                          
   */
  public int SpectatorCarIndex;
  /**
   * SLI Pro support, 0 = inactive, 1 = active                          
   */
  public int SliProNativeSupport;
  /**
   * Number of marshal zones to follow                           
   */
  public int NumMarshalZones;
  /**
   * List of marshal zones - max 21                         
   */
  public F12021MarshalZone[] MarshalZones = new F12021MarshalZone[21];
  /**
   * 0 = no safety car, 1 = full safety car
   * 2 = virtual safety car                            
   */
  public int SafetyCarStatus;
  /**
   * 0 = offline, 1 = online                         
   */
  public int NetworkGame;
  /**
   * Number of weather samples to follow                         
   */
  public int NumWeatherForecastSamples;
  /**
   * Array of weather forecast samples                      
   */
  public F12021WeatherForecastSample[] WeatherForecastSamples = new F12021WeatherForecastSample[56];
  /**
   * Forecast accuracy. 0 = perfect, 1 = Approximate
   */
  public int ForecastAccuracy;
  /**
   * AI Difficulty rating - 0-110
   */
  public int AIDifficulty;
  /**
   * Identify for season - persists across saves
   */
  public Long SeasonLinkIdentifier;
  /**
   * Identifier for weekend - persists acros saves
   */
  public Long WeekendLinkIdentifier;
  /**
   * Identifier for session - persists across saves
   */
  public Long SessionLinkIdentifier;
  /**
   * Ideal lap to pit for current strategy
   */
  public int PitStopWindowIdealLap;
  /**
   * Latest lap to pit on current strategy
   */
  public int PitStopWindowLatestLap;
  /**
   * Predicted position to rejoin at (player)
   */
  public int PitStopRejoinPosition;
  /**
   * Steering assist 0 = off, 1 = on
   */
  public int SteeringAssist;
  /**
   * Braking Assist 0 = off, 1 = low, 2 = medium, 3 = high
   */
  public int BrakingAssist;
  /**
   * Gearbox Assist 1 = manual, 2 = manual & suggested gear, 3 = auto
   */
  public int GearboxAssist;
  /**
   * Pit Assist 0 = off, 1 = on
   */
  public int PitAssist;
  /**
   * Pit Release Assist 0 = off, 1 = on
   */
  public int PitReleaseAssist;
  /**
   * ERS Assist 0 = off, 1 = on
   */
  public int ERSAssist;
  /**
   * DRS Assist 0 = off, 1 = on
   */
  public int DRSAssist;
  /**
   * Dynamic Racing Line 0 = off, 1 = corners only, 2 = full
   */
  public int DynamicRacingLine;
  /**
   * Dynamic Racing Line Type 0 = 2D, 1 = 3D
   */
  public int DynamicRacingLineType;
}
