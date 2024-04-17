package oogasalad.model.gameengine.strike;

import java.util.Optional;
import java.util.function.BiConsumer;
import oogasalad.model.gameengine.GameEngine;
import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.collidable.ownable.Ownable;

public class IncrementPointStrikePolicy implements StrikePolicy {

  @Override
  public BiConsumer<Integer, GameEngine> getStrikePolicy() {
    return (controllableId, engine) -> {
      Collidable c = engine.getCollidableContainer().getCollidable(controllableId);
      Optional<Ownable> optionalOwnable = c.getOwnable();
      optionalOwnable.ifPresent(Ownable::incrementTemporaryScore);
    };
  }
}
