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

  //Amount of change since last read
  private float raw_dx = 0;
  private float raw_dy = 0;

  private float tot_dxLinear = 0;
  private float tot_dyLinear = 0;


  //Convert for Decimal Values
  //Pixel to Feet conversion factor
  private float X_Left_Per_Ft  = 450;
  private float X_Right_Per_Ft = 450;
  private float Y_Fwd_Per_Ft   = 450;
  private float Y_Rev_Per_Ft   = 450;

  //Distance Covered
  private float dxLinear = 0;
  private float dyLinear = 0;

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

    dxLinear = 0;
    dyLinear = 0;

    tot_dxLinear = 0;
    tot_dyLinear = 0;

    raw_dx = 0;
    raw_dy = 0;

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


    //Ensure that im getting values
    SmartDashboard.putNumber("Raw Y", raw_dy);
    SmartDashboard.putNumber("Raw X", raw_dx);

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

 public double getX() {
    return dxLinear; // TODO: verify you want to return dx
  }

  public double getY() {
    return dyLinear; // TODO: verify you want to return dy
  }
}
