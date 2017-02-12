package org.teamresistance.frc.subsystem.shooter;

import org.strongback.command.Requirable;
import org.strongback.components.Solenoid;

public class Shooter implements Requirable {
  static final double CHARGE_UP_TIME = 2.0; // seconds
  static final double RELOAD_TIME = 0.3; // seconds

  private final Solenoid trigger;
  private final Solenoid shooter;

  public Shooter(Solenoid trigger, Solenoid shooter) {
    this.trigger = trigger;
    this.shooter = shooter;
  }

  void chargeUp() {
    trigger.extend();
  }

  void discharge() {
    if (trigger.isExtending()) {
      shooter.extend();
    } else {
      System.out.println("Ignoring discharge: must extend trigger before firing");
    }
  }

  void reload() {
    trigger.retract();
    shooter.retract();
  }
}
