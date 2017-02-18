package org.teamresistance.frc;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Created by Joseph for testing the Optical Flow
 *
 * Registers
 * 0x00  -   Product ID
 * 0x01  -   Revision ID
 * 0x02  -   Motion
 * 0x03  -   Delta x
 * 0x04  -   Delta y
 * 0x05  -   Squall
 *
 */
public class OpticalFlow {

  private  final SPI spi;
  private  final byte[] dataReceived;
  private  final byte[] register = new byte[] {0};

  //Test Vars
  double test2;
  double test;
  double x = 0;
  double graph = 0;

  //Amount of change since last read
  private float raw_dx = 0;
  private float raw_dy = 0;

  //Summation of raw_dx/y over time
  private float tot_dx = 0;
  private float tot_dy = 0;
  private float tot_dxLinear = 0;
  private float tot_dyLinear = 0;


  //Convert for Decimal Values
  //Pixel to Feet conversion factor
  private float X_Left_Per_Ft  = 450;
  private float X_Right_Per_Ft = 450;
  private float Y_Fwd_Per_Ft   = 450;
  private float Y_Rev_Per_Ft   = 450;

  //Distance Covered
  private float dx = 0;
  private float dy = 0;
  private float dxLinear = 0;
  private float dyLinear = 0;

  //Vars for Jim's Math code
  double lastOrient;
  double dxPerDegCCW = 4.8;
  double dxPerDegCW = -4.8;
  double dyPerDegCCW = 1.7;
  double dyPerDegCW = -1.7;

  public OpticalFlow() {
    spi = new SPI(Port.kOnboardCS0);    //Finds the OF on the SPI ports
    spi.setChipSelectActiveLow();
    spi.setClockActiveHigh();
    spi.setClockRate(500000);

    dataReceived = new byte[1];
    for(int i = 0; i < dataReceived.length; i++) {
      dataReceived[i] = 0;
    }
  }

  public void init() {
    int motionRegister = readRegister((byte)2);
    SmartDashboard.putNumber("Motion Register", motionRegister);

    dx = 0;
    dy = 0;
    dxLinear = 0;
    dyLinear = 0;

    tot_dx = 0;
    tot_dy = 0;
    tot_dxLinear = 0;
    tot_dyLinear = 0;

    raw_dx = 0;
    raw_dy = 0;

    test = 0;
    test2 = 0;

  }

  public void update() {
    SmartDashboard.putNumber("Product ID", readRegister((byte) 0));
    SmartDashboard.putNumber("Squall:", readRegister((byte) 5));

    int motionRegister = readRegister((byte) 2);
    SmartDashboard.putNumber("Motion Register:", motionRegister);

    //Refresh raw values after register
    raw_dx = 0;
    raw_dy = 0;

    // Update the raw_dx/y var
    if ((motionRegister & 0x80) != 0) {
      raw_dx = readRegister((byte) 3);   //use registry to update the change in position
      raw_dy = readRegister((byte) 4);
    }

    //---------------------------------------------- Linear ------------------------------------------------------------
    //Update total value
    tot_dxLinear += raw_dx;
    tot_dyLinear += raw_dy;

    //Find Actual Distance Covered
    if (raw_dx < 0) {
      dxLinear = tot_dxLinear / X_Left_Per_Ft;   //If the bot is going left, use left conversion factor
    } else {
      dxLinear = tot_dxLinear / X_Right_Per_Ft;  //If the bot is going right, use right conversion factor
    }
    if (raw_dy < 0) {
      dyLinear = tot_dxLinear / Y_Rev_Per_Ft;    //If the bot is going backwards, use backwards conversion factor
    } else {
      dyLinear = tot_dyLinear / Y_Fwd_Per_Ft;    //If the bot is going forward, use forward conversion factor
    }

    //---------------------------------------- Non Linear -------------------------------------------------------------

    double psntOrient = IO.navX.getAngle();       //Get the present orientation
    double raw_do = lastOrient - psntOrient;      //calc delta orientation
    if (raw_do > 180) {raw_do = 360-raw_do;}      //Angle wrap so its cant exceed 180 Degrees
    if (raw_do < -180) {raw_do = -(360+raw_do);}
    lastOrient = psntOrient;                      //Grab Present Orientation

    x += 0.01;
    graph = (Math.cos((x * 100)*((Math.PI)/180))) / 100;
    SmartDashboard.putNumber("graph",graph);

    //Find Actual Distance Covered
    //--------Do dX--------------
    if (raw_dx < 0) {
      //although this can be calc'ed using est'ed radius, it's easier to manually get the counts/deg
      //cntsPerDeg = (ofsFtXoffset * PI() / 180.0) * X_Left_Per_Ft = 10.472333 for a 1' offset and 600 XPerFt
      raw_dx -= raw_do * dxPerDegCCW;                            //compensate for off center rotation
      if (raw_do != 0) {
        raw_dx += (((raw_dx*100)%360) / (Math.cos(Math.toRadians(raw_do))) / 100);      //compensate for
        // orientation
      }
      tot_dx += raw_dx;                                           //Update Total
      dx = tot_dx / X_Left_Per_Ft;                                //If the bot is going left, use left conversion factor
    } else {
      raw_dx -= raw_do * dxPerDegCW;                           //compensate for off center rotation
      if (raw_do != 0) {
        test += raw_dx; //----------------------------------------------------------------------------------------------------------------------------
        raw_dx += (((raw_dx*100)%360) / (Math.cos(Math.toRadians(raw_do))) / 100);      //compensate for orientation
        test2 = raw_dx; //----------------------------------------------------------------------------------------------------------------------------
      }
      tot_dx += raw_dx;
      dx = tot_dx / X_Right_Per_Ft;                               //If the bot is going right, use right conversion
      // factor
    }

    //---------Do dY--------------
    if (raw_dy < 0) {
      raw_dy -= raw_do * dyPerDegCCW;                             //compensate for off center rotation
      if (raw_do != 90) {
        raw_dy += (((raw_dx*100)%360) / (Math.cos(Math.toRadians(raw_do))) / 100);      //compensate for orientation
      }
      tot_dy += raw_dy;                                           //Update Total
      dy = tot_dy / Y_Rev_Per_Ft;                                 //If the bot is going backwards, use backwards
      // conversion factor
    } else {
      raw_dy += raw_do * dyPerDegCW;                             //compensate for off center rotation
      if (raw_do != 90) {
        raw_dy += (((raw_dx*100)%360) / (Math.cos(Math.toRadians(raw_do))) / 100);      //compensate for orientation
      }
      tot_dy += raw_dy;                                           //Update Total
      dy = tot_dy / Y_Fwd_Per_Ft;                                 //If the bot is going forward, use forward conversion factor
    }

    //Find Actual Distance Covered
    if (raw_dx < 0) {
      dx = tot_dx / X_Left_Per_Ft;   //If the bot is going left, use left conversion factor
    } else {
      dx = tot_dx / X_Right_Per_Ft;  //If the bot is going right, use right conversion factor
    }
    if (raw_dy < 0) {
      dy = tot_dy / Y_Rev_Per_Ft;    //If the bot is going backwards, use backwards conversion factor
    } else {
      dy = tot_dy / Y_Fwd_Per_Ft;    //If the bot is going forward, use forward conversion factor
    }

    //--------------------------------- Smartdashboard Goodies -------------------------------------------------------

    // Test Vars
    SmartDashboard.putNumber("raw_do", raw_do);

    SmartDashboard.putNumber("Present Orientation", psntOrient);

    //Ensure that im getting values
    SmartDashboard.putNumber("Raw Y", raw_dy);
    SmartDashboard.putNumber("Raw X", raw_dx);
    SmartDashboard.putNumber("Total Y", tot_dy);
    SmartDashboard.putNumber("Total X", tot_dx);
    SmartDashboard.putNumber("Actual Y", dy);
    SmartDashboard.putNumber("Actual X", dx);

    //Ensure that im getting values Linear
    SmartDashboard.putNumber("Total X (Linear)", tot_dxLinear);
    SmartDashboard.putNumber("Total Y (Linear)", tot_dyLinear);
    SmartDashboard.putNumber("Actual X (Linear)", dxLinear);
    SmartDashboard.putNumber("Actual Y (Linear)", dyLinear);

  }

  private int readRegister(byte register) {
    this.register[0] = register;
    spi.write(this.register, 1); // Writes the register to be read
    spi.read(true, dataReceived, 1); // Reads the garbage
    spi.read(false, dataReceived, 1); // Reads the real register value
    return dataReceived[0];
  }

 /* public double getX() {
    return dx;
  }

  public void setX(double dx) {
    this.dx = (long) dx;
  }

  public double getY() {
    return dy;
  }

  public void setY(double dy) {
    this.dy = (long) dy;
  }*/

}
