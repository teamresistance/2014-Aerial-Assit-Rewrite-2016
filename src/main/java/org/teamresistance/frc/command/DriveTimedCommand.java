package org.teamresistance.frc.command;

import org.teamresistance.frc.subsystem.drive.Drive;
import org.teamresistance.frc.subsystem.drive.DriveStrafingController;

public class DriveTimedCommand extends DriveCommand {

  public DriveTimedCommand(Drive drive, double orientation, double headingDeg, double timeoutSeconds) {
    super(drive, new DriveStrafingController(orientation, headingDeg), timeoutSeconds);
  }
}
