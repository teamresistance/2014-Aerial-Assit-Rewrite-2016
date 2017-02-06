package org.teamresistance.frc;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import org.strongback.components.Motor;
import org.strongback.components.ui.FlightStick;
import org.strongback.drive.MecanumDrive;
import org.strongback.hardware.Hardware;

/**
 * @author Shreya Ravi
 * @author Tarik C. Brown
 */
public class IO {
  public static final Motor frontLeftMotor = Hardware.Motors.victorSP(0);
  public static final Motor frontRightMotor = Hardware.Motors.victorSP(1).invert();
  public static final Motor rearLeftMotor = Hardware.Motors.victorSP(2);
  public static final Motor rearRightMotor = Hardware.Motors.victorSP(3).invert();
  public static final Motor shooterMotor = Hardware.Motors.victorSP(4);
  public static final Motor agitatorMotor = Hardware.Motors.victorSP(5);
  public static final Motor snorfleMotor = Hardware.Motors.victor(6);

  private static final boolean SHOOTER_REVERSED = false;
  private static final boolean AGITATOR_REVERSED = false;
  public static Encoder shooterEncoder = new Encoder(4, 0, SHOOTER_REVERSED, CounterBase.EncodingType.k4X);
  public static Encoder agitatorEncoder = new Encoder(4, 5, AGITATOR_REVERSED, CounterBase.EncodingType.k4X);

  // Math found that could work with shooter encoder, right now im going to put random numbers
  //diamater of the wheel * pi = distance per rotation
  // 1 / ticks per rotation = rotations per tick
  //distance per rotation * rotations per tick = distance per tick
  //diamater of wheel * pi / ticks per rotation = distance per tick
 // double distancePerTick = 3 * Math.PI / 200;

  public static final Gyro3D gyro = new Gyro3D(SPI.Port.kMXP);

  public static final MecanumDrive robotDrive = new MecanumDrive(
      frontLeftMotor,
      rearLeftMotor,
      frontRightMotor,
      rearRightMotor,
      gyro
  );
}
