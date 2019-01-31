package org.suggs.fsm.execution

import org.suggs.fsm.behavior.Event

interface FsmStateManager {

    fun storeActiveState(state: String)

    fun getActiveState(): String

    fun storeDeferredEvents(vararg event: Event)

    fun getDeferredEvents(): Set<Event>

}