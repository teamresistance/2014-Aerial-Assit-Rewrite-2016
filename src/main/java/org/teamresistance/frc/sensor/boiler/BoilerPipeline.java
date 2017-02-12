package org.teamresistance.frc.sensor.boiler;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.util.ArrayList;
import java.util.OptionalDouble;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionPipeline;

public class BoilerPipeline implements VisionPipeline {
  private static final int CANVAS_WIDTH_PX = 320;
  private final BoilerGrip pipeline;

  public BoilerPipeline() {
    pipeline = new BoilerGrip();
  }

  @Override
  public void process(Mat image) {
    pipeline.setsource0(image);
    pipeline.process();
  }

  OptionalDouble getRelativeOffset() {
    // Grab the last output
    ArrayList<MatOfPoint> hulls = pipeline.convexHullsOutput();

    // Abort if there aren't only two tapes
    int numberOfContours = hulls.size();
    SmartDashboard.putNumber("Vision: Number of contours", numberOfContours);
    if (numberOfContours != 2) return OptionalDouble.empty();

    // Calculate the offset, relative to the center with a domain of -1 to +1
    return hulls.stream().mapToDouble(hull -> {
      Moments moments = Imgproc.moments(hull);
      double centerX = moments.get_m10() / moments.get_m00();
      return ((2 * centerX) / CANVAS_WIDTH_PX) - 1; // relativeCenterX
    }).average();
  }
}