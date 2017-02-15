package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.teamresistance.frc.subsystem.snorfler.Snorfler;

/**
 * @author Tarik C. Brown
 */
public class SnorfleOutCommand extends Command {
  private final Snorfler snorfler;

  public SnorfleOutCommand(Snorfler snorfler) {
    this.snorfler = snorfler;
  }

  @Override
  public boolean execute() {
    snorfler.reverseSnorfling();
    return false;
  }

  @Override
  public void interrupted() {
    // TODO
  }
}
