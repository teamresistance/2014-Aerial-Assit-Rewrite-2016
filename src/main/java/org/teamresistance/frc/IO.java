package org.teamresistance.frc;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import org.strongback.components.Motor;
import org.strongback.components.ui.FlightStick;
import org.strongback.drive.MecanumDrive;
import org.strongback.hardware.Hardware;

/**
 * @author Shreya Ravi
 * Edited by Tarik
 */
public class IO {
  public static final Motor frontLeftMotor = Hardware.Motors.victorSP(0);
  public static final Motor frontRightMotor = Hardware.Motors.victorSP(1);
  public static final Motor rearLeftMotor = Hardware.Motors.victorSP(2);
  public static final Motor rearRightMotor = Hardware.Motors.victorSP(3);


  public static final Gyro3D gyro = new Gyro3D(SPI.Port.kMXP);

  public static final MecanumDrive robotDrive = new MecanumDrive(
      frontLeftMotor,
      rearLeftMotor,
      frontRightMotor,
      rearRightMotor,
      gyro
  );

  public static final FlightStick leftJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(0);
  public static final FlightStick rightJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(1);
  public static final FlightStick coJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(2);


  // constant needed for shooter encoder
  private static final boolean Shooter_Reversed = false;
  // new motor for shooter
  public static final Motor shooterMotor = Hardware.Motors.victorSP(4);

  public IO() {
    // init the encoder
    //Encoder(int aChannel, int bChannel, boolean reverseDirection, CounterBase.EncodingType encodingType)
    Encoder shooterEncoder = new Encoder(4, 5, Shooter_Reversed, CounterBase.EncodingType.k4X);

    // Math found that could work with shooter encoder, right now im going to put random numbers
    //diamater of the wheel * pi = distance per rotation
    // 1 / ticks per rotation = rotations per tick
    //distance per rotation * rotations per tick = distance per tick
    //diamater of wheel * pi / ricks per rotation = distance per tick
    double distancePerTick = 3 * Math.PI / 200;
    shooterEncoder.setDistancePerPulse(distancePerTick);
  }
}
