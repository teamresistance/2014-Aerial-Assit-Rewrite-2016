package org.teamresistance.frc.util.testing;

class JoystickMap {
  static final int UNASSIGNED = -1;

  static class LeftJoystick {
    // Stick
    static final int ANGLE_HOLD = 1;
    static final int CANCEL = 2;
    static final int ANGLE_HOLD_A = 4;
    static final int ANGLE_HOLD_B = 3;
    static final int ANGLE_HOLD_C = 5;

    // Base
    static final int NAVX_RESET = 6;
    static final int AUTO_TIMED_HOPPER = 7;
  }

  static class RightJoystick {
    // Empty
  }
}
