package org.teamresistance.frc.util.testing;

import org.strongback.components.ui.FlightStick;
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
    reactor.onTriggeredSubmit(joystickB.getButton(6), () -> new SnorfleToggleCommand(snorfler));

    // Press and hold to reverse the snorfler
    reactor.onTriggeredSubmit(joystickB.getButton(7), () -> new SnorfleReverseCommand(snorfler));
    reactor.onUntriggeredSubmit(joystickB.getButton(7), () -> new SnorfleStopReversingCommand(snorfler));
  }
}
