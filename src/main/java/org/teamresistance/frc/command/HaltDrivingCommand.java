package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.teamresistance.frc.subsystem.drive.Drive;

public class HaltDrivingCommand extends Command {
  private final Drive drive;

  public HaltDrivingCommand(Drive drive, double seconds) {
    super(seconds, drive);
    this.drive = drive;
  }

  @Override
  public void initialize() {
    drive.hackPressBrake();
  }

  @Override
  public boolean execute() {
    return false; // Doesn't matter because this is a timed command
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
