package org.teamresistance.frc;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import org.strongback.command.Requirable;

/**
 * Created by shrey on 2/6/2017.
 */
public class InvertibleSolenoidWithPosition extends InvertibleSolenoid {

  private final InvertibleDigitalInput retractedLimit;

  public InvertibleSolenoidWithPosition(
      int channel,
      boolean isSolenoidInverted,
      InvertibleDigitalInput retractedLimit) {
    super(channel, isSolenoidInverted);
    this.retractedLimit = retractedLimit;
  }

  @Override
  public boolean isRetracted() {
    return retractedLimit.get();
  }
}
