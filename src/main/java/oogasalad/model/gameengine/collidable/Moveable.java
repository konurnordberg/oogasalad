package oogasalad.model.gameengine.collidable;

import oogasalad.model.gameengine.collidable.Collidable;
import oogasalad.model.gameengine.physicsengine.PhysicsEngine;

public class Moveable extends Collidable {

  public Moveable(int id, double mass, double x, double y,
      PhysicsEngine physicsEngine, boolean visible) {
    super(id, mass, x, y, physicsEngine, visible);
  }

  @Override
  public double[] calculateNewSpeed(Collidable other, double dt) { //gets speed of thing its
    // colliding WITH, not itself
    double massSum = other.getMass() + getMass();
    double xv = (2 * getMass() * getVelocityX() + (other.getMass() - getMass()) * other.getVelocityX()) / massSum;
    double yv = (2 * getMass() * getVelocityY() + (other.getMass() - getMass()) * other.getVelocityY()) / massSum;
    return new double [] {xv, yv};
  }

}
