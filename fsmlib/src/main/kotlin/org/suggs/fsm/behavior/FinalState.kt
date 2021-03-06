package org.suggs.fsm.behavior

import org.suggs.fsm.behavior.Event.Companion.COMPLETION_EVENT_NAME
import org.suggs.fsm.execution.BusinessEvent
import org.suggs.fsm.execution.FsmExecutionContext

class FinalState(name: String,
                 container: Region,
                 entryBehavior: Behavior,
                 exitBehavior: Behavior)
    : State(name, container, HashSet(), entryBehavior, exitBehavior) {

    companion object {
        const val DEFAULT_FINAL_STATE_NAME = "FINAL"
    }

    override fun enter(event: BusinessEvent, fsmExecutionContext: FsmExecutionContext) {
        fsmExecutionContext.stateManager.storeActiveState(deriveQualifiedName())
        val completionEvent = BusinessEvent(COMPLETION_EVENT_NAME, event.identifier)
        container.container.processEvent(completionEvent, fsmExecutionContext)
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