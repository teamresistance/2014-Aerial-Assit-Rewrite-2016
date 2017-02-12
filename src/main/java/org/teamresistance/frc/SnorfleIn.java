package org.teamresistance.frc;

import com.sun.org.apache.bcel.internal.classfile.ConstantString;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.strongback.components.Motor;

/**
 * @author Shreya Ravi
 */
public class SnorfleIn extends Snorfle {

  public SnorfleIn(Motor rollers, Motor spinners) {
    super(rollers, spinners, Constants.ROLLER_IN_SPEED, Constants.SPINNER_IN_SPEED);
  }
}
