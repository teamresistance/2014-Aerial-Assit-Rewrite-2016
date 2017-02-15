package org.teamresistance.frc.subsystem.snorfler;

import org.strongback.components.Motor;
import org.strongback.components.Stoppable;

/**
 * @author Tarik C. Brown
 */
public final class Snorfler implements Stoppable {
  private static final double SNORFLE_SPEED = 0.5;
  private static final double STOP_SPEED = 0.0;

  private final Motor snorflerMotor;
  private State state = State.STOPPED;

  private enum State {
    STOPPED, SNORFLING, REVERSING
  }

  public Snorfler(Motor snorflerMotor) {
    this.snorflerMotor = snorflerMotor;
  }

  public void toggleSnorfling() {
    if (state == State.REVERSING) {
      snorflerMotor.setSpeed(SNORFLE_SPEED);
      state = State.SNORFLING;
    } else if (state == State.STOPPED) {
      snorflerMotor.setSpeed(SNORFLE_SPEED);
      state = State.REVERSING;
    } else {
      snorflerMotor.setSpeed(STOP_SPEED);
      state = State.STOPPED;
    }
  }

  public void startReversing() {
    if (state == State.STOPPED) {
      snorflerMotor.setSpeed(-SNORFLE_SPEED);
      state = State.REVERSING;
    }
  }

  public void stopReversing() {
    if (state == State.REVERSING) {
      snorflerMotor.setSpeed(STOP_SPEED);
      state = State.STOPPED;
    }
  }

  @Override
  public void stop() {
    snorflerMotor.stop();
  }
}
