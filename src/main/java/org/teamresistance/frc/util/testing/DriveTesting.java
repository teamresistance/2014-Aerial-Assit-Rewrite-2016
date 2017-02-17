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

public class DriveTesting extends CommandTesting {
  private final Drive drive;
  private final NavX navX;

  public DriveTesting(Drive drive, NavX navX, FlightStick joystickA, FlightStick joystickB) {
    super(joystickA, joystickB);
    this.drive = drive;
    this.navX = navX;
  }

  public void enableCancelling() {
    // Cancel ongoing Drive commands. The interrupted commands should hand back operator control
    reactor.onTriggered(joystickA.getButton(3), () -> {
      Strongback.submit(Command.cancel(drive));
      drive.setOpenLoop();
    });
  }

  public void enableAngleHold() {
    // Hold the current angle of the robot while the trigger is held
    reactor.onTriggeredSubmit(joystickA.getTrigger(), () -> new HoldAngleCommand(drive, navX.getAngle()));
    reactor.onUntriggeredSubmit(joystickA.getTrigger(), () -> Command.cancel(drive)); // FIXME doesn't cancel
  }

  public void enableAngleHoldTests() {
    reactor.onTriggeredSubmit(joystickB.getButton(3), () -> new HoldAngleCommand(drive, 135));
    reactor.onTriggeredSubmit(joystickB.getButton(4), () -> new HoldAngleCommand(drive, 0));
  }

  public void enableStrafeTests() {
    // Drive straight, strafe 90 degrees, and strafe 45 -- each for 2 seconds
    reactor.onTriggeredSubmit(joystickA.getButton(6), () -> new DriveTimedCommand(drive, 0, 0, 1.5));
    reactor.onTriggeredSubmit(joystickA.getButton(11), () -> new DriveTimedCommand(drive, 0, 90, 1.5));
    reactor.onTriggeredSubmit(joystickA.getButton(10), () -> new DriveTimedCommand(drive, 0, 45, 1.5));
  }

  public void enableTimedAutoToHopper() {
    // Drive straight, pause for 2s, then strafe 90 degrees
    reactor.onTriggeredSubmit(joystickA.getButton(7), () -> CommandGroup.runSequentially(
        new DriveTimedCommand(drive, 0, 0, 0.9),
        new DriveTimedCommand(drive, 0, 90, 1.5),
        Command.pause(1.5),
        new DriveTimedCommand(drive, 0, 270, 1.0),
        new DriveTimedCommand(drive, 0, 180, 0.6),
        new HoldAngleCommand(drive, 135)
    ));
  }

  public void enableHardBraking() {
    reactor.onTriggeredSubmit(joystickA.getButton(5), () -> new BrakeCommand(drive, 1));
  }

  public void enableHardBrakingTests() {
    // Lock the drive motors and hopefully stop the robot
    reactor.onTriggeredSubmit(joystickA.getButton(2), () -> CommandGroup.runSequentially(
        new DriveTimedCommand(drive, 0, 0, 1.5),
        new BrakeCommand(drive, 1)
    ));
  }

  public void enableNavXReset() {
    // Reset the NavX
    reactor.onTriggered(joystickA.getButton(6), () -> navX.getRawNavX().reset());
  }
}
