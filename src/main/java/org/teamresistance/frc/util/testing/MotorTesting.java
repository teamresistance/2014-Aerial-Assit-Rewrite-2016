package org.teamresistance.frc.util.testing;

import org.strongback.SwitchReactor;
import org.strongback.components.ui.FlightStick;
import org.teamresistance.frc.Robot;

import edu.wpi.first.wpilibj.VictorSP;

/**
 * @author Rothanak So
 */
public class MotorTesting {

  /**
   * Sets up a brute-force mechanism for ensuring all motors run on the robot. Registers each PWM
   * motor from 1-9 with a corresponding button on the joystick. PWM 0 is registered to button 10.
   * Tweak this method as necessary. Call this method in {@link Robot#robotInit()}.
   *
   * WARNING: Make sure you haven't assigned the Victors anywhere else (comment out the IO class),
   * otherwise you'll get an error saying the motor on that port was already initialized.
   */
  public static void registerTestCommands(SwitchReactor reactor, FlightStick joystick) {
    for (int i = 1; i < 10; i++) {
      VictorSP victor = new VictorSP(i);
      reactor.whileTriggered(joystick.getButton(i), () -> victor.setSpeed(.2));
      reactor.whileUntriggered(joystick.getButton(i), () -> victor.setSpeed(0));
    }

    VictorSP victor = new VictorSP(0);
    reactor.whileTriggered(joystick.getButton(10), () -> victor.setSpeed(.2));
    reactor.whileUntriggered(joystick.getButton(10), () -> victor.setSpeed(0));
  }
}
