package F12021Packet;

public class F12021ParticipantData {
  /**
   * Index of participant in array
   */
  public int Index;
  public Long FrameIdentifier;
  public float SessionTime;
  public int SessionLookupID;
  /**
   * Whether the vehicle is AI (1) or Human (0) controlled
   */
  public int AiControlled;
  /**
   * Driver id - see appendix
   */
  public int DriverId;
  /**
   * Network id = unique identifier for network players
   */
  public int NetworkId;
  /**
   * Team id - see appendix
   */
  public int TeamId;
  /**
   * My team flag - 1 = My Team, 0 = otherwise
   */
  public int MyTeam;
  /**
   * Race number of the car
   */
  public int RaceNumber;
  /**
   * Nationality of the driver
   */
  public int Nationality;
  /**
   *Name of participant in UTF-8 format – null terminated Will be truncated with … (U+2026) if too long
   */
  public String Name;
  /**
   * The player's UDP setting, 0 = restricted, 1 = public
   */
  public int YourTelemetry;
}
