package org.teamresistance.frc;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

/**
 * @author Shreya Ravi
 */
public class IO {
  public static final SpeedController leftFrontMotor = new Victor(0);
  public static final SpeedController leftRearMotor = new Victor(2);
  public static final SpeedController rightFrontMotor = new Victor(1);
  public static final SpeedController rightRearMotor = new Victor(3);

  static {
    rightFrontMotor.setInverted(true);
    rightRearMotor.setInverted(true);
  }

  public static final NavX navX = new NavX(SPI.Port.kMXP);

  public static final RobotDrive robotDrive = new RobotDrive(
      leftFrontMotor,
      leftRearMotor,
      rightFrontMotor,
      rightRearMotor
  );
  //public static CDROutputStream_1_0 opticalFlow;
}
