package F12021Packet;

public class F12021CarDamageData {
  /**
   * Index of the car
   */
  public int Index;
  public int SessionLookupID;
  /**
   * Tyre Wear (percentage)
   */
  public float[] TyresWear;
  /**
   * Tyre Damage (percentage)
   */
  public int[] TyresDamage;
  /**
   * Brakes Damage (percentage)
   */
  public int[] BrakesDamage;
  /**
   * Front left wing damage (percentage)
   */
  public int FrontLeftWingDamage;
  /**
   * Front right wing damage (percentage)
   */
  public int FrontRightWingDamage;
  /**
   * Rear wing damage (percentage)
   */
  public int RearWingDamage;
  /**
   * Floor damage (Percentage)
   */
  public int FloorDamage;
  /**
   * Diffuser Damage (percentage)
   */
  public int DiffuserDamage;
  /**
   * Sidepod damage (percentage)
   */
  public int SidepodDamage;
  /**
   * Indicator for DRS fault, 0 = ok, 1 = fault
   */
  public int DRSFault;
  /**
   * Gear box damage (percentage)
   */
  public int GearBoxDamage;
  /**
   * Engine damage (percentage)
   */
  public int EngineDamage;
  /**
   * Engine wear MGU-H (percentage)
   */
  public int EngineMGUHWear;
  /**
   * Engine wear ES (percentage)
   */
  public int EngineESWear;
  /**
   * Engine wear CE (percentage)
   */
  public int EngineCEWear;
  /**
   * Engine wear ICE (percentage)
   */
  public int EngineICEWear;
  /**
   * Engine wear MGU-K (percentage)
   */
  public int EngineMGUKWear;
  /**
   * Engine wear TC (percentage)
   */
  public int EngineTCWear;
}
