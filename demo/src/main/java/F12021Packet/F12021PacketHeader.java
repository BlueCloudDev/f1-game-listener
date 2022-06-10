package F12021Packet;


public class F12021PacketHeader {
  /**
   * 2021.
   */
  public int PacketFormat;
  /**
   * Player name assigned via PLAYER_NAME environment variable on the server
   */
  public String PlayerName;
  public int EventId;
  /**
   * Game major version - "X.00"
   */
  public int GameMajorVersion;
  /**
   * Game minor version - "1.XX"
   */
  public int GameMinorVersion;
  /**
   * Version of this packet type, all start from 1.
   */
  public int PacketVersion;
  /**
   * Identifier for the packet type: 0 - Motion 1 - Session 2 - Lap Data 3 -
   * Event 4 - Participants 5 - Car Setups 6 - Car Telemetry 7 - Car Status 8 -
   * Final Classification 9 - Lobby Info 10 - Car Damage 11 - Session History
   */
  public int PacketId;
  /**
   * Unique identifier for the session.
   */
  public String SessionUID;
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
  public int PlayerCarIndex;
  /**
   * Index of second player's car in the array.
   */
  public int SecondaryPlayerCarIndex;
}
