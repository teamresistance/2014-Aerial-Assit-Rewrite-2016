package org.teamresistance.frc.command.climb;

import org.strongback.command.Command;
import org.teamresistance.frc.subsystem.climb.Climber;

import edu.wpi.first.wpilibj.Timer;

/**
 * @author Sabrina
 */
public class ClimbRope extends Command {
  private final Climber climber;
  private final Timer timer;
  private final double currentThreshold;
  private final double timeThresholdSeconds;

  public ClimbRope(Climber climber, double currentThreshold, double timeThresholdSeconds) {
    super(climber);
    this.climber = climber;
    this.currentThreshold = currentThreshold;
    this.timeThresholdSeconds = timeThresholdSeconds;
    this.timer = new Timer();
  }

  @Override
  public void initialize() {
    timer.start();
  }

  @Override
  public boolean execute() {
    // Stop climbing only when the current has exceeded the threshold for a minimum period
    if (climber.getCurrent() >= currentThreshold) {
      if (timer.get() >= timeThresholdSeconds) {
        return true;
      }
    } else {
      timer.reset();
    }

    // Keep chugging
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
