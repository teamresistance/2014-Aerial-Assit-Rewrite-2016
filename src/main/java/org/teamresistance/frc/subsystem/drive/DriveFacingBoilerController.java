package org.teamresistance.frc.subsystem.drive;

import org.teamresistance.frc.Feedback;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;

import java.util.OptionalDouble;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.strongback.control.SoftwarePIDController.SourceType;

public class DriveFacingBoilerController implements Controller<Drive.Signal> {
  private static final double TOLERANCE = 0.02;
  private static final double KP = 3; // maps the input domain to the output domain [-.6, +.6]
  private static final double KD = 0;
  private static final double KI = 0;

  private final SynchronousPID pid;

  public DriveFacingBoilerController() {
    double kp = SmartDashboard.getNumber("Vision: FaceBoiler PID/p", KP);
    this.pid = new SynchronousPID(SourceType.DISTANCE, kp, KI, KD)
        .withConfigurations(controller -> controller
            .withInputRange(-1.0, 1.0) // offset percentage
            .withOutputRange(-.7, .7) // motor
            .withTarget(0) // we want to be centered
            .withTolerance(TOLERANCE));

    SmartDashboard.putData("Vision: FaceBoiler PID", pid);
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Feedback feedback) {
    // If we see the goal, rotate toward it. Otherwise, pass the feed forward through.
    OptionalDouble maybeOffset = feedback.goalOffset;
    double rotateSpeed;
    if (maybeOffset.isPresent()) {
      rotateSpeed = pid.calculate(maybeOffset.getAsDouble());
      if (rotateSpeed > 0.2) {
        // change nothing
      } else if (rotateSpeed > 0.05) {
        rotateSpeed = 0.2;
      } else if (rotateSpeed > -0.05) {
        rotateSpeed = 0;
      } else if (rotateSpeed > -0.2) {
        rotateSpeed = -0.2;
      }
    } else {
      rotateSpeed = feedForward.rotateSpeed;
    }
    SmartDashboard.putNumber("Vision: FaceBoiler rotate speed", rotateSpeed);
    return Drive.Signal.createFieldOriented(feedForward.xSpeed, feedForward.ySpeed, rotateSpeed);
  }
}
