package org.teamresistance.frc.subsystem.drive;

import org.strongback.command.Requirable;
import org.strongback.components.Stoppable;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.drive.MecanumDrive;
import org.teamresistance.frc.Pose;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.subsystem.Looping;
import org.teamresistance.frc.subsystem.OpenLoopController;

/**
 * @author Shreya Ravi
 * @author Rothanak So
 */
public class Drive implements Looping, Stoppable, Requirable {
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
    setController(new OpenLoopController<>());
  }

  public void setController(Controller<Signal> controller) {
    this.controller = controller;
  }

  @Override
  public void onUpdate(Pose pose) {
    Signal feedForward = new Signal(xSpeed.read(), ySpeed.read(), rotateSpeed.read());
    Signal computed = controller.computeSignal(feedForward, pose);
    robotDrive.cartesian(computed.xSpeed, computed.ySpeed, computed. rotateSpeed);
  }

  @Override
  public void stop() {
    setController(new StopController());
  }

  final static class Signal {
    final double xSpeed;
    final double ySpeed;
    final double rotateSpeed;

    Signal(double xSpeed, double ySpeed, double rotateSpeed) {
      this.xSpeed = xSpeed;
      this.ySpeed = ySpeed;
      this.rotateSpeed = rotateSpeed;
    }
  }
}
