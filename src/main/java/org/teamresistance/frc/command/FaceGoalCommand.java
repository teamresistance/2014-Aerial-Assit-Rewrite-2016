package org.teamresistance.frc.command;

import org.strongback.command.Command;
import org.strongback.command.Requirable;
import org.teamresistance.frc.sensor.goal.GoalSensor;
import org.teamresistance.frc.subsystem.drive.Drive;
import org.teamresistance.frc.subsystem.drive.DriveFacingGoalController;

/**
 * A command that forever turns the robot to face the goal.
 * <p>
 * Requires a lease on the {@link Drive} subsystem. The only way to finish this command is to cancel
 * it (see: {@link Command#cancel(Requirable...)} or interrupt it by submitting another command that
 * requires a lease on {@link Drive}.
 *
 * @author Rothanak So
 * @see GoalSensor
 */
public class FaceGoalCommand extends Command {
  private final Drive drive;
  private final DriveFacingGoalController controller;

  public FaceGoalCommand(Drive drive) {
    this.drive = drive;
    this.controller = new DriveFacingGoalController();
  }

  @Override
  public void initialize() {
    drive.setController(controller);
  }

  @Override
  public boolean execute() {
    return controller.isOnTarget();
  }

  @Override
  public void interrupted() {
    this.end();
  }

  @Override
  public void end() {
    drive.setOpenLoop();
  }
}
