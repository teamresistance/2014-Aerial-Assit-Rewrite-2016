package org.teamresistance.frc.command.grabber;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.strongback.command.Command;
import org.teamresistance.frc.InvertibleSolenoidWithPosition;
import org.teamresistance.frc.subsystem.grabber.Grabber;

/**
 * Created by shrey on 2/6/2017.
 */
public class GearRetract extends Command {

  private final InvertibleSolenoidWithPosition extendSolenoid;

  public GearRetract(InvertibleSolenoidWithPosition extendSolenoid) {
    super(extendSolenoid);
    this.extendSolenoid = extendSolenoid;
  }

  @Override
  public boolean execute() {
    extendSolenoid.retract();
    SmartDashboard.putBoolean("Gear Retracted", extendSolenoid.isRetracted());
    return extendSolenoid.isRetracted();
  }

}
