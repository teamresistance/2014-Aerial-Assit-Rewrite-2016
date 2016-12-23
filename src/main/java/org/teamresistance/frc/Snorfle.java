package org.teamresistance.frc;

import org.strongback.command.Command;
import org.strongback.components.Motor;

/**
 * @author Shreya Ravi
 */
public class Snorfle extends Command {

  Motor rollers;
  Motor spinners;
  double rollerSpeed;
  double spinnerSpeed;

  public Snorfle(Motor rollers, Motor spinners, double rollerSpeed, double spinnerSpeed) {
    this.rollers = rollers;
    this.spinners = spinners;
    this.rollerSpeed = rollerSpeed;
    this.spinnerSpeed = spinnerSpeed;
  }

  @Override
  public boolean execute() {
    rollers.setSpeed(rollerSpeed);
    spinners.setSpeed(spinnerSpeed);
    return false;
  }

  @Override
  public void interrupted() {
    rollers.setSpeed(Constants.ROLLER_STOP_SPEED);
    spinners.setSpeed(Constants.SPINNER_STOP_SPEED);
  }
}
