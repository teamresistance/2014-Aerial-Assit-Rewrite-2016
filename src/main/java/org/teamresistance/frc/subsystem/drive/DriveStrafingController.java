package org.teamresistance.frc.subsystem.drive;

import org.teamresistance.frc.Pose;
import org.teamresistance.frc.subsystem.Controller;

public class DriveStrafingController implements Controller<Drive.Signal> {
  private static final double SPEED = 0.6;

  private final Controller<Drive.Signal> angleController;
  private final double directionDegrees;

  public DriveStrafingController(double degrees) {
    this.angleController = new DriveHoldingAngleController(0);
    this.directionDegrees = degrees;
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Pose feedback) {
    double rotateSpeed = angleController.computeSignal(feedForward, feedback).rotateSpeed;
    return Drive.Signal.createRobotOriented(SPEED, directionDegrees, rotateSpeed);
  }
}
