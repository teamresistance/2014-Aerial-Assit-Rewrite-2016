package org.teamresistance.frc;

import org.strongback.components.Motor;

/**
 * @author Shreya Ravi
 */
public class SnorfleOut extends Snorfle {

  public SnorfleOut(Motor rollers, Motor spinners) {
    super(rollers, spinners, Constants.ROLLER_OUT_SPEED, Constants.SPINNER_OUT_SPEED);
  }
}
