package com.janio;

import com.janio.exception.ErrorMapping;
import com.janio.exception.StateMachineException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SimpleStateMachine<S extends Enum, E extends Enum> implements StateMachine<S, E> {

  private Map<S, List<EventStatePair<S, E>>> links;
  private S                                  currentState;

  public SimpleStateMachine(S currentState, Map<S, List<EventStatePair<S, E>>> links) {
    this.currentState = currentState;
    this.links = links;
  }

  @Override
  public S start(S currentState) {
    if (currentState == null) {
      System.out.println("Current state should not be null");
      throw new StateMachineException(ErrorMapping.INVALID_START_STATE_ERROR);
    }
    this.currentState = currentState;
    return currentState;
  }

  @Override
  public S getCurrentState() {
    return this.currentState;
  }

  @Override
  public S onEvent(E event) {

    if (currentState == null) {
      throw new IllegalStateException();
    }

    List<EventStatePair<S, E>> eventStatePairs = links.get(
        currentState);
    if (eventStatePairs == null) {
      System.out.println("Illegal Operation on StateMachine");
      throw new StateMachineException(ErrorMapping.CONFIGURATION_ERROR);
    }

    Optional<S> state = eventStatePairs.stream()
        .filter(eventStatePair -> eventStatePair.event == event)
        .findFirst()
        .map(eventStatePair -> eventStatePair.state);

    state.ifPresent(s -> this.currentState = s);
    return this.currentState;
  }

}
