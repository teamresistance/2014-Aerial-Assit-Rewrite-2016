package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.teamresistance.frc.subsystem.drive.Drive;

public class DiagonalCommand extends Command {
  private final Drive drive;

  public DiagonalCommand(Drive drive, double timeDiagonalSeconds) {
    super(timeDiagonalSeconds, drive);
    this.drive = drive;
  }

  @Override
  public void initialize() {
    drive.hackDiagonalStart();
  }

  @Override
  public boolean execute() {
    return false; // For now, strafe until the timeout expires.
  }

  @Override
  public void interrupted() {
    end();
  }

  @Override
  public void end() {
    drive.hackDiagonalStop();
  }
}
