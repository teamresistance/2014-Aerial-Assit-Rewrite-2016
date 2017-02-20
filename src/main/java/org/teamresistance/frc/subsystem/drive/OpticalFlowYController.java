package org.teamresistance.frc.subsystem.drive;

import org.strongback.control.SoftwarePIDController;
import org.teamresistance.frc.Feedback;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;

/**
 * Created by Tarik Brown on 2/19/2017.
 */
public class OpticalFlowYController implements Controller<Drive.Signal> {
  private final DriveHoldingAngleController angleController;
  private final SynchronousPID opticalPID;
  private final double KP = 0.8;
  private final double KI = 0.0;
  private final double KD = 0.0;
  private final double opticTolerance = 1;

  public OpticalFlowYController(double targety) {
    this.angleController = new DriveHoldingAngleController(0);
    this.opticalPID = new SynchronousPID("OFSY PID", SoftwarePIDController.SourceType.DISTANCE, KP, KI, KD)
        .withConfigurations(controller -> controller
            .withInputRange(0,20)
            .withOutputRange(-1.0,1.0)
            .withTarget(targety)
            .withTolerance(opticTolerance));
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Feedback feedback) {
    double ySpeed = this.opticalPID.calculate(feedback.dydist);
//    ySpeed = ySpeed > 0 ? ySpeed + .2: ySpeed - .2; // Add or subtract 0.2 depending on direction
    double rotationSpeed = angleController.computeSignal(feedForward, feedback).rotateSpeed;
    // TODO: make sure you actually want robotOriented; depends on if OFS returns robot-oriented dx/dy
    return Drive.Signal.createRobotOriented(ySpeed,180, rotationSpeed);
  }

  @Override
  public boolean isOnTarget() {
    return opticalPID.isWithinTolerance();
  }
}
