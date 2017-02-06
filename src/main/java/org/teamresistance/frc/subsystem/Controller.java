package org.teamresistance.frc.subsystem;

import org.teamresistance.frc.Feedback;

/**
 * TODO: Documentation (high priority)
 * @param <T> The type of data outputted by the {@link Controller} (the "signal").
 * @author Rothanak So
 */
@FunctionalInterface
public interface Controller<T> {

  T computeSignal(T feedForward, Feedback feedback);

  default boolean isOnTarget() {
    return false;
  }
}
