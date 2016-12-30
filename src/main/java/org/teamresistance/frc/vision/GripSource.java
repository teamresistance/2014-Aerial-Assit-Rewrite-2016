package org.teamresistance.frc.vision;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public final class GripSource implements Source<ContourReport> {
  private static final String TABLE_NAME = "GRIP/myContoursReport";
  private final NetworkTable table = NetworkTable.getTable(TABLE_NAME);

  @Override
  public final Optional<List<ContourReport>> getContours() {
    double[] areas = getNumberArray("area");
    double[] centerXs = getNumberArray("centerX");
    double[] centerYs = getNumberArray("centerY");
    double[] heights = getNumberArray("height");
    double[] widths = getNumberArray("width");

    // Build the list of reports
    ArrayList<ContourReport> contours = new ArrayList<>();
    for (int i = 0, length = areas.length; i < length; i++) {
      contours.add(new ContourReport(areas[i], centerXs[i], centerYs[i], heights[i], widths[i]));
    }

    // Return all the goals we detected
    return contours.isEmpty() ? Optional.empty() : Optional.of(contours);
  }

  private double[] getNumberArray(String key) {
    double[] defaultValue = new double[0];
    return table.getNumberArray(key, defaultValue);
  }
}
