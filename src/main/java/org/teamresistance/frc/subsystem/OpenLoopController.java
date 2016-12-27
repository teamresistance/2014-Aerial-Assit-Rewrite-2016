package org.teamresistance.frc.subsystem;

import org.teamresistance.frc.Pose;

public final class OpenLoopController<S> implements Controller<S> {

  @Override
  public S computeSignal(S feedForward, Pose feedback) {
    return feedForward;
  }

  @Override
  public boolean isOnTarget() {
    return false; // never on target
  }
}
