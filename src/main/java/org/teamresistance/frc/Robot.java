package org.teamresistance.frc;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.hardware.Hardware;
import edu.wpi.first.wpilibj.IterativeRobot;
import org.strongback.components.ui.FlightStick;
import org.teamresistance.frc.util.XboxController;
import org.teamresistance.frc.subsystem.drive.Drive;
import org.teamresistance.frc.command.HoldAngleCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * Main robot class. Override methods from {@link IterativeRobot} to define behavior.
 *
 * @author Shreya Ravi
 * @author Rothanak So
 * @author Joseph Delcastillo III
 */
public class Robot extends IterativeRobot {
  private final FlightStick leftJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(0);
  private final FlightStick rightJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(1);
  private final FlightStick coJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(2);

  private XboxController XboxControl = new XboxController();

  private OpticalFlow opFlow = new OpticalFlow();


  private final Drive drive = new Drive(
      IO.robotDrive,
      leftJoystick.getRoll(),
      leftJoystick.getPitch(),
      rightJoystick.getRoll()
  );

  @Override
  public void robotInit() {
    opFlow.init();

    Strongback.configure().recordNoEvents().recordNoData();
    final SwitchReactor reactor = Strongback.switchReactor();

    // Reset the gyro
    reactor.onTriggered(rightJoystick.getButton(2), () -> IO.gyro.getNavX().reset());

    // Hold angle at 135 or 0 degrees until cancelled or interrupted
    reactor.onTriggeredSubmit(rightJoystick.getButton(3), () -> new HoldAngleCommand(drive, 135));
    reactor.onTriggeredSubmit(rightJoystick.getButton(4), () -> new HoldAngleCommand(drive, 0));

    //reactor.onTriggeredSubmit(rightJoystick.getButton(11), OpticalFlow::init);

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

    XboxControl.update();

    opFlow.update();

    Feedback feedback = new Feedback(orientation);
    drive.onUpdate(feedback);
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
  }
}
