package org.teamresistance.frc.util;

import org.strongback.components.Motor;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class MotorSpy implements Motor {
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
