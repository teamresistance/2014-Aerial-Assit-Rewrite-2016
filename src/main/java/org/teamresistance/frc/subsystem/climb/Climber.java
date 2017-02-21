package org.teamresistance.frc.subsystem.climb;

import org.strongback.command.Command;
import org.strongback.command.Requirable;
import org.strongback.components.Motor;
import org.strongback.components.PowerPanel;
import org.strongback.components.Stoppable;
import org.teamresistance.frc.command.climb.ClimbRope;

/**
 * @author Sabrina
 */
public class Climber implements Requirable, Stoppable {
  private static final double CLIMB_SPEED = 1.0;

  private final Motor motor;
  private final PowerPanel pdp;
  private final int channel;

  public Climber(Motor motor, PowerPanel pdp, int channel) {
    this.motor = motor;
    this.pdp = pdp;
    this.channel = channel;
  }

  public Command climbRope(double currentThreshold, double timeThresholdSeconds) {
    return new ClimbRope(this, currentThreshold, timeThresholdSeconds);
  }

  public double getCurrent() {
    return pdp.getCurrent(channel);
  }

  public void startClimbing() {
    motor.setSpeed(CLIMB_SPEED);
  }

  @Override
  public void stop() {
    motor.setSpeed(0);
  }
}
