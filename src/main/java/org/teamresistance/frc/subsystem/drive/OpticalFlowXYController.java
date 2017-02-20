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

  private double xKp = 0.2;
  private double xKi = 0.0;
  private double xKd = 0.0;
  private double opFlowToleranceX = 0.5;

  private double yKp = 0.2;
  private double yKi = 0.0;
  private double yKd = 0.0;
  private double getOpFlowToleranceY = 0.5;

  //Try Combining two PID

  public OpticalFlowXYController(double targetX, double targetY) {
    this.angleController = new DriveHoldingAngleController(0);
    this.opticalPIDX = new SynchronousPID("Optical Flow XY (X)", SourceType.DISTANCE, xKp, xKi, xKd)
        .withConfigurations(controller -> controller
            .withInputRange(-50, 50)
            .withOutputRange(-0.7, 0.7)
            .withTarget(targetX)//-------
            .withTolerance(opFlowToleranceX));
    this.opticalPIDY = new SynchronousPID("Optical Flow XY (Y)", SourceType.DISTANCE, yKp, yKi, yKd)
        .withConfigurations(controller -> controller
            .withInputRange(-50, 50)
            .withOutputRange(-0.7, 0.7)
            .withTarget(targetY)
            .withTolerance(getOpFlowToleranceY));
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
