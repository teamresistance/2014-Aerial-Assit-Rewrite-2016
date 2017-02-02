package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.strongback.command.Requirable;
import org.teamresistance.frc.subsystem.ClosedLooping;
import org.teamresistance.frc.subsystem.Controller;

/**
 * This abstract class makes it easy to create a {@link Command} that sets a {@link Controller} on a
 * subsystem by managing the lifecycle of the controller.
 *
 * @author Rothanak So
 * @see HoldAngleCommand
 * @see StrafeCommand
 */
// Human-readable version: class ControllerCommand<Subsystem, Controller, Signal> extends Command
abstract class ControllerCommand<T extends ClosedLooping<V> & Requirable, U extends
    Controller<V>, V> extends Command {
  private final T subsystem;
  private final U controller;
  private final boolean dontFinish;

  ControllerCommand(T subsystem, U controller, boolean dontFinish) {
    super(subsystem);
    this.subsystem = subsystem;
    this.controller = controller;
    this.dontFinish = dontFinish;
  }

  ControllerCommand(T subsystem, U controller, double timeoutSeconds) {
    super(timeoutSeconds, subsystem);
    this.subsystem = subsystem;
    this.controller = controller;
    this.dontFinish = false;
  }

  @Override
  public void initialize() {
    subsystem.setController(controller);
  }

  @Override
  public boolean execute() {
    // Finish the command only if the controller is on target and the command isn't set to run
    // indefinitely. If the Command has a timeout, Strongback will ignore the return value.
    return !dontFinish && controller.isOnTarget();
  }

  @Override
  public void interrupted() {
    end();
  }

  @Override
  public void end() {
    // Hand back operator control
    subsystem.setOpenLoop();
  }
}
