package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.strongback.components.Solenoid;
import org.teamresistance.frc.subsystem.GearGrabber.Grabber;

/**
 * @author Sabrina
 */
public class LowerArm extends Command{
  private final Grabber grabber;

  public LowerArm(Grabber grabber){
    super(grabber);
    this.grabber = grabber;
  }

  @Override
  public boolean execute() {
    grabber.lowerArm();
    return true;
  }
}
