package org.teamresistance.frc.subsystem.drive;

import org.teamresistance.frc.Pose;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;

import static org.strongback.control.SoftwarePIDController.SourceType.RATE;

public class TurnInPlaceController implements Controller<Drive.Signal> {
  private static final double TOLERANCE_DEGREES = 2;
  private static final double KP = 0;
  private static final double KD = 0;
  private static final double KI = 0;
  private static final double FF = 0;

  private final SynchronousPID pid;

  public TurnInPlaceController(double targetAngle) {
    this.pid = new SynchronousPID(RATE, KP, KI, KD, FF)
        .withConfigurations(controller -> controller
            .withInputRange(0, 360)
            .withOutputRange(-1.0, 1.0)
            .withTolerance(TOLERANCE_DEGREES)
            .withTarget(targetAngle)
            .continuousInputs(true));
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal oldSignal, Pose pose) {
    double rotateSpeed = pid.calculate(pose.angle);
    return new Drive.Signal(oldSignal.xSpeed, oldSignal.ySpeed, rotateSpeed);
  }

  @Override
  public boolean isOnTarget() {
    return pid.isWithinTolerance();
  }
}
