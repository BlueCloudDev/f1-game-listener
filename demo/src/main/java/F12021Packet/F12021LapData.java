package F12021Packet;

public class F12021LapData {
  /**
   * Index of the lap data in packet array
   */
  public int Index;
  /**
   * Last lap time in seconds                               
   */
  public Long LastLapTime;
  /**
   * Current time around the lap in seconds                             
   */
  public Long CurrentLapTime;
  /**
   * Sector 1 time in milliseconds                               
   */
  public int Sector1TimeInMS;
  /**
   * Sector 2 time in milliseconds                               
   */
  public int Sector2TimeInMS;
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
  public int CarPosition;
  /**
   * Current lap number                        
   */
  public int CurrentLapNum;
  /**
   * 0 = none, 1 = pitting, 2 = in pit area                        
   */
  public int PitStatus;
  /**
   * Number of pit stops taken this race
   */
  public int NumPitStops;
  /**
   * 0 = sector1, 1 = sector2, 2 = sector3                        
   */
  public int Sector;
  /**
   * Current lap invalid - 0 = valid, 1 = invalid                        
   */
  public int CurrentLapInvalid;
  /**
   * Accumulated time penalties in seconds to be added                        
   */
  public int Penalties;
  /**
   * Accumulated number of warnings issued
   */
  public int Warnings;
  /**
   * Num drive through pens left to serve
   */
  public int NumUnservedDriveThroughPens;
  /**
   * Num stop go pens left to serve
   */
  public int NumUnservedStopGoPens;
  /**
   * Grid position the vehicle started the race in                        
   */
  public int GridPosition;
  /**
   * Status of driver - 0 = in garage, 1 = flying lap
   * 2 = in lap, 3 = out lap, 4 = on track                       
   */
  public int DriverStatus;
  /**
   * Result status - 0 = invalid, 1 = inactive, 2 = active
   * 3 = finished, 4 = didnotfinish, 5 = disqualified, 6 = not classified
   * 7 = retired                 
   */
  public int ResultStatus;
  /**
   * Pit lane timeing, 0 = inactive, 1 = active
   */
  public int PitLaneTimerActive;
  /**
   * If active, the current time spent in the pit lane in MS
   */
  public int PitLaneTimeInLaneInMS;
  /**
   * Time of the actual pit stop in ms
   */
  public int PitStopTimerInMS;
  /**
   * Whether the car should serve penalty this stop
   */
  public int PitStopShouldServePen;
}
