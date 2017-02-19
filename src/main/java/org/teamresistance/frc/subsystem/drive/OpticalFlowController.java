package org.teamresistance.frc.subsystem.drive;

import org.strongback.control.SoftwarePIDController.SourceType;
import org.teamresistance.frc.Feedback;
import org.teamresistance.frc.OpticalFlow;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;

/**
 * Created by Tarik Brown on 2/17/2017.
 */
public class OpticalFlowController implements Controller<Drive.Signal> {
  private final DriveHoldingAngleController angleController;
  private final SynchronousPID opticalPID;
  private final double KP = 0.8;
  private final double KI = 0.0;
  private final double KD = 0.0;
  private final double opticTolerance = 5.0;

  private final double hacktarget;
  public double opflow;
  public OpticalFlowController(double targetx) {
    hacktarget = targetx;
    this.angleController = new DriveHoldingAngleController(0);
    this.opticalPID = new SynchronousPID("OFS PID", SourceType.DISTANCE, KP, KI, KD)
        .withConfigurations(controller -> controller
        .withInputRange(0,10)
        .withOutputRange(-.5,.5)
        .withTarget(targetx)
        .withTolerance(opticTolerance));
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Feedback feedback) {
    double xSpeed = this.opticalPID.calculate(feedback.dxdist);
    xSpeed = xSpeed > 0 ? xSpeed + .02: xSpeed - .02; // Add or subtract 0.2 depending on direction
    double rotationSpeed = angleController.computeSignal(feedForward, feedback).rotateSpeed;
    // TODO: make sure you actually want robotOriented; depends on if OFS returns robot-oriented dx/dy
    return Drive.Signal.createFieldOriented(xSpeed, 0,rotationSpeed);
  }

  @Override
  public boolean isOnTarget() {
   return opticalPID.isWithinTolerance();
  }
}
