package org.teamresistance.frc;

import edu.wpi.first.wpilibj.SPI;
import org.strongback.components.Motor;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.drive.MecanumDrive;
import org.strongback.hardware.Hardware;

/**
 * Created by shrey on 12/18/2016.
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

  public static final ContinuousRange translateXSpeed = leftJoystick.getRoll();
  public static final ContinuousRange translateYSpeed = leftJoystick.getPitch();
}
