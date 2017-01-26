package org.teamresistance.frc.sensor;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;

/**
 * Created by nkhlp on 1/25/2017.
 */
public class PingSensor {
  boolean low = false;
  boolean high = true;

  final int MAX_DIST = 23200;

  private final DigitalOutput trigOut;
  private final DigitalInput echoIn;

  public PingSensor(int echo, int trig) {
    echoIn = new DigitalInput(echo);
    trigOut = new DigitalOutput(trig);
  }

  public void setup() {
    trigOut.set(low);

  }

  public void loop() {
    long t1;
    long t2;
    long pulseWidth;
    float cm;
    float inches;

    trigOut.set(high);
    //delay 10 microseconds: delayMicroseconds(10);
    trigOut.set(low);

    while(echoIn.get() == 0);
    //not sure how to implement the digitalRead(ECHO_PIN) method from arduino code in the while loop

    //



  }


}