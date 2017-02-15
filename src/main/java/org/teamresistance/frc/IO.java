package org.teamresistance.frc;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import org.strongback.components.Motor;
import org.strongback.drive.MecanumDrive;
import org.strongback.hardware.Hardware;
import org.teamresistance.frc.util.MotorSpy;

import edu.wpi.first.wpilibj.SPI;

/**
 * @author Shreya Ravi
 * @author Tarik Brown
 */
public class IO {
  public static final Motor frontLeftMotor = new MotorSpy(Hardware.Motors.victor(0), "FL");
  public static final Motor frontRightMotor = new MotorSpy(Hardware.Motors.victor(1).invert(), "FR");
  public static final Motor rearLeftMotor = new MotorSpy(Hardware.Motors.victor(2), "RL");
  public static final Motor rearRightMotor = new MotorSpy(Hardware.Motors.victor(3).invert(), "RR");

  public static final Motor snorflerMotor = Hardware.Motors.victor(5);

  public static final Gyro3D gyro = new Gyro3D(SPI.Port.kMXP);

  public static final Motor climberMotor = Hardware.Motors.victor(4);

  public static final PowerDistributionPanel pdp = new PowerDistributionPanel();

  public static final MecanumDrive robotDrive = new MecanumDrive(
      frontLeftMotor,
      rearLeftMotor,
      frontRightMotor,
      rearRightMotor,
      gyro
  );

}
