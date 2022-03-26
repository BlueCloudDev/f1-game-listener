package F12021Packet;

public class F12021PacketCarTelemetryData {
  public F12021CarTelemetryData[] CarTelemetryData = new F12021CarTelemetryData[22];
  /**
   * Index of MFD panel open - 255 = MFD closed
   * Single player, race – 0 = Car setup, 1 = Pits
   * 2 = Damage, 3 =  Engine, 4 = Temperatures
   * May vary depending on game mode                                            
   */
  public int MFDPanelIndex;
  /**
   * See above
   */
  public int MFDPanelIndexSecondaryPlayer;
  /**
   * Suggested gear for the player (1-8) 0 if no gear suggested
   */
  public int SuggestedGear;
}
