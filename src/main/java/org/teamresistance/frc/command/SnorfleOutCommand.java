package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.strongback.components.Motor;

/**
 * @author Tarik C. Brown
 */
public class SnorfleOutCommand extends Command {
  public final double snorfspeed;
  public final Motor snorfleMotor;
  public final double stopSnorf;
  public SnorfleOutCommand(double snorfspeed, Motor snorfleMotor, double stopSnorf) {
    this.snorfspeed = snorfspeed;
    this.snorfleMotor = snorfleMotor;
    this.stopSnorf = stopSnorf;
  }

  @Override
  public boolean execute() {
    snorfleMotor.setSpeed(snorfspeed);
    return false;
  }
  @Override
  public void interrupted() {
    snorfleMotor.setSpeed(stopSnorf);
  }
}
