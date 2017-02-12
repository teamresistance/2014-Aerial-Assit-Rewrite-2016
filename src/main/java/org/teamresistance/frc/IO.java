package org.teamresistance.frc;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import org.strongback.components.Motor;
import org.strongback.drive.MecanumDrive;
import org.strongback.hardware.Hardware;

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
