package org.teamresistance.frc.subsystem.drive;

import org.strongback.control.SoftwarePIDController.SourceType;
import org.teamresistance.frc.Feedback;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Tarik Brown
 */
public class OpticalFlowXYController implements Controller<Drive.Signal> {
  private final DriveHoldingAngleController angleController;
  private final SynchronousPID opticalPIDX;
  private final SynchronousPID opticalPIDY;

  public OpticalFlowXYController(double targetX, double targetY) {
    this.angleController = new DriveHoldingAngleController(0);
    this.opticalPIDX = new SynchronousPID("Optical Flow XY (X)", SourceType.DISTANCE, 0.5, 0.0, 0.0)
        .withConfigurations(controller -> controller
            .withInputRange(-50, 50)
            .withOutputRange(-0.7, 0.7)
            .withTarget(targetX)
            .withTolerance(0.5));
    this.opticalPIDY = new SynchronousPID("Optical Flow XY (Y)", SourceType.DISTANCE, 0.5, 0.0, 0.0)
        .withConfigurations(controller -> controller
            .withInputRange(-50, 50)
            .withOutputRange(-0.7, 0.7)
            .withTarget(targetY)
            .withTolerance(0.5));
  }

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Feedback feedback) {
    double rotationSpeed = angleController.computeSignal(feedForward, feedback).rotateSpeed;
    double xSpeed = opticalPIDX.calculate(feedback.dx);
    double ySpeed = opticalPIDY.calculate(feedback.dy);

    SmartDashboard.putNumber("[DEBUG] xSpeed", xSpeed);
    SmartDashboard.putNumber("[DEBUG] ySpeed", ySpeed);

    return Drive.Signal.createRobotOrientedRaw(xSpeed, ySpeed, rotationSpeed);
  }

  @Override
  public boolean isOnTarget() {
    return opticalPIDX.isWithinTolerance() && opticalPIDY.isWithinTolerance();
  }
}
