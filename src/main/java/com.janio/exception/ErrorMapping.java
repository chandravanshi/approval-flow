package com.janio.exception;

public enum ErrorMapping {

  INVALID_START_STATE_ERROR("Invalid start state"),
  CONFIGURATION_ERROR("Configuration Error");

  private String message;


  public String getMessage() {
    return message;
  }

  ErrorMapping(String message) {
    this.message = message;
  }
}
