package org.teamresistance.frc;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.strongback.command.Command;

import java.util.function.Supplier;

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
  private long raw_dx = 0;
  private long raw_dy = 0;

  //Summation of raw_dx/y over time
  private long tot_dx = 0;
  private long tot_dy = 0;


  //Convert for Decimal Values
  //Pixel to Feet conversion factor
  private long X_Left_Per_Ft = 590;
  private long X_Right_Per_Ft = 575;
  private long Y_Fwd_Per_Ft = 595;
  private long Y_Rev_Per_Ft = 595;

  //Distance Covered
  private long dx = 0;
  private long dy = 0;

  //Vars for Franks code
//  double xFacFt = 800;
//  double yFacFt = 800;

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

    tot_dx = 0;
    tot_dy = 0;

    raw_dx = 0;
    raw_dy = 0;

    SmartDashboard.putNumber("Raw X:",raw_dx);
    SmartDashboard.putNumber("Raw Y:",raw_dy);
    SmartDashboard.putNumber("Total X:",tot_dx);
    SmartDashboard.putNumber("Total Y:",tot_dy);
    SmartDashboard.putNumber("Actual X:",dx);
    SmartDashboard.putNumber("Actual Y:",dy);

  }

  public void update() {
    SmartDashboard.putNumber("Product ID", readRegister((byte)0));
    SmartDashboard.putNumber("Squall:", readRegister((byte)5));

    int motionRegister = readRegister((byte)2);
    SmartDashboard.putNumber("Motion Register:", motionRegister);

    //Refresh raw values after register
    raw_dx = 0;
    raw_dy = 0;

    // Update the raw_dx/y var
    if((motionRegister & 0x80) != 0) {
      raw_dx = readRegister((byte)3);   //use registry to update the change in position
      raw_dy = readRegister((byte)4);
    }
		/*Math for angle issues
		*Franks math, IDK if it works, IDK what it means
		*/
//    double rad = Math.toRadians(IO.gyro.getAngle());
//    double cos = Math.cos(rad);
//    double sin = Math.sin(rad);
//    double tot_dx = raw_dx / xFacFt;
//    double tot_dy = raw_dy / yFacFt;
//    dx += tot_dx * cos - tot_dy * sin;
//    dy += tot_dx * sin + tot_dy * cos;

    //Update total value
    tot_dx += raw_dx;
    tot_dy += raw_dy;

    //Find Actual Distance Covered
    if (raw_dx < 0){
      dx = tot_dx / X_Left_Per_Ft;   //If the bot is going left, use left conversion factor
    }
    else {
      dx = tot_dx / X_Right_Per_Ft;  //If the bot is going right, use right conversion factor
    }
    if (raw_dy < 0){
      dy = tot_dx / Y_Rev_Per_Ft;    //If the bot is going backwards, use backwards conversion factor
    }
    else {
      dy = tot_dy / Y_Fwd_Per_Ft;    //If the bot is going forward, use forward conversion factor
    }

    //Ensure that im getting values
    SmartDashboard.putNumber("Raw X:",raw_dx);
    SmartDashboard.putNumber("Raw Y:",raw_dy);
    SmartDashboard.putNumber("Total X:",tot_dx);
    SmartDashboard.putNumber("Total Y:",tot_dy);
    SmartDashboard.putNumber("Actual X:",dx);
    SmartDashboard.putNumber("Actual Y:",dy);

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
