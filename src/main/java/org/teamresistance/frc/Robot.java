package org.teamresistance.frc;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Command;
import org.strongback.components.ui.Gamepad;
import org.strongback.hardware.Hardware;
import org.teamresistance.frc.command.*;
import org.teamresistance.frc.subsystem.drive.Drive;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.teamresistance.frc.command.DriveOfsXY;


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

    final double ofsxSetpoint = 1.0;
    SmartDashboard.putNumber("OFSX Setpoint", ofsxSetpoint);
    reactor.onTriggeredSubmit(xboxDriver.getLeftBumper(),  () -> new DriveToX(drive,
        SmartDashboard.getNumber("OFSX Setpoint", ofsxSetpoint)));
    final double ofsySetpoint = 10.0;
    SmartDashboard.putNumber("OFSY Setpoint", ofsySetpoint);
    reactor.onTriggeredSubmit(xboxDriver.getRightBumper(), () -> new DriveToY(drive,
        SmartDashboard.getNumber("OFSY Setpoint", ofsySetpoint)));
    reactor.onTriggeredSubmit(xboxDriver.getStart(), () -> Command.cancel(drive));

    final double ofsySeqSetpoint = 5.0;
    final double ofsyTimeout = 1.0;
    SmartDashboard.putNumber("OFSY Timeout",  ofsyTimeout);
    SmartDashboard.putNumber("OFSY Setpoint(Sequence)",  ofsySeqSetpoint);
    reactor.onTriggeredSubmit(xboxDriver.getY(), () -> new DriveToYSequence(drive,
        SmartDashboard.getNumber("OFSY Setpoint(Sequence)", ofsySeqSetpoint),
        SmartDashboard.getNumber("OFSY Timeout", ofsyTimeout)));

    final double ofsX = 5;
    final double ofsY = 5;
    SmartDashboard.putNumber("[XY] x", ofsX);
    SmartDashboard.putNumber("[XY] y", ofsY);
    reactor.onTriggeredSubmit(xboxDriver.getX(), () -> new DriveOfsXY(drive,
        opFlow,
        SmartDashboard.getNumber("[XY] x", ofsX),
        SmartDashboard.getNumber("[XY] y", ofsY)
    ));

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