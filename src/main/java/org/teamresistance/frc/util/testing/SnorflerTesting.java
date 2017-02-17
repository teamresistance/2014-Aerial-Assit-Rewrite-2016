package org.teamresistance.frc.util.testing;

import org.strongback.components.ui.FlightStick;
import org.teamresistance.frc.IO;
import org.teamresistance.frc.command.SnorfleReverseCommand;
import org.teamresistance.frc.command.SnorfleStopReversingCommand;
import org.teamresistance.frc.command.SnorfleToggleCommand;
import org.teamresistance.frc.subsystem.snorfler.Snorfler;

public class SnorflerTesting extends CommandTesting {
  private final Snorfler snorfler;

  public SnorflerTesting(Snorfler snorfler, FlightStick joystickA, FlightStick joystickB) {
    super(joystickA, joystickB);
    this.snorfler = snorfler;
  }
  
  public void enableAllSnorflerTests() {
    // Press to toggle the snorfler forward/off
    reactor.onTriggered(joystickB.getButton(11), () -> IO.snorflerMotor.setSpeed(1.0));
    reactor.onUntriggered(joystickB.getButton(11), () -> IO.snorflerMotor.setSpeed(0.0));

    // Press and hold to reverse the snorfler
    reactor.onTriggered(joystickB.getButton(10), () -> IO.snorflerMotor.setSpeed(-1.0));
    reactor.onUntriggered(joystickB.getButton(10), () -> IO.snorflerMotor.setSpeed(0.0));
  }
}
