package org.teamresistance.frc;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Command;
import org.strongback.command.CommandGroup;
import org.strongback.components.ui.FlightStick;
import org.strongback.hardware.Hardware;
import org.teamresistance.frc.command.HaltDrivingCommand;
import org.teamresistance.frc.command.HoldAngleCommand;
import org.teamresistance.frc.command.StrafeCommand;
import org.teamresistance.frc.subsystem.drive.Drive;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Main robot class. Override methods from {@link IterativeRobot} to define behavior.
 *
 * @author Shreya Ravi
 * @author Rothanak So
 */
public class Robot extends IterativeRobot {
  private final FlightStick leftJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(0);
  private final FlightStick rightJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(1);
  private final FlightStick coJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(2);

  private final Drive drive = new Drive(
      IO.robotDrive,
      leftJoystick.getRoll(),
      leftJoystick.getPitch(),
      rightJoystick.getRoll()
  );

  @Override
  public void robotInit() {
    Strongback.configure().recordNoEvents().recordNoData().initialize();
    final SwitchReactor reactor = Strongback.switchReactor();

    // Hold the current angle of the robot while the trigger is held
    reactor.onTriggeredSubmit(leftJoystick.getTrigger(), () -> new HoldAngleCommand(drive, 90));
    reactor.onUntriggeredSubmit(leftJoystick.getTrigger(), () -> Command.cancel(drive));

    // Drive straight, strafe 90 degrees, and strafe 45 -- each for 2 seconds
    reactor.onTriggeredSubmit(leftJoystick.getButton(6), () -> new StrafeCommand(drive, 0, 1.5));
    reactor.onTriggeredSubmit(leftJoystick.getButton(11), () -> new StrafeCommand(drive, 90, 1.5));
    reactor.onTriggeredSubmit(leftJoystick.getButton(10), () -> new StrafeCommand(drive, 45, 1.5));

    // Drive straight, pause for 2s, then strafe 90 degrees
    reactor.onTriggeredSubmit(leftJoystick.getButton(7), () -> CommandGroup.runSequentially(
        new StrafeCommand(drive, 0, 1.5),
        new HaltDrivingCommand(drive, 1.5),
        new StrafeCommand(drive, 90, 1.5)
    ));

    // Cancel ongoing Drive commands. The interrupted commands should hand back operator control
    reactor.onTriggered(leftJoystick.getButton(3), () -> Strongback.submit(Command.cancel(drive)));

    // Lock the drive motors and hopefully stop the robot
    reactor.onTriggeredSubmit(leftJoystick.getButton(5), () -> new HaltDrivingCommand(drive, 0.5));
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

    Pose pose = new Pose(orientation);
    drive.onUpdate(pose);
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
  }
}
