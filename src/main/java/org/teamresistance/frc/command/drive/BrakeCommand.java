package org.teamresistance.frc.command.drive;

import org.strongback.command.Command;
import org.teamresistance.frc.subsystem.drive.Drive;

import edu.wpi.first.wpilibj.interfaces.Accelerometer;

public class BrakeCommand extends Command {
  private static final double TOLERANCE = 1; // no idea what the units of this are

  private final Drive drive;
  private final Accelerometer accelerometer; // ignored for now

  public BrakeCommand(Drive drive, Accelerometer accelerometer, double timeoutSeconds) {
    super(timeoutSeconds, drive);
    this.drive = drive;
    this.accelerometer = accelerometer;
  }

  @Override
  public void initialize() {
    drive.hackPressBrake();
  }

  @Override
  public boolean execute() {
    return false; // For now, brake until the timeout expires.

    // FIXME: The following checks cause the command to end almost immediately.
    //return Math.abs(accelerometer.getX()) < TOLERANCE
    //    && Math.abs(accelerometer.getY()) < TOLERANCE;
  }

  @Override
  public void interrupted() {
    end();
  }

  @Override
  public void end() {
    drive.hackLiftBrake();
  }
}
