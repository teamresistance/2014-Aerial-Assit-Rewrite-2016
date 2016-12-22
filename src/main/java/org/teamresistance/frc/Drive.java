package org.teamresistance.frc;

import org.strongback.components.Stoppable;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.drive.MecanumDrive;

/**
 * @author Shreya Ravi
 */
public class Drive implements Stoppable {

  private final MecanumDrive robotDrive;
  private final ContinuousRange translateXSpeed;
  private final ContinuousRange translateYSpeed;
  private final ContinuousRange rotateSpeed;

  public Drive(MecanumDrive robotDrive, ContinuousRange translateXSpeed, ContinuousRange translateYSpeed, ContinuousRange rotateSpeed) {
    this.robotDrive = robotDrive;
    this.translateXSpeed = translateXSpeed;
    this.translateYSpeed = translateYSpeed;
    this.rotateSpeed = rotateSpeed;
    States.driveState = DriveState.OP_CONTROL;
  }

  public void update() throws IllegalStateException {
    if (States.driveState == DriveState.OP_CONTROL) {
      this.robotDrive.cartesian(this.translateXSpeed.read(), this.translateYSpeed.read(), this.rotateSpeed.read());
    } else if (States.driveState == DriveState.STOP) {
      this.robotDrive.stop();
    } else {
      throw new IllegalStateException();
    }
  }

  public void stop() {
    this.robotDrive.stop();
  }
}
