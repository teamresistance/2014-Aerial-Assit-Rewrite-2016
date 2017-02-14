package org.teamresistance.frc.subsystem.snorfler;

import org.strongback.components.Motor;
import org.strongback.hardware.Hardware;

import static org.strongback.components.Motor.Direction.STOPPED;

/**
 * @author Tarik C. Brown
 */
public class Snorfler {

  public enum SnorfleState {
    STOPPED,
    SNORFLING,
    REVERSED,
  }

  private SnorfleState state = SnorfleState.STOPPED;

  public static final Motor snorflerMotor = Hardware.Motors.victor(5);
  public static final double snorfSpeed = .5;
  public static final double stopSnorf = 0.0;

  public void startSnorfling() {
    if (state == SnorfleState.STOPPED ) {
      snorflerMotor.setSpeed(snorfSpeed);
      state = SnorfleState.SNORFLING;
    }
      else if (state == SnorfleState.SNORFLING) {
        snorflerMotor.setSpeed(stopSnorf);
        state = SnorfleState.STOPPED;
      }
    }

  public void reverseSnorfling() {
    if (state == SnorfleState.STOPPED) {
      snorflerMotor.setSpeed(-snorfSpeed);
      state = SnorfleState.SNORFLING;
    }
  }

}
