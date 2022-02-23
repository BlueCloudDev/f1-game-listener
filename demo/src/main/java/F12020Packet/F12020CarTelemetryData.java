package F12020Packet;

public class F12020CarTelemetryData {
  /**
   * Speed of car in kilometres per hour
   */
  public short Speed;
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
  public short Clutch;
  /**
   * Gear selected (1-8, N=0, R=-1)
   */
  public short Gear;
  /**
   * Engine RPM
   */
  public short EngineRPM;
  /**
   * 0 = off, 1 = on
   */
  public short DRS;
  /**
   * Rev lights indicator (percentage)
   */
  public short RevLightsPercent;
  /**
   * Brakes temperature (celsius)
   */
  public short[] BrakesTemperature;
  /**
   * Tyres surface temperature (celsius)
   */
  public short[] TyresSurfaceTemperature;
  /**
   * Tyres inner temperature (celsius)
   */
  public short[] TyresInnerTemperature;
  /**
   * Engine temperature (celsius)
   */
  public short EngineTemperature;
  /**
   * Tyres pressure (PSI)
   */
  public float[] TyresPressure;
  /**
   * Driving surface, see appendices
   */
  public short[] SurfaceType;
}
