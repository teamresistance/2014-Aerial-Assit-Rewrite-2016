package org.teamresistance.frc.command.climb;

import org.strongback.command.Command;
import org.teamresistance.frc.subsystem.climb.Climber;

/**
 * @author Sabrina
 */
public class ClimbRope extends Command {
  private final Climber climber;
  private double prevTime;
  private double currTime;
  private double elapsedTime;
  private boolean onEnter;
  private final double currentThreshold;
  private final double timeThreshold;

  public ClimbRope(Climber climber, double currentThreshold, double timeThreshold) {
    super(climber);
    this.climber = climber;
    this.currentThreshold = currentThreshold;
    this.timeThreshold = timeThreshold;
    this.onEnter = true;
    this.elapsedTime = 0.0;
  }

  @Override
  public boolean execute() {
    if (climber.getCurrent() >= currentThreshold) {
      if (onEnter) {
        prevTime = System.currentTimeMillis();
        onEnter = false;
      } else {
        currTime = System.currentTimeMillis();
        elapsedTime = (currTime - prevTime) / (1000.0);
      }

      if (elapsedTime >= timeThreshold) {
        return true;
      }

    } else {
      onEnter = true;
      elapsedTime = 0.0;
    }

    climber.startClimbing();
    return false;
  }

  @Override
  public void interrupted() {
    end();
  }

  @Override
  public void end() {
    climber.stop();
  }
}
