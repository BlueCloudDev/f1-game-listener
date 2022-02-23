package F12020Packet;

public class F12020WeatherForecastSample {
  /**
   * 0 = unknown, 1 = P1, 2 = P2, 3 = P3, 4 = Short P, 5 = Q1
   * 6 = Q2, 7 = Q3, 8 = Short Q, 9 = OSQ, 10 = R, 11 = R2, 12 = Time Trial                                        
   */
  public short SessionType;
  /**
   * Time in minutes the forcast is for                                
   */
  public short TimeOffset;
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
   * Track temp. in degrees celsius                               
   */
  public short AirTemperature;
}
