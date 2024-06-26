package oogasalad.model.gameparser;

import oogasalad.model.api.data.GameObjectProperties;
import oogasalad.model.gameengine.gameobject.DefaultStrikeable;
import oogasalad.model.gameengine.gameobject.GameObject;
import oogasalad.model.gameengine.gameobject.controllable.DefaultControllable;
import oogasalad.model.gameengine.gameobject.scoreable.DefaultScoreable;

public class CollidableFactory {

  public static GameObject createCollidable(GameObjectProperties co) {

    GameObject c = new GameObject(co);

    if (co.properties().contains("strikeable")) {
      c.addStrikeable(new DefaultStrikeable(c));
    }
    if (co.properties().contains("scoreable")) {
      c.addScoreable(new DefaultScoreable(c));
    }
    if (co.properties().contains("controllable")) {
      c.addControllable(new DefaultControllable(c));
    }
    return c;
  }
}
