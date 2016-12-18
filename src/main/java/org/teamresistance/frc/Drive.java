package org.teamresistance.frc;

import org.strongback.components.ui.ContinuousRange;
import org.strongback.drive.MecanumDrive;

/**
 * Created by shrey on 12/18/2016.
 */
public class Drive {

  public final MecanumDrive robotDrive;

  public Drive(MecanumDrive robotDrive) {
    this.robotDrive = robotDrive;
    States.driveState = DriveState.OP_CONTROL;
  }

  public void update(ContinuousRange translateXSpeed, ContinuousRange translateYSpeed, ContinuousRange rotation) {
    if (States.driveState == DriveState.OP_CONTROL) {
         this.robotDrive.cartesian(translateXSpeed.read(), translateYSpeed.read(), rotation.read());
    } else if (States.driveState == DriveState.STOP) {
      this.robotDrive.stop();
    }
  }

  public void stop() {
    this.robotDrive.stop();
  }

}
