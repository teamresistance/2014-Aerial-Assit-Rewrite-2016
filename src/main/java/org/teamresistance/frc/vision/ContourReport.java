package org.teamresistance.frc.vision;

public final class ContourReport {
  public final double area;
  public final double centerX;
  public final double centerY;
  public final double height;
  public final double width;

  ContourReport(double area, double centerX, double centerY, double height, double width) {
    this.area = area;
    this.centerX = centerX;
    this.centerY = centerY;
    this.height = height;
    this.width = width;
  }
}
