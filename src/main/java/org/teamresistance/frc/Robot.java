package org.teamresistance.frc;

import org.strongback.Strongback;
import org.teamresistance.frc.sensor.goal.GoalSensor;
import org.teamresistance.frc.subsystem.drive.Drive;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * Main robot class. Override methods from {@link IterativeRobot} to define behavior.
 *
 * @author Shreya Ravi
 */
public class Robot extends IterativeRobot {
  private final Drive drive = new Drive(
      IO.robotDrive,
      IO.leftJoystick.getRoll(),
      IO.leftJoystick.getPitch(),
      IO.rightJoystick.getRoll()
  );
  private final GoalSensor goalSensor = new GoalSensor();

  @Override
  public void robotInit() {
    Strongback.configure().recordNoEvents().recordNoData().initialize();
  }

  @Override
  public void autonomousInit() {
    Strongback.start();
  }

  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopInit() {
    Strongback.start();
  }

  @Override
  public void teleopPeriodic() {
    Pose currentPose = new Pose(goalSensor::getGoalOffset);
    drive.onUpdate(currentPose);
  }

  @Override
  public void disabledInit() {
    // stop every subsystem
    drive.stop();
    Strongback.disable();
  }
}
