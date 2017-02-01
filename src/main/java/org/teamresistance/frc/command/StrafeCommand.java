package org.teamresistance.frc.command;

import org.teamresistance.frc.subsystem.drive.Drive;
import org.teamresistance.frc.subsystem.drive.DriveStrafingController;

public class StrafeCommand extends ControllerCommand<Drive, DriveStrafingController, Drive.Signal> {

  public StrafeCommand(Drive drive, double heading, double timeoutSeconds) {
    super(drive, new DriveStrafingController(heading), timeoutSeconds);
  }
}
