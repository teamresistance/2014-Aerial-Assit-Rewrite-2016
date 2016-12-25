package org.teamresistance.frc.vision;

public interface Locator<T, L> {
  L locate(T target);
}
