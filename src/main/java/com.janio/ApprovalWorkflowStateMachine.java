package com.janio;

import static com.janio.ApprovalEvent.APPROVE;
import static com.janio.ApprovalEvent.HOLD;
import static com.janio.ApprovalEvent.REJECT;
import static com.janio.ApprovalState.APPROVED;
import static com.janio.ApprovalState.PENDING;
import static com.janio.ApprovalState.REJECTED;

public class ApprovalWorkflowStateMachine {

  public static StateMachine<ApprovalState, ApprovalEvent> newInstance() {
    return new StateMachineBuilder<ApprovalState, ApprovalEvent>().begin().from(APPROVED)
        .onEvent(REJECT).to(REJECTED)
        .and()
        .from(PENDING).onEvent(APPROVE).to(APPROVED)
        .and()
        .from(PENDING).onEvent(REJECT).to(REJECTED)
        .and()
        .from(APPROVED).onEvent(HOLD).to(PENDING)
        .and()
        .from(REJECTED).onEvent(HOLD).to(PENDING)
        .build();
  }
}
