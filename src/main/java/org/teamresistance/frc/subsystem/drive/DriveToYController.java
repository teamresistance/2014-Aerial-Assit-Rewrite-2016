package org.teamresistance.frc.subsystem.drive;

import org.strongback.control.SoftwarePIDController;
import org.teamresistance.frc.Feedback;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;

/**
 * Created by shrey on 1/25/2017.
 */
public class DriveToYController implements Controller<Drive.Signal> {
  private final SynchronousPID rotationPID;
  private static final double ROTATION_TOLERANCE = 1;
  private static final double ROTATION_KP = 0.008;
//  private static final double ROTATION_KD = 0;
//  private static final double ROTATION_KI = 0;
//  private static final double ROTATION_FF = 0;
  private double yDistPingSensor;
  private final double hackTargetY;

  private final SynchronousPID yTranslationPID;
  private static final double Y_TRANSLATION_TOLERANCE = 10;
  private static final double Y_TRANSLATION_KP = 0.02;
//  private static final double Y_TRANSLATION_KD = 0;
//  private static final double Y_TRANSLATION_KI = 0;
//  private static final double Y_TRANSLATION_FF = 0;

  public DriveToYController(double targetY) {
    hackTargetY = targetY;

    this.rotationPID = new SynchronousPID(SoftwarePIDController.SourceType.DISTANCE,ROTATION_KP,0,0,0)
        .withConfigurations(controller -> controller
            .withInputRange(0, 360) // gyro
            .withOutputRange(-1.0, 1.0) // motor
            .withTarget(0)
            .withTolerance(ROTATION_TOLERANCE)
            .continuousInputs(true));

    this.yTranslationPID = new SynchronousPID(SoftwarePIDController.SourceType.DISTANCE,Y_TRANSLATION_KP,0,0,0)
        .withConfigurations(controller -> controller
            .withInputRange(0, 120) // ping sensor -- 10 ft or 120 inches -- can modify later
            .withOutputRange(-0.5, 0.5) // motor
            .withTarget(targetY)
            .withTolerance(Y_TRANSLATION_TOLERANCE));
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Feedback feedback) {
    double ySpeed = this.yTranslationPID.calculate(feedback.yDist);
    double rotateSpeed = this.rotationPID.calculate(feedback.currentAngle);
    yDistPingSensor = feedback.yDist;
    return Drive.Signal.createFieldOriented(0,ySpeed,rotateSpeed);
  }

  @Override
  public boolean isOnTarget() {
    return (yDistPingSensor >= hackTargetY);
//    return yTranslationPID.isWithinTolerance();
  }
}
