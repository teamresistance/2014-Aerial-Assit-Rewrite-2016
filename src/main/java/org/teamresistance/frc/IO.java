package org.teamresistance.frc;

import org.strongback.components.Motor;
import org.strongback.components.PowerPanel;
import org.strongback.components.TalonSRX;
import org.strongback.hardware.Hardware;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.SPI;

import static org.strongback.hardware.Hardware.Motors.talonSRX;
import static org.strongback.hardware.Hardware.Motors.victorSP;

/**
 * @author Rothanak So
 */
public class IO {

  private static final class PWM {

    // Drive assignments
    private static final int LF_WHEEL = 0;
    private static final int LR_WHEEL = 1;
    private static final int RF_WHEEL = 2;
    private static final int RR_WHEEL = 3;

    // Shooter assignments
    private static final int SHOOTER_WHEEL = 4; // TODO: not Talon?
    private static final int SHOOTER_CONVEYOR = 5;
    private static final int SHOOTER_AGITATOR = 6;

    // Snorfler, grabulator, and climber assignments
    private static final int BALL_SNORFLER = 7;
    private static final int GRABULATOR_ROTATOR = 8;
    private static final int CLIMBER = 9;

    // Compressor assignment
    private static final int AIR_COMPRESSOR = 2;
  }

  private static final class DIO {
    private static final int GRABULATOR_RETRACTED_LIMIT = -1; // TODO
    private static final int GRABULATOR_TOOTH_BANNER = -1; // TODO
    private static final int GRABULATOR_SHOE_BANNER = -1; // TODO
  }

  private static final class PCM {
    // TODO: solenoids are assigned to the PCM, right?
  }

  private static final class CAN {
    private static final int SHOOTER_WHEEL = 1; // TODO: verify
  }

  public static final class PDP {
    public static final int CLIMBER = 8;
  }

  // Power distribution panel
  public static final PowerPanel powerPanel = Hardware.powerPanel();

  // NavX-MXP navigation sensor
  public static final NavX navX = new NavX(SPI.Port.kMXP);

  // Drive motors
  public static final Motor lfMotor = victorSP(PWM.LF_WHEEL);
  public static final Motor rfMotor = victorSP(PWM.RF_WHEEL);
  public static final Motor rLMotor = victorSP(PWM.LR_WHEEL);
  public static final Motor rrMotor = victorSP(PWM.RR_WHEEL);

  // Shooter motors
  public static final TalonSRX shooterMotor = talonSRX(CAN.SHOOTER_WHEEL);
  public static final Motor shooterConveyorMotor = victorSP(PWM.SHOOTER_CONVEYOR);
  public static final Motor shooterAgitatorMotor = victorSP(PWM.SHOOTER_AGITATOR);

  // Snorfler, gear, and climber motors
  public static final Motor snorflerMotor = victorSP(PWM.BALL_SNORFLER);
  public static final Motor gearRotatorMotor = victorSP(PWM.GRABULATOR_ROTATOR);
  public static final Motor climberMotor = victorSP(PWM.CLIMBER);

  // Compressor
  public static final Compressor compressor = new Compressor();

  // TODO all the rest -- solenoids, dio
}
