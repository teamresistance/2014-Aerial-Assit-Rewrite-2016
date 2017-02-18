package org.teamresistance.frc.subsystem.drive;

import org.strongback.command.Command;
import org.strongback.command.CommandGroup;
import org.strongback.command.Requirable;
import org.strongback.components.AngleSensor;
import org.strongback.components.ui.ContinuousRange;
import org.teamresistance.frc.Feedback;
import org.teamresistance.frc.IO;
import org.teamresistance.frc.subsystem.ClosedLooping;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.subsystem.OpenLoopController;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The drive subsystem. The <b>only</b> way to manipulate the drivetrain is through this class. It
 * runs on a loop for the entire duration of the match and supports both operator and autonomous
 * control.
 * <p>
 * Set the current <b>behavior</b> by supplying a {@link Controller} for {@link Signal}s through
 * {@link #setController(Controller)}. These calls to {@link #setController(Controller)} can be
 * queued and scheduled using {@link Command}s and {@link CommandGroup}s to create higher-level
 * routines for controlling the robot. The {@link Controller#computeSignal} method of the active
 * controller will be invoked during {@link #onUpdate(Feedback)}, and the resulting output will be fed
 * into {@link #onSignal(Signal)}.
 * <p>
 * The subsystem starts with the {@link OpenLoopController} in place. It enables the default
 * follow-through behavior of driving the robot field-oriented in response to the raw joystick
 * values from the operator. Re-enable this behavior at any time by calling {@link #setOpenLoop()}.
 * <p>
 * Advanced {@link Controller} implementations can suppress the raw joystick values (the
 * "feedforward") and supply their own speeds, allowing for semi-autonomous behaviors like
 * maintaining the current heading or fully-autonomous behaviors like pathfinding. Both
 * field-oriented and robot-oriented {@link Signal}s are supported.
 *
 * @author Shreya Ravi
 * @author Rothanak So
 */
public class Drive extends ClosedLooping<Drive.Signal> implements Requirable {
  private final RobotDrive robotDrive;
  private final AngleSensor gyro;

  private boolean hackBrakingLatch;

  public Drive(RobotDrive robotDrive, AngleSensor gyro, ContinuousRange xSpeed, ContinuousRange ySpeed,
               ContinuousRange rotateSpeed) {
    super(() -> Signal.createFieldOriented(xSpeed.read(), ySpeed.read(), rotateSpeed.read()));
    this.robotDrive = robotDrive;
    this.gyro = gyro;
  }

  @Override
  protected void onSignal(Drive.Signal signal) {
    // Spin the motors inwards if we're stopped
    if (hackBrakingLatch) {
      SmartDashboard.putBoolean("Is Braking?", true);
      final double power = 0.3;
      IO.leftFrontMotor.set(-power);
      IO.rightFrontMotor.set(-power);
      IO.leftFrontMotor.set(power);
      IO.rightRearMotor.set(power);
      return; // abort so the drive signal doesn't mess things up
    }

    SmartDashboard.putBoolean("Is Braking?", false);

    if (signal.robotOriented) {
      // Robot-oriented: convert the speeds from cartesian to polar
      double magnitude = Math.sqrt(signal.xSpeed * signal.xSpeed + signal.ySpeed * signal.ySpeed);
      double direction = Math.toDegrees(Math.atan2(signal.ySpeed, signal.xSpeed));
      robotDrive.mecanumDrive_Polar(magnitude, direction, signal.rotateSpeed);
    } else {
      // Field-oriented
      robotDrive.mecanumDrive_Cartesian(signal.xSpeed, signal.ySpeed, signal.rotateSpeed, gyro.getAngle());
    }
  }

  public void hackPressBrake() {
    hackBrakingLatch = true; // start stopping
  }

  public void hackLiftBrake() {
    hackBrakingLatch = false; // lift the latch
  }

  public static final class Signal {
    final double xSpeed;
    final double ySpeed;
    final double rotateSpeed;
    final boolean robotOriented;

    public Signal(double xSpeed, double ySpeed, double rotateSpeed, boolean robotOriented) {
      this.xSpeed = xSpeed;
      this.ySpeed = ySpeed;
      this.rotateSpeed = rotateSpeed;
      this.robotOriented = robotOriented;
    }

    static Signal createFieldOriented(double xSpeed, double ySpeed, double rotateSpeed) {
      return new Signal(-xSpeed, ySpeed, rotateSpeed, false);
    }

    static Signal createRobotOriented(double speed, double headingDeg, double rotateSpeed) {
      double xSpeed = speed * Math.cos(Math.toRadians(headingDeg));
      double ySpeed = speed * Math.sin(Math.toRadians(headingDeg));
      return new Signal(xSpeed, ySpeed, rotateSpeed, true);
    }
  }
}
