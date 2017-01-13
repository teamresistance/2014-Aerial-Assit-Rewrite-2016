package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.strongback.command.Requirable;
import org.teamresistance.frc.subsystem.drive.Drive;
import org.teamresistance.frc.subsystem.drive.DriveHoldingAngleController;

/**
 * A command that turns the robot toward the target angle and attempts to hold it indefinitely.
 * Requires a lease on the {@link Drive} subsystem. The only way to finish this command is to cancel
 * it (see: {@link Command#cancel(Requirable...)} or interrupt it by submitting another command that
 * requires a lease on {@link Drive}.
 *
 * @author Rothanak So
 */
public class HoldAngleCommand extends Command {
  private final Drive drive;
  private final DriveHoldingAngleController controller;

  public HoldAngleCommand(Drive drive, double targetAngle) {
    super(drive);
    this.drive = drive;
    this.controller = new DriveHoldingAngleController(targetAngle);
  }

  @Override
  public void initialize() {
    drive.setController(controller);
  }

  @Override
  public boolean execute() {
    return false; // Unlike a TurnToAngle command, we want HoldAngle to run indefinitely.
  }

  @Override
  public void interrupted() {
    end();
  }

  @Override
  public void end() {
    drive.setOpenLoop();
  }
}
