package F12020Packet;

public class F12020ParticipantData {
  /**
   * Index of participant in array
   */
  public int Index;
  /**
   * Whether the vehicle is AI (1) or Human (0) controlled
   */
  public short AiControlled;
  /**
   * Driver id - see appendix
   */
  public short DriverId;
  /**
   * Team id - see appendix
   */
  public short TeamId;
  /**
   * Race number of the car
   */
  public short RaceNumber;
  /**
   * Nationality of the driver
   */
  public short Nationality;
  /**
   *Name of participant in UTF-8 format – null terminated Will be truncated with … (U+2026) if too long
   */
  public String Name;
  /**
   * The player's UDP setting, 0 = restricted, 1 = public
   */
  public short YourTelemetry;
}
