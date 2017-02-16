package org.teamresistance.frc;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import edu.wpi.first.wpilibj.Sendable;
import org.strongback.hardware.Hardware;
import org.strongback.components.ui.Gamepad;
import edu.wpi.first.wpilibj.IterativeRobot;
import org.teamresistance.frc.subsystem.drive.Drive;
import org.strongback.components.ui.ContinuousRange;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Main robot class. Override methods from {@link IterativeRobot} to define behavior.
 *
 * @author Shreya Ravi
 * @author Rothanak So
 */
public class Robot extends IterativeRobot {
  private final Gamepad xboxDriver = Hardware.HumanInterfaceDevices.xbox360(0); //The 1st Controller goes in port 0
  private final Gamepad xboxCoDriver= Hardware.HumanInterfaceDevices.xbox360(1);    //The 1st Controller goes in port 1

  private final Drive drive = new Drive(
      IO.robotDrive,
      xboxDriver.getLeftY(),
      xboxDriver.getLeftX(),
      xboxDriver.getRightX()
  );


  public Robot() {

  }


  @Override
  public void robotInit() {

    Strongback.configure().recordNoEvents().recordNoData();
    final SwitchReactor reactor = Strongback.switchReactor();

    // Reset the gyro
    reactor.onTriggered(xboxDriver.getA(), () -> IO.gyro.getNavX().reset());

  }


  @Override
  public void autonomousInit() {
    Strongback.start();
  }

  @Override
  public void teleopInit() {
    Strongback.start();
  }

  @Override
  public void teleopPeriodic() {
    // Post our orientation on the SD for debugging purposes
    double orientation = IO.gyro.getAngle();
    SmartDashboard.putNumber("Gyro Angle", orientation);

    Feedback feedback = new Feedback(orientation);
    drive.onUpdate(feedback);
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
  }
}
