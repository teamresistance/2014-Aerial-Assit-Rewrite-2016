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
      //double v = Math.abs(knobAngle - gyroAngle + 180) % 360 - 180;
      //SmartDashboard.putNumber("Knob-Gyro Difference", v);
      //if (v > DEADBAND_DEGREES) {
      //  return 0;
      //}

      currentRotationPid = new SynchronousPID(SoftwarePIDController
          .SourceType.RATE, 0.010, 0, 0)
          .withConfigurations(controller -> controller
              .withInputRange(0, 360) // gyro
              .withOutputRange(-0.5, 0.5) // motor
              .withTarget(knobAngle) // degrees
              .withTolerance(2) // degrees
              .continuousInputs(true));
    }

    return currentRotationPid.calculate(gyro.getAngle());
  }
}
