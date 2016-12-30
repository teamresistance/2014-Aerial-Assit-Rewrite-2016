package org.teamresistance.frc.sensor.goal;

import org.teamresistance.frc.vision.ContourReport;
import org.teamresistance.frc.vision.Locator;

final class GoalLocator implements Locator<ContourReport, Goal> {
  private static final double CANVAS_WIDTH_PX = 320;

  @Override
  public Goal locate(ContourReport contourReport) {
    double xOffsetPercentage = contourReport.centerX / CANVAS_WIDTH_PX;
    double widthPx = contourReport.width;
    return new Goal(xOffsetPercentage, widthPx);
  }
}
