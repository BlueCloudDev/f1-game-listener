package F12020Packet;


public class F12020PacketHeader {
  /**
   * 2020.
   */
  public int PacketFormat;
  /**
   * Game major version - "X.00"
   */
  public short GameMajorVersion;
  /**
   * Game minor version - "1.XX"
   */
  public short GameMinorVersion;
  /**
   * Version of this packet type, all start from 1.
   */
  public short PacketVersion;
  /**
   * Identifier for the packet type: 0 - Motion 1 - Session 2 - Lap Data 3 -
   * Event 4 - Participants 5 - Car Setups 6 - Car Telemetry 7 - Car Status 8 -
   * Final Classification
   */
  public short PacketId;
  /**
   * Unique identifier for the session.
   */
  public Long SessionUID;
  /**
   * Session timestamp.
   */
  public float SessionTime;
  /**
   * Identifier for the frame the data was retrieved on.
   */
  public Long FrameIdentifier;
  /**
   * Index of player's car in the array.
   */
  public short PlayerCarIndex;
  /**
   * Index of second player's car in the array.
   */
  public short SecondaryPlayerCarIndex;
}
