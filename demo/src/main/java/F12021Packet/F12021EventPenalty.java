package F12021Packet;

public class F12021EventPenalty {
  /**
   * Penalty type – see Appendices
   */
  public int PenaltyType;
  /**
   * Infringement type – see Appendices  
   */
  public int InfringementType;
  /**
   * Vehicle index of the car the penalty is applied to 
   */
  public int VehicleIdx;
  /**
   * Vehicle index of the other car involved 
   */
  public int OtherVehicleIdx;
  /**
   * Time gained, or time spent doing action in seconds 
   */
  public int Time;
  /**
   * Lap the penalty occurred on 
   */
  public int LapNum;
  /**
   * Number of places gained by this 
   */
  public int PlacesGained;
}
