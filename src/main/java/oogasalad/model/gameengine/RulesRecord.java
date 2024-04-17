package oogasalad.model.gameengine;

import java.util.List;
import java.util.Map;
import oogasalad.Pair;
import oogasalad.model.gameengine.gameobject.PhysicsHandler;
import oogasalad.model.gameengine.command.Command;
import oogasalad.model.gameengine.condition.Condition;
import oogasalad.model.gameengine.statichandlers.StaticStateHandler;
import oogasalad.model.gameengine.strike.StrikePolicy;
import oogasalad.model.gameengine.turn.TurnPolicy;

public record RulesRecord(Map<Pair, List<Command>> collisionHandlers,
                          Condition winCondition, Condition roundPolicy, List<Command> advanceTurn,
                          List<Command> advanceRound,
                          Map<Pair, PhysicsHandler> physicsMap, TurnPolicy turnPolicy,
                          StaticStateHandler staticStateHandler, StrikePolicy strikePolicy) {


}
