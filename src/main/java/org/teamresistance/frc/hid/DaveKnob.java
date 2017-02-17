package org.teamresistance.frc.hid;

import org.strongback.components.AngleSensor;
import org.strongback.components.ui.ContinuousRange;
import org.teamresistance.frc.subsystem.drive.DriveHoldingAngleController;
import org.teamresistance.frc.util.SynchronousPID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.strongback.control.SoftwarePIDController.SourceType;

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
    double difference = -1 * (Math.abs(knobAngle - gyroAngle + 180) % 360 - 180);

    SmartDashboard.putNumber("Knob: Error", difference);
    SmartDashboard.putBoolean("Knob: Latched?", false);

    // Only update the setpoint when the robot isn't already in the middle of rotating
    if (currentRotationPid == null) {
      if (difference > DEADBAND_DEGREES) return 0;
      currentRotationPid = createPid(knobAngle);
    } else if (currentRotationPid.isWithinTolerance() && difference < DEADBAND_DEGREES) {
      SmartDashboard.putBoolean("Knob: Latched?", true);
      currentRotationPid = createPid(knobAngle);
    }

    return currentRotationPid.calculate(gyroAngle);
  }

  private SynchronousPID createPid(double setpoint) {
    return new SynchronousPID("Knob Rotation", SourceType.DISTANCE,
        DriveHoldingAngleController.KP,
        DriveHoldingAngleController.KI,
        DriveHoldingAngleController.KD)
        .withConfigurations(controller -> controller
            .withInputRange(0, 360) // gyro
            .withOutputRange(-0.1, 0.1) // motor
            .withTarget(setpoint) // degrees
            .withTolerance(2) // degrees
            .continuousInputs(true));
  }
}
