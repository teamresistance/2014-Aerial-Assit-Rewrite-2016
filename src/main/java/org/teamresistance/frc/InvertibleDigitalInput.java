package org.teamresistance.frc;

import edu.wpi.first.wpilibj.DigitalInput;
import org.strongback.command.Requirable;

/**
 * Created by shrey on 2/8/2017.
 */
public class InvertibleDigitalInput implements Requirable {

  private final DigitalInput limitSwitch;
  private final boolean isInverted;

  public InvertibleDigitalInput(int channel, boolean isInverted) {
    this.isInverted = isInverted;
    limitSwitch = new DigitalInput(channel);
  }

  public boolean get() {
    if (isInverted) {
      return !limitSwitch.get();
    }
    return limitSwitch.get();
  }

}
