package org.teamresistance.frc.command;

import org.strongback.command.CommandGroup;
import org.teamresistance.frc.subsystem.drive.Drive;

/**
 * Created by shrey on 1/29/2017.
 */
public class DriveToYX extends CommandGroup {

  private final Drive drive;
  private final double timeY;
  private final double targetY;
  private final double timeX;
  private final double targetX;


  public DriveToYX(Drive drive, double timeY, double targetY, double timeX, double targetX) {
    this.drive = drive;
    this.timeY = timeY;
    this.targetY = targetY;
    this.timeX = timeX;
    this.targetX = targetX;
    sequentially(new DriveToY(drive, timeY, targetY), new DriveToX(drive, timeX, targetX));
  }

}
