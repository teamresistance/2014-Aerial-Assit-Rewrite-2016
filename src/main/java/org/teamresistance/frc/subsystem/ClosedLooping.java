package org.teamresistance.frc.subsystem;

import org.teamresistance.frc.Pose;

import java.util.function.Supplier;

/**
 * TODO: Documentation (high priority)
 * @param <T> The type of data outputted by the {@link Controller} (the "signal").
 * @author Rothanak So
 */
public abstract class ClosedLooping<T> implements Looping {
  private final Supplier<T> feedForward;
  private Controller<T> controller = new OpenLoopController<>();

  public ClosedLooping(Supplier<T> feedForward) {
    this.feedForward = feedForward;
  }

  public final void setOpenLoop() {
    setController(new OpenLoopController<>());
  }

  public final void setController(Controller<T> controller) {
    this.controller = controller;
  }

  @Override
  public final void onUpdate(Pose pose) {
    onSignal(controller.computeSignal(feedForward.get(), pose));
  }

  protected abstract void onSignal(T signal);
}
