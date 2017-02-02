package org.teamresistance.frc.subsystem.drive;

import org.strongback.control.SoftwarePIDController;
import org.teamresistance.frc.Pose;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;

/**
 * Created by shrey on 1/25/2017.
 */
public class DriveToXController implements Controller<Drive.Signal> {
  private final SynchronousPID rotationPID;
  private static final double ROTATION_TOLERANCE = 1;
  private static final double ROTATION_KP = 0.008;
//  private static final double ROTATION_KD = 0;
//  private static final double ROTATION_KI = 0;
//  private static final double ROTATION_FF = 0;

  private final SynchronousPID xTranslationPID;
  private static final double X_TRANSLATION_TOLERANCE = 10;
  private static final double X_TRANSLATION_KP = 0.02;
//  private static final double X_TRANSLATION_KD = 0;
//  private static final double X_TRANSLATION_KI = 0;
//  private static final double X_TRANSLATION_FF = 0;

  private static final double ADDED_POWER = 0.2;

  public DriveToXController(double targetX) {
    this.rotationPID = new SynchronousPID(SoftwarePIDController.SourceType.DISTANCE,
        ROTATION_KP,0,0,0)
        .withConfigurations(controller -> controller
            .withInputRange(0, 360) // gyro
            .withOutputRange(-1.0, 1.0) // motor
            .withTarget(0)
            .withTolerance(ROTATION_TOLERANCE)
            .continuousInputs(true));
    this.xTranslationPID = new SynchronousPID(SoftwarePIDController.SourceType.DISTANCE,
        X_TRANSLATION_KP,0,0,0)
        .withConfigurations(controller -> controller
            .withInputRange(0, 120) // ping sensor -- 10 ft or 120 inches -- can modify later
            .withOutputRange(-0.5, 0.5) // motor
            .withTarget(targetX)
            .withTolerance(X_TRANSLATION_TOLERANCE)
            .continuousInputs(true));
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Pose feedback) {
    // multiplied xSpeed by -1 because otherwise, would go in wrong direction
    double xSpeed = this.xTranslationPID.calculate(feedback.xDist) + this.ADDED_POWER;
    double rotateSpeed = this.rotationPID.calculate(feedback.currentAngle);
    return new Drive.Signal(xSpeed,0,rotateSpeed);
  }

  @Override
  public boolean isOnTarget() {
    return xTranslationPID.isWithinTolerance();
  }
}
