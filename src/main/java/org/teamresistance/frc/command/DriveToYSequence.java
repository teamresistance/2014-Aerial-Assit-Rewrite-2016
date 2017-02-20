package org.teamresistance.frc.command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.strongback.command.CommandGroup;
import org.teamresistance.frc.subsystem.drive.Drive;

/**
 * Created by Tarik Brown on 2/19/2017.
 */
public class DriveToYSequence extends CommandGroup {
  private final Drive drive;
  private final double targetY;
  private final double brakeTime;

  public DriveToYSequence(Drive drive, double targetY, double brakeTime) {
    this.drive = drive;
    this.targetY = targetY;
    this.brakeTime = brakeTime;
    sequentially(
      new DriveToY(drive, targetY),
      new BrakeCommand(drive, brakeTime)
    );
  }
}
