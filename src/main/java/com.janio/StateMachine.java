package com.janio;

public interface StateMachine<S extends Enum, E extends Enum> {

  S start(S currentState);

  S getCurrentState();

  S onEvent(E event);

}
