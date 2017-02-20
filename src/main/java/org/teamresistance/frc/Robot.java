package org.teamresistance.frc;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.CommandGroup;
import org.strongback.components.ui.Gamepad;
import org.strongback.hardware.Hardware;
import org.teamresistance.frc.command.DriveOpticalX;
import org.teamresistance.frc.command.DriveOpticalXBraking;
import org.teamresistance.frc.command.DriveOpticalXY;
import org.teamresistance.frc.command.DriveOpticalY;
import org.teamresistance.frc.command.DriveOpticalYBraking;
import org.teamresistance.frc.subsystem.drive.Drive;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


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

  private OpticalFlow opticalFlow = new OpticalFlow();
  private final Drive drive = new Drive(IO.robotDrive, IO.navX,
      xboxDriver.getLeftY(), xboxDriver.getLeftX(), xboxDriver.getRightX());

  @Override
  public void robotInit() {
    Strongback.configure().recordNoEvents().recordNoData();
    final SwitchReactor reactor = Strongback.switchReactor();

    opticalFlow.init();

    // Reset the gyro
    reactor.onTriggered(xboxDriver.getA(), () -> IO.navX.getRawNavX().reset());

    // Drive X
    final double xSetpoint = 1.86;
    SmartDashboard.putNumber("[X] Setpoint", xSetpoint);
    reactor.onTriggeredSubmit(xboxDriver.getLeftBumper(),
        () -> new DriveOpticalX(drive, SmartDashboard.getNumber("[X] Setpoint", xSetpoint)));

    // Drive Y
    final double ySetpoint = 1.86;
    SmartDashboard.putNumber("[Y] Setpoint", ySetpoint);
    reactor.onTriggeredSubmit(xboxDriver.getRightBumper(),
        () -> new DriveOpticalY(drive, SmartDashboard.getNumber("[Y] Setpoint", ySetpoint)));

    // Drive Y with braking
    final double yWithBrakeSetpoint = 1.86;
    final double yWithBrakeTimeout = 1.0;
    SmartDashboard.putNumber("[Y Braking] Timeout", yWithBrakeTimeout);
    SmartDashboard.putNumber("[Y Braking] Setpoint", yWithBrakeSetpoint);
    reactor.onTriggeredSubmit(xboxDriver.getY(),
        () -> new DriveOpticalYBraking(drive,
            SmartDashboard.getNumber("[Y Braking] Setpoint", yWithBrakeSetpoint),
            SmartDashboard.getNumber("[Y Braking] Timeout", yWithBrakeTimeout)
        ));

    // Drive X with braking
    final double xWithBrakeSetpoint = 1.86;
    final double xWithBrakeTimeout = 1.0;
    SmartDashboard.putNumber("[X Braking] Timeout", xWithBrakeTimeout);
    SmartDashboard.putNumber("[X Braking] Setpoint", xWithBrakeSetpoint);
    reactor.onTriggeredSubmit(xboxDriver.getX(),
        () -> new DriveOpticalXBraking(drive,
            SmartDashboard.getNumber("[X Braking] Setpoint", xWithBrakeSetpoint),
            SmartDashboard.getNumber("[X Braking] Timeout", xWithBrakeTimeout)
        ));

    // Drive X Y sequentially
    reactor.onTriggeredSubmit(xboxDriver.getSelect(),
        () -> CommandGroup.runSequentially(
            new DriveOpticalYBraking(drive,
                SmartDashboard.getNumber("[Y Braking] Setpoint", yWithBrakeSetpoint),
                SmartDashboard.getNumber("[Y Braking] Timeout", yWithBrakeTimeout)
            ),
            new DriveOpticalXBraking(drive,
                SmartDashboard.getNumber("[X Braking] Setpoint", xWithBrakeSetpoint),
                SmartDashboard.getNumber("[X Braking] Timeout", xWithBrakeTimeout)
            )
        ));

    // Drive X Y together
    final double xySetpointX = 1.86;
    final double xySetpointY = 1.86;
    SmartDashboard.putNumber("[XY] Setpoint X", xySetpointX);
    SmartDashboard.putNumber("[XY] Setpoint Y", xySetpointY);
    reactor.onTriggeredSubmit(xboxDriver.getStart(),
        () -> new DriveOpticalXY(drive,
            SmartDashboard.getNumber("[XY] Setpoint X", xySetpointX),
            SmartDashboard.getNumber("[XY] Setpoint Y", xySetpointY)));

    // Reset the OF sensor
    reactor.onTriggered(xboxDriver.getB(), () -> opticalFlow.init());
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
    opticalFlow.update();

    Feedback feedback = new Feedback(IO.navX.getAngle(), opticalFlow.getX(), opticalFlow.getY());
    SmartDashboard.putNumber("Gyro", feedback.currentAngle);
    drive.onUpdate(feedback);
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
  }
}