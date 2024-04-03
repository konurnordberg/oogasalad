package oogasalad.model.gameengine.collidable;

import java.util.List;
import java.util.Stack;
import oogasalad.model.api.CollidableRecord;

public abstract class Collidable {
  private final double myMass;
  private double myX;
  private double myY;
  private double myVelocityX;
  private double myVelocityY;
  private int myId;
  private double myNextX;
  private double myNextY;
  private double myNextVelocityX;
  private double myNextVelocityY;
  private boolean myVisible;
  private Stack<List<Integer>> locationHistory;

  public Collidable(int id, double mass, double x, double y,
      boolean visible) {
    myId = id;
    myMass = mass;
    myX = x;
    myY = y;
    myVelocityX = 0.0;
    myVelocityY = 0.0;
    myNextX = x;
    myNextY = y;
    myVisible = visible;
  }
  public void onCollision(Collidable other, double dt) {
    double[] result = other.calculateNewSpeed(this, dt);
    myNextVelocityX = result[0];
    myNextVelocityY = result[1];
  }

  public void updatePostCollisionVelocity() {
    myVelocityY = myNextVelocityY;
    myVelocityX = myNextVelocityX;
  }

  public abstract double[] calculateNewSpeed(Collidable other, double dt);

  public CollidableRecord getCollidableRecord() {
    return new CollidableRecord(myId, myMass, myX, myY, myVelocityX, myVelocityY, myVisible);
  }

  public void move(double dt) {
    myNextX = myX + dt * myVelocityX;
    myNextY = myY + dt * myVelocityY;
  }

  /**
  public void addToLocationHistory(List<Integer> newLocation) {
    locationHistory.push(newLocation);
  }

  public void moveToOldLocation(List<Integer> newLocation) {
    locationHistory.pop();
    myX = locationHistory.peek().get(0);
    myY = locationHistory.peek().get(1);
  }
  */

  public void update() {
    myX = myNextX;
    myY = myNextY;
   }

  public void applyInitialVelocity(double magnitude, double direction) {
    myVelocityX = magnitude * Math.cos(direction);
    myNextVelocityX = myVelocityX;
    myVelocityY = magnitude * Math.sin(direction);
    myNextVelocityY = myVelocityY;

  }

  protected double getVelocityX() {
    return myVelocityX;
  }
  protected double getVelocityY() {
    return myVelocityY;
  }
  protected double getMass() {
    return myMass;
  }
  public int getId() {
    return myId;
  }

  protected boolean getVisible() {
    return myVisible;
  }
  protected double getX() {
    return myX;
  }
  protected double getY() {
    return myY;
  }

}