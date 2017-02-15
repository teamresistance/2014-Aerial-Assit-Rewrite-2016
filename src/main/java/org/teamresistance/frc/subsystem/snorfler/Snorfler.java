package org.teamresistance.frc.subsystem.snorfler;

import org.strongback.components.Motor;

/**
 * @author Tarik C. Brown
 */
public final class Snorfler {
  private static final double SNORFLE_SPEED = 0.5;
  private static final double STOP_SPEED = 0.0;

  private final Motor snorflerMotor;
  private SnorfleState state = SnorfleState.STOPPED;

  private enum SnorfleState {
    STOPPED, SNORFLING
  }

  public Snorfler(Motor snorflerMotor) {
    this.snorflerMotor = snorflerMotor;
  }

  public void startSnorfling() {
    if (state == SnorfleState.STOPPED) {
      snorflerMotor.setSpeed(SNORFLE_SPEED);
      state = SnorfleState.SNORFLING;
    } else if (state == SnorfleState.SNORFLING) {
      snorflerMotor.setSpeed(STOP_SPEED);
      state = SnorfleState.STOPPED;
    }
  }

  public void reverseSnorfling() {
    if (state == SnorfleState.STOPPED) {
      snorflerMotor.setSpeed(-SNORFLE_SPEED);
      state = SnorfleState.SNORFLING;
    }
  }
}
