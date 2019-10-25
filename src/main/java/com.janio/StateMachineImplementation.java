package com.janio;

import java.util.Scanner;

public class StateMachineImplementation {

  public static void main(String [] args) {
    StateMachine<ApprovalState, ApprovalEvent> machine = ApprovalWorkflowStateMachine.newInstance();
    try{
      machine.start(ApprovalState.valueOf(args[0]));
    } catch (Exception exception) {
      System.out.println("Not a valid state. Please input valid state.");
    }
    Scanner scanner = new Scanner(System.in);
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
