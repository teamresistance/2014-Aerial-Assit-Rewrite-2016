package org.teamresistance.frc.vision;

import java.util.List;
import java.util.Optional;

public interface Source<C> {
  Optional<List<C>> getContours();
}
