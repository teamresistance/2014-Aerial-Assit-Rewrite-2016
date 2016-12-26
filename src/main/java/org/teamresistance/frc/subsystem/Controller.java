package org.teamresistance.frc.subsystem;

import org.teamresistance.frc.Pose;

public interface Controller<S> {

  S computeSignal(S oldSignal, Pose pose);

  boolean isOnTarget();
}
