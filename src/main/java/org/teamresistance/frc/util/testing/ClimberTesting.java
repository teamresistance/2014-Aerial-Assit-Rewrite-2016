package org.teamresistance.frc.util.testing;

import org.strongback.components.ui.FlightStick;
import org.teamresistance.frc.IO;

import static org.teamresistance.frc.util.testing.JoystickMap.RightJoystick.CLIMBER;

public class ClimberTesting extends CommandTesting {

  public ClimberTesting(FlightStick joystickA, FlightStick joystickB, FlightStick joystickC) {
    super(joystickA, joystickB, joystickC);
  }

  public void enableClimberTest() {
    // Press and hold to climb
    reactor.whileTriggered(joystickB.getButton(CLIMBER), () -> IO.climberMotor.setSpeed(1.0));
    reactor.whileUntriggered(joystickB.getButton(CLIMBER), () -> IO.climberMotor.stop());
  }
}
