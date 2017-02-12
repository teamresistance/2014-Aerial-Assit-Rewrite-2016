package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.teamresistance.frc.subsystem.snorfler.Snorfler;

/**
 * Created by owner on 2/12/2017.
 */
public class SnorfleStopCommand extends Command {
  public final Snorfler snorfler;

  public SnorfleStopCommand(Snorfler snorfler) {
    this.snorfler = snorfler;
  }

  @Override
  public boolean execute() {
    snorfler.stopSnorfling();
    return false;
  }

}
