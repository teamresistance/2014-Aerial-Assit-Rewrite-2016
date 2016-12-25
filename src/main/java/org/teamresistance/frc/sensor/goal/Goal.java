package org.teamresistance.frc.sensor.goal;

final class Goal {
  final double xDistance;
  final double yDistance;
  final double perceivedWidth;

  Goal(double xDistance, double yDistance, double perceivedWidth) {
    this.xDistance = xDistance;
    this.yDistance = yDistance;
    this.perceivedWidth = perceivedWidth;
  }
}
