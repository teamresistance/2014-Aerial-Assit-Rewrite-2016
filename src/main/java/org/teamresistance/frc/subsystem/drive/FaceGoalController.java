package org.teamresistance.frc.subsystem.drive;

import org.teamresistance.frc.Pose;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;

import java.util.Optional;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.strongback.control.SoftwarePIDController.SourceType.RATE;

public class FaceGoalController implements Controller<Drive.Signal> {
  private static final double TOLERANCE = 5;
  private static final double KP = 0;
  private static final double KD = 0;
  private static final double KI = 0;
  private static final double FF = 0;

  private final SynchronousPID pid;

  public FaceGoalController() {
    this.pid = new SynchronousPID(RATE, KP, KI, KD, FF)
        .withConfigurations(controller -> controller
            .withInputRange(0, 100) // percent
            .withOutputRange(-1.0, 1.0)
            .withTolerance(TOLERANCE)
            .withTarget(50) // center on the goal
            .continuousInputs(true));

    // Send the PID to the SmartDashboard to allow the gains to be tuned
    SmartDashboard.putData("Face Goal Controller", pid);
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Pose feedback) {
    Optional<Double> goalOffset = feedback.goalOffset.get();

    // If we see the goal, rotate toward it. Otherwise, pass the feed forward through.
    double rotateSpeed = goalOffset.map(pid::calculate).orElseGet(() -> feedForward.rotateSpeed);
    return new Drive.Signal(feedForward.xSpeed, feedForward.ySpeed, rotateSpeed);
  }

  @Override
  public boolean isOnTarget() {
    return pid.isWithinTolerance();
  }
}
