package org.teamresistance.frc;

import org.strongback.annotation.Experimental;
import org.strongback.command.Command;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.subsystem.Looping;

/**
 * A superstructure that represents the entire physical state of the robot at the given tick, such
 * as its current heading and relative position. It helps ensure all operations within the same tick
 * operate on the same sensor readings: we would propagate the current tick's {@link Feedback} rather
 * than the sensor objects directly.
 * <p>
 * A new {@link Feedback} with the latest sensor readings should be created on every tick/update. The
 * looper is responsible for passing the {@link Feedback} to its dependent {@link Looping} subsystems
 * (and anything else that requires the {@link Feedback}).
 * <p>
 * This plays nicely with the {@link Controller}s by preserving their purity as functions whose
 * outputs are solely defined by their inputs, thus enabling their composition. For higher-level
 * {@link Command}s, it may be preferable to simply propagate the sensor objects directly.
 *
 * @author Rothanak So
 */
@Experimental
public final class Feedback {
  public final double currentAngle;
  public final double xVal;
  public final double yVal;

  Feedback(double currentAngle, double xVal, double yVal) {
    this.currentAngle = currentAngle;
    this.xVal = xVal;
    this.yVal = yVal;
  }
}
