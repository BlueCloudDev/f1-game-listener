package F12021Packet;

public class F12021WeatherForecastSample {
  /**
   * 0 = unknown, 1 = P1, 2 = P2, 3 = P3, 4 = Short P, 5 = Q1
   * 6 = Q2, 7 = Q3, 8 = Short Q, 9 = OSQ, 10 = R, 11 = R2, 12 = Time Trial                                        
   */
  public int SessionType;
  /**
   * Time in minutes the forcast is for                                
   */
  public int TimeOffset;
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
   * Track temp. change - 0 = up, 1 = down, 2 = no change
   */
  public int TractTemperatureChange;
  /**
   * Track temp. in degrees celsius                               
   */
  public int AirTemperature;
  /**
   * Air temp change -  0 = up, 1 = down, 2 = no change
   */
  public int AirTemperatureChange;
  /**
   * Rain percentage (0-100)
   */
  public int RainPercentage;
}
