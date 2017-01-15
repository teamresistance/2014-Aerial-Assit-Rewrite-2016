package org.teamresistance.frc.util;

import org.strongback.control.SoftwarePIDController;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Serves as a bridge between a Consumer and a Supplier. Use it when you need to manually supply
 * values to a {@link Supplier} or manually read the value from a {@link Consumer}.
 * <p>
 * Author's note: This is basically only used to turn those crazy asynchronous PIDs into synchronous
 * PIDs, since they only take callbacks and don't actually have a method that returns the computed
 * value. See: {@link SoftwarePIDController}, which then is coerced into {@link SynchronousPID}.
 *
 * @param <T> the type of value being consumed and supplied, e.g. Double
 * @author Rothanak So
 */
final class Relay<T> implements Consumer<T>, Supplier<T> {
  private T value;

  @Override
  public void accept(T value) {
    this.value = value;
  }

  @Override
  public T get() {
    return value;
  }
}
