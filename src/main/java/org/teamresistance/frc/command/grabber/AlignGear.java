package org.teamresistance.frc.command.grabber;

import org.strongback.command.Command;
import org.strongback.components.Motor;
import org.teamresistance.frc.InvertibleDigitalInput;

/**
 * Created by shrey on 2/7/2017.
 */
public class AlignGear extends Command {

  private final Motor rotateGearMotor;
  private final InvertibleDigitalInput gearAlignBannerSensor;

  public AlignGear(Motor rotateGear, InvertibleDigitalInput gearAlignBannerSensor) {
    super(rotateGear, gearAlignBannerSensor);
    this.rotateGearMotor = rotateGear;
    this.gearAlignBannerSensor = gearAlignBannerSensor;
  }

  @Override
  public boolean execute() {
    rotateGearMotor.setSpeed(0.1);
    return gearAlignBannerSensor.get();
  }

  public void interrupted() {
    end();
  }

  public void end() {
    rotateGearMotor.setSpeed(0);
  }

}
