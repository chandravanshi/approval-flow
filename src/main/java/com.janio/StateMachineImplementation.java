package com.janio;

import java.util.Scanner;

public class StateMachineImplementation {

  public static void main(String [] args) {
    StateMachine<ApprovalState, ApprovalEvent> machine = ApprovalWorkflowStateMachine.newInstance();
    Scanner scanner = new Scanner(System.in);
    String state = null;
    if(args.length > 0) {
      state = args[0];
    }
    while(true) {
      try{
        machine.start(ApprovalState.valueOf(state));
        break;
      } catch (Exception exception) {
        System.out.println("Not a valid initial state. Please input valid initial state.");
        state = scanner.next();
      }
    }

    while(true) {
      try {
        System.out.println("Enter next event.");
        machine.onEvent(ApprovalEvent.valueOf(scanner.next()));
        System.out.println(machine.getCurrentState());
      } catch(IllegalArgumentException exception) {
        System.out.println("Not a valid event. Please enter valid event.");
      }
    }
  }
}
