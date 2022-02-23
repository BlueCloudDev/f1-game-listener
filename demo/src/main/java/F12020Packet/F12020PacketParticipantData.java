package F12020Packet;

public class F12020PacketParticipantData {
  /**
   * Number of active cars in the data – should match number of cars on HUD
   */
  public short NumActiveCars;
  public F12020ParticipantData[] ParticipantData = new F12020ParticipantData[22];
}
