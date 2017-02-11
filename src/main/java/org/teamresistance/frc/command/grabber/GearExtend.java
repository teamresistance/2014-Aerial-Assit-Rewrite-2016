package org.teamresistance.frc.command.grabber;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.strongback.command.Command;
import org.teamresistance.frc.InvertibleSolenoidWithPosition;

/**
 * Created by shrey on 2/7/2017.
 */
public class GearExtend extends Command {

  private final InvertibleSolenoidWithPosition extendSolenoid;

  public GearExtend(double timeLimit, InvertibleSolenoidWithPosition extendSolenoid) {
    super(timeLimit, extendSolenoid);
    this.extendSolenoid = extendSolenoid;
  }

  @Override
  public boolean execute() {
    extendSolenoid.extend();
    SmartDashboard.putBoolean("Gear Extended", extendSolenoid.isExtended());
    return false;
  }

}
