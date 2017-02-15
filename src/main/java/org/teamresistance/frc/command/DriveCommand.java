package org.teamresistance.frc.command;

import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.subsystem.drive.Drive;

abstract class DriveCommand extends ControllerCommand<Drive, Controller<Drive.Signal>, Drive.Signal> {

  DriveCommand(Drive subsystem, Controller<Drive.Signal> controller, boolean dontFinish) {
    super(subsystem, controller, dontFinish);
  }

  DriveCommand(Drive subsystem, Controller<Drive.Signal> controller, double timeoutSeconds) {
    super(subsystem, controller, timeoutSeconds);
  }
}
