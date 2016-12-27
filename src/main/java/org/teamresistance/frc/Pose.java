package org.teamresistance.frc;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class Pose {
  public final Optional<Double> goalOffset;

  Pose(Optional<Double> goalOffset) {
    this.goalOffset = goalOffset;
  }
}
