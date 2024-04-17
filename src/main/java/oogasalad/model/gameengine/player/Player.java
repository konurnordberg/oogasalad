package oogasalad.model.gameengine.player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.model.api.PlayerRecord;
import oogasalad.model.gameengine.collidable.Strikeable;
import oogasalad.model.gameengine.collidable.ownable.Scoreable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {

  private static final Logger LOGGER = LogManager.getLogger(Player.class);
  private final int playerId;
  private List<Strikeable> myStrikeables;
  private List<Scoreable> myOwnables;
  private final Map<String, Double> variables;
  private int activeControllableIndex;
  private boolean roundCompleted = false;
  private int turnsCompleted;
  private double temporaryScore;

  public Player(int id) {
    playerId = id;
    roundCompleted = false;
    turnsCompleted = 0;
    variables = new HashMap<>();
    variables.put("score", 0.0);
  }

  public void addControllables(List<Strikeable> strikeables) {
    myStrikeables = strikeables;
    activeControllableIndex = strikeables.size()-1;
  }
  public void addOwnables(List<Scoreable> ownables) {
    myOwnables = ownables;
  }

  //TODO
  public void updateActiveControllableId() {
    if(myStrikeables.size()>1){
    activeControllableIndex = (activeControllableIndex + 1) % myStrikeables.size();
    }
  }


  public void setVariable(String key, double value) {
    variables.put(key, value);
  }

  protected PlayerRecord getPlayerRecord(boolean active) {
    try {
      double score = variables.get("score");
      for (Scoreable o : myOwnables) {
        score += o.getTemporaryScore();
      }
      return new PlayerRecord(playerId, score,
          myStrikeables.get(activeControllableIndex).asGameObject().getId(),
          active);
    } catch (NullPointerException e) {
      LOGGER.warn("Invalid player");
      return null;
    }
  }

  public int getId() {
    return playerId;
  }

  public boolean isRoundCompleted() {
    return roundCompleted;
  }

  public void completeRound() {
    roundCompleted = true;
  }

  public int getControllableId() {
    return myStrikeables.get(activeControllableIndex).asGameObject().getId();
  }

  protected void setFromRecord(PlayerRecord record) {
    variables.put("score", record.score());
  }

  private void clearDelayedPoints() {
    for (Scoreable o : myOwnables) {
      o.setTemporaryScore(0);
    }
  }

  protected void applyDelayedScore() {
    for (Scoreable o : myOwnables) {
      variables.put("score", variables.get("score") + o.getTemporaryScore());
    }
  }

  public void completeTurn() {
    turnsCompleted++;
  }

  protected int getTurnsCompleted() {
    return turnsCompleted;
  }

  public void startRound() {
    roundCompleted = false;
    turnsCompleted = 0;
    clearDelayedPoints();
  }

}
