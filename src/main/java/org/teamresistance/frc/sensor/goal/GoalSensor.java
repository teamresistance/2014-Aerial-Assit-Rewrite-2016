package org.teamresistance.frc.sensor.goal;

import org.teamresistance.frc.vision.ContourReport;
import org.teamresistance.frc.vision.GripSource;
import org.teamresistance.frc.vision.Source;

import java.util.List;
import java.util.Optional;

public class GoalSensor {
  private static final int CANVAS_WIDTH_PX = 320;
  private final Source<ContourReport> source = new GripSource();
  public Optional<Double> getGoalOffset() {
    Optional<List<ContourReport>> maybeContours = source.getContours();

    // Abort if no goals were detected
    if (!maybeContours.isPresent()) return Optional.empty();

    // Average the centers of the two tapes to get a stronger estimate of the offset
    List<ContourReport> contours = maybeContours.get();
    double averageCenterX = (contours.get(0).centerX + contours.get(1).centerX) / 2;

    // Offset is relative to the center, with a domain of -1 to +1
    double relativeOffset = ((2 * averageCenterX) / CANVAS_WIDTH_PX) - 1;
    return Optional.of(relativeOffset);
  }
}