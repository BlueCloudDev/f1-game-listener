package F12020Packet;

public class F12020PacketCarTelemetryData {
  public F12020CarTelemetryData[] CarTelemetryData = new F12020CarTelemetryData[22];
  /**
   * Bit flags specifying which buttons are being pressed currently - see appendices
   */
  public long ButtonStatus;
  /**
   * Index of MFD panel open - 255 = MFD closed
   * Single player, race – 0 = Car setup, 1 = Pits
   * 2 = Damage, 3 =  Engine, 4 = Temperatures
   * May vary depending on game mode                                            
   */
  public short MFDPanelIndex;
  /**
   * See above
   */
  public short MFDPanelIndexSecondaryPlayer;
  /**
   * Suggested gear for the player (1-8) 0 if no gear suggested
   */
  public short SuggestedGear;
}
