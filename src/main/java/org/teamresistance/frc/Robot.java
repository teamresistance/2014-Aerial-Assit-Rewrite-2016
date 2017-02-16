package org.teamresistance.frc;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.components.ui.FlightStick;
import org.strongback.drive.MecanumDrive;
import org.strongback.hardware.Hardware;
import org.teamresistance.frc.subsystem.drive.Drive;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * Main robot class. Override methods from {@link IterativeRobot} to define behavior.
 *
 * @author Shreya Ravi
 * @author Rothanak So
 * @author Tarik C. Brown
 */
public class Robot extends IterativeRobot {
  private final FlightStick leftJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(0);
  private final FlightStick rightJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(1);
  private final FlightStick coJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(2);

  private final MecanumDrive robotDrive =
      new MecanumDrive(IO.lfMotor, IO.lRMotor, IO.rfMotor, IO.rrMotor, IO.navX);
  private final Drive drive =
      new Drive(robotDrive, leftJoystick.getRoll(), leftJoystick.getPitch(), rightJoystick.getRoll());

  @Override
  public void robotInit() {
    Strongback.configure().recordNoEvents().recordNoData();
    final SwitchReactor reactor = Strongback.switchReactor();

    //// Hold the current angle of the robot while the trigger is held
    //reactor.onTriggeredSubmit(leftJoystick.getTrigger(), () -> new HoldAngleCommand(drive, IO.navX.getAngle()));
    //reactor.onUntriggeredSubmit(leftJoystick.getTrigger(), () -> Command.cancel(drive)); // FIXME doesn't cancel
    //
    //// Drive straight, strafe 90 degrees, and strafe 45 -- each for 2 seconds
    //reactor.onTriggeredSubmit(leftJoystick.getButton(6), () -> new DriveTimedCommand(drive, 0, 0, 1.5));
    //reactor.onTriggeredSubmit(leftJoystick.getButton(11), () -> new DriveTimedCommand(drive, 0, 90, 1.5));
    //reactor.onTriggeredSubmit(leftJoystick.getButton(10), () -> new DriveTimedCommand(drive, 0, 45, 1.5));
    //
    //// Drive straight, pause for 2s, then strafe 90 degrees
    //reactor.onTriggeredSubmit(leftJoystick.getButton(7), () -> CommandGroup.runSequentially(
    //    new DriveTimedCommand(drive, 0, 0, 0.9),
    //    new DriveTimedCommand(drive, 0, 90, 1.5),
    //    Command.pause(1.5),
    //    new DriveTimedCommand(drive, 0, 270, 1.0),
    //    new DriveTimedCommand(drive, 0, 180, 0.6),
    //    new HoldAngleCommand(drive, 135)
    //));
    //
    //reactor.onTriggeredSubmit(leftJoystick.getButton(2), () -> CommandGroup.runSequentially(
    //    new DriveTimedCommand(drive, 0, 0, 1.5),
    //    new BrakeCommand(drive, IO.navX, 1)
    //));
    //
    //reactor.onTriggeredSubmit(rightJoystick.getButton(3), () -> new HoldAngleCommand(drive, 135));
    //reactor.onTriggeredSubmit(rightJoystick.getButton(4), () -> new HoldAngleCommand(drive, 0));
    //
    //// Cancel ongoing Drive commands. The interrupted commands should hand back operator control
    //reactor.onTriggered(leftJoystick.getButton(3), () -> {
    //  Strongback.submit(Command.cancel(drive));
    //  drive.setOpenLoop();
    //});
    //
    //// Lock the drive motors and hopefully stop the robot
    //reactor.onTriggeredSubmit(leftJoystick.getButton(5), () -> new BrakeCommand(drive, IO.navX, 1));
    //
    //// Drive straight, strafe 90 degrees, and strafe 45 -- each for 2 seconds
    //reactor.onTriggeredSubmit(leftJoystick.getButton(6), () -> new DriveTimedCommand(drive, 0, 0, 1.5));
    //reactor.onTriggeredSubmit(leftJoystick.getButton(11), () -> new DriveTimedCommand(drive, 0, 90, 1.5));
    //reactor.onTriggeredSubmit(leftJoystick.getButton(10), () -> new DriveTimedCommand(drive, 0, 45, 1.5));
    //
    //// Drive straight, pause for 2s, then strafe 90 degrees
    //reactor.onTriggeredSubmit(leftJoystick.getButton(7), () -> CommandGroup.runSequentially(
    //    new DriveTimedCommand(drive, 0, 0, 0.9),
    //    new DriveTimedCommand(drive, 0, 90, 1.5),
    //    Command.pause(1.5),
    //    new DriveTimedCommand(drive, 0, 270, 1.0),
    //    new DriveTimedCommand(drive, 0, 180, 0.6),
    //    new HoldAngleCommand(drive, 135)
    //));
    //
    //// Reset the navX
    //reactor.onTriggered(rightJoystick.getButton(2), () -> IO.navX.getNavX().reset());
    //
    //// Hold angle at 135 or 0 degrees until cancelled or interrupted
    //reactor.onTriggeredSubmit(rightJoystick.getButton(3), () -> new HoldAngleCommand(drive, 135));
    //reactor.onTriggeredSubmit(rightJoystick.getButton(4), () -> new HoldAngleCommand(drive, 0));
    //
    //// Press to toggle the snorfler forward/off
    //reactor.onTriggeredSubmit(leftJoystick.getButton(6), () -> new SnorfleToggleCommand(snorfler));
    //
    //// Press and hold to reverse the snorfler
    //reactor.onTriggeredSubmit(leftJoystick.getButton(7), () -> new SnorfleReverseCommand(snorfler));
    //reactor.onUntriggeredSubmit(leftJoystick.getButton(7), () -> new SnorfleStopReversingCommand(snorfler));
    //reactor.onTriggeredSubmit(leftJoystick.getTrigger(), () -> new HoldAngleCommand(drive, 90));
    //reactor.onUntriggeredSubmit(leftJoystick.getTrigger(), () -> Command.cancel(drive));
    //
    //// Press and hold to climb
    //reactor.onTriggeredSubmit(rightJoystick.getButton(3), () -> climber.climbRope(40,0.5));
    //reactor.onUntriggeredSubmit(rightJoystick.getButton(3), () -> Command.cancel(climber));
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
    //double orientation = IO.navX.getAngle();
    //SmartDashboard.putNumber("Gyro Angle", orientation);

    //Feedback feedback = new Feedback(orientation);
    //drive.onUpdate(feedback);
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
  }
}
