package F12021Packet;

public class F12021PacketSessionHistoryData {
  public int Id;
  public String SessionUID;
  /**
   * car index
   */
  public int Index;
  public int SessionLookupID;
  /**
   * Number of laps in the data (including current partial lap)
   */
  public int NumLaps;
  /**
   * Number of tyre stints in the data
   */
  public int NumTyreStints;
  /**
   * Lap of the best lap
   */
  public int BestLapTimeLapNum;
  /**
   * Lap of best Sector 1
   */
  public int BestSector1LapNum;
  /**
   * Lap of best Sector 2
   */
  public int BestSector2LapNum;
  /**
   * Lap of best Sector 3
   */
  public int BestSector3LapNum;
  public F12021LapHistoryData[] LapHistoryData = new F12021LapHistoryData[100];
  public F12021TyreStintHistoryData[] TyreStintsHistoryData = new F12021TyreStintHistoryData[8]; 
}
