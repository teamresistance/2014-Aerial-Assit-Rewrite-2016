package org.teamresistance.frc.subsystem.shoot;

import edu.wpi.first.wpilibj.PIDSourceType;
import org.strongback.control.SoftwarePIDController;
import org.teamresistance.frc.Pose;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.subsystem.drive.Drive;
import org.teamresistance.frc.util.SynchronousPID;

import static org.teamresistance.frc.IO.shooterEncoder;

/**
 * @author Tarik C. Brown
 */
public class ShooterController implements Controller<Drive.Signal> {
  private static final double TOLERANCE = 2;
  private static final double KP = 0.0;
  private static final double KD = 0.0;
  private static final double KI = 0.0;
  private static final double FF = 0.0;

  private SynchronousPID shooterPid;

  public ShooterController(double targetRate) {
    this.shooterPid = new SynchronousPID(SoftwarePIDController.SourceType.DISTANCE, KP, KI, KD, FF)
        .withConfigurations(controller -> controller
            .withInputRange(-1.0,1.0)
            .withOutputRange(-1.0, 1.0) // motor
            .withTarget(targetRate)
            .withTolerance(TOLERANCE)
            .continuousInputs(true));
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Pose feedback) {
    return null;
  }

  public boolean isOnTarget() {
    return shooterPid.isWithinTolerance();
  }
}
