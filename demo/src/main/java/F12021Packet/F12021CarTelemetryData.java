package F12021Packet;

public class F12021CarTelemetryData {
  /**
   * Index of car in array
   */
  public int Index;
  /**
   * Speed of car in kilometres per hour
   */
  public int Speed;
  /**
   * Amount of throttle applied (0.0 to 1.0)
   */
  public float Throttle;
  /**
   * Steering (-1.0 (full left lock) to 1.0 (full right lock))
   */
  public float Steer;
  /**
   * Amount of brake applied (0.0 to 1.0)
   */
  public float Brake;
  /**
   * Amount of clutch applied (0 to 100)
   */
  public int Clutch;
  /**
   * Gear selected (1-8, N=0, R=-1)
   */
  public int Gear;
  /**
   * Engine RPM
   */
  public int EngineRPM;
  /**
   * 0 = off, 1 = on
   */
  public int DRS;
  /**
   * Rev lights indicator (percentage)
   */
  public int RevLightsPercent;
  /**
   * Rev lights (bit 0 = leftmost LED, bit 14 = rightmost LED)
   */
  public int RevLightsBitValue;
  /**
   * Brakes temperature (celsius)
   */
  public int[] BrakesTemperature;
  /**
   * Tyres surface temperature (celsius)
   */
  public int[] TyresSurfaceTemperature;
  /**
   * Tyres inner temperature (celsius)
   */
  public int[] TyresInnerTemperature;
  /**
   * Engine temperature (celsius)
   */
  public int EngineTemperature;
  /**
   * Tyres pressure (PSI)
   */
  public float[] TyresPressure;
  /**
   * Driving surface, see appendices
   */
  public int[] SurfaceType;
}
