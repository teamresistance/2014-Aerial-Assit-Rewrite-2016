package org.teamresistance.frc.command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.strongback.command.Command;
import org.teamresistance.frc.subsystem.drive.Drive;
import org.teamresistance.frc.subsystem.drive.OpticalFlowController;

/**
 * Created by shrey on 1/28/2017.
 */
public class DriveToX extends Command {

  private final OpticalFlowController controller;
  private final Drive drive;

  public DriveToX(Drive drive, double targetX) {
    super(drive);
    this.drive = drive;
    this.controller = new OpticalFlowController(targetX);
  }
  @Override
  public void initialize() {
    drive.setController(controller);
  }
  @Override
  public boolean execute() {
    boolean onTarget = controller.isOnTarget();
    SmartDashboard.putBoolean("DriveToX on Target?", onTarget);
    return onTarget;
  }

  @Override
  public void interrupted() {
    end();
  }

  @Override
  public void end() {
    drive.setOpenLoop();
  }
}
