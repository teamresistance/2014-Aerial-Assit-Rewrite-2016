package org.teamresistance.frc;

import org.strongback.components.Stoppable;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.drive.MecanumDrive;

/**
 * @author Shreya Ravi
 */
public class Drive implements Stoppable {

  private final MecanumDrive robotDrive;
  private final ContinuousRange xSpeed;
  private final ContinuousRange ySpeed;
  private final ContinuousRange rotateSpeed;

  public Drive(MecanumDrive robotDrive, ContinuousRange xSpeed, ContinuousRange ySpeed, ContinuousRange rotateSpeed) {
    this.robotDrive = robotDrive;
    this.xSpeed = xSpeed;
    this.ySpeed = ySpeed;
    this.rotateSpeed = rotateSpeed;
    States.driveState = DriveState.OP_CONTROL;
  }

  public void update() {
    if (States.driveState == DriveState.OP_CONTROL) {
      robotDrive.cartesian(xSpeed.read(), ySpeed.read(), rotateSpeed.read());
    } else if (States.driveState == DriveState.STOP) {
      robotDrive.stop();
    } else {
      throw new IllegalStateException();
    }
  }

  public void stop() {
    robotDrive.stop();
  }
}
