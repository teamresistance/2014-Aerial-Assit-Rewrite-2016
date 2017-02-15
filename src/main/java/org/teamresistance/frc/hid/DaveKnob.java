package org.teamresistance.frc.hid;

import org.strongback.components.AngleSensor;
import org.strongback.components.ui.ContinuousRange;
import org.teamresistance.frc.util.SynchronousPID;

import static org.strongback.control.SoftwarePIDController.*;

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
    final double knobAngle = knob.getAngle();
    final double gyroAngle = gyro.getAngle();

    // Calculate the shortest distance between the two angles, for checking the deadband
    double difference = Math.abs(knobAngle - gyroAngle + 180) % 360 - 180;

    // Only update the setpoint when the robot isn't already in the middle of rotating
    if (currentRotationPid == null) {
      if (difference > DEADBAND_DEGREES) return 0;
      currentRotationPid = createPid(knobAngle);
    } else if (currentRotationPid.isWithinTolerance() && difference < DEADBAND_DEGREES) {
      currentRotationPid = createPid(knobAngle);
    }

    return currentRotationPid.calculate(gyro.getAngle());
  }

  private SynchronousPID createPid(double setpoint) {
    return new SynchronousPID(SourceType.RATE, 0.010, 0, 0)
        .withConfigurations(controller -> controller
            .withInputRange(0, 360) // gyro
            .withOutputRange(-0.8, 0.8) // motor
            .withTarget(setpoint) // degrees
            .withTolerance(2) // degrees
            .continuousInputs(true));
  }
}
