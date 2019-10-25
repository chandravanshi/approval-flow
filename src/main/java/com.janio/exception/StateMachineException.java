package com.janio.exception;


public class StateMachineException extends RuntimeException {

  public StateMachineException(ErrorMapping errorMapping) {
    super(errorMapping.getMessage());
  }
}
