package org.teamresistance.frc;

import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.strongback.command.Command;

/**
 * @author Tarik C. Brown
 */
public class UltraSensor extends Command {
  public Ultrasonic ping1;
  public Ultrasonic ping2;

  public UltraSensor() {
    // Should be Ultrasonic(Ping Channel, Echo Channel
    // Sets the channels and makes the sensors start running in auto
    ping1 = new Ultrasonic(0,1);
    ping2 = new Ultrasonic(2,3);
    ping1.setAutomaticMode(true);
    ping2.setAutomaticMode(true);
  }
  @Override
  public boolean execute() {
    //gets the distance from ping sensors
    double range = ping1.getRangeInches();
    double range2 = ping2.getRangeInches();
    // I believe this will display the distance on the smart dashboard
    SmartDashboard.putData("ping distance y",ping1);
    SmartDashboard.putData("ping distance x",ping2);
    return false;
  }
}

