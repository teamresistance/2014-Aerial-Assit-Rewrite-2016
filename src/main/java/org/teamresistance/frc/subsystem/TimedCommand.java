package org.teamresistance.frc.subsystem;

import org.strongback.command.Command;
import org.strongback.command.Requirable;

public abstract class TimedCommand extends Command {

  protected TimedCommand(double timeoutInSeconds, Requirable... requirements) {
    super(timeoutInSeconds, requirements);
  }

  @Override
  public final boolean execute() {
    executeUntilTimeout();
    return false;
  }

  protected abstract void executeUntilTimeout();
}
