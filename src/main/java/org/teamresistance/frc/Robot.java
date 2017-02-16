package org.teamresistance.frc;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.components.ui.DirectionalAxis;
import org.strongback.hardware.Hardware;
import org.strongback.components.ui.Gamepad;
import edu.wpi.first.wpilibj.IterativeRobot;
import org.teamresistance.frc.subsystem.drive.Drive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Main robot class. Override methods from {@link IterativeRobot} to define behavior.
 *
 * @author Shreya Ravi
 * @author Rothanak So
 */
public class Robot extends IterativeRobot {
  private final Gamepad xboxController = Hardware.HumanInterfaceDevices.xbox360(0);

  private final Drive drive = new Drive(
      IO.robotDrive,
      xboxController.getLeftY(),
      xboxController.getLeftX(),
      xboxController.getRightX()
  );


  double LeftX = xboxController.getLeftX();
  double LeftY = xboxController.getLeftY();

  DirectionalAxis dPad_1 = xboxController.getDPad(1);


  @Override
  public void robotInit() {

    Strongback.configure().recordNoEvents().recordNoData();
    final SwitchReactor reactor = Strongback.switchReactor();

    // Reset the gyro
    reactor.onTriggered(xboxController.getA(), () -> IO.gyro.getNavX().reset());
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

    SmartDashboard.putNumber("Left X", LeftX);
    SmartDashboard.putNumber("Left Y", LeftY);
    SmartDashboard.putNumber("Right X", );
    SmartDashboard.putNumber("Right Y", );
    SmartDashboard.putNumber("Left Bumper", );
    SmartDashboard.putNumber("Left Trigger", );
    SmartDashboard.putNumber("Right Bumper", );
    SmartDashboard.putNumber("Right Trigger", );
    SmartDashboard.putNumber("A", );
    SmartDashboard.putNumber("B", );
    SmartDashboard.putNumber("X", );
    SmartDashboard.putNumber("Y", );
    SmartDashboard.putNumber("Start", );
    SmartDashboard.putNumber("Start", xboxController.getStart() );
    SmartDashboard.putNumber("DPad 1", );

    Feedback feedback = new Feedback(orientation);
    drive.onUpdate(feedback);
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
  }
}
