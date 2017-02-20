package org.teamresistance.frc.command;

import org.strongback.command.CommandGroup;
import org.teamresistance.frc.subsystem.drive.Drive;

/**
 * Created by Tarik Brown on 2/19/2017.
 */
public class DriveToXSequence extends CommandGroup {
  private final Drive drive;
  private final double targetX;
  private final double brakeTime;

  public DriveToXSequence(Drive drive, double targetX, double brakeTime) {
    this.drive = drive;
    this.targetX= targetX;
    this.brakeTime = brakeTime;
    sequentially(
        new DriveToY(drive, targetX),
        new BrakeCommand(drive, brakeTime)
    );
  }
}
