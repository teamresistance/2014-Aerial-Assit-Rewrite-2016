package org.teamresistance.frc.subsystem.drive;

import org.junit.jupiter.api.Test;
import org.strongback.components.Motor;
import org.strongback.drive.MecanumDrive;
import org.strongback.mock.Mock;

import static org.assertj.core.api.Assertions.assertThat;

class DriveTest {
  // Mock motors allow us to verify the motor outputs are correct without needing the actual
  // hardware. The speed of the motor is stored in memory for later retrieval by getSpeed().
  private final Motor leftFront = Mock.stoppedMotor();
  private final Motor leftRear = Mock.stoppedMotor();
  private final Motor rightFront = Mock.stoppedMotor();
  private final Motor rightRear = Mock.stoppedMotor();

  // We're using a real MecanumDrive instance instead of a mock because the logic inside
  // MecanumDrive is part of the system we're testing. It's in an instance field rather than
  // being inlined to allow us to directly call robotDrive.cartesian()/polar() later and compare
  // the results to calling drive.onSignal().
  private final MecanumDrive robotDrive = new MecanumDrive(leftFront, leftRear, rightFront,
      rightRear, Mock.angleSensor());

  // Drive is system-under-test (SUT), which includes the MecanumDrive dependency. We can treat
  // it as an isolated black box where its methods are the inputs and the motors are the outputs.
  private Drive drive = new Drive(robotDrive, () -> 0, () -> 0, () -> 0);

  @Test
  void onSignal_WithRobotOrientedSignal_ShouldDrivePolar() {
    final double magnitude = 0.5; // speed
    final double direction = 90; // degrees
    final double rotation = 0.5; // speed

    // Move the robot robot-oriented via our Drive subsystem
    drive.onSignal(Drive.Signal.createRobotOriented(magnitude, direction, rotation));
    double signaledLf = leftFront.getSpeed();
    double signaledLr = leftRear.getSpeed();
    double signaledRf = rightFront.getSpeed();
    double signaledRr = rightRear.getSpeed();
    resetMotors();

    // Move the robot robot-oriented directly via the library
    robotDrive.polar(magnitude, direction, rotation);
    double directLf = leftFront.getSpeed();
    double directLr = leftRear.getSpeed();
    double directRf = rightFront.getSpeed();
    double directRr = rightRear.getSpeed();

    // Ensure our Drive subsystem properly forwarded the calls to the library
    assertThat(signaledLf).isEqualTo(directLf);
    assertThat(signaledLr).isEqualTo(directLr);
    assertThat(signaledRf).isEqualTo(directRf);
    assertThat(signaledRr).isEqualTo(directRr);
  }

  private void resetMotors() {
    leftFront.stop();
    leftRear.stop();
    rightFront.stop();
    rightRear.stop();
  }
}