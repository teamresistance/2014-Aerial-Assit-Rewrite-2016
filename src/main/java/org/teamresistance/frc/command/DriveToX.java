package org.teamresistance.frc.command;

import org.teamresistance.frc.subsystem.drive.Drive;
import org.teamresistance.frc.subsystem.drive.OpticalFlowXController;

/**
 * @author Tarik C. Brown
 */
public class DriveToX extends DriveCommand {

  public DriveToX(Drive drive, double targetX) {
    super(drive, new OpticalFlowXController(targetX), false);
  }


}
