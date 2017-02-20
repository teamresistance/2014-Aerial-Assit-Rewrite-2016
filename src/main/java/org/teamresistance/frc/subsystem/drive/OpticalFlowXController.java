package org.teamresistance.frc.subsystem.drive;

import org.strongback.control.SoftwarePIDController.SourceType;
import org.teamresistance.frc.Feedback;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;

/**
 * Created by Tarik Brown on 2/17/2017.
 */
public class OpticalFlowXController implements Controller<Drive.Signal> {
  private final DriveHoldingAngleController angleController;
  private final SynchronousPID opticalPID;
  private static final double KP = 0.8;
  private static final double KI = 0.0;
  private static final double KD = 0.0;
  private static final double TOLERANCE = 1;

  public OpticalFlowXController(double targetX) {
    this.angleController = new DriveHoldingAngleController(0);
    this.opticalPID = new SynchronousPID("Optical Flow X", SourceType.DISTANCE, KP, KI, KD)
        .withConfigurations(controller -> controller
            .withInputRange(-20, 20)
            .withOutputRange(-0.7, 0.7)
            .withTarget(targetX)
            .withTolerance(TOLERANCE));
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Feedback feedback) {
    double xSpeed = this.opticalPID.calculate(feedback.dx);
    //xSpeed = xSpeed > 0 ? xSpeed + .2 : xSpeed - .2; // Add or subtract 0.2 depending on direction
    double rotationSpeed = angleController.computeSignal(feedForward, feedback).rotateSpeed;
    return Drive.Signal.createRobotOriented(xSpeed, 90, rotationSpeed);
  }

  @Override
  public boolean isOnTarget() {
    return opticalPID.isWithinTolerance();
  }
}
