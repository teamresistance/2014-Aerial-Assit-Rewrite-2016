package org.teamresistance.frc.subsystem.snorfler;

import org.junit.jupiter.api.Test;
import org.strongback.components.Motor;
import org.strongback.mock.Mock;

import static org.assertj.core.api.Assertions.assertThat;

class SnorflerTest {
  private final Motor motor = Mock.stoppedMotor();
  private final Snorfler snorfler = new Snorfler(motor);

  // Complex tests build on the assumptions established by former tests, so we're really only
  // testing a particular functionality once. That way, when a group of related tests fails we
  // can find the root cause by tracing the problem to the simplest test (assumption) that failed.

  @Test
  void toggle_WhenIdle_ShouldSnorfle() {
    snorfler.toggleSnorfling();
    assertThat(motor.getSpeed()).isPositive();
  }

  @Test
  void toggle_WhenSnorfling_ShouldIdle() {
    snorfler.toggleSnorfling(); // start snorfling
    snorfler.toggleSnorfling(); // stop snorfling
    assertThat(motor.getSpeed()).isZero();
  }

  @Test
  void reverse_WhenIdle_ShouldReverse() {
    snorfler.startReversing();
    assertThat(motor.getSpeed()).isNegative();
  }

  @Test
  void reverse_WhenSnorfling_ShouldStillSnorfle() {
    snorfler.toggleSnorfling(); // start snorfling
    snorfler.startReversing(); // snorfling takes precedence
    assertThat(motor.getSpeed()).isPositive();
  }

  @Test
  void toggle_WhenReversing_ShouldSnorfle() {
    snorfler.startReversing(); // start reversing
    snorfler.toggleSnorfling(); // snorfling takes precedence
    assertThat(motor.getSpeed()).isPositive();
  }

  @Test
  void stopReversing_WhenReversing_ShouldStop() {
    snorfler.startReversing();
    snorfler.stopReversing();
    assertThat(motor.getSpeed()).isZero();
  }

  @Test
  void stopReversing_WhenSnorfling_ShouldStillSnorfle() {
    snorfler.toggleSnorfling(); // start snorfling
    snorfler.stopReversing();
    assertThat(motor.getSpeed()).isPositive();
  }

  /**
   * Edge case: pressing toggle while holding reverse should snorfle. Releasing reverse will have
   * no effect. This test exists for clarity; its assumptions are already covered by the unit
   * tests.
   */
  @Test
  void testEdgeCase() {
    // Hold down reverse
    snorfler.startReversing();

    // Press toggle
    snorfler.toggleSnorfling();

    // Should snorfle
    assertThat(motor.getSpeed()).isPositive();

    // Release reverse
    snorfler.stopReversing();

    // Should have no effect
    assertThat(motor.getSpeed()).isPositive();
  }
}