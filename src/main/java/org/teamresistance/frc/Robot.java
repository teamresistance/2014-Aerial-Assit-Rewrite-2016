package org.teamresistance.frc;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Command;
import org.strongback.components.DistanceSensor;
import org.strongback.components.ui.FlightStick;
import org.strongback.hardware.Hardware;
import org.teamresistance.frc.command.HoldAngleCommand;
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
  private DistanceSensor rightping = Hardware.DistanceSensors.digitalUltrasonic(0, 1);
  private DistanceSensor rearping = Hardware.DistanceSensors.digitalUltrasonic(2,3);
  private double rightpingDistance = rightping.getDistanceInInches();
  private double rearDistance = rearping.getDistanceInInches();

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
    Pose pose = new Pose(IO.gyro.getAngle(),rightping.getDistanceInInches(),rearping.getDistanceInInches());
    SmartDashboard.putNumber("Ping Y",rightpingDistance);
    SmartDashboard.putNumber("Ping X",rearDistance);
    drive.onUpdate(pose);
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
  }
}
