package org.teamresistance.frc.command.climb;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import org.strongback.command.Command;
import org.strongback.components.Motor;

/**
 * @author Sabrina
 */
public class ClimbRope extends Command {
  private final Motor climberMotor;
  private final PowerDistributionPanel pdp;
  private double prevTime;
  private double currTime;
  private double elapsedTime;
  private boolean onEnter;
  private final double currentThreshhold;
  private final double timeThreshhold;

  public ClimbRope(Motor climberMotor, PowerDistributionPanel pdp, double currentThreshhold, double timeThreshhold){
    super(climberMotor);
    this.climberMotor = climberMotor;
    this.pdp = pdp;
    onEnter = true;
    elapsedTime = 0.0;
    this.currentThreshhold = currentThreshhold;
    this.timeThreshhold = timeThreshhold;
  }

  @Override
  public boolean execute() {
    if (pdp.getCurrent(5) >= currentThreshhold){
      if (onEnter) {
        prevTime = System.currentTimeMillis();
        onEnter = false;
      } else {
        currTime = System.currentTimeMillis();
        elapsedTime = (currTime - prevTime)/(1000.0);
      }

      if (elapsedTime >= timeThreshhold) {
        return true;
      }

    } else {
      onEnter = true;
      elapsedTime = 0.0;
    }

    climberMotor.setSpeed(1);
    return false;
  }

  @Override
  public void interrupted() {
    end();
  }

  @Override
  public void end() {
    climberMotor.setSpeed(0.0);
  }

}
