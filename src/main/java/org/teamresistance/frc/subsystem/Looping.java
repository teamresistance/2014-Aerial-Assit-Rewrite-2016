package org.teamresistance.frc.subsystem;

import org.teamresistance.frc.Pose;

/**
 * TODO: Documentation (high priority)
 * @author Rothanak So
 */
@FunctionalInterface
public interface Looping {

  void onUpdate(Pose pose);
}
