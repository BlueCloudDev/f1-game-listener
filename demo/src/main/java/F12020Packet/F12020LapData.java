package F12020Packet;

public class F12020LapData {
  /**
   * Index of the lap data in packet array
   */
  public int Index;
  /**
   * Last lap time in seconds                               
   */
  public float LastLapTime;
  /**
   * Current time around the lap in seconds                             
   */
  public float CurrentLapTime;
  /**
   * Sector 1 time in milliseconds                               
   */
  public short Sector1TimeInMS;
  /**
   * Sector 2 time in milliseconds                               
   */
  public short Sector2TimeInMS;
  /**
   * Best lap time of the session in seconds                              
   */
  public float BestLapTime;
  /**
   * Lap number best time acheived on                              
   */
  public short BestLapNum;
  /**
   * Sector 1 time of best lap in the session in milliseconds                            
   */
  public short BestLapSector1TimeInMS;
  /**
   * Sector 2 time of best lap in the session in milliseconds                            
   */
  public short BestLapSector2TimeInMS;
  /**
   * Sector 3 time of best lap in the session in milliseconds                            
   */
  public short BestLapSector3TimeInMS;
  /**
   * Best overall sector 1 time of the session in milliseconds                        
   */
  public short BestOverallSector1TimeInMS;
  /**
   * Lap number best overall sector 1 time achieved on                        
   */
  public short BestOverallSector1LapNum;
  /**
   * Best overall sector 2 time of the session in milliseconds                        
   */
  public short BestOverallSector2TimeInMS;  
  /**
   * Lap number best overall sector 2 time achieved on                        
   */
  public short BestOverallSector2LapNum;
  /**
   * Best overall sector 3 time of the session in milliseconds                        
   */
  public short BestOverallSector3TimeInMS;
  /**
   * Lap number best overall sector 3 time achieved on                        
   */
  public short BestOverallSector3LapNum;
  /**
   * Distance vehicle is around current lap in metres - could be negative if line hasn't been crossed yet                     
   */
  public float LapDistance;
  /**
   * Total distance travelled in session in metres - could be negative if line hasn't been crossed yet                        
   */
  public float TotalDistance;
  /**
   * Delta in seconds for safety car                        
   */
  public float SafetyCarDelta;
  /**
   * Car race position                        
   */
  public short CarPosition;
  /**
   * Current lap number                        
   */
  public short CurrentLapNum;
  /**
   * 0 = none, 1 = pitting, 2 = in pit area                        
   */
  public short PitStatus;
  /**
   * 0 = sector1, 1 = sector2, 2 = sector3                        
   */
  public short Sector;
  /**
   * Current lap invalid - 0 = valid, 1 = invalid                        
   */
  public short CurrentLapInvalid;
  /**
   * Accumulated time penalties in seconds to be added                        
   */
  public short Penalties;
  /**
   * Grid position the vehicle started the race in                        
   */
  public short GridPosition;
  /**
   * Status of driver - 0 = in garage, 1 = flying lap
   * 2 = in lap, 3 = out lap, 4 = on track                       
   */
  public short DriverStatus;
  /**
   * Result status - 0 = invalid, 1 = inactive, 2 = active
   * 3 = finished, 4 = disqualified, 5 = not classified
   * 6 = retire                 
   */
  public short ResultStatus;
}
