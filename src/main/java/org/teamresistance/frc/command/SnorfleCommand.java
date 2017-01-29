package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.strongback.components.Motor;

/**
 * @author Tarik C. Brown
 */
public class SnorfleCommand extends Command {
  public final double snorfspeed;
  public Motor snorfleMotor;

  public SnorfleCommand(Motor snorfleMotor, double snorfspeed) {
    super(snorfleMotor);
    this.snorfspeed = snorfspeed;
    this.snorfleMotor = snorfleMotor;
  }

  @Override
  public boolean execute() {
    snorfleMotor.setSpeed(snorfspeed);
    return false;
  }
  @Override
  public void interrupted() {
    snorfleMotor.setSpeed(0.0);
  }
}
