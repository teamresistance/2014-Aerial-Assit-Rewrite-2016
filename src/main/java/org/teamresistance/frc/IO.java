package org.teamresistance.frc;

import org.strongback.components.Motor;

import edu.wpi.first.wpilibj.SPI;

import static org.strongback.hardware.Hardware.Motors.victor;
import static org.strongback.hardware.Hardware.Motors.victorSP;

/**
 * @author Rothanak So
 */
public class IO {

  private static final class PWM {

    // Drive assignments
    private static final int LF_WHEEL = 1;
    private static final int LR_WHEEL = 3;
    private static final int RF_WHEEL = 8;
    private static final int RR_WHEEL = 7;

    // Shooter assignments
    private static final int SHOOTER_WHEEL = 4;
    private static final int SHOOTER_FEEDER = 2;
    private static final int SHOOTER_AGITATOR = 6;

    // Snorfler, grabulator, and climber assignments
    private static final int BALL_SNORFLER = 0;
    private static final int GRABULATOR_ROTATOR = 5;
    private static final int CLIMBER = 9;
  }

  // NavX-MXP navigation sensor
  public static final NavX navX = new NavX(SPI.Port.kMXP);

  // Drive motors
  public static final Motor lfMotor = victor(PWM.LF_WHEEL);
  public static final Motor lRMotor = victor(PWM.LR_WHEEL);
  public static final Motor rfMotor = victor(PWM.RF_WHEEL).invert();
  public static final Motor rrMotor = victor(PWM.RR_WHEEL).invert();

  // Shooter motors
  public static final Motor shooterMotor = victorSP(PWM.SHOOTER_WHEEL);
  public static final Motor shooterConveyorMotor = victorSP(PWM.SHOOTER_FEEDER).invert();
  public static final Motor shooterAgitatorMotor = victorSP(PWM.SHOOTER_AGITATOR);

  // Snorfler, gear, and climber motors
  public static final Motor snorflerMotor = victorSP(PWM.BALL_SNORFLER).invert();
  public static final Motor gearRotatorMotor = victorSP(PWM.GRABULATOR_ROTATOR);
  public static final Motor climberMotor = victorSP(PWM.CLIMBER).invert();
}
