package org.teamresistance.frc;

import org.strongback.components.Motor;

/**
 * @author Shreya Ravi
 */
public class SnorfleIn extends Snorfle {

  public SnorfleIn(Motor rollers, Motor spinners) {
    super(rollers, spinners, Constants.ROLLER_IN_SPEED, Constants.SPINNER_IN_SPEED);
  }
}
