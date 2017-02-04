package org.teamresistance.frc.hid;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DaveKnobTest {

  @Test
  void whenInputOutsideDeadband_ShouldReturnZero() {
    // Robot needs to correct +40deg
    DaveKnob knob = new DaveKnob(() -> 40, () -> 0);
    assertThat(knob.read()).isZero();
  }

  @Test
  void whenInputNearFeedback_ShouldUseShortestDistance() {
    // Robot needs to correct -10deg
    DaveKnob knob1 = new DaveKnob(() -> 350, () -> 0);
    DaveKnob knob2 = new DaveKnob(() -> 170, () -> 180);
    assertThat(knob1.read()).isEqualTo(knob2.read());
  }
}