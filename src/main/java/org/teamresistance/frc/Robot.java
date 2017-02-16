package org.teamresistance.frc;

import org.strongback.Strongback;
import edu.wpi.first.wpilibj.IterativeRobot;
import org.strongback.SwitchReactor;
import org.strongback.components.ui.Gamepad;
import org.strongback.hardware.Hardware;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.teamresistance.frc.subsystem.drive.Drive;

/**
 * Main robot class. Override methods from {@link IterativeRobot} to define behavior.
 *
 * @author Shreya Ravi
 * @author Rothanak So
 */
public class Robot extends IterativeRobot {
  //private final FlightStick leftJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(0);
  //private final FlightStick rightJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(2);
  private final Gamepad xboxController = Hardware.HumanInterfaceDevices.xbox360(0);

  private OpticalFlow opFlow = new OpticalFlow();


  private final Drive drive = new Drive(
      IO.robotDrive,
      xboxController.getLeftY(),
      xboxController.getLeftX(),
      xboxController.getRightTrigger()
  );

  @Override
  public void robotInit() {
    opFlow.init();

    Strongback.configure().recordNoEvents().recordNoData();
    final SwitchReactor reactor = Strongback.switchReactor();

//    // Hold the current angle of the robot while the trigger is held
//    reactor.onTriggeredSubmit(leftJoystick.getTrigger(), () -> new HoldAngleCommand(drive, IO.gyro.getAngle()));
//    reactor.onUntriggeredSubmit(leftJoystick.getTrigger(), () -> Command.cancel(drive)); // FIXME doesn't cancel
//
//    reactor.onTriggeredSubmit(rightJoystick.getButton(3), () -> new HoldAngleCommand(drive, 135));
//    reactor.onTriggeredSubmit(rightJoystick.getButton(4), () -> new HoldAngleCommand(drive, 0));
//
//    // Cancel ongoing Drive commands. The interrupted commands should hand back operator control
//    reactor.onTriggered(leftJoystick.getButton(3), () -> {
//      Strongback.submit(Command.cancel(drive));
//      drive.setOpenLoop();
//    });
//
    // Reset the gyro
    reactor.onTriggered(xboxController.getButton(7), () -> IO.gyro.getNavX().reset());
//
//    // Hold angle at 135 or 0 degrees until cancelled or interrupted
//    reactor.onTriggeredSubmit(rightJoystick.getButton(3), () -> new HoldAngleCommand(drive, 135));
//    reactor.onTriggeredSubmit(rightJoystick.getButton(4), () -> new HoldAngleCommand(drive, 0));

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

    //XboxControl.update();

    opFlow.update();

    Feedback feedback = new Feedback(orientation);
    drive.onUpdate(feedback);
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
  }
}
