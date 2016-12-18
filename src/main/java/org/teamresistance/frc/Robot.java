package org.teamresistance.frc;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.strongback.Strongback;

/**
 * Main robot class. Override methods from {@link IterativeRobot} to define behavior.
 */
public class Robot extends IterativeRobot {

    Drive drive;

    @Override
    public void robotInit() {
      System.out.println(BuildConfig.AGENT);

      Strongback.configure().recordNoEvents().recordNoData().initialize();
      drive = new Drive(IO.robotDrive);
    }
}
