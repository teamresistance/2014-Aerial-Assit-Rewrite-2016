package org.teamresistance.frc;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Command;
import org.strongback.components.ui.FlightStick;
import org.strongback.hardware.Hardware;
import org.teamresistance.frc.command.GrabGear;
import org.teamresistance.frc.command.HoldAngleCommand;
import org.teamresistance.frc.command.LowerArm;
import org.teamresistance.frc.command.ReleaseGear;
import org.teamresistance.frc.subsystem.GearGrabber.Grabber;
import org.teamresistance.frc.subsystem.drive.Drive;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * Main robot class. Override methods from {@link IterativeRobot} to define behavior.
 *
 * @author Shreya Ravi
 * @author Rothanak So
 * @author Sabrina
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

  private final Grabber grabber = new Grabber(
      IO.grabSolenoid,
      IO.armSolenoid
  );


  @Override
  public void robotInit() {
    Strongback.configure().recordNoEvents().recordNoData().initialize();
    final SwitchReactor reactor = Strongback.switchReactor();

    // Hold the current angle of the robot while the trigger is held
    reactor.onTriggeredSubmit(leftJoystick.getTrigger(), () -> new HoldAngleCommand(drive, 90));
    reactor.onUntriggeredSubmit(leftJoystick.getTrigger(), () -> Command.cancel(drive));

    //Solenoid test buttons
    reactor.onTriggeredSubmit(leftJoystick.getButton(2), () -> new GrabGear(grabber));
    reactor.onTriggeredSubmit(leftJoystick.getButton(3), () -> new ReleaseGear(grabber));
    reactor.onTriggeredSubmit(leftJoystick.getButton(4), () -> new LowerArm(grabber));
    reactor.onTriggeredSubmit(leftJoystick.getButton(5), () -> new LowerArm(grabber));

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
    Pose pose = new Pose(IO.gyro.getAngle());
    drive.onUpdate(pose);
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
  }
}
