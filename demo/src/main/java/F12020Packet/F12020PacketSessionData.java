package F12020Packet;



public class F12020PacketSessionData {
  /**
   * Weather - 0 = clear, 1 = light cloud, 2 = overcast
   * 3 = light rain, 4 = heavy rain, 5 = storm                             
   */
  public short Weather;
  /**
   * Track temp. in degrees celsius                               
   */
  public short TrackTemperature;
  /**
   * Air temp. in degrees celsius                               
   */
  public short AirTemperature;
  /**
   * Total number of laps in this race                             
   */
  public short TotalLaps;
  /**
   * Track length in meters                             
   */
  public float TrackLength;
  /**
   * 0 = unknown, 1 = P1, 2 = P2, 3 = P3, 4 = Short P
   * 5 = Q1, 6 = Q2, 7 = Q3, 8 = Short Q, 9 = OSQ
   * 10 = R, 11 = R2, 12 = Time Trial
   */
  public short SessionType;
  /**
   * -1 for unknown, 0-21 for tracks, see appendix                            
   */
  public short TrackID;
  /**
   * Formula, 0 = F1 Modern, 1 = F1 Classic, 2 = F2,
   * 3 = F1 Generic                   
   */
  public short Formula;
  /**
   * Time left in session in seconds                          
   */
  public float SessionTimeLeft;
  /**
   * Session duration in seconds                        
   */
  public float SessionDuration;
  /**
   * Pit speed limit in kilometers per hour                        
   */
  public short PitSpeedLimit;
  /**
   * Whether the game is paused                        
   */
  public short GamePaused;
  /**
   * Whether the player is spectating          
   */
  public short IsSpectating;
  /**
   * Index of the car being spectated                          
   */
  public short SpectatorCarIndex;
  /**
   * SLI Pro support, 0 = inactive, 1 = active                          
   */
  public short SliProNativeSupport;
  /**
   * Number of marshal zones to follow                           
   */
  public short NumMarshalZones;
  /**
   * List of marshal zones - max 21                         
   */
  public F12020MarshalZone[] MarshalZones = new F12020MarshalZone[21];
  /**
   * 0 = no safety car, 1 = full safety car
   * 2 = virtual safety car                            
   */
  public short SafetyCarStatus;
  /**
   * 0 = offline, 1 = online                         
   */
  public short NetworkGame;
  /**
   * Number of weather samples to follow                         
   */
  public short NumWeatherForecastSamples;
  /**
   * Array of weather forecast samples                      
   */
  public F12020WeatherForecastSample[] WeatherForecastSamples = new F12020WeatherForecastSample[20];
}
