package org.teamresistance.frc;

/**
 * Allows otherwise-isolated cross-cutting behaviors to be encapsulated into simple
 * classes that are then called from {@link Robot} as appropriate. The most common―
 * but not the only―use case is for the separation of Autonomous and Teleop concerns.
 *
 * @author Rothanak So
 */
interface RobotDelegate {

  /**
   * Called one time when the robot is first initialized during {@link Robot#robotInit()}.
   */
  default void robotInit() {
  }

  /**
   * Called one time during the initialization of the behavior.
   */
  default void onInit() {
  }

  /**
   * Called periodically.
   */
  default void onPeriodic() {
  }

  /**
   * Called once during the disabling of the behavior.
   */
  default void onDisabled() {
  }
}
