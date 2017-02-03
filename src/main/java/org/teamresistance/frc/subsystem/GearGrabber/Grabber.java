package org.teamresistance.frc.subsystem.GearGrabber;

import org.strongback.command.Requirable;
import org.strongback.components.Solenoid;

/**
 * @author Sabrina
 */
public class Grabber implements Requirable{
  private final Solenoid grabSolenoid;
  private final Solenoid armSolenoid;

  public Grabber(Solenoid grabSolenoid, Solenoid armSolenoid){
    this.grabSolenoid = grabSolenoid;
    this.armSolenoid = armSolenoid;
  }

  public void grab(){
    grabSolenoid.extend();
  }

  public void release(){
    grabSolenoid.retract();
  }

  public void lowerArm(){
    grabSolenoid.extend();
  }

  public void liftArm(){
    armSolenoid.retract();
  }

}
