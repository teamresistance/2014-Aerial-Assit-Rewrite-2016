package org.teamresistance.frc.util.testing;

import org.strongback.components.ui.FlightStick;
import org.teamresistance.frc.IO;

import static org.teamresistance.frc.util.testing.JoystickMap.RightJoystick.AGITATE_WHILE_FEEDING;
import static org.teamresistance.frc.util.testing.JoystickMap.RightJoystick.FEED_AND_SHOOT;
import static org.teamresistance.frc.util.testing.JoystickMap.RightJoystick.SNORFLE_IN;
import static org.teamresistance.frc.util.testing.JoystickMap.RightJoystick.SNORFLE_OUT;

public class SnorflerTesting extends CommandTesting {

  public SnorflerTesting(FlightStick joystickA, FlightStick joystickB, FlightStick joystickC) {
    super(joystickA, joystickB, joystickC);
  }

  public void enableFeedingShootingTest() {
    // Trigger spins snorfler and feeder
    reactor.whileTriggered(joystickB.getButton(FEED_AND_SHOOT), () -> {
      if (joystickB.getButton(AGITATE_WHILE_FEEDING).isTriggered()) {
        IO.agitatorMotor.set(0.3);
      } else {
        IO.agitatorMotor.stopMotor();
      }
      IO.shooterMotor.set(1.0);
      IO.feederMotor.set(1.0);
    });
    reactor.whileUntriggered(joystickB.getButton(FEED_AND_SHOOT), () -> {
      IO.shooterMotor.stopMotor();
      IO.feederMotor.stopMotor();
      IO.agitatorMotor.stopMotor();
    });
  }

  public void enableSnorflerTest() {
    reactor.onTriggered(joystickB.getButton(SNORFLE_IN), () -> IO.snorflerMotor.set(1.0));
    reactor.onUntriggered(joystickB.getButton(SNORFLE_IN), IO.snorflerMotor::stopMotor);

    // Press and hold to reverse the snorfler
    reactor.onTriggered(joystickB.getButton(SNORFLE_OUT), () -> IO.snorflerMotor.set(-1.0));
    reactor.onUntriggered(joystickB.getButton(SNORFLE_OUT), IO.snorflerMotor::stopMotor);
  }
}
