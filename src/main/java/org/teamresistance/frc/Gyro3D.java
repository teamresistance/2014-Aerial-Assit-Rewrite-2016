package org.teamresistance.frc;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import org.strongback.components.AngleSensor;

/**
 * Created by shrey on 12/18/2016.
 */
public class Gyro3D extends AHRS implements AngleSensor {

  public Gyro3D(SPI.Port spi_port_id) {
    super(spi_port_id);
  }


  @Override
  public double getAngle() {
    return getYawVal();
  }

  public double normalize(double angle) {
    return ((angle%360)+360)%360;
  }

  public double getYawVal() {
    return normalize((double)super.getYaw());
  }

  public double getRollVal() {
    return normalize((double)super.getRoll());
  }

  public double getPitchVal() {
    return normalize((double)super.getPitch());
  }

  public boolean isStraight(double deadband, double startAngle) {
    double currAngle = getYawVal();
    if (Math.abs(currAngle - startAngle) <= deadband) {
      return true;
    } else {
      return false;
    }
  }
}
