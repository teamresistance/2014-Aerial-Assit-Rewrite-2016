package org.teamresistance.frc.command;

import org.teamresistance.frc.subsystem.drive.Drive;
import org.teamresistance.frc.subsystem.drive.OpticalFlowXYController;

/**
 * Created by Tarik Brown on 2/19/2017.
 */
public class DriveOpticalXY extends DriveCommand {

  public DriveOpticalXY(Drive drive, double targetXSeq, double targetYSeq) {
    super(drive, new OpticalFlowXYController(targetXSeq, targetYSeq), false);
  }
}
