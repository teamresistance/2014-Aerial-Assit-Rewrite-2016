package org.teamresistance.frc.subsystem.drive;

import org.strongback.control.SoftwarePIDController.SourceType;
import org.teamresistance.frc.Feedback;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;

/**
 * Created by Tarik Brown on 2/19/2017.
 */
public class OpticalFlowYController implements Controller<Drive.Signal> {
  private final DriveHoldingAngleController angleController;
  private final SynchronousPID opticalPID;

  private static final double KP = 0.2;
  private static final double KI = 0.0;
  private static final double KD = 0.0;
  private static final double tolerance = 0.25; // feet

  public OpticalFlowYController(double targetY) {
    this.angleController = new DriveHoldingAngleController(0);
    this.opticalPID = new SynchronousPID("Optical Flow Y", SourceType.DISTANCE, KP, KI, KD)
        .withConfigurations(controller -> controller
            .withInputRange(-20, 20)
            .withOutputRange(-0.7, 0.7)
            .withTarget(targetY)
            .withTolerance(tolerance));
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Feedback feedback) {
    double ySpeed = this.opticalPID.calculate(feedback.dy);
    //ySpeed = ySpeed > 0 ? ySpeed + .2: ySpeed - .2; // Add or subtract 0.2 depending on direction
    double rotationSpeed = angleController.computeSignal(feedForward, feedback).rotateSpeed;
    return Drive.Signal.createRobotOriented(ySpeed, 180, rotationSpeed);
  }

  @Override
  public boolean isOnTarget() {
    return opticalPID.isWithinTolerance();
  }
}
