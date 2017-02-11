package org.teamresistance.frc;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.components.ui.FlightStick;
import org.strongback.hardware.Hardware;
import org.teamresistance.frc.command.grabber.GearExtend;
import org.teamresistance.frc.command.grabber.GearRetract;
import org.teamresistance.frc.command.grabber.RotateDown;
import org.teamresistance.frc.command.grabber.RotateUp;
import org.teamresistance.frc.subsystem.grabber.Grabber;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
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
//  private final FlightStick coJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(2);
//
//  private final Drive drive = new Drive(
//      IO.robotDrive,
//      leftJoystick.getRoll(),
//      leftJoystick.getPitch(),
//      rightJoystick.getRoll()
//  );

  @Override
  public void robotInit() {
    Grabber grabber = new Grabber(null,null,null,null,null,null);
    Strongback.configure().recordNoEvents().recordNoData();
//    IO.xDistPing.setAutomaticMode(true);
//    IO.yDistPing.setAutomaticMode(true);
    final SwitchReactor reactor = Strongback.switchReactor();
      reactor.onTriggeredSubmit(rightJoystick.getButton(6), () -> new GearExtend(1.0, IO.flipper));
      reactor.onTriggeredSubmit(rightJoystick.getButton(7), () -> new GearRetract(IO.flipper));

      reactor.onTriggeredSubmit(rightJoystick.getButton(10), () -> new RotateUp(1.0, IO.flipper, IO.antler));
      reactor.onTriggeredSubmit(rightJoystick.getButton(11), () -> new RotateDown(1.0, IO.flipper, IO.antler));

//      reactor.onTriggeredSubmit(leftJoystick.getButton(6), () -> CommandGroup.runSequentially(
//          grabber.lookForGear(),
//          grabber.pickupGear(),
//          grabber.resetFromPicking()));
//      reactor.onUntriggeredSubmit(leftJoystick.getButton(6), () -> grabber.resetFromPicking());
//
//
//    //Hold the current angle of the robot while the trigger is held
//    reactor.onTriggeredSubmit(leftJoystick.getTrigger(), () -> new HoldAngleCommand(drive, IO.gyro.getAngle()));
//    reactor.onUntriggeredSubmit(leftJoystick.getTrigger(), () -> Command.cancel(drive)); // FIXME doesn't cancel

    // Cancel ongoing Drive commands. The interrupted commands should hand back operator control
    //reactor.onTriggered(leftJoystick.getButton(3), () -> {
    //  Strongback.submit(Command.cancel(drive));
    //  drive.setOpenLoop();
    //});

    // Lock the drive motors and hopefully stop the robot
    //reactor.onTriggeredSubmit(leftJoystick.getButton(5), () -> new BrakeCommand(drive, IO.gyro, 1));

    // Drive straight, strafe 90 degrees, and strafe 45 -- each for 2 seconds
    //reactor.onTriggeredSubmit(leftJoystick.getButton(6), () -> new DriveTimedCommand(drive, 0, 0, 1.5));
    //reactor.onTriggeredSubmit(leftJoystick.getButton(11), () -> new DriveTimedCommand(drive, 0, 90, 1.5));
    //reactor.onTriggeredSubmit(leftJoystick.getButton(10), () -> new DriveTimedCommand(drive, 0, 45, 1.5));

    // Drive straight, pause for 2s, then strafe 90 degrees
    //reactor.onTriggeredSubmit(leftJoystick.getButton(7), () -> CommandGroup.runSequentially(
    //    new DriveTimedCommand(drive, 0, 0, 0.9),
    //    new DriveTimedCommand(drive, 0, 90, 1.5),
    //    Command.pause(1.5),
    //    new DriveTimedCommand(drive, 0, 270, 1.0),
    //    new DriveTimedCommand(drive, 0, 180, 0.6),
    //    new HoldAngleCommand(drive, 135)
    //));

    // Reset the gyro
    //reactor.onTriggered(rightJoystick.getButton(2), () -> IO.gyro.getNavX().reset());

    // Hold angle at 135 or 0 degrees until cancelled or interrupted
    //reactor.onTriggeredSubmit(rightJoystick.getButton(3), () -> new HoldAngleCommand(drive, 135));
    //reactor.onTriggeredSubmit(rightJoystick.getButton(4), () -> new HoldAngleCommand(drive, 0));

//    reactor.onTriggeredSubmit(leftJoystick.getButton(6), () -> new DriveToYX(drive,60,66,60,0));
//    reactor.onTriggeredSubmit(leftJoystick.getButton(7), () -> new DriveToY(drive, 10, 66));
//    reactor.onTriggeredSubmit(leftJoystick.getButton(8), () -> new DriveToX(drive, 10, 0));
  }

  @Override
  public void autonomousInit() {
    Strongback.start();
    // make sure these measurements are right
  }

  @Override
  public void teleopInit() {
    Strongback.start();
  }

  @Override
  public void teleopPeriodic() {
    // Post our orientation on the SD for debugging purposes
    //double orientation = IO.gyro.getAngle();
    //SmartDashboard.putNumber("Gyro Angle", orientation);

    //Feedback feedback = new Feedback(orientation,
//        IO.xDistPing.getRangeInches(),
//        IO.xDistPing.getDistanceInInches(),
//        IO.yDistPing.getRangeInches());
//        IO.yDistPing.getDistanceInInches());
//    SmartDashboard.putNumber("Y Distance (Ping): ", feedback.yDist);
//    SmartDashboard.putNumber("X Distance (Ping): ", feedback.xDist);
//    SmartDashboard.putNumber("Gyro Angle: ", feedback.currentAngle);
//    drive.onUpdate(feedback);
    SmartDashboard.putNumber("Test Val", 3);
    IO.compressorRelay.set(IO.compressor.enabled() ? Relay.Value.kOn : Relay.Value.kOff);
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
  }
}
