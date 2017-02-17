package org.teamresistance.frc.subsystem.drive;

import org.strongback.control.SoftwarePIDController.SourceType;
import org.teamresistance.frc.Feedback;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * {@link Drive} controller that turns the robot to match and angle and tries to hold that angle
 * while the controller is active. The robot can keep driving while this controller is active, as
 * the x and y speeds of the feedforward {@link Drive.Signal} are passed through.
 *
 * @author Rothanak So
 */
public class DriveHoldingAngleController implements Controller<Drive.Signal> {
  // We're using a proportional-only loop, where motor output = kP * error. Our error range is
  // from -180 to +180, and so a very small kP must be used to ensure the output isn't full speed
  // (>= 1.0 or <= -1.0) for the majority of travel. For example, a kP of 0.008 only outputs full
  // speed when the error exceeds 1 / 0.008, or 125 degrees.
  private static final double TOLERANCE = 2;
  public static final double KP = 0.044;
  public static final double KI = 0.0007;
  public static final double KD = 0.088;

  private final SynchronousPID rotationPid;

  public DriveHoldingAngleController(double targetDegrees) {
    this.rotationPid = new SynchronousPID("Angle Hold", SourceType.DISTANCE, KP, 0, KD)
        .withConfigurations(controller -> controller
            .withInputRange(0, 360) // navX
            .withOutputRange(-1.0, 1.0) // motor
            .withTarget(targetDegrees)
            .withTolerance(TOLERANCE)
            .continuousInputs(true));
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Feedback feedback) {
    double rotationSpeed = rotationPid.calculate(feedback.currentAngle);
    return Drive.Signal.createFieldOriented(feedForward.xSpeed, feedForward.ySpeed, rotationSpeed);
  }

  @Override
  public boolean isOnTarget() {
    return rotationPid.isWithinTolerance();
  }
}
