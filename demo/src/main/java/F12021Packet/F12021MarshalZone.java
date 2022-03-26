package F12021Packet;

public class F12021MarshalZone {
  /**
   * Fraction (0..1) of way through the lap the marshal zone starts
   */
  public float ZoneStart;
  /**
   * -1 = invalid/unknown, 0 = none, 1 = green, 2 = blue, 3 = yellow, 4 = red
   */
  public int ZoneFlag;
}
