package F12021Packet;

public class F12021TyreStintHistoryData {
  public int Id;
  public int StintNumber;
  public int SessionHistoryId;
  /**
   * Lap the tyre usage ends on (255 of current tyre)
   */
  public int EndLap;
  /**
   * Tyre actual compound
   */
  public int TyreActualCompound;
  public String TyreActualCompoundString;
  /**
   * Tyre visual compound
   */
  public int TyreVisualCompound;
  public String TyreVisualCompoundString;
}
