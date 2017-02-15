package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.teamresistance.frc.subsystem.snorfler.Snorfler;

/**
 * @author Tarik C. Brown
 */
public class SnorfleReverseCommand extends Command {
  private final Snorfler snorfler;

  public SnorfleReverseCommand(Snorfler snorfler) {
    this.snorfler = snorfler;
  }

  @Override
  public boolean execute() {
    snorfler.startReversing();
    return false;
  }

  @Override
  public void interrupted() {
    end();
  }

  @Override
  public void end() {
    snorfler.stop();
  }
}
