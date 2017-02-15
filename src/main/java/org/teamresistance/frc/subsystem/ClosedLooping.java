package org.teamresistance.frc.subsystem;

import org.teamresistance.frc.Feedback;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

    final String subsystemName = getClass().getSimpleName(); // will get parent class
    final String controllerName = controller.getClass().getSimpleName();
    SmartDashboard.putString(subsystemName + " Controller", controllerName);
  }

  @Override
  public final void onUpdate(Feedback feedback) {
    onSignal(controller.computeSignal(feedForward.get(), feedback));
  }

  protected abstract void onSignal(T signal);
}
