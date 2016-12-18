package org.teamresistance.frc;

import org.strongback.Strongback;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * Main robot class. Override methods from {@link IterativeRobot} to define behavior.
 */
public class Robot extends IterativeRobot {
  private final RobotDelegate teleop = new TeleopDelegate();
  private final RobotDelegate auto = new AutoDelegate();

  @Override
  public void robotInit() {
    Strongback.configure()
        .recordNoCommands()
        .recordNoData()
        .recordNoEvents()
        .initialize();

    auto.robotInit();
    teleop.robotInit();
  }

  @Override
  public void autonomousInit() {
    Strongback.start();
    auto.onInit();
  }

  @Override
  public void autonomousPeriodic() {
    auto.onPeriodic();
  }

  @Override
  public void teleopInit() {
    Strongback.restart();
    teleop.onInit();
  }

  @Override
  public void teleopPeriodic() {
    teleop.onPeriodic();
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
    auto.onDisabled();
    teleop.onDisabled();
  }
}
