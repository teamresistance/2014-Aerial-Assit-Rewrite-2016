package org.teamresistance.frc.subsystem;

import org.teamresistance.frc.Pose;

public interface Controller<S> {

  S computeSignal(S feedForward, Pose feedback);

  boolean isOnTarget();
}
