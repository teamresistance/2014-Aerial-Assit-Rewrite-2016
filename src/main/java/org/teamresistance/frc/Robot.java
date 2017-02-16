package org.teamresistance.frc;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import edu.wpi.first.wpilibj.Sendable;
import org.strongback.hardware.Hardware;
import org.strongback.components.ui.Gamepad;
import edu.wpi.first.wpilibj.IterativeRobot;
import org.teamresistance.frc.subsystem.drive.Drive;
import org.strongback.components.ui.ContinuousRange;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Main robot class. Override methods from {@link IterativeRobot} to define behavior.
 *
 * @author Shreya Ravi
 * @author Rothanak So
 */
public class Robot extends IterativeRobot {
  private final Gamepad xboxDriver = Hardware.HumanInterfaceDevices.xbox360(0); //The 1st Controller goes in port 0
  private final Gamepad xboxCoDriver= Hardware.HumanInterfaceDevices.xbox360(1);    //The 1st Controller goes in port 1

  private final Drive drive = new Drive(
      IO.robotDrive,
      xboxDriver.getLeftY(),
      xboxDriver.getLeftX(),
      xboxDriver.getRightX()
  );

  //Driver
  ContinuousRange leftX = xboxDriver.getLeftX();
  ContinuousRange leftY = xboxDriver.getLeftY();

  ContinuousRange rightX = xboxDriver.getRightX();
  ContinuousRange rightY = xboxDriver.getRightY();

  boolean leftBumper = false;
  boolean leftStick  = false;

  boolean rightBumper = false;
  boolean rightStick  = false;

  boolean aPressed  = false;
  boolean bPressed  = false;
  boolean xPressed  = false;
  boolean yPressed  = false;

  //------------------------------------------------//
  //Co Driver
  ContinuousRange leftXCo = xboxCoDriver.getLeftX();
  ContinuousRange leftYCo = xboxCoDriver.getLeftY();

  ContinuousRange rightXCo = xboxCoDriver.getRightX();
  ContinuousRange rightYCo = xboxCoDriver.getRightY();

  boolean startPressedCo  = false;
  boolean selectPressedCo = false;

  boolean leftBumperCo = false;
  boolean leftStickCo  = false;

  boolean rightBumperCo = false;
  boolean rightStickCo  = false;

  boolean aPressedCo  = false;
  boolean bPressedCo  = false;
  boolean xPressedCo  = false;
  boolean yPressedCo  = false;

  boolean startPressed  = false;
  boolean selectPressed = false;

//  DirectionalAxis dPad_1 = xboxDriver.getDPad(1);

  public Robot() {

  }


  @Override
  public void robotInit() {

    Strongback.configure().recordNoEvents().recordNoData();
    final SwitchReactor reactor = Strongback.switchReactor();

    // Reset the gyro
    reactor.onTriggered(xboxCoDriver.getA(), () -> IO.gyro.getNavX().reset());

    //Test Xbox Buttons
    reactor.onTriggered( xboxDriver.getLeftBumper() , () -> leftBumper = true );
    reactor.onTriggered( xboxDriver.getLeftStick() , () -> leftStick = true );

    reactor.onTriggered( xboxDriver.getRightBumper() , () -> rightBumper = true );
    reactor.onTriggered( xboxDriver.getRightStick() , () -> rightStick = true );

    reactor.onTriggered( xboxDriver.getA() , () -> aPressed = true );
    reactor.onTriggered( xboxDriver.getB() , () -> bPressed = true );
    reactor.onTriggered( xboxDriver.getX() , () -> xPressed = true );
    reactor.onTriggered( xboxDriver.getY() , () -> yPressed = true );

    reactor.onTriggered( xboxDriver.getStart() , () -> startPressed = true );
    reactor.onTriggered( xboxDriver.getSelect() , () -> selectPressed = true );

    //Test Xbox Buttons for Covdriver
    reactor.onTriggered( xboxCoDriver.getLeftBumper() , () -> leftBumper = true );
    reactor.onTriggered( xboxCoDriver.getLeftStick() , () -> leftStick = true );

    reactor.onTriggered( xboxCoDriver.getRightBumper() , () -> rightBumper = true );
    reactor.onTriggered( xboxCoDriver.getRightStick() , () -> rightStick = true );

    reactor.onTriggered( xboxCoDriver.getA() , () -> aPressed = true );
    reactor.onTriggered( xboxCoDriver.getB() , () -> bPressed = true );
    reactor.onTriggered( xboxCoDriver.getX() , () -> xPressed = true );
    reactor.onTriggered( xboxCoDriver.getY() , () -> yPressed = true );

    reactor.onTriggered( xboxCoDriver.getStart() , () -> startPressed = true );
    reactor.onTriggered( xboxCoDriver.getSelect() , () -> selectPressed = true );

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

    SmartDashboard.putData("Left X (Co)", (Sendable) leftXCo );
    SmartDashboard.putData("Left Y (Co)", (Sendable) leftYCo );
    SmartDashboard.putData("Right X (Co) ", (Sendable) rightXCo );
    SmartDashboard.putData("Right Y (Co) ", (Sendable) rightYCo );
    SmartDashboard.putBoolean("Left Stick (Co) ", leftStickCo );
    SmartDashboard.putBoolean("Right Stick (Co) ", rightStickCo );
    SmartDashboard.putBoolean("Left Bumper (Co) ", leftBumperCo );
    SmartDashboard.putBoolean("Right Bumper (Co) ", rightBumperCo );
//    SmartDashboard.putNumber("Left Trigger (Co) ", );
//    SmartDashboard.putNumber("Right Trigger (Co) ", );
    SmartDashboard.putBoolean("A (Co) ", aPressedCo );
    SmartDashboard.putBoolean("B (Co) ", bPressedCo );
    SmartDashboard.putBoolean("X (Co) ", xPressedCo );
    SmartDashboard.putBoolean("Y (Co) ", yPressedCo );
    SmartDashboard.putBoolean("Start (Co) ", startPressedCo );
    SmartDashboard.putBoolean("Start (Co) ", selectPressedCo );
//    SmartDashboard.putNumber("DPad 1 (Co) ", );

    Feedback feedback = new Feedback(orientation);
    drive.onUpdate(feedback);
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
  }
}
