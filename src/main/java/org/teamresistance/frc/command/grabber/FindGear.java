package org.teamresistance.frc.command.grabber;

import org.strongback.command.Command;
import org.teamresistance.frc.InvertibleDigitalInput;

/**
 * Created by shrey on 2/7/2017.
 */
public class FindGear extends Command {

  private final InvertibleDigitalInput gearPresentBannerSensor;

  public FindGear(InvertibleDigitalInput gearPresentBannerSensor) {
    super(gearPresentBannerSensor);
    this.gearPresentBannerSensor = gearPresentBannerSensor;
  }

  @Override
  public boolean execute() {
    return gearPresentBannerSensor.get();
  }

}
