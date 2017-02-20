package org.teamresistance.frc.command;

import org.teamresistance.frc.OpticalFlow;
import org.teamresistance.frc.command.DriveToX;
import org.teamresistance.frc.command.DriveToY;
import org.teamresistance.frc.subsystem.drive.Drive;
import org.teamresistance.frc.subsystem.drive.OpticalFlowXYController;

/**
 * Created by Tarik Brown on 2/19/2017.
 */
public class DriveOfsXY extends DriveCommand {
  private final OpticalFlow opticalFlow;

  public DriveOfsXY(Drive drive, OpticalFlow opticalFlow, double targetXSeq, double targetYSeq) {
    super(drive, new OpticalFlowXYController(targetXSeq, targetYSeq), false);
    this.opticalFlow = opticalFlow;
  }

  @Override
  public boolean execute() {
    opticalFlow.update();
    return super.execute();
  }
}

