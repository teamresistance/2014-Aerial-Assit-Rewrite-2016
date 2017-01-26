package org.teamresistance.frc.autonomous;

import org.teamresistance.frc.subsystem.Controller;
import edu.wpi.first.wpilibj.PIDSourceType;
import org.strongback.control.SoftwarePIDController;
import org.teamresistance.frc.Pose;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;


/**
 * Created by owner on 1/25/2017.
 */
public class AutoPingController implements Controller {
  private SynchronousPID pingPID;
  private static final double TOLERANCE = 2;
  private static final double KP = 0.0;
  private static final double KD = 0.0;
  private static final double KI = 0.0;
  private static final double FF = 0.0;
  double targetDistance;

  public AutoPingController(SynchronousPID pingPID) {

  }


  @Override
  public Object computeSignal(Object feedForward, Pose feedback) {
    return null;
  }
}
