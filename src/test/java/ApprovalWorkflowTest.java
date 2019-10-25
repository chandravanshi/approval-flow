import static com.janio.ApprovalEvent.APPROVE;
import static com.janio.ApprovalEvent.HOLD;
import static com.janio.ApprovalEvent.REJECT;
import static com.janio.ApprovalState.APPROVED;
import static com.janio.ApprovalState.PENDING;
import static com.janio.ApprovalState.REJECTED;

import com.janio.ApprovalEvent;
import com.janio.ApprovalState;
import com.janio.ApprovalWorkflowStateMachine;
import com.janio.StateMachine;
import com.janio.exception.StateMachineException;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ApprovalWorkflowTest {

  private static StateMachine<ApprovalState, ApprovalEvent> stateMachine;


  @BeforeClass
  public static void init() {
    stateMachine = ApprovalWorkflowStateMachine.newInstance();
  }

  @DataProvider
  public static Object[][] validTransition() {
    Object [][] list = {{APPROVED, HOLD, PENDING},
        {APPROVED, REJECT, REJECTED},
        {PENDING, APPROVE, APPROVED},
        {PENDING, REJECT, REJECTED},
        {REJECTED, HOLD, PENDING}};
    return list;
  }

  @DataProvider
  public static Object[][] noTransition() {
    Object [][] list = {{APPROVED, APPROVE},
        {PENDING, HOLD},
        {REJECTED, APPROVE},
        {REJECTED, REJECT}};
    return list;
  }

  @DataProvider
  public static Object[] multipleTransition() {
    StateTransition firstTransition = new StateTransition();
    firstTransition.initialState = PENDING;
    firstTransition.finalState = APPROVED;
    firstTransition.events = Arrays.asList(REJECT, HOLD, REJECT, APPROVE, HOLD, APPROVE);

    StateTransition secondTransition = new StateTransition();
    secondTransition.initialState = APPROVED;
    secondTransition.finalState = REJECTED;
    secondTransition.events = Arrays.asList(REJECT, HOLD, REJECT, APPROVE, HOLD, REJECT);

    Object[] list = {firstTransition, secondTransition};
    return list;
  }


  @Test(dataProvider = "validTransition")
  public void testValidStateTransition(Object[] input) {
    stateMachine.start((ApprovalState) input[0]);
    stateMachine.onEvent((ApprovalEvent) input[1]);
    Assert.assertEquals(input[2], stateMachine.getCurrentState(), "Invalid state transition found");
  }

  @Test(dataProvider = "noTransition")
  public void testNoStateTransition(Object[] input) {
    stateMachine.start((ApprovalState) input[0]);
    stateMachine.onEvent((ApprovalEvent) input[1]);
    Assert.assertEquals(input[0], stateMachine.getCurrentState(), "Invalid state transition found");
  }

  @Test(expectedExceptions = StateMachineException.class)
  public void shouldThrowExceptionOnStartWithNullState() {
    stateMachine.start(null);
  }

  @Test(dataProvider = "multipleTransition")
  public void shouldComeToRightStateAfterMultipleTransitions(Object input) {
    StateTransition transition = (StateTransition) input;
    stateMachine.start(transition.initialState);
    transition.events.forEach(event -> stateMachine.onEvent(event));
    Assert.assertEquals(transition.finalState, stateMachine.getCurrentState());
  }

  private static class StateTransition {
    ApprovalState       initialState;
    ApprovalState       finalState;
    List<ApprovalEvent> events;
  }

}
