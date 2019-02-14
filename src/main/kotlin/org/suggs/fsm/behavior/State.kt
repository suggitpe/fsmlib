package org.suggs.fsm.behavior

import org.slf4j.LoggerFactory
import org.suggs.fsm.behavior.builders.EventBuilder.Companion.anEventCalled
import org.suggs.fsm.behavior.traits.Namespace
import org.suggs.fsm.behavior.traits.Processable
import org.suggs.fsm.execution.BusinessEvent
import org.suggs.fsm.execution.FsmExecutionContext
import org.suggs.fsm.execution.UnprocessableEventException

abstract class State(name: String,
                     container: Namespace,
                     private val deferrableTriggers: Set<Trigger>,
                     val entryBehavior: Behavior,
                     val exitBehavior: Behavior)
    : Processable, Vertex(name, container) {

    companion object {
        val log = LoggerFactory.getLogger(this::class.java)!!
        const val TRANSITIONING = "TRANSITIONING"
    }

    override fun processEvent(event: BusinessEvent, fsmExecutionContext: FsmExecutionContext) {
        val validTransitions = findFireableTransitionsFor(event)
        when {
            validTransitions.size == 1 -> validTransitions.first().fire(event, fsmExecutionContext)
            validTransitions.size > 1 -> throw IllegalStateException("State [$name] has more than one valid transition")
            validTransitions.isEmpty() -> {
                if (weCanDefer(event)) {
                    log.debug("Storing deferrable event [$event] for a future time")
                    fsmExecutionContext.stateManager.storeDeferredEvents(anEventCalled(event.type).build())
                } else {
                    log.debug("No valid transitions for [${event}] from state [$name]")
                    throw UnprocessableEventException("No valid transitions from $name for $event")
                }
            }
        }
    }

    private fun findFireableTransitionsFor(event: BusinessEvent): Set<Transition> {
        return outgoing.filter { it.isFireableFor(event) }.toSet()
    }

    fun weCanDefer(businessEvent: BusinessEvent): Boolean {
        return deferrableTriggers.map { it.event }.contains(anEventCalled(businessEvent.type).build())
    }

    fun findEventsThatFireTransitionsFrom(deferredEvents: Set<Event>): Set<Event> {
        val eventsFromOutgoingTriggers = outgoing.flatMap { transition -> transition.triggers.map { trigger -> trigger.event.name } }
        return deferredEvents.filter { event -> event.name in eventsFromOutgoingTriggers }.toSet()
    }

}
