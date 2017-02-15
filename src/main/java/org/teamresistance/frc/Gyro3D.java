package org.teamresistance.frc;

import com.kauailabs.navx.frc.AHRS;

import org.strongback.components.AngleSensor;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 * @author Shreya Ravi
 */
public class Gyro3D implements AngleSensor, Accelerometer {
  private final AHRS gyro;

  public Gyro3D(SPI.Port port) {
    this.gyro = new AHRS(port);
  }

  /**
   * Equivalent of getYaw()
   */
  @Override
  public double getAngle() {
    return normalize((double) gyro.getYaw());
  }

  private double normalize(double angle) {
    return ((angle % 360) + 360) % 360;
  }

  public double getRoll() {
    return normalize((double) gyro.getRoll());
  }

  public double getPitch() {
    return normalize((double) gyro.getPitch());
  }

  public boolean isAngle(double threshold, double angle) {
    return Math.abs(getAngle() - angle) <= threshold;
  }

  public AHRS getNavX() {
    return gyro;
  }

  @Override
  public void setRange(Range range) {
    throw new UnsupportedOperationException();
  }

  @Override
  public double getX() {
    return gyro.getWorldLinearAccelX();
  }

  @Override
  public double getY() {
    return gyro.getWorldLinearAccelY();
  }

  @Override
  public double getZ() {
    return gyro.getWorldLinearAccelZ();
  }
}
