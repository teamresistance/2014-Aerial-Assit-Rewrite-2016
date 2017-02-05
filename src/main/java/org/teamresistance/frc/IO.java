package org.teamresistance.frc;

import org.strongback.components.Motor;
import org.strongback.drive.MecanumDrive;
import org.strongback.hardware.Hardware;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Shreya Ravi
 */
public class IO {
  private static class MotorSpy implements Motor {
    private final Motor motor;
    private final String smartDashboardName;

    public MotorSpy(Motor motor, String smartDashboardName) {
      this.motor = motor;
      this.smartDashboardName = smartDashboardName;
    }

    @Override
    public double getSpeed() {
      return motor.getSpeed();
    }

    @Override
    public Motor setSpeed(double speed) {
      SmartDashboard.putNumber(smartDashboardName + ": setting speed to", speed);
      return motor.setSpeed(speed);
    }
  }

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
}
