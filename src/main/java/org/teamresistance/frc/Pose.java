package org.teamresistance.frc;

import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class Pose {
  public final Supplier<Optional<Double>> goalOffset;

  Pose(Supplier<Optional<Double>> goalOffset) {
    this.goalOffset = goalOffset;
  }
}
