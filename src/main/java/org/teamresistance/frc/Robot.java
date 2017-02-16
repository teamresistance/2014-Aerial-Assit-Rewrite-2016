package org.teamresistance.frc;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
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

  private OpticalFlow opFlow = new OpticalFlow();


  private final Drive drive = new Drive(
      IO.robotDrive,
      xboxController.getLeftY(),
      xboxController.getLeftX(),
      xboxController.getRightX()
  );

  @Override
  public void robotInit() {
    opFlow.init();

    Strongback.configure().recordNoEvents().recordNoData();
    final SwitchReactor reactor = Strongback.switchReactor();
//
    // Reset the gyro
    reactor.onTriggered(xboxController.getButton(7), () -> IO.gyro.getNavX().reset());
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

    //XboxControl.update();

    opFlow.update();

    Feedback feedback = new Feedback(orientation);
    drive.onUpdate(feedback);
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
  }
}
