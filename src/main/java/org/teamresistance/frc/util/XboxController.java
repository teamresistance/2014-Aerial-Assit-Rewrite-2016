package org.teamresistance.frc.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Created by Joseph Delcastillo III on 2/13/17.
 */
public class XboxController {

  Joystick XboxController = new Joystick(1); // Where "1" is the index of the joystick (you can set this in the Driver Station software).

  public void  init() {

  }

  public void update() {

    double xAxisLeft = XboxController.getRawAxis(0);      // Where "0" is the index of the X axis on the left stick
    double yAxisLeft = XboxController.getRawAxis(1);      // Where "1" is the index of the Y axis on the left stick
    double LeftTrigger = XboxController.getRawAxis(2);    // Where "2" is the index of the left trigger
    double RightTrigger = XboxController.getRawAxis(3);   // Where "3" is the index of the right trigger
    double xAxisRight = XboxController.getRawAxis(4);     // Where "4" is the index of the X axis on the right stick
    double yAxisRight = XboxController.getRawAxis(5);     // Where "5" is the index of the Y axis on the right stick

    boolean aPressed = XboxController.getRawButton(0);            // Where "0" is the index of the button A
    boolean bPressed = XboxController.getRawButton(1);            // Where "1" is the index of the button B
    boolean xPressed = XboxController.getRawButton(2);            // Where "2" is the index of the button X
    boolean yPressed = XboxController.getRawButton(3);            // Where "3" is the index of the button Y
    boolean leftBumperPressed = XboxController.getRawButton(4);   // Where "4" is the index of the button Left Bumper
    boolean rightBumperPressed = XboxController.getRawButton(5);  // Where "5" is the index of the button Right Bumper
    boolean backPressed = XboxController.getRawButton(6);         // Where "6" is the index of the button Back
    boolean startPressed = XboxController.getRawButton(7);        // Where "7" is the index of the button Start
    boolean leftStickPressed = XboxController.getRawButton(8);    // Where "8" is the index of the button Left Stick Button
    boolean rightStickPressed = XboxController.getRawButton(9);   // Where "9" is the index of the button Right Stick Button

    SmartDashboard.putNumber("X Axis Left Controller", xAxisLeft);
    SmartDashboard.putNumber("Y Axis Left Controller", yAxisLeft);
    SmartDashboard.putNumber("Left Trigger", LeftTrigger);
    SmartDashboard.putNumber("Right Trigger", RightTrigger);
    SmartDashboard.putNumber("X Axis Right Controller", xAxisRight);
    SmartDashboard.putNumber("Y Axis Right Controller", yAxisRight);

    SmartDashboard.putBoolean("A", aPressed );
    SmartDashboard.putBoolean("B", bPressed );
    SmartDashboard.putBoolean("X", xPressed );
    SmartDashboard.putBoolean("Y", yPressed );
    SmartDashboard.putBoolean("Left Bumper", leftBumperPressed );
    SmartDashboard.putBoolean("Right Bumper ", rightBumperPressed );
    SmartDashboard.putBoolean("Back", backPressed );
    SmartDashboard.putBoolean("Start", startPressed );
    SmartDashboard.putBoolean("Left Stick", leftStickPressed );
    SmartDashboard.putBoolean("Right Stick", rightStickPressed );

  }


}
