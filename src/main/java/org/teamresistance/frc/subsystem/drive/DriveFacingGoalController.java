package org.teamresistance.frc.subsystem.drive;

import org.teamresistance.frc.Pose;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;

import java.util.Optional;

import static org.strongback.control.SoftwarePIDController.SourceType;

public class DriveFacingGoalController implements Controller<Drive.Signal> {
  private static final double TOLERANCE = 2;
  private static final double KP = 0.008;
  private static final double KD = 0;
  private static final double KI = 0;
  private static final double FF = 0;

  private final SynchronousPID pid;

  public DriveFacingGoalController() {
    this.pid = new SynchronousPID(SourceType.DISTANCE, KP, KI, KD, FF)
        .withConfigurations(controller -> controller
            .withInputRange(-1.0, 1.0) // offset percentage
            .withOutputRange(-1.0, 1.0) // motor
            .withTarget(0) // we want to be centered
            .withTolerance(TOLERANCE)
            .continuousInputs(true));
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Pose feedback) {
    // If we see the goal, rotate toward it. Otherwise, pass the feed forward through.
    Optional<Double> maybeOffset = feedback.goalOffset.get();
    double rotateSpeed = maybeOffset.map(pid::calculate).orElseGet(() -> feedForward.rotateSpeed);
    return new Drive.Signal(feedForward.xSpeed, feedForward.ySpeed, rotateSpeed);
  }
}
