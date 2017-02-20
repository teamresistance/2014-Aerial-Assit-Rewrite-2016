package org.teamresistance.frc.command;

import org.strongback.command.CommandGroup;
import org.teamresistance.frc.subsystem.drive.Drive;

/**
 * @author Tarik Brown
 */
public class DriveOpticalYBraking extends CommandGroup {

  public DriveOpticalYBraking(Drive drive, double targetY, double brakeTime) {
    sequentially(
      new DriveOpticalY(drive, targetY),
      new BrakeCommand(drive, brakeTime)
    );
  }
}
