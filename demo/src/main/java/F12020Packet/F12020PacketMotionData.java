package F12020Packet;

public class F12020PacketMotionData {
  public F12020CarMotionData[] CarMotionData = new F12020CarMotionData[22];

  // Additional information for player car ONLY
  /**
   * Note: All wheel arrays have the following order: RL, RR, FL, FR
   */
  public float[] SuspensionPosition = new float[4];
  /**
   * Velocity of the suspension
   */
  public float[] SuspensionVelocity = new float[4];
  /**
   * Acceleration of the suspension
   */
  public float[] SuspensionAcceleration = new float[4];
  /**
   * Speed of each wheel
   */
  public float[] WheelSpeed = new float[4];
  /**
   * Slip of each wheel
   */
  public float[] WheelSlip = new float[4];
  /**
   * Velocity in local space X
   */
  public float LocalVelocityX;
  /**
   * Velocity in local space Y
   */
  public float LocalVelocityY;
  /**
   * Velocity in local space Z
   */
  public float LocalVelocityZ;
  /**
   * Angular velocity x-component
   */
  public float AngularVelocityX;
  /**
   * Angular velocity y-component
   */
  public float AngularVelocityY;
  /**
   * Angular velocity Z-component
   */
  public float AngularVelocityZ;
  /**
   * Angular Acceleration x-component
   */
  public float AngularAccelerationX;
  /**
   * Angular Acceleration y-component
   */
  public float AngularAccelerationY;
  /**
   * Angular Acceleration z-component
   */
  public float AngularAccelerationZ;
  /**
   * Current front wheels angle in radians
   */
  public float FrontWheelsAngle;
}
