package F12020Packet;

public class F12020EventPenalty {
  /**
   * Penalty type – see Appendices
   */
  public short PenaltyType;
  /**
   * Infringement type – see Appendices  
   */
  public short InfringementType;
  /**
   * Vehicle index of the car the penalty is applied to 
   */
  public short VehicleIdx;
  /**
   * Vehicle index of the other car involved 
   */
  public short OtherVehicleIdx;
  /**
   * Time gained, or time spent doing action in seconds 
   */
  public short Time;
  /**
   * Lap the penalty occurred on 
   */
  public short LapNum;
  /**
   * Number of places gained by this 
   */
  public short PlacesGained;
}
