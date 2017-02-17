package org.teamresistance.frc.util.testing;

import org.strongback.components.ui.FlightStick;
import org.teamresistance.frc.IO;
import org.teamresistance.frc.subsystem.snorfler.Snorfler;

public class SnorflerTesting extends CommandTesting {
  private final Snorfler snorfler;

  public SnorflerTesting(Snorfler snorfler, FlightStick joystickA, FlightStick joystickB) {
    super(joystickA, joystickB);
    this.snorfler = snorfler;
  }

  public void enableCompositeTest() {
    // Trigger spins snorfler and feeder
    reactor.whileTriggered(joystickB.getTrigger(), () -> {
      if (joystickB.getButton(3).isTriggered()) {
        IO.shooterAgitatorMotor.setSpeed(0.3);
      } else {
        IO.shooterAgitatorMotor.setSpeed(0.0);
      }
      IO.shooterMotor.setSpeed(1.0);
      IO.feederMotor.setSpeed(1.0);
    });
    reactor.whileUntriggered(joystickB.getTrigger(), () -> {
      IO.shooterMotor.setSpeed(0.0);
      IO.feederMotor.setSpeed(0.0);
      IO.shooterAgitatorMotor.setSpeed(0.0);
    });
  }
  
  public void enableAllSnorflerTests() {
    reactor.onTriggered(joystickB.getButton(11), () -> IO.snorflerMotor.setSpeed(1.0));
    reactor.onUntriggered(joystickB.getButton(11), () -> IO.snorflerMotor.setSpeed(0.0));

    // Press and hold to reverse the snorfler
    reactor.onTriggered(joystickB.getButton(10), () -> IO.snorflerMotor.setSpeed(-1.0));
    reactor.onUntriggered(joystickB.getButton(10), () -> IO.snorflerMotor.setSpeed(0.0));
  }
}
