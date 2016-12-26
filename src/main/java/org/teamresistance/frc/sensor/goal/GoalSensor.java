package org.teamresistance.frc.sensor.goal;

import org.strongback.Executable;
import org.teamresistance.frc.vision.ContourReport;
import org.teamresistance.frc.vision.GripSource;
import org.teamresistance.frc.vision.Source;

import java.util.function.Consumer;

public class GoalSensor implements Executable {
  private final Consumer<Goal> listener;
  private final Source<ContourReport> source;
  private final GoalLocator locator;
  private final GoalScorer scorer;

  public GoalSensor(Consumer<Goal> listener) {
    this.listener = listener;
    this.source = new GripSource();
    this.locator = new GoalLocator();
    this.scorer = new GoalScorer();
  }

  @Override
  public void execute(long timeInMillis) {
    source.getContours()
        .flatMap(contours -> contours.stream()
            .map(locator::locate) // convert each contour into location data
            .max(scorer))         // pick the best location
        .ifPresent(listener);
  }
}