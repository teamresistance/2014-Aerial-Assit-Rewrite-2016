package org.teamresistance.frc.util.testing;

import org.strongback.command.Command;
import org.strongback.components.ui.FlightStick;
import org.teamresistance.frc.subsystem.climb.Climber;

public class ClimberTesting extends CommandTesting {
  private final Climber climber;

  public ClimberTesting(Climber climber, FlightStick joystickA, FlightStick joystickB) {
    super(joystickA, joystickB);
    this.climber = climber;
  }

  public void enableAllClimberTests() {
    // Press and hold to climb
    reactor.onTriggeredSubmit(joystickB.getButton(3), () -> climber.climbRope(40, 0.5));
    reactor.onUntriggeredSubmit(joystickB.getButton(3), () -> Command.cancel(climber));
  }
}
