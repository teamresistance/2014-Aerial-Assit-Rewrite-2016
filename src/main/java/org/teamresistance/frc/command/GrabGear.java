package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.strongback.components.Solenoid;
import org.teamresistance.frc.subsystem.GearGrabber.Grabber;

/**
 * @author Sabrina
 */
public class GrabGear extends Command{
  private final Grabber grabber;

  public GrabGear(Grabber grabber){
    super(grabber);
    this.grabber = grabber;
  }

  @Override
  public boolean execute() {
    grabber.grab();
    return true;
  }

}
