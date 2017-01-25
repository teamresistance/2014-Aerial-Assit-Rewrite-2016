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
  public static final Motor shootMotor = Hardware.Motors.victorSP(4);
  public static final Motor agitatorMotor = Hardware.Motors.victorSP(5);
  public static final Motor snorfleMotor = Hardware.Motors.victor(6);

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
  private static final boolean Agitator_Reversed = false;
  // new motor for shooter
  public static final Motor shooterMotor = Hardware.Motors.victorSP(4);
  // init the encoder
  //Encoder(int aChannel, int bChannel, boolean reverseDirection, CounterBase.EncodingType encodingType)
  public static  Encoder shooterEncoder = new Encoder(4,0, Shooter_Reversed, CounterBase.EncodingType.k4X);
  public static  Encoder agitatorEncoder = new Encoder(4,5,Agitator_Reversed, CounterBase.EncodingType.k4X);
}
