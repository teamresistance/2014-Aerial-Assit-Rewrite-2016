package org.teamresistance.frc;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.strongback.Strongback;

/**
 * Main robot class. Override methods from {@link IterativeRobot} to define behavior.
 *
 * @author Shreya Ravi
 */
public class Robot extends IterativeRobot {

  Drive drive = drive = new Drive(IO.robotDrive, IO.leftJoystick.getRoll(), IO.leftJoystick.getPitch(), IO.rightJoystick.getRoll());

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
    drive.update();
  }

  @Override
  public void disabledInit() {
    // stop every subsystem
    drive.stop();
    Strongback.disable();
  }
}
