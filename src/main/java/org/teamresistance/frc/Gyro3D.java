package org.teamresistance.frc;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import org.strongback.components.AngleSensor;

/**
 * @author Shreya Ravi
 */
public class Gyro3D extends AHRS implements AngleSensor {

  public Gyro3D(SPI.Port port) {
    super(port);
  }


  @Override
  public double getAngle() {
    return getYawVal();
  }

  private double normalize(double angle) {
    return ((angle % 360) + 360) % 360;
  }

  public double getYawVal() {
    return normalize((double) super.getYaw());
  }

  public double getRollVal() {
    return normalize((double) super.getRoll());
  }

  public double getPitchVal() {
    return normalize((double) super.getPitch());
  }

  public boolean isAngle(double threshold, double angle) {
    double currAngle = getYawVal();
    return ((Math.abs(currAngle - angle) <= threshold) ? true : false);
  }
}
