package org.teamresistance.frc.hid;

import org.strongback.components.AngleSensor;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.control.SoftwarePIDController;
import org.teamresistance.frc.util.SynchronousPID;

public final class DaveKnob implements ContinuousRange {
  private static final double DEADBAND_DEGREES = 30.0;
  private final AngleSensor knob;
  private final AngleSensor gyro;

  private SynchronousPID currentRotationPid;

  public DaveKnob(AngleSensor knob, AngleSensor gyro) {
    this.knob = knob;
    this.gyro = gyro;
  }

  @Override
  public double read() {
    // Only update the setpoint when the robot isn't already in the middle of rotating
    if (currentRotationPid == null || currentRotationPid.isWithinTolerance()) {
      final double knobAngle = knob.getAngle();
      final double gyroAngle = gyro.getAngle();

      // Don't rotate if the shortest distance between the two angles is too large
      if (Math.abs(knobAngle - gyroAngle + 180) % 360 - 180 > DEADBAND_DEGREES)
        return 0;

      currentRotationPid = new SynchronousPID(SoftwarePIDController
          .SourceType.RATE, 0.015, 0, 0)
          .withConfigurations(controller -> controller
              .withInputRange(0, 360) // gyro
              .withOutputRange(-1.0, 1.0) // motor
              .withTarget(knobAngle) // degrees
              .withTolerance(2) // degrees
              .continuousInputs(true));
    }

    return currentRotationPid.calculate(gyro.getAngle());
  }
}
