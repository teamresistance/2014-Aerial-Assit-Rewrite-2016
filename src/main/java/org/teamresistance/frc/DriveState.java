package org.teamresistance.frc;

import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.subsystem.drive.Drive;

/**
 * @author Shreya Ravi
 * @deprecated Superseded by {@link Controller}s passed via {@link Drive#setController}.
 */
public enum DriveState {
  OP_CONTROL,
  STOP;
}
