package org.teamresistance.frc.sensor.boiler;

import java.util.OptionalDouble;

import edu.wpi.first.wpilibj.vision.VisionRunner;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class BoilerListener implements BoilerSensor, VisionRunner.Listener<BoilerPipeline> {
  private boolean pipelineRan;
  private OptionalDouble relativeOffset;

  private final Object visionLock = new Object();

  @Override
  public void copyPipelineOutputs(BoilerPipeline pipeline) {
    synchronized (visionLock) {
      pipelineRan = true;
      relativeOffset = pipeline.getRelativeOffset();
    }
  }

  @Override
  public OptionalDouble getRelativeOffset() {
    synchronized (visionLock) {
      // Ensure the pipeline has run
      if (!pipelineRan) return OptionalDouble.empty();
      return relativeOffset;
    }
  }
}
