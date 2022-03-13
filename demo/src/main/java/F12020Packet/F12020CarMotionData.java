package F12020Packet;

public class F12020CarMotionData {
  /**
   * Index of car motion data in array
   */
  public int Index;
  /**
   * World space X position
   */
  public float WorldPositionX;

  /**
   * World space Y position
   */
  public float WorldPositionY;

  /**
   * World space Z position
   */
  public float WorldPositionZ;

  /**
   * Velocity in world space X
   */
  public float WorldVelocityX;

  /**
   * Velocity in world space Y
   */
  public float WorldVelocityY;

  /**
   * Velocity in world space Z
   */
  public float WorldVelocityZ;

  /**
   * World space forward X direction (normalized)
   */
  public float WorldForwardDirX;

  /**
   * World space forward Y direction (normalized)
   */
  public float WorldForwardDirY;

  /**
   * World space forward Z direction (normalized)
   */
  public float WorldForwardDirZ;

  /**
   * World space right X direction (normalized)
   */
  public float WorldRightDirX;

  /**
   * World space right Y direction (normalized)
   */
  public float WorldRightDirY;

  /**
   * World space right Z direction (normalized)
   */
  public float WorldRightDirZ;

  /**
   * Lateral G-Force component
   */
  public float GForceLateral;

  /**
   * Longitudinal G-Force component
   */
  public float GForceLongitudinal;

  /**
   * Vertical G-Force component
   */
  public float GForceVertical;

  /**
   * Yaw angle in radians
   */
  public float Yaw;

  /**
   * Pitch angle in radians
   */
  public float Pitch;

  /**
   * Roll angle in radians
   */
  public float Roll;
}
