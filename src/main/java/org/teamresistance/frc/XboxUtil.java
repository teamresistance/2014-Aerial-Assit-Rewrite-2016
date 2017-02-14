package org.teamresistance.frc;

import edu.wpi.first.wpilibj.Joystick;
/**
 * Created by Tarik Brown on 11/25/2016.
 */
public class XboxUtil {
  public static class XboxController extends Joystick {

    public XboxController(final int port) {
      super(port);
    }

    public double getLeftX() {
      return getRawAxis(0);
    }

    public double getLeftY() {
      return getRawAxis(1);
    }

    public double getRightX() {
      return getRawAxis(4);
    }

    public double getRightY() {
      return getRawAxis(5);
    }

    public boolean getButtonA() {
      return getRawButton(0);
    }

    public boolean getButtonB() {
      return getRawButton(1);
    }

    public boolean getButtonX() {
      return getRawButton(2);
    }

    public boolean getButtonY() {
      return getRawButton(3);
    }

    public boolean getLeftBumper() {
      return getRawButton(4);
    }

    public boolean getRightBumper() {
      return getRawButton(5);
    }
  }
}