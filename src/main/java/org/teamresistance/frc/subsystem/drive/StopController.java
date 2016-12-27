package org.teamresistance.frc.subsystem.drive;

import org.teamresistance.frc.Pose;
import org.teamresistance.frc.subsystem.Controller;

public final class StopController implements Controller<Drive.Signal> {

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Pose feedback) {
    return new Drive.Signal(0, 0, 0);
  }

  @Override
  public boolean isOnTarget() {
    return false; // never on target
  }
}
