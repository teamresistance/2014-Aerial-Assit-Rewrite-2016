package org.teamresistance.frc.subsystem.drive;

import org.teamresistance.frc.Pose;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;

import static org.strongback.control.SoftwarePIDController.SourceType;

/**
 * {@link Drive} controller that turns the robot to match and angle and tries to hold that angle
 * while the controller is active. The robot can keep driving while this controller is active, as
 * the x and y speeds of the feedforward {@link Drive.Signal} are passed through.
 *
 * @author Rothanak So
 */
public class DriveHoldingAngleController implements Controller<Drive.Signal> {
  private static final double TOLERANCE = 2;
  private static final double KP = 0.008;
  private static final double KD = 0;
  private static final double KI = 0;
  private static final double FF = 0;

  private final SynchronousPID rotationPid;

  public DriveHoldingAngleController(double targetDegrees) {
    this.rotationPid = new SynchronousPID(SourceType.DISTANCE, KP, KI, KD, FF)
        .withConfigurations(controller -> controller
            .withInputRange(0, 360) // gyro
            .withOutputRange(-1.0, 1.0) // motor
            .withTarget(targetDegrees)
            .withTolerance(TOLERANCE)
            .continuousInputs(true));
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Pose feedback) {
    double rotationSpeed = rotationPid.calculate(feedback.currentAngle);
    return new Drive.Signal(feedForward.xSpeed, feedForward.ySpeed, rotationSpeed);
  }

  @Override
  public boolean isOnTarget() {
    return rotationPid.isWithinTolerance();
  }
}
