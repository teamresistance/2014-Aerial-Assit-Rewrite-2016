package org.teamresistance.frc.util;

import org.strongback.control.SoftwarePIDController;
import org.strongback.control.SoftwarePIDController.SourceType;

import java.util.function.Function;

public class SynchronousPID {
  private final SoftwarePIDController controller;
  private final Relay<Double> inputRelay = new Relay<>();
  private final Relay<Double> outputRelay = new Relay<>();

  public SynchronousPID(SourceType type, double kP, double kI, double kD) {
    this(type, kP, kI, kD, 0.0);
  }

  public SynchronousPID(SourceType type, double kP, double kI, double kD, double feedForward) {
    this.controller = new SoftwarePIDController(type, inputRelay::get, outputRelay::accept)
        .withGains(kP, kI, kD, feedForward)
        .enable();
  }

  public SynchronousPID withConfigurations(Function<SoftwarePIDController,
      SoftwarePIDController> configurator) {
    configurator.apply(controller);
    return this;
  }

  public boolean isWithinTolerance() {
    return controller.isWithinTolerance();
  }

  public double calculate(double input) {
    inputRelay.accept(input);
    controller.computeOutput();
    return outputRelay.get();
  }
}
