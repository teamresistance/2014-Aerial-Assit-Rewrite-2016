package org.teamresistance.frc.command;

import org.teamresistance.frc.subsystem.drive.Drive;
import org.teamresistance.frc.subsystem.drive.DriveHoldingAngleController;

/**
 * @author Sabrina
 */
public class TurnToAngle extends DriveCommand {

  public TurnToAngle(Drive drive, double targetDegrees) {
    super(drive, new DriveHoldingAngleController(targetDegrees), false);
  }
}
