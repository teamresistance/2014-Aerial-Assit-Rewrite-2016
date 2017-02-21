package org.teamresistance.frc.util.testing;

import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.components.ui.FlightStick;

class CommandTesting {
  static final SwitchReactor reactor = Strongback.switchReactor();
  final FlightStick joystickA;
  final FlightStick joystickB;
  final FlightStick joystickC;

  CommandTesting(FlightStick joystickA, FlightStick joystickB, FlightStick joystickC) {
    this.joystickA = joystickA;
    this.joystickB = joystickB;
    this.joystickC = joystickC;
  }
}
