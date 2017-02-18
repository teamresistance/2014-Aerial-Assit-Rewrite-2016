package org.teamresistance.frc;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.components.ui.Gamepad;
import org.strongback.hardware.Hardware;
import org.teamresistance.frc.command.DriveToX;
import org.teamresistance.frc.subsystem.drive.Drive;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.teamresistance.frc.subsystem.drive.OpticalFlowController;
import org.teamresistance.frc.util.SynchronousPID;


/**
 * Main robot class. Override methods from {@link IterativeRobot} to define behavior.
 *
 * @author Shreya Ravi
 * @author Rothanak So
 * @author Tarik C. Brown
 * @author Joseph Delcastillo III
 */
public class Robot extends IterativeRobot {
  private final Gamepad xboxDriver = Hardware.HumanInterfaceDevices.xbox360(0);
  //private final Gamepad xboxCoDriver= Hardware.HumanInterfaceDevices.xbox360(1);

  private OpticalFlow opFlow = new OpticalFlow();

  private final Drive drive = new Drive(
      IO.robotDrive,
      IO.navX,
      xboxDriver.getLeftY(),
      xboxDriver.getLeftX(),
      xboxDriver.getRightX()
  );

  @Override
  public void robotInit() {
    opFlow.init();

    Strongback.configure().recordNoEvents().recordNoData();
    final SwitchReactor reactor = Strongback.switchReactor();

    // Reset the gyro
    reactor.onTriggered(xboxDriver.getA(), () -> IO.navX.getRawNavX().reset());

    reactor.onTriggered(xboxDriver.getX(),  () -> new DriveToX(drive,3));

    // Reset the OF sensor
    reactor.onTriggered(xboxDriver.getB(), () -> opFlow.init());
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
    opFlow.update();
    Feedback feedback = new Feedback(IO.navX.getAngle(),opFlow.getX(), opFlow.getY());
    SmartDashboard.putNumber("Gyro", feedback.currentAngle);
    drive.onUpdate(feedback);
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
  }
}
