package org.teamresistance.frc.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Serves as a bridge between a Consumer and a Supplier. Use it when you need to manually supply
 * values to a {@link Supplier} or manually read the value from a {@link Consumer}.
 *
 * @param <T> the type of consumer and supplier, e.g. Double
 * @author Rothanak So
 */
public final class Relay<T> implements Consumer<T>, Supplier<T> {
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
