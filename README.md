# approval-flow
This application implement approval flow using state machine.
It is generic enough to create any state machine and its transition using Enums.

This contains one implementation with following states and events:

States:  APPROVED, REJECTED, PENDING

Events:  APPROVE, REJECT, HOLD

Build tool used is MAVEN.
Use below comman to build jars and run tests:
 - mvn clean install
Once jar is created in /target folder use below command to initialize and start state machine:
 - java -jar approval-flow-1.0-SNAPSHOT.jar {state}
   ex: java -jar approval-flow-1.0-SNAPSHOT.jar PENDING    will start state machine with initial state PENDING.
It will prompt you to enter next event and then using that event state machine transition to next state which will be printed on console.
