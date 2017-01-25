package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.teamresistance.frc.IO;

/**
 * @author Tarik C. Brown
 */
public class ShootCommand extends Command {

  // Simple Shoot Command that can be used to start the shooter motors during autonomous
  // You can adjust time and speed of motors (may implement the encoders in the future)
  private static double time;

  public ShootCommand() {
    super(time);
  }

  @Override
  public boolean execute() {
    IO.shooterMotor.setSpeed(1.0);
    IO.agitatorMotor.setSpeed(1.0);
    return false;
  }

  @Override
  public void interrupted() {
    end();
  }

  @Override
  public void end() {
    IO.shooterMotor.setSpeed(0);
    IO.agitatorMotor.setSpeed(0);
  }

}
