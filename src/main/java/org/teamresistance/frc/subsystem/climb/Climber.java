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

  public Climber(Motor climberMotor, PowerDistributionPanel pdp) {
    this.climberMotor = climberMotor;
    this.pdp = pdp;
  }

  public Command climbRope() {
    return new ClimbRope(climberMotor, pdp, 40, .5);
  }
}
