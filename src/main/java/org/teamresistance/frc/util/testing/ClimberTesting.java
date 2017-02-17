package org.teamresistance.frc.util.testing;

import org.strongback.command.Command;
import org.strongback.components.ui.FlightStick;
import org.teamresistance.frc.IO;
import org.teamresistance.frc.subsystem.climb.Climber;

public class ClimberTesting extends CommandTesting {
  private final Climber climber;

  public ClimberTesting(Climber climber, FlightStick joystickA, FlightStick joystickB) {
    super(joystickA, joystickB);
    this.climber = climber;
  }

  public void enableClimberDebugging() {
    reactor.whileTriggered(joystickB.getButton(8), () -> IO.climberMotor.setSpeed(1.0));
    reactor.whileUntriggered(joystickB.getButton(8), () -> IO.climberMotor.stop());
  }

  public void enableAllClimberTests() {
    // Press and hold to climb
    reactor.onTriggered(joystickB.getButton(3), () -> IO.climberMotor.setSpeed(-1.0));
    reactor.onUntriggered(joystickB.getButton(3), () -> IO.climberMotor.setSpeed(0.0));
  }
}
