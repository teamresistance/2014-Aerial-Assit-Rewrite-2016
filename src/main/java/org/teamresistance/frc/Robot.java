package org.teamresistance.frc;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.command.Command;
import org.strongback.command.CommandGroup;
import org.strongback.components.ui.FlightStick;
import org.strongback.hardware.Hardware;
import org.teamresistance.frc.command.BrakeCommand;
import org.teamresistance.frc.command.DriveTimedCommand;
import org.teamresistance.frc.command.FaceGoalCommand;
import org.teamresistance.frc.command.HoldAngleCommand;
import org.teamresistance.frc.sensor.boiler.BoilerListener;
import org.teamresistance.frc.sensor.boiler.BoilerPipeline;
import org.teamresistance.frc.subsystem.drive.Drive;

import java.util.ArrayList;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 * Main robot class. Override methods from {@link IterativeRobot} to define behavior.
 *
 * @author Shreya Ravi
 * @author Rothanak So
 */
public class Robot extends IterativeRobot {
  private final FlightStick leftJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(0);
  private final FlightStick rightJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(1);
  private final FlightStick coJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(2);

  private final Drive drive = new Drive(
      IO.robotDrive,
      leftJoystick.getRoll(),
      leftJoystick.getPitch(),
      rightJoystick.getRoll()
  );

  // Vision
  private static final String AXIS_CAMERA_IP = "";
  private final AxisCamera axisCamera = CameraServer.getInstance().addAxisCamera(AXIS_CAMERA_IP);
  private final BoilerPipeline boilerPipeline = new BoilerPipeline();
  private final BoilerListener boilerListener = new BoilerListener();
  private final VisionThread visionThread = new VisionThread(axisCamera, boilerPipeline,
      boilerListener);

  @Override
  public void robotInit() {
    new Thread(() -> {
      // This entire thread is solely responsible for outputting post-processed images to the
      // SmartDashboard. It doesn't do any vision processing itself--the VisionThread handles that.
      // Don't forget to call run() after instantiating this thread.

      // Save bandwidth by ensuring inputSource res == outputStream res
      final int width = 640;
      final int height = 480;

      // Change the camera settings through the SmartDashboard (?!)
      axisCamera.setResolution(width, height);
      axisCamera.setExposureManual((int) SmartDashboard.getNumber("Axis: Exposure", 50));
      axisCamera.setWhiteBalanceManual((int) SmartDashboard.getNumber("Axis: White Balance", 3000));
      axisCamera.setBrightness((int) SmartDashboard.getNumber("Axis: Brightness", 50));

      CvSink inputSource = CameraServer.getInstance().getVideo(axisCamera);
      CvSource outputStream = CameraServer.getInstance().putVideo("Hello Driver", width, height);

      // Convenient color palette for drawing our shapes (BGR format)
      final Scalar green = new Scalar(0, 255, 0);
      final Scalar yellow = new Scalar(0, 255, 255);
      final Scalar blue = new Scalar(255, 0, 0);

      while (!Thread.interrupted()) {
        Mat grabbedFrame = new Mat();
        inputSource.grabFrame(grabbedFrame);

        // Copy the image to a new reference. Leave the original reference alone in case the boiler
        // processing code happens to be holding the exact same reference... because C.
        Mat image = new Mat();
        grabbedFrame.copyTo(image);

        // Steal the most recently computed hulls from the pipeline listener
        ArrayList<MatOfPoint> convexHulls = boilerListener.getHulls();

        // Draw the raw convex hulls
        Imgproc.drawContours(image, convexHulls, -1, green, 2);

        // Draw the bouding boxes
        convexHulls.forEach(hull -> {
          Rect rect = Imgproc.boundingRect(hull);
          Imgproc.rectangle(image, rect.tl(), rect.br(), yellow, 2);
        });

        // Draw a friendly circle regardless of if there are hulls -- for troubleshooting
        Imgproc.circle(image, new Point(50, 50), 50, blue, 2);

        // Notifies the downstream sinks
        outputStream.putFrame(image);
      }
    }).run();

    Strongback.configure().recordNoEvents().recordNoData();
    final SwitchReactor reactor = Strongback.switchReactor();

    // Hold the current angle of the robot while the trigger is held
    reactor.onTriggeredSubmit(leftJoystick.getTrigger(), () -> new HoldAngleCommand(drive, IO.gyro.getAngle()));
    reactor.onUntriggeredSubmit(leftJoystick.getTrigger(), () -> Command.cancel(drive)); // FIXME doesn't cancel

    reactor.onTriggeredSubmit(leftJoystick.getButton(2), () -> CommandGroup.runSequentially(
        new DriveTimedCommand(drive, 0, 0, 1.5),
        new BrakeCommand(drive, IO.gyro, 1)
    ));

    // Cancel ongoing Drive commands. The interrupted commands should hand back operator control
    reactor.onTriggered(leftJoystick.getButton(3), () -> {
      Strongback.submit(Command.cancel(drive));
      drive.setOpenLoop();
    });

    // Lock the drive motors and hopefully stop the robot
    reactor.onTriggeredSubmit(leftJoystick.getButton(5), () -> new BrakeCommand(drive, IO.gyro, 1));

    // Drive straight, strafe 90 degrees, and strafe 45 -- each for 2 seconds
    reactor.onTriggeredSubmit(leftJoystick.getButton(6), () -> new DriveTimedCommand(drive, 0, 0, 1.5));
    reactor.onTriggeredSubmit(leftJoystick.getButton(11), () -> new DriveTimedCommand(drive, 0, 90, 1.5));
    reactor.onTriggeredSubmit(leftJoystick.getButton(10), () -> new DriveTimedCommand(drive, 0, 45, 1.5));

    // Drive straight, pause for 2s, then strafe 90 degrees
    reactor.onTriggeredSubmit(leftJoystick.getButton(7), () -> CommandGroup.runSequentially(
        new DriveTimedCommand(drive, 0, 0, 0.9),
        new DriveTimedCommand(drive, 0, 90, 1.5),
        Command.pause(1.5),
        new DriveTimedCommand(drive, 0, 270, 1.0),
        new DriveTimedCommand(drive, 0, 180, 0.6),
        new HoldAngleCommand(drive, 135)
    ));

    // Reset the gyro
    reactor.onTriggered(rightJoystick.getButton(2), () -> IO.gyro.getNavX().reset());

    // Hold angle at 135 or 0 degrees until cancelled or interrupted
    reactor.onTriggeredSubmit(rightJoystick.getButton(3), () -> new HoldAngleCommand(drive, 135));
    reactor.onTriggeredSubmit(rightJoystick.getButton(4), () -> new HoldAngleCommand(drive, 0));

    // Face the vision target while button 8 is held
    reactor.onTriggeredSubmit(leftJoystick.getButton(8), () -> new FaceGoalCommand(drive));
    reactor.onUntriggeredSubmit(leftJoystick.getButton(8), () -> Command.cancel(drive));
  }

  @Override
  public void autonomousInit() {
    Strongback.start();
  }

  @Override
  public void teleopInit() {
    Strongback.start();
    visionThread.start(); // Vision
  }

  @Override
  public void teleopPeriodic() {
    Feedback feedback = new Feedback(IO.gyro.getAngle(), boilerListener.getRelativeOffset());
    SmartDashboard.putNumber("Gyro Angle", feedback.currentAngle);
    SmartDashboard.putNumber("Goal Offset", feedback.goalOffset.orElse(-1));

    drive.onUpdate(feedback);
  }

  @Override
  public void disabledInit() {
    Strongback.disable();
  }
}
