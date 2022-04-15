package F12021Packet;

public class F12021CarSetupData {
  /**
   * Index of car data in array
   */
  public int Index;
  public Long FrameIdentifier;
  public float SessionTime;
  public int SessionLookupID;
  /**
   * Front wing aero
   */
  public int FrontWing;
  /**
   * Rear wing aero
   */
  public int RearWing;
  /**
   * Differential adjustment on throttle (percentage)
   */
  public int OnThrottle;
  /**
   * Differential adjustment off throttle (percentage)
   */
  public int OffThrottle;
  /**
   * Front camber angle (suspension geometry)
   */
  public float FrontCamber;
  /**
   * Rear camber angle (suspension geometry)
   */
  public float RearCamber;
  /**
   * Front toe angle (suspension geometry)
   */
  public float FrontToe;
  /**
   * Rear toe angle (suspension geometry)
   */
  public float RearToe;
  /**
   * Front suspension
   */
  public int FrontSuspension;
  /**
   * Rear suspension
   */
  public int RearSuspension;
  /**
   * Front anti-roll bar
   */
  public int FrontAntiRollBar;
  /**
   * Rear anti-roll bar
   */
  public int RearAntiRollBar;
  /**
   * Front suspension height
   */
  public int FrontSuspensionHeight;
  /**
   * Rear suspension height
   */
  public int RearSuspensionHeight;
  /**
   * Brake pressure (percentage)
   */
  public int BrakePressure;
  /**
   * Brake bias (percentage)
   */
  public int BrakeBias;
  /**
   * Rear left tyre pressure (PSI)
   */
  public float RearLeftTyrePressure;
  /**
   * Rear right tyre pressure (PSI)
   */
  public float RearRightTyrePressure;
  /**
   * Front left tyre pressure (PSI)
   */
  public float FrontLeftTyrePressure;
  /**
   * Front right tyre pressure (PSI)
   */
  public float FrontRightTyrePressure;
  /**
   * Ballast
   */
  public int Ballast;
  /**
   * Fuel Load
   */
  public float FuelLoad;
}
