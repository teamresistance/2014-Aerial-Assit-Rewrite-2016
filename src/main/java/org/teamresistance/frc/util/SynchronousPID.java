package org.teamresistance.frc.util;

import org.strongback.control.SoftwarePIDController;
import org.strongback.control.SoftwarePIDController.SourceType;

import java.util.function.Function;

import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 * TODO: Documentation (medium priority)
 * @author Rothanak So
 */
public class SynchronousPID implements LiveWindowSendable {
  private final SoftwarePIDController controller;
  private final Relay<Double> inputRelay = new Relay<>();
  private final Relay<Double> outputRelay = new Relay<>();
  private boolean hasRun = false;
  private boolean isConfigured = false;

  public SynchronousPID(String name, SourceType type, double kP, double kI, double kD) {
    this.controller = new SoftwarePIDController(type, inputRelay::get, outputRelay::accept)
        .withGains(
            SmartDashboard.getNumber(name + "/p", kP),
            SmartDashboard.getNumber(name + "/i", kI),
            SmartDashboard.getNumber(name + "/d", kD))
        .enable();
    SmartDashboard.putData(name, this);
  }

  public SynchronousPID withConfigurations(Function<SoftwarePIDController,
      SoftwarePIDController> configurator) {
    configurator.apply(controller);
    isConfigured = true;
    return this;
  }

  public boolean isWithinTolerance() {
    return hasRun && controller.isWithinTolerance();
  }

  public double calculate(double input) {
    hasRun = true;
    if (!isConfigured)
      throw new IllegalStateException("PID not configured. Did you remember to call " +
          "`withConfigurations`? Refer to the documentation for usage notes.");

    inputRelay.accept(input);
    controller.computeOutput();
    return outputRelay.get();
  }

  @Override
  public void updateTable() {
    controller.updateTable();
  }

  @Override
  public void startLiveWindowMode() {
    controller.startLiveWindowMode();
  }

  @Override
  public void stopLiveWindowMode() {
    controller.stopLiveWindowMode();
  }

  @Override
  public void initTable(ITable subtable) {
    controller.initTable(subtable);
  }

  @Override
  public ITable getTable() {
    return controller.getTable();
  }

  @Override
  public String getSmartDashboardType() {
    return controller.getSmartDashboardType();
  }
}
