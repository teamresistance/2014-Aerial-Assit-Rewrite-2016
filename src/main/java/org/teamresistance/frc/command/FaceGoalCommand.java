package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.teamresistance.frc.subsystem.drive.Drive;
import org.teamresistance.frc.subsystem.drive.FaceGoalController;

public class FaceGoalCommand extends Command {
  private final Drive drive;
  private final FaceGoalController controller;

  public FaceGoalCommand(Drive drive) {
    this.drive = drive;
    this.controller = new FaceGoalController();
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
  public void interrupted() {
    this.end();
  }

  @Override
  public void end() {
    drive.setOpenLoop();
  }
}
