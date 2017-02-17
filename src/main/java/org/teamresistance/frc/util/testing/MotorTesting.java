package org.teamresistance.frc.util.testing;

import org.strongback.SwitchReactor;
import org.strongback.components.ui.FlightStick;
import org.teamresistance.frc.IO;
import org.teamresistance.frc.Robot;

import edu.wpi.first.wpilibj.VictorSP;

/**
 * @author Rothanak So
 */
public class MotorTesting {

  /**
   * Sets up a brute-force mechanism for identifying all motors on the robot. Registers each PWM
   * motor from 1-9 with a corresponding button on the joystick. PWM 0 is registered to button 10.
   * Tweak this method as necessary. Call this method in {@link Robot#robotInit()}.
   * <p>
   * WARNING: Make sure you haven't assigned the Victors anywhere else (comment out the IO class),
   * otherwise you'll get an error saying the motor on that port was already initialized.
   */
  public static void registerIdentificationCommands(SwitchReactor reactor, FlightStick joystick) {
    for (int i = 1; i < 10; i++) {
      VictorSP victor = new VictorSP(i);
      reactor.whileTriggered(joystick.getButton(i), () -> victor.setSpeed(.2));
      reactor.whileUntriggered(joystick.getButton(i), () -> victor.setSpeed(0));
    }

    VictorSP victor = new VictorSP(0);
    reactor.whileTriggered(joystick.getButton(10), () -> victor.setSpeed(.2));
    reactor.whileUntriggered(joystick.getButton(10), () -> victor.setSpeed(0));
  }

  /**
   * Registers buttons for spinning the individual drive motors. Use this method after you have
   * identified the motors (see: {@link #registerIdentificationCommands} and instantiated them in
   * {@link IO} and now need to ensure the motors are mapped properly and are not inverted.
   */
  public static void registerVerificationCommands(SwitchReactor reactor, FlightStick joystick) {
    reactor.whileTriggered(joystick.getButton(6), () -> IO.leftFrontMotor.set(0.2));
    reactor.whileUntriggered(joystick.getButton(6), () -> IO.leftFrontMotor.set(0.0));

    reactor.whileTriggered(joystick.getButton(7), () -> IO.leftRearMotor.set(0.2));
    reactor.whileUntriggered(joystick.getButton(7), () -> IO.leftRearMotor.set(0.0));

    reactor.whileTriggered(joystick.getButton(11), () -> IO.rightFrontMotor.set(0.2));
    reactor.whileUntriggered(joystick.getButton(11), () -> IO.rightFrontMotor.set(0.0));

    reactor.whileTriggered(joystick.getButton(10), () -> IO.rightRearMotor.set(0.2));
    reactor.whileUntriggered(joystick.getButton(10), () -> IO.rightRearMotor.set(0.0));
  }
}
