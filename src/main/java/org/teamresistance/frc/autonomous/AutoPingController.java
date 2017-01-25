package org.teamresistance.frc.autonomous;

import org.teamresistance.frc.subsystem.Controller;
import edu.wpi.first.wpilibj.PIDSourceType;
import org.strongback.control.SoftwarePIDController;
import org.teamresistance.frc.Pose;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.subsystem.drive.Drive;
import org.teamresistance.frc.util.SynchronousPID;


/**
 * @author Tarik C. Brown
 */
public class AutoPingController implements Controller<Drive.Signal> {
  private final double targetDistance;
  private static final double TOLERANCE = 2;
  private static final double KP = 0.0;
  private static final double KD = 0.0;
  private static final double KI = 0.0;
  private static final double FF = 0.0;

  private SynchronousPID pingPid;

  public AutoPingController(double targetDistance) {
    this.targetDistance = targetDistance;
    this.pingPid = new SynchronousPID(SoftwarePIDController.SourceType.DISTANCE,KP,KI,KD);
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Pose feedback) {
    return null;
  }
}
