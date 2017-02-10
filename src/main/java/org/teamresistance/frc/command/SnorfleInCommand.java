package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.strongback.components.Motor;

/**
 * @author Tarik C. Brown
 */
public class SnorfleInCommand extends Command {
  public final double snorfSpeed;
  public final Motor snorfleMotor;
  public final double stopSnorf;

  public SnorfleInCommand(double snorfspeed, Motor snorfleMotor, double stopSnorf) {
    this.snorfSpeed = snorfspeed;
    this.snorfleMotor = snorfleMotor;
    this.stopSnorf = stopSnorf;
  }

  @Override
  public boolean execute() {
    snorfleMotor.setSpeed(snorfSpeed);
    return false;
  }
  @Override
  public void interrupted() {
    snorfleMotor.setSpeed(stopSnorf);

  }
}
