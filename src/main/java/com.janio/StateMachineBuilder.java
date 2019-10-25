package com.janio;

import com.janio.EventStatePair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateMachineBuilder<S extends Enum, E extends Enum> {

  private Map<S, List<EventStatePair<S, E>>> links = new HashMap<>();

  private void link(S fromState, E event, S to) {
      links.putIfAbsent(fromState, new ArrayList<>());
      List<EventStatePair<S, E>> eventStatePairs = links.get(fromState);
      eventStatePairs.add(new EventStatePair<>(to, event));
  }

  public StateMachine<S, E> build() {
    return new SimpleStateMachine<>(null, links);
  }

  public StateTransitionBuilder and() {
    return this.new StateTransitionBuilder();
  }

  public StateTransitionBuilder begin() {
    return this.new StateTransitionBuilder();
  }

  public class StateTransitionBuilder {

    private S fromState;

   public FromStateBuilder from(S state) {
      this.fromState = state;
      return this.new FromStateBuilder();
    }

   public class FromStateBuilder {

      private E event;

      public EventBuilder onEvent(E event) {
        this.event = event;
        return this.new EventBuilder();
      }

      public class EventBuilder {

        private S toState;

        public StateMachineBuilder to(S toState) {

          this.toState = toState;
          StateMachineBuilder.this
              .link(
                  StateTransitionBuilder.this.fromState,
                  FromStateBuilder.this.event,
                  this.toState);
          return StateMachineBuilder.this;
        }

      }
    }
  }


}
