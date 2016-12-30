package org.teamresistance.frc.sensor.goal;

import org.teamresistance.frc.vision.ContourReport;
import org.teamresistance.frc.vision.GripSource;
import org.teamresistance.frc.vision.Source;

import java.util.Optional;

public class GoalSensor {
  private final Source<ContourReport> source;
  private final GoalLocator locator;
  private final GoalScorer scorer;

  public GoalSensor() {
    this.source = new GripSource();
    this.locator = new GoalLocator();
    this.scorer = new GoalScorer();
  }

  public Optional<Double> getGoalOffset() {
    return source.getContours()
        .flatMap(contours -> contours.stream()
            .map(locator::locate) // convert each contour into location data
            .max(scorer))         // pick the best location
        .map(goal -> goal.xOffsetPercent);
  }
}