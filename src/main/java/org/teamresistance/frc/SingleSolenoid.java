package org.teamresistance.frc;

/**
 * Created by shrey on 2/6/2017.
 */
public interface SingleSolenoid {
  void extend();
  void retract();
  boolean isExtended();
  boolean isRetracted();
}
