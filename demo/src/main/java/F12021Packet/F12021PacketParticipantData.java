package F12021Packet;

public class F12021PacketParticipantData {
  /**
   * Number of active cars in the data – should match number of cars on HUD
   */
  public int NumActiveCars;
  public F12021ParticipantData[] ParticipantData = new F12021ParticipantData[22];
}
