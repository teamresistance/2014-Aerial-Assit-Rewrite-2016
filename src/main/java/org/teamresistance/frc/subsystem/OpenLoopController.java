package org.teamresistance.frc.subsystem;

import org.teamresistance.frc.Pose;

/**
 * TODO: Documentation (high priority)
 * @author Rothanak So
 */
public final class OpenLoopController<T> implements Controller<T> {

  @Override
  public T computeSignal(T feedForward, Pose feedback) {
    return feedForward;
  }

  @Override
  public boolean isOnTarget() {
    return false; // never on target
  }
}
