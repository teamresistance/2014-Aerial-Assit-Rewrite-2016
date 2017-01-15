package org.teamresistance.frc;

import org.strongback.components.Motor;
import org.strongback.drive.MecanumDrive;
import org.strongback.hardware.Hardware;

import edu.wpi.first.wpilibj.SPI;

/**
 * @author Shreya Ravi
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
}
