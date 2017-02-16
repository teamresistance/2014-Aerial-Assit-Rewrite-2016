package org.teamresistance.frc;

import org.strongback.Strongback;
import org.strongback.components.ui.FlightStick;
import org.strongback.drive.MecanumDrive;
import org.strongback.hardware.Hardware;
import org.teamresistance.frc.subsystem.drive.Drive;
import org.teamresistance.frc.subsystem.snorfler.Snorfler;
import org.teamresistance.frc.util.testing.DriveTesting;
import org.teamresistance.frc.util.testing.SnorflerTesting;

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

  private final Drive drive = new Drive(
      new MecanumDrive(IO.lfMotor, IO.lRMotor, IO.rfMotor, IO.rrMotor, IO.navX),
      leftJoystick.getRoll(), leftJoystick.getPitch(), rightJoystick.getRoll());

  private final Snorfler snorfler = new Snorfler(IO.snorflerMotor);

  @Override
  public void robotInit() {
    Strongback.configure().recordNoEvents().recordNoData();
    DriveTesting driveTesting = new DriveTesting(drive, IO.navX, leftJoystick, rightJoystick);
    SnorflerTesting snorflerTesting = new SnorflerTesting(snorfler, leftJoystick, rightJoystick);

    driveTesting.enableAngleHold();
    driveTesting.enableAngleHoldTests();
    driveTesting.enableCancelling();
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

    //Feedback feedback = new Feedback(orientation);
    //drive.onUpdate(feedback);
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
  }
}
