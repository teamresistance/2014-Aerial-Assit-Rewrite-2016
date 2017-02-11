package org.teamresistance.frc.command.drive;

import org.strongback.command.CommandGroup;
import org.teamresistance.frc.subsystem.drive.Drive;

/**
 * Created by shrey on 1/29/2017.
 */
public class DriveToYX extends CommandGroup {

  private final Drive drive;
  private final double timeOutY;
  private final double targetY;
  private final double timeOutX;
  private final double targetX;


  public DriveToYX(Drive drive, double timeOutY, double targetY, double timeOutX, double targetX) {
    this.drive = drive;
    this.timeOutY = timeOutY;
    this.targetY = targetY;
    this.timeOutX = timeOutX;
    this.targetX = targetX;
    sequentially(new DriveToY(drive, timeOutY, targetY), new DriveToX(drive, timeOutX, targetX));
  }

}
