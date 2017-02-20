package org.teamresistance.frc.subsystem.drive;

import org.strongback.control.SoftwarePIDController;
import org.strongback.control.SoftwarePIDController.SourceType;
import org.teamresistance.frc.Feedback;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;

/**
 * Created by Tarik Brown on 2/19/2017.
 */
public class OpticalFlowXYController implements Controller<Drive.Signal> {
  private final DriveHoldingAngleController angleController;
  private final SynchronousPID opticalPIDX;
  private final SynchronousPID opticalPIDY;

  public OpticalFlowXYController(double targetX, double targetY) {
    this.angleController = new DriveHoldingAngleController(0);
    this.opticalPIDX = new SynchronousPID("OFSX PID", SourceType.DISTANCE, 0.8, 0.0, 0.0)
        .withConfigurations(controller -> controller
            .withInputRange(-50, 50)
            .withOutputRange(-1.0, 1.0)
            .withTarget(targetX)
            .withTolerance(0.5));
    this.opticalPIDY = new SynchronousPID("OFSY PID", SourceType.DISTANCE, 0.8, 0.0, 0.0)
        .withConfigurations(controller -> controller
            .withInputRange(-50, 50)
            .withOutputRange(-1.0, 1.0)
            .withTarget(targetY)
            .withTolerance(0.5));
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Feedback feedback) {
    // Rot
    double rotationSpeed = angleController.computeSignal(feedForward, feedback).rotateSpeed;

    // X
    double xSpeed = opticalPIDX.calculate(feedback.dxdist);
//    xSpeed = xSpeed > 0 ? xSpeed + .2 : xSpeed - .2; // Add or subtract 0.2 depending on direction

    // Y
    double ySpeed = opticalPIDY.calculate(feedback.dydist);
//    ySpeed = ySpeed > 0 ? ySpeed + .2: ySpeed - .2; // Add or subtract 0.2 depending on direction

    return Drive.Signal.createRobotOrientedRaw(xSpeed + 0.1 , ySpeed + 0.1 , rotationSpeed);
  }

  @Override
  public boolean isOnTarget() {
    return opticalPIDX.isWithinTolerance() && opticalPIDY.isWithinTolerance();
  }
}
