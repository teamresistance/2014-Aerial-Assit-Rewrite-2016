package org.teamresistance.frc.subsystem.shooter;

import org.strongback.command.Command;
import org.strongback.command.CommandGroup;
import org.teamresistance.frc.subsystem.TimedCommand;

public final class FireShooter extends CommandGroup {

  public FireShooter(Shooter shooter) {
    // TODO: in Robot.java, remember to only dispatch if the ball sensor returns true.
    sequentially(
        new ChargeUp(shooter),
        new Discharge(shooter),
        new Reload(shooter)
    );
  }

  private final class ChargeUp extends TimedCommand {
    private final Shooter shooter;

    private ChargeUp(Shooter shooter) {
      super(Shooter.CHARGE_UP_TIME, shooter);
      this.setNotInterruptible();
      this.shooter = shooter;
    }

    @Override
    protected void executeUntilTimeout() {
      shooter.chargeUp();
    }
  }

  private final class Discharge extends Command {
    private final Shooter shooter;

    private Discharge(Shooter shooter) {
      this.shooter = shooter;
    }

    @Override
    public boolean execute() {
      shooter.discharge();
      return true; // immediately finish
    }
  }

  private final class Reload extends TimedCommand {
    private final Shooter shooter;

    private Reload(Shooter shooter) {
      super(Shooter.RELOAD_TIME, shooter);
      this.setNotInterruptible();
      this.shooter = shooter;
    }

    @Override
    protected void executeUntilTimeout() {
      shooter.reload();
    }
  }
}
