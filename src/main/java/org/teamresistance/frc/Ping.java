package org.teamresistance.frc;

import edu.wpi.first.wpilibj.Ultrasonic;

/**
 * Created by shrey on 1/26/2017.
 */
public class Ping {

  private final Ultrasonic ultrasonicSensor;

  public Ping(int pingChannel, int echoChannel) {
    this.ultrasonicSensor = new Ultrasonic(pingChannel, echoChannel);
    this.ultrasonicSensor.setAutomaticMode(true);
  }

  public double getDistance() {
    return this.ultrasonicSensor.getRangeInches();
  }

}
