package org.teamresistance.frc.command;
import org.strongback.command.Command;
import org.teamresistance.frc.subsystem.drive.Drive;
import org.teamresistance.frc.subsystem.drive.DriveToYController;

/**
 * Created by shrey on 1/25/2017.
 */
public class DriveToY extends Command {

  private final DriveToYController controller;
  private final Drive drive;

  public DriveToY(Drive drive, double time, double targetY) {
    super(time, drive);
    this.drive = drive;
    this.controller = new DriveToYController(targetY);
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
    end();
  }

  @Override
  public void end() {
    drive.stop();
  }
}
