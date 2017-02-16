package org.teamresistance.frc;

import com.sun.org.apache.xpath.internal.functions.FuncFalse;
import edu.wpi.first.wpilibj.Sendable;
import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.DirectionalAxis;
import org.strongback.hardware.Hardware;
import org.strongback.components.ui.Gamepad;
import edu.wpi.first.wpilibj.IterativeRobot;
import org.teamresistance.frc.subsystem.drive.Drive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Main robot class. Override methods from {@link IterativeRobot} to define behavior.
 *
 * @author Shreya Ravi
 * @author Rothanak So
 */
public class Robot extends IterativeRobot {
  private final Gamepad xboxController = Hardware.HumanInterfaceDevices.xbox360(0); //The 0 indicates that the Controller is in the 1st port

  private final Drive drive = new Drive(
      IO.robotDrive,
      xboxController.getLeftY(),
      xboxController.getLeftX(),
      xboxController.getRightX()
  );


  ContinuousRange leftX = xboxController.getLeftX();
  ContinuousRange leftY = xboxController.getLeftY();

  ContinuousRange rightX = xboxController.getRightX();
  ContinuousRange rightY = xboxController.getRightY();

  boolean leftBumper = false;
  boolean leftStick  = false;

  boolean rightBumper = false;
  boolean rightStick  = false;

  boolean aPressed  = false;
  boolean bPressed  = false;
  boolean xPressed  = false;
  boolean yPressed  = false;

  boolean startPressed  = false;
  boolean selectPressed = false;

//  DirectionalAxis dPad_1 = xboxController.getDPad(1);

  public Robot() {

  }


  @Override
  public void robotInit() {

    Strongback.configure().recordNoEvents().recordNoData();
    final SwitchReactor reactor = Strongback.switchReactor();

    // Reset the gyro
    reactor.onTriggered(xboxController.getA(), () -> IO.gyro.getNavX().reset());

    reactor.onTriggered( xboxController.getLeftBumper() , () -> leftBumper = true );
    reactor.onTriggered( xboxController.getLeftStick() , () -> leftStick = true );

    reactor.onTriggered( xboxController.getRightBumper() , () -> rightBumper = true );
    reactor.onTriggered( xboxController.getRightStick() , () -> rightStick = true );

    reactor.onTriggered( xboxController.getA() , () -> aPressed = true );
    reactor.onTriggered( xboxController.getB() , () -> bPressed = true );
    reactor.onTriggered( xboxController.getX() , () -> xPressed = true );
    reactor.onTriggered( xboxController.getY() , () -> yPressed = true );

    reactor.onTriggered( xboxController.getStart() , () -> startPressed = true );
    reactor.onTriggered( xboxController.getSelect() , () -> selectPressed = true );

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

    SmartDashboard.putData("Left X", (Sendable) leftX );
    SmartDashboard.putData("Left Y", (Sendable) leftY );
    SmartDashboard.putData("Right X", (Sendable) rightX );
    SmartDashboard.putData("Right Y", (Sendable) rightY );
    SmartDashboard.putBoolean("Left Stick", leftStick );
    SmartDashboard.putBoolean("Right Stick", rightStick);
    SmartDashboard.putBoolean("Left Bumper", leftBumper );
    SmartDashboard.putBoolean("Right Bumper", rightBumper);
//    SmartDashboard.putNumber("Left Trigger", );
//    SmartDashboard.putNumber("Right Trigger", );
    SmartDashboard.putBoolean("A", aPressed );
    SmartDashboard.putBoolean("B", bPressed );
    SmartDashboard.putBoolean("X", xPressed );
    SmartDashboard.putBoolean("Y", yPressed );
    SmartDashboard.putBoolean("Start", startPressed );
    SmartDashboard.putBoolean("Start", selectPressed );
//    SmartDashboard.putNumber("DPad 1", );

    Feedback feedback = new Feedback(orientation);
    drive.onUpdate(feedback);
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
  }
}
