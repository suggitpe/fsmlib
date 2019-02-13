package org.suggs.fsm.behavior

import org.suggs.fsm.behavior.traits.Namespace
import org.suggs.fsm.execution.BusinessEvent
import org.suggs.fsm.execution.FsmExecutionContext

class FinalState(name: String,
                 container: Namespace,
                 entryBehavior: Behavior,
                 exitBehavior: Behavior)
    : State(name, container, HashSet(), entryBehavior, exitBehavior) {

    override fun enter(event: BusinessEvent, fsmExecutionContext: FsmExecutionContext) {
        fsmExecutionContext.stateManager.storeActiveState(deriveQualifiedName())
        // TODO send a completion event to the composite state if it exists
    }

    override fun doEntryAction(event: BusinessEvent, fsmExecutionContext: FsmExecutionContext) {
        entryBehavior.execute(event)
    }

    override fun exit(event: BusinessEvent, fsmExecutionContext: FsmExecutionContext) {
        throw NotImplementedError()
    }

    override fun doExitAction(event: BusinessEvent, fsmExecutionContext: FsmExecutionContext) {
        throw NotImplementedError()
    }
}