package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.teamresistance.frc.subsystem.drive.Drive;

public class BrakeCommand extends Command {
  private final Drive drive;

  public BrakeCommand(Drive drive, double timeoutSeconds) {
    super(timeoutSeconds, drive);
    this.drive = drive;
  }

  @Override
  public void initialize() {
    drive.hackPressBrake();
  }

  @Override
  public boolean execute() {
    return false; // For now, brake until the timeout expires.
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
