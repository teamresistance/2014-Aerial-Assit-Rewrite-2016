package org.teamresistance.frc.subsystem.shooter;

import org.strongback.command.Command;
import org.strongback.command.CommandGroup;

public final class FireShooter extends CommandGroup {

  public FireShooter(Shooter shooter) {
    // TODO: in Robot.java, remember to only dispatch if the ball sensor returns true.
    sequentially(
        Command.create(Shooter.CHARGE_UP_TIME, shooter::chargeUp),
        Command.create(shooter::discharge),
        Command.create(Shooter.RELOAD_TIME, shooter::reload)
    );
  }
}
