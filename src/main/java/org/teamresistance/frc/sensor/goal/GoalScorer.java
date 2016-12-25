package org.teamresistance.frc.sensor.goal;

import java.util.Comparator;

class GoalScorer implements Comparator<Goal>{

  @Override
  public int compare(Goal goalX, Goal goalY) {
    // Widest one wins because it means the robot is already at a decent angle
    return goalX.widthPx > goalY.widthPx ? 1 : -1;
  }
}
