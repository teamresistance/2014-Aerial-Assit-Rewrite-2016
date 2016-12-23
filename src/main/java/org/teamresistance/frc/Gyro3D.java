package org.teamresistance.frc;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import org.strongback.components.AngleSensor;

/**
 * @author Shreya Ravi
 */
public class Gyro3D implements AngleSensor {

  private final AHRS gyro;

  public Gyro3D(SPI.Port port) {
    this.gyro = new AHRS(port);
  }

  @Override
  /**
   * Equivalent of getYaw()
   */
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
}
