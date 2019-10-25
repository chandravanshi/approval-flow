package com.janio;

class EventStatePair<S,E> {
  final S state;
  final E event;

  EventStatePair(S state, E event) {
    this.state = state;
    this.event = event;
  }
}
