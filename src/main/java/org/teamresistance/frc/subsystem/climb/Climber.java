package org.teamresistance.frc.subsystem.climb;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import org.strongback.command.Command;
import org.strongback.components.Motor;
import org.teamresistance.frc.command.climb.ClimbRope;

/**
 * @author Sabrina
 */
public class Climber {

  private final Motor climberMotor;
  private final PowerDistributionPanel pdp;
  private final int channel;

  public Climber(Motor climberMotor, PowerDistributionPanel pdp, int channel) {
    this.climberMotor = climberMotor;
    this.pdp = pdp;
    this.channel = channel;
  }

  public Command climbRope(double currentThreshold, double timeThreshold) {
    return new ClimbRope(climberMotor, pdp, currentThreshold, timeThreshold, channel);
  }
}
