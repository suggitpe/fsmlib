package org.suggs.fsm.behavior.builders

import org.suggs.fsm.behavior.FinalState
import org.suggs.fsm.behavior.FinalState.Companion.DEFAULT_FINAL_STATE_NAME
import org.suggs.fsm.behavior.Region
import org.suggs.fsm.behavior.Vertex
import org.suggs.fsm.behavior.builders.EmptyBehaviourBuilder.Companion.anEmptyBehavior

class FinalStateBuilder(name: String)
    : VertexBuilder(name) {

    constructor() : this(DEFAULT_FINAL_STATE_NAME)

    override fun withDeferrableTriggers(vararg newTriggers: TriggerBuilder): VertexBuilder {
        throw IllegalStateException("You cannot set deferrable triggers on a final state")
    }

    override fun withEntryBehavior(behavior: BehaviorBuilder): VertexBuilder {
        throw IllegalStateException("You cannot define entry behaviors on final states")
    }

    override fun withExitBehavior(behavior: BehaviorBuilder): VertexBuilder {
        throw IllegalStateException("You cannot define exit behaviors on final states")
    }

    override fun build(container: Region): Vertex {
        return FinalState(name,
                container,
                anEmptyBehavior().build(),
                anEmptyBehavior().build())
    }

}
