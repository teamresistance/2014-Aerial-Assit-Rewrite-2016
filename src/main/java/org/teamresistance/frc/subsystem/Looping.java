package org.teamresistance.frc.subsystem;

import org.teamresistance.frc.Feedback;

/**
 * TODO: Documentation (high priority)
 * @author Rothanak So
 */
@FunctionalInterface
public interface Looping {

  void onUpdate(Feedback feedback);
}
