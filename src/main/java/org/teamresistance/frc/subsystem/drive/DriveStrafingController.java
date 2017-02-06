package org.teamresistance.frc.subsystem.drive;

import org.teamresistance.frc.Feedback;
import org.teamresistance.frc.subsystem.Controller;

public class DriveStrafingController implements Controller<Drive.Signal> {
  private static final double SPEED = 0.6;

  private final Controller<Drive.Signal> angleController;
  private final double headingDeg;

  public DriveStrafingController(double orientation, double headingDeg) {
    this.angleController = new DriveHoldingAngleController(orientation);
    this.headingDeg = headingDeg;
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Feedback feedback) {
    double rotateSpeed = angleController.computeSignal(feedForward, feedback).rotateSpeed;
    return Drive.Signal.createRobotOriented(SPEED, headingDeg, rotateSpeed);
  }
}
