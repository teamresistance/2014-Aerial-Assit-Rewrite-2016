package org.teamresistance.frc;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Command;
import org.strongback.command.CommandGroup;
import org.strongback.components.ui.FlightStick;
import org.strongback.hardware.Hardware;
import org.teamresistance.frc.command.drive.DriveToX;
import org.teamresistance.frc.command.drive.DriveToY;
import org.teamresistance.frc.command.drive.DriveToYX;
import org.teamresistance.frc.command.grabber.*;
import org.teamresistance.frc.subsystem.drive.Drive;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.teamresistance.frc.subsystem.grabber.Grabber;

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

  public static boolean isInterrupted = false;
  private final Grabber grabber = new Grabber(
      IO.gripSolenoid,
      IO.extendSolenoid,
      IO.rotateSolenoid,
      IO.rotateGearMotor,
      IO.gearPresentBannerSensor,
      IO.gearAlignBannerSensor);

  @Override
  public void robotInit() {
    Strongback.configure().recordNoEvents().recordNoData();
//    IO.xDistPing.setAutomaticMode(true);
//    IO.yDistPing.setAutomaticMode(true);
    final SwitchReactor reactor = Strongback.switchReactor();
      reactor.onTriggeredSubmit(rightJoystick.getButton(6), () -> new GearExtend(1.0, IO.extendSolenoid));
      reactor.onTriggeredSubmit(rightJoystick.getButton(7), () -> new GearRetract(IO.extendSolenoid));

      reactor.onTriggeredSubmit(rightJoystick.getButton(10), () -> new RotateUp(1.0, IO.extendSolenoid, IO.rotateSolenoid));
      reactor.onTriggeredSubmit(rightJoystick.getButton(11), () -> new RotateDown(1.0, IO.extendSolenoid, IO.rotateSolenoid));

      reactor.onTriggeredSubmit(rightJoystick.getTrigger(), () -> grabber.lookForGear());
      reactor.onTriggeredSubmit(rightJoystick.getButton(3), () -> grabber.pickupGear());
//      reactor.onUntriggeredSubmit(rightJoystick.getTrigger(), () -> new InterruptFindGear());
      reactor.onUntriggeredSubmit(rightJoystick.getTrigger(), () -> Command.cancel(IO.gearPresentBannerSensor));
      reactor.onTriggeredSubmit(rightJoystick.getButton(4), () -> grabber.deliverGear());

//    //Hold the current angle of the robot while the trigger is held
//    reactor.onTriggeredSubmit(leftJoystick.getTrigger(), () -> new HoldAngleCommand(drive, 90));
//    reactor.onUntriggeredSubmit(leftJoystick.getTrigger(), () -> Command.cancel(drive));

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
//    Pose pose = new Pose(IO.gyro.getAngle(),
//        IO.xDistPing.getRangeInches(),
//        IO.xDistPing.getDistanceInInches(),
//        IO.yDistPing.getRangeInches());
//        IO.yDistPing.getDistanceInInches());
//    SmartDashboard.putNumber("Y Distance (Ping): ", pose.yDist);
//    SmartDashboard.putNumber("X Distance (Ping): ", pose.xDist);
//    SmartDashboard.putNumber("Gyro Angle: ", pose.currentAngle);
//    drive.onUpdate(pose);
    SmartDashboard.putNumber("Test Val", 3);
    IO.compressorRelay.set(IO.compressor.enabled() ? Relay.Value.kOn : Relay.Value.kOff);
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
  }
}
