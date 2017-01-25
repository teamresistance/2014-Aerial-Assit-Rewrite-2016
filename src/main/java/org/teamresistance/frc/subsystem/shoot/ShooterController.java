package org.teamresistance.frc.subsystem.shoot;

import edu.wpi.first.wpilibj.PIDSourceType;
import org.strongback.control.SoftwarePIDController;
import org.teamresistance.frc.Pose;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;

import static org.teamresistance.frc.IO.shooterEncoder;

/**
 * @author Tarik C. Brown
 */
public class ShooterController implements Controller {
  private SynchronousPID shootPID;
  private static final double TOLERANCE = 2;
  private static final double KP = 0.0;
  private static final double KD = 0.0;
  private static final double KI = 0.0;
  private static final double FF = 0.0;
  double targetRate;

  public ShooterController(SynchronousPID shootPID) {
    this.shootPID = new SynchronousPID(SoftwarePIDController.SourceType.DISTANCE, KP, KI, KD, FF)
        .withConfigurations(controller -> controller
            .withOutputRange(-1.0, 1.0) // motor
            .withTarget(targetRate)
            .withTolerance(TOLERANCE)
            .continuousInputs(true));

    // Math found that could work with shooter encoder, right now im going to put random numbers
    //diamater of the wheel * pi = distance per rotation
    // 1 / ticks per rotation = rotations per tick
    //distance per rotation * rotations per tick = distance per tick
    //diamater of wheel * pi / ticks per rotation = distance per tick
    double distancePerTick = 3 * Math.PI / 200;
    shooterEncoder.setDistancePerPulse(distancePerTick);
    shooterEncoder.setPIDSourceType(PIDSourceType.kRate);
  }

  @Override
  public Object computeSignal(Object feedForward, Pose feedback) {
    return null;
  }

  public boolean isOnTarget() {
    return shootPID.isWithinTolerance();
  }
}
