package org.teamresistance.frc;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * @author Rothanak So
 */
public class IO {

  private static final class PWM {

    // Drive assignments
    private static final int LF_WHEEL = 7;
    private static final int LR_WHEEL = 8;
    private static final int RF_WHEEL = 3;
    private static final int RR_WHEEL = 1;

    // Shooter assignments
    private static final int SHOOTER_WHEEL = 4;
    private static final int FEEDER_SNORFLER = 2;
    private static final int AGITATOR = 6;

    // Snorfler, grabulator, and climber assignments
    private static final int BALL_SNORFLER = 0;
    private static final int GEAR_ROTATOR = 5;
    private static final int CLIMBER = 9;
  }

  private static final class RELAY {
    private static final int LIGHTS = 1; // TODO: verify
  }

  private static final class CAN {
    private static final int PDP = 0;
    private static final int PCM = 1;
  }

  public static final class PDP {
    public static final int CLIMBER = 8;
  }

  // Relay for green LEDs
  public static final Relay cameraLights = new Relay(RELAY.LIGHTS);

  // Power distribution panel (PDP)
  public static final PowerDistributionPanel powerPanel = new PowerDistributionPanel(CAN.PDP);

  // NavX-MXP navigation sensor
  public static final NavX navX = new NavX(SPI.Port.kMXP);

  // Drive motors
  public static final SpeedController leftFrontMotor = new Victor(PWM.LF_WHEEL);
  public static final SpeedController leftRearMotor = new Victor(PWM.LR_WHEEL);
  public static final SpeedController rightFrontMotor = new Victor(PWM.RF_WHEEL);
  public static final SpeedController rightRearMotor = new Victor(PWM.RR_WHEEL);

  static {
    rightFrontMotor.setInverted(true);
    rightRearMotor.setInverted(true);
  }

  // Shooter motors
  public static final SpeedController shooterMotor = new VictorSP(PWM.SHOOTER_WHEEL);
  public static final SpeedController feederMotor = new VictorSP(PWM.FEEDER_SNORFLER);
  public static final SpeedController agitatorMotor = new VictorSP(PWM.AGITATOR);

  static {
    feederMotor.setInverted(true);
  }

  // Snorfler, gear, and climber motors
  public static final SpeedController snorflerMotor = new VictorSP(PWM.BALL_SNORFLER);
  public static final SpeedController gearRotatorMotor = new VictorSP(PWM.GEAR_ROTATOR);
  public static final SpeedController climberMotor = new VictorSP(PWM.CLIMBER);

  static {
    snorflerMotor.setInverted(true);
    climberMotor.setInverted(true);
  }
}
