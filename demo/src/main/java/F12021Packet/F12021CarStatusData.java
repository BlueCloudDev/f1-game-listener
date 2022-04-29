package F12021Packet;

public class F12021CarStatusData {
  /**
   * Index of car status data in array
   */
  public int Index;
  public int SessionLookupID;
  /**
   * 0 (off) - 2 (high)
   */
  public int TractionControl;
  /**
   * 0 (off) - 1 (on)
   */
  public int AntiLockBrakes;
  /**
   * Fuel mix - 0 = lean, 1 = standard, 2 = rich, 
   */
  public int FuelMix;
  /**
   * Front brake bias (percentage)
   */
  public int FrontBrakeBias;
  /**
   * Pit limiter status - 0 = off, 1 = on
   */
  public int PitLimiterStatus;
  /**
   * Current fuel mass
   */
  public float FuelInTank;
  /**
   * FuelCapacity
   */
  public float FuelCapacity;
  /**
   * Fuel remaining in terms of laps (value on MFD)
   */
  public float FuelRemainingLaps;
  /**
   * Cars max RPM, point of rev limiter
   */
  public int MaxRPM;
  /**
   * Car idle RPM
   */
  public int IdleRPM;
  /**
   * Maximum number of gears
   */
  public int MaxGears;
  /**
   * 0 = not allowed, 1 = allowed, -1 = unknown
   */
  public int DRSAllowed;
  /**
   * 0 = DRS not available, non-zero - DRS will be available in [X] metres                            
   */
  public int DRSActivationDistance;
  /**
   * F1 Modern - 16 = C5, 17 = C4, 18 = C3, 19 = C2, 20 = C1
   * 7 = inter, 8 = wet
   * F1 Classic - 9 = dry, 10 = wet
   * F2 – 11 = super soft, 12 = soft, 13 = medium, 14 = hard
   * 15 = wet
   */
  public int ActualTyreCompound;
  /**
   * F1 visual (can be different from actual compound)
   * 16 = soft, 17 = medium, 18 = hard, 7 = inter, 8 = wet
   * F1 Classic – same as above
   * F2 – same as above
   */
  public int VisualTyreCompound;
  /**
   * Age in laps of the current set of tyres
   */
  public int TyresAgeLaps;
  /**
   * -1 = invalid/unknown, 0 = none, 1 = green
   * 2 = blue, 3 = yellow, 4 = red
   */
  public int VehicleFiaFlags;
  /**
   * ERS energy store in Joules
   */
  public float ERSStoreEnergy;
  /**
   * ERS deployment mode, 0 = none, 1 = medium 2 = overtake, 3 = hotlap
   */
  public int ERSDeployMode;
  /**
   * ERS energy harvested this lap by MGU-K
   */
  public float ERSHarvestedThisLapMGUK;
  /**
   * ERS energy harvested this lap by MGU-H
   */
  public float ERSHarvestedThisLapMGUH;
  /**
   * ERS energy deployed this lap
   */
  public float ERSDeployedThisLap;
  /**
   * Whether the car is paused in a network game
   */
  public int NetworkPaused;
}
