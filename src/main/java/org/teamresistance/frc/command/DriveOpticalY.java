package org.teamresistance.frc.command;

import org.teamresistance.frc.subsystem.drive.Drive;
import org.teamresistance.frc.subsystem.drive.OpticalFlowYController;

/**
 * Created by Tarik Brown on 2/19/2017.
 */
public class DriveOpticalY extends DriveCommand {

  public DriveOpticalY(Drive drive, double targetY) {
    super(drive, new OpticalFlowYController(targetY), false);
  }
}
