package org.teamresistance.frc.subsystem.grabber;

import org.strongback.command.Command;
import org.strongback.command.CommandGroup;
import org.strongback.command.Requirable;
import org.strongback.components.Motor;
import org.teamresistance.frc.InvertibleDigitalInput;
import org.teamresistance.frc.InvertibleSolenoid;
import org.teamresistance.frc.InvertibleSolenoidWithPosition;
import org.teamresistance.frc.command.grabber.*;

/**
 * Created by shrey on 2/6/2017.
 */
public class Grabber implements Requirable {

  private final InvertibleSolenoid gripSolenoid;
  private final InvertibleSolenoidWithPosition extendSolenoid;
  private final InvertibleSolenoid rotateSolenoid;
  private final Motor rotateGearMotor;
  private final InvertibleDigitalInput gearPresentBannerSensor;
  private final InvertibleDigitalInput gearAlignBannerSensor;

  public Grabber(InvertibleSolenoid gripSolenoid,
                 InvertibleSolenoidWithPosition extendSolenoid,
                 InvertibleSolenoid rotateSolenoid,
                 Motor rotateGearMotor,
                 InvertibleDigitalInput gearPresentBannerSensor,
                 InvertibleDigitalInput gearAlignBannerSensor) {
    this.gripSolenoid = gripSolenoid;
    this.extendSolenoid = extendSolenoid;
    this.rotateSolenoid = rotateSolenoid;
    this.rotateGearMotor = rotateGearMotor;
    this.gearPresentBannerSensor = gearPresentBannerSensor;
    this.gearAlignBannerSensor = gearAlignBannerSensor;
  }

//  public CommandGroup lookForGear() {
//    return CommandGroup.runSequentially(
//        new GearRetract(extendSolenoid),
//        CommandGroup.runSimultaneously(
//            new RotateDown(1.0, extendSolenoid, rotateSolenoid),
//            new ReleaseGear(1.0, gripSolenoid)
//        ),
//        new FindGear(gearPresentBannerSensor)
//    );
//  }
//
//  public CommandGroup pickupGear() {
//    return CommandGroup.runSequentially(
//        new GearExtend(1.0, extendSolenoid),
//        new GrabGear(1.0, gripSolenoid),
//        new GearRetract(extendSolenoid)
//    );
//  }
//
//  public CommandGroup resetFromPicking() {
//    return CommandGroup.runSequentially(
//        new GearRetract(extendSolenoid),
//        new RotateUp(1.0, extendSolenoid, rotateSolenoid)
//    );
//  }





//  public CommandGroup deliver()

}
