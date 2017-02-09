package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.teamresistance.frc.subsystem.drive.DriveHoldingAngleController;
import org.teamresistance.frc.subsystem.drive.Drive;
import org.teamresistance.frc.subsystem.drive.DriveHoldingAngleController;

/**
 * @author Sabrina
 */
public class TurnToAngle extends Command {
  private final Drive drive;
  private final DriveHoldingAngleController controller;

  public TurnToAngle(Drive drive, double targetAngle) {
    this.drive = drive;
    this.controller = new DriveHoldingAngleController(targetAngle);
  }

  @Override
  public void initialize() {
    drive.setController(controller);
  }

  @Override
  public boolean execute() {
    return controller.isOnTarget();
  }

  @Override
  public void end() {
    drive.stop();
  }
}
