package org.teamresistance.frc;

import com.sun.corba.se.impl.encoding.CDROutputStream_1_0;
import org.strongback.components.Motor;
import org.strongback.drive.MecanumDrive;
import org.strongback.hardware.Hardware;
import org.teamresistance.frc.util.MotorSpy;

import edu.wpi.first.wpilibj.SPI;

/**
 * @author Shreya Ravi
 */
public class IO {
  public static final Motor frontLeftMotor = new MotorSpy(Hardware.Motors.victor(0), "FL");
  public static final Motor frontRightMotor = new MotorSpy(Hardware.Motors.victor(1).invert(), "FR");
  public static final Motor rearLeftMotor = new MotorSpy(Hardware.Motors.victor(2), "RL");
  public static final Motor rearRightMotor = new MotorSpy(Hardware.Motors.victor(3).invert(), "RR");

  public static final Gyro3D gyro = new Gyro3D(SPI.Port.kMXP);

  public static final MecanumDrive robotDrive = new MecanumDrive(
      frontLeftMotor,
      rearLeftMotor,
      frontRightMotor,
      rearRightMotor,
      gyro
  );
  //public static CDROutputStream_1_0 opticalFlow;
}
