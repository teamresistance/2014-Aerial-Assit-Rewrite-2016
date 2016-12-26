package org.teamresistance.frc.sensor.goal;

import org.teamresistance.frc.vision.ContourReport;
import org.teamresistance.frc.vision.Locator;

final class GoalLocator implements Locator<ContourReport, Goal> {
  private static final double FOCAL_LENGTH_INCHES = 0; // needs to be determined algebraically
  private static final double CANVAS_WIDTH_PX = 320;
  private static final double CANVAS_HEIGHT_PX = 240;
  private static final double KNOWN_WIDTH = 0;

  @Override
  public Goal locate(ContourReport contourReport) {
    double perceivedCenterX = contourReport.centerX;
    double perceivedWidth = contourReport.width;

    // Find the pixels-per-metric for the current plane to allow us to convert pixels to inches.
    double ppm = perceivedWidth / KNOWN_WIDTH;

    // Calculate the x offset of the goal relative to the center of the image, in inches.
    double xDistance = (perceivedCenterX - CANVAS_WIDTH_PX) / ppm;

    // Calculate the distance between the goal and camera, in inches.
    double yDistance = (KNOWN_WIDTH * FOCAL_LENGTH_INCHES) / perceivedWidth;

    return new Goal(xDistance, yDistance);
  }
}
