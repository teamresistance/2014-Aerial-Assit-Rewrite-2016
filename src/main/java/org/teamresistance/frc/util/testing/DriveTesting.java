package org.teamresistance.frc.util.testing;

import org.strongback.Strongback;
import org.strongback.command.Command;
import org.strongback.command.CommandGroup;
import org.strongback.components.ui.FlightStick;
import org.teamresistance.frc.NavX;
import org.teamresistance.frc.command.BrakeCommand;
import org.teamresistance.frc.command.DriveTimedCommand;
import org.teamresistance.frc.command.HoldAngleCommand;
import org.teamresistance.frc.subsystem.drive.Drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.teamresistance.frc.util.testing.JoystickMap.LeftJoystick.ANGLE_HOLD;
import static org.teamresistance.frc.util.testing.JoystickMap.LeftJoystick.ANGLE_HOLD_A;
import static org.teamresistance.frc.util.testing.JoystickMap.LeftJoystick.ANGLE_HOLD_B;
import static org.teamresistance.frc.util.testing.JoystickMap.LeftJoystick.ANGLE_HOLD_C;
import static org.teamresistance.frc.util.testing.JoystickMap.LeftJoystick.AUTO_TIMED_HOPPER;
import static org.teamresistance.frc.util.testing.JoystickMap.LeftJoystick.CANCEL;
import static org.teamresistance.frc.util.testing.JoystickMap.LeftJoystick.NAVX_RESET;
import static org.teamresistance.frc.util.testing.JoystickMap.UNASSIGNED;

public class DriveTesting extends CommandTesting {
  private final Drive drive;
  private final NavX navX;

  public DriveTesting(Drive drive, NavX navX, FlightStick joystickA, FlightStick joystickB,
                      FlightStick joystickC) {
    super(joystickA, joystickB, joystickC);
    this.drive = drive;
    this.navX = navX;
  }

  public void enableAngleHold() {
    // Hold the current angle of the robot while the trigger is held
    reactor.onTriggeredSubmit(joystickA.getButton(ANGLE_HOLD), () -> new HoldAngleCommand(drive, navX.getAngle()));
    reactor.onUntriggeredSubmit(joystickA.getButton(ANGLE_HOLD), () -> {
      drive.setOpenLoop();
      return Command.cancel(drive);
    });
  }

  public void enableCancelling() {
    // Cancel ongoing Drive commands. The interrupted commands should hand back operator control
    reactor.onTriggered(joystickA.getButton(CANCEL), () -> {
      Strongback.submit(Command.cancel(drive));
      drive.setOpenLoop();
    });
  }

  public void enableAngleHoldTests() {
    final int defaultAngle = 0;
    SmartDashboard.putNumber("Angle Target", defaultAngle);
    reactor.onTriggeredSubmit(joystickA.getButton(ANGLE_HOLD_A), () -> new HoldAngleCommand(drive, 45));
    reactor.onTriggeredSubmit(joystickA.getButton(ANGLE_HOLD_B), () -> new HoldAngleCommand(drive, 0));
    reactor.onTriggeredSubmit(joystickA.getButton(ANGLE_HOLD_C), () -> new HoldAngleCommand(drive,
        SmartDashboard.getNumber("Angle Target", defaultAngle)));
  }

  public void enableNavXReset() {
    // Reset the NavX
    reactor.onTriggered(joystickA.getButton(NAVX_RESET), () -> navX.getRawNavX().reset());
  }

  public void enableTimedAutoToHopper() {
    // Drive straight, pause for 2s, then strafe 90 degrees
    reactor.onTriggeredSubmit(joystickA.getButton(AUTO_TIMED_HOPPER), () -> CommandGroup.runSequentially(
        new DriveTimedCommand(drive, 0, 0, 0.9),
        new DriveTimedCommand(drive, 0, 90, 1.5),
        Command.pause(1.5),
        new DriveTimedCommand(drive, 0, 270, 1.0),
        new DriveTimedCommand(drive, 0, 180, 0.6),
        new HoldAngleCommand(drive, 135)
    ));
  }

  public void enableHardBraking() {
    reactor.onTriggeredSubmit(joystickA.getButton(UNASSIGNED), () -> new BrakeCommand(drive, 1));
  }

  public void enableHardBrakingTests() {
    // Lock the drive motors and hopefully stop the robot
    reactor.onTriggeredSubmit(joystickA.getButton(UNASSIGNED), () -> CommandGroup.runSequentially(
        new DriveTimedCommand(drive, 0, 0, 1.5),
        new BrakeCommand(drive, 1)
    ));
  }
}
