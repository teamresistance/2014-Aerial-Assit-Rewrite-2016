package org.teamresistance.frc.command.grabber;


import org.strongback.command.Command;
import org.teamresistance.frc.InvertibleSolenoid;

/**
 * Created by shrey on 2/7/2017.
 */
public class GrabGear extends Command {

  private final InvertibleSolenoid gripSolenoid;

  public GrabGear(double timeLimit, InvertibleSolenoid gripSolenoid) {
    super(timeLimit, gripSolenoid);
    this.gripSolenoid = gripSolenoid;
  }

  @Override
  public boolean execute() {
    gripSolenoid.retract();
    return false;
  }

}
