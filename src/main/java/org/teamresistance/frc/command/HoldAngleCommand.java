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
public class HoldAngleCommand extends DriveCommand {

  public HoldAngleCommand(Drive drive, double targetAngle) {
    // Unlike a TurnToAngle command, we want HoldAngle to run indefinitely.
    super(drive, new DriveHoldingAngleController(targetAngle), true);
  }
}
