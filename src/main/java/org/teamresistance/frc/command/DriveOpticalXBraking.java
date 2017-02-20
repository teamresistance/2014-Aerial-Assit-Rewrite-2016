package org.teamresistance.frc.command;

import org.strongback.command.CommandGroup;
import org.teamresistance.frc.subsystem.drive.Drive;

/**
 * @author Tarik Brown
 */
public class DriveOpticalXBraking extends CommandGroup {

  public DriveOpticalXBraking(Drive drive, double targetX, double diagonalTime) {
    sequentially(
        new DriveOpticalX(drive, targetX),
        new BrakeCommand(drive, diagonalTime)
    );
  }
}
