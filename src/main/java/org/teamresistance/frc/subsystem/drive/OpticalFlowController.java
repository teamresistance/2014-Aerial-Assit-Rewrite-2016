package org.teamresistance.frc.subsystem.drive;

import org.strongback.control.SoftwarePIDController;
import org.teamresistance.frc.Feedback;
import org.teamresistance.frc.OpticalFlow;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.util.SynchronousPID;

/**
 * Created by Tarik Brown on 2/17/2017.
 */
public class OpticalFlowController implements Controller<Drive.Signal> {
  private final SynchronousPID opticalPID;
  private final SynchronousPID rotationPID;
  private final double KP = 0.02;
  private final double KI = 0.0;
  private final double KD = 0.0;
  private final double rotationKP = 0.0;
  private final double opticTolerance = 1.0;
  private final double rotationTolerance = 1.0;

  public OpticalFlow opticalFlow = new OpticalFlow();
  public OpticalFlowController(double targetx){

    this.rotationPID = new SynchronousPID("thispid",
        SoftwarePIDController.SourceType.DISTANCE,rotationKP,0,0)
        .withConfigurations(controller -> controller
            .withInputRange(0, 360) // gyro
            .withOutputRange(-1.0, 1.0) // motor
            .withTarget(0)
            .withTolerance(rotationTolerance)
            .continuousInputs(true));

    this.opticalPID = new SynchronousPID("opflowpid", SoftwarePIDController.SourceType.DISTANCE,KP,KI,KD)
        .withConfigurations(controller -> controller
        .withInputRange(0,10)
        .withOutputRange(-.5,.5)
        .withTarget(targetx)
        .withTolerance(opticTolerance));
  }
  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Feedback feedback) {
   double xspeed = this.opticalPID.calculate(feedback.dxdist) + .02;
   double rotationspeed = this.rotationPID.calculate(feedback.currentAngle);
    final boolean robotOriented = true;
    return new Drive.Signal(xspeed,0,rotationspeed,robotOriented);
  }
  @Override
  public boolean isOnTarget() {
    return opticalPID.isWithinTolerance();
  }
}
