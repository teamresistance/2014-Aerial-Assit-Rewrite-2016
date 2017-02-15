package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.teamresistance.frc.subsystem.snorfler.Snorfler;

/**
 * @author Tarik C. Brown
 */
public class SnorfleInCommand extends Command {
  private final Snorfler snorfler;

  public SnorfleInCommand(Snorfler snorfler) {
    this.snorfler = snorfler;
  }

  @Override
  public boolean execute() {
    snorfler.startSnorfling();
    return false;
  }

  @Override
  public void interrupted() {
    // TODO
  }
}
