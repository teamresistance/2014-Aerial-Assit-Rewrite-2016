package org.teamresistance.frc.subsystem.drive;

import org.teamresistance.frc.Pose;
import org.teamresistance.frc.subsystem.Controller;
import org.teamresistance.frc.subsystem.OpenLoopController;

/**
 * A special {@link Drive} controller that kills all the motors. Pass this controller in through
 * {@link Drive#setController(Controller)} to replace the active controller and stop driving.
 * <p>
 * Note: This controller is still <b>closed-loop</b> will ignore all joystick inputs. Unless it is
 * an emergency stop, after stopping remember to call {@link Drive#setController(Controller)} with
 * an {@link OpenLoopController} to restore control back to the operator, or prefer using {@link
 * OpenLoopController} instead of {@link DriveHaltingController} in the first place.
 *
 * @author Rothanak So
 */
public final class DriveHaltingController implements Controller<Drive.Signal> {

  @Override
  public Drive.Signal computeSignal(Drive.Signal feedForward, Pose feedback) {
    // TODO: Spin the wheels inward instead to halt. May need some API tweaks.
    return Drive.Signal.createFieldOriented(0, 0, 0);
  }
}
