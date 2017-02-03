package org.teamresistance.frc;

import org.strongback.components.Motor;
import org.strongback.drive.MecanumDrive;
import org.strongback.hardware.Hardware;
import org.strongback.components.Solenoid;

import edu.wpi.first.wpilibj.SPI;

/**
 * @author Shreya Ravi
 */
public class IO {
  public static final Motor frontLeftMotor = Hardware.Motors.victor(0);
  public static final Motor frontRightMotor = Hardware.Motors.victor(1).invert();
  public static final Motor rearLeftMotor = Hardware.Motors.victor(2);
  public static final Motor rearRightMotor = Hardware.Motors.victor(3).invert();

  public static final Gyro3D gyro = new Gyro3D(SPI.Port.kMXP);

  public static final Solenoid grabSolenoid = Hardware.Solenoids.doubleSolenoid(1, 2, Solenoid.Direction.EXTENDING);
  public static final Solenoid armSolenoid = Hardware.Solenoids.doubleSolenoid(3, 4, Solenoid.Direction.EXTENDING);

  public static final MecanumDrive robotDrive = new MecanumDrive(
      frontLeftMotor,
      rearLeftMotor,
      frontRightMotor,
      rearRightMotor,
      gyro
  );
}
