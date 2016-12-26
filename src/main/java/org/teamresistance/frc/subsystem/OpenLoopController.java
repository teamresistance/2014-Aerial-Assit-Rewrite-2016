package org.teamresistance.frc.subsystem;

import org.teamresistance.frc.Pose;

public final class OpenLoopController<S> implements Controller<S> {

  @Override
  public S computeSignal(S oldSignal, Pose pose) {
    return oldSignal;
  }

  @Override
  public boolean isOnTarget() {
    return false; // never on target
  }
}
