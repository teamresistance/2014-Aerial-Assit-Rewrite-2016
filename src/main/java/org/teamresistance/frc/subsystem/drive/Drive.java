package org.teamresistance.frc.subsystem.drive;

import org.strongback.command.Requirable;
import org.strongback.components.Stoppable;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.drive.MecanumDrive;
import org.teamresistance.frc.Pose;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.subsystem.OpenLoopController;
import org.teamresistance.frc.subsystem.Loopable;

/**
 * @author Shreya Ravi
 * @author Rothanak So
 */
public class Drive implements Loopable, Stoppable, Requirable {
  private final MecanumDrive robotDrive;
  private final ContinuousRange xSpeed;
  private final ContinuousRange ySpeed;
  private final ContinuousRange rotateSpeed;
  private Controller<Drive.Signal> controller = new OpenLoopController<>();

  public Drive(MecanumDrive robotDrive, ContinuousRange xSpeed, ContinuousRange ySpeed,
               ContinuousRange rotateSpeed) {
    this.robotDrive = robotDrive;
    this.xSpeed = xSpeed;
    this.ySpeed = ySpeed;
    this.rotateSpeed = rotateSpeed;
  }

  public void setOpenLoop() {
    this.controller = new OpenLoopController<>();
  }

  public void setController(Controller<Signal> controller) {
    this.controller = controller;
  }

  @Override
  public void onUpdate(Pose pose) {
    Signal raw = new Signal(xSpeed.read(), ySpeed.read(), rotateSpeed.read());
    Signal computed = controller.computeSignal(raw, pose);
    robotDrive.cartesian(computed.xSpeed, computed.ySpeed, computed.rotateSpeed);
  }

  @Override
  public void stop() {
    robotDrive.stop();
  }

  public final static class Signal {
    public final double xSpeed;
    public final double ySpeed;
    public final double rotateSpeed;

    public Signal(double xSpeed, double ySpeed, double rotateSpeed) {
      this.xSpeed = xSpeed;
      this.ySpeed = ySpeed;
      this.rotateSpeed = rotateSpeed;
    }
  }
}
