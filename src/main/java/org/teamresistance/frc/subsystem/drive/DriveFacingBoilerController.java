package org.teamresistance.frc.subsystem.drive;

import org.teamresistance.frc.Feedback;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;

import java.util.OptionalDouble;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.strongback.control.SoftwarePIDController.SourceType;

public class DriveFacingBoilerController implements Controller<Drive.Signal> {
  private static final double TOLERANCE = 2;
  private static final double KP = 0.6; // maps the input domain to the output domain [-.6, +.6]
  private static final double KD = 0;
  private static final double KI = 0;

  private final SynchronousPID pid;

  public DriveFacingBoilerController() {
    this.pid = new SynchronousPID(SourceType.DISTANCE, KP, KI, KD)
        .withConfigurations(controller -> controller
            .withInputRange(-1.0, 1.0) // offset percentage
            .withOutputRange(-0.6, 0.6) // motor
            .withTarget(0) // we want to be centered
            .withTolerance(TOLERANCE));

    SmartDashboard.putData("Vision: FaceBoiler PID", pid);
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Feedback feedback) {
    // If we see the goal, rotate toward it. Otherwise, pass the feed forward through.
    OptionalDouble maybeOffset = feedback.goalOffset;
    double rotateSpeed = maybeOffset.isPresent()
        ? pid.calculate(maybeOffset.getAsDouble())
        : feedForward.rotateSpeed;
    SmartDashboard.putNumber("Vision: FaceBoiler rotate speed", rotateSpeed);
    return Drive.Signal.createFieldOriented(feedForward.xSpeed, feedForward.ySpeed, rotateSpeed);
  }
}
