package org.suggs.fsm.execution

import org.slf4j.LoggerFactory
import org.suggs.fsm.behavior.Event
import java.time.LocalDateTime

open class StubFsmStateManager : FsmStateManager {

    var state: String = ""
    val deferredEvents = HashSet<Event>()
    val audits = ArrayList<Audit>()

    companion object {
        val log = LoggerFactory.getLogger(this::class.java)!!
    }

    override fun storeDeferredEvents(vararg events: Event) {
        deferredEvents.addAll(events)
    }

    override fun getDeferredEvents(): Set<Event> {
        return deferredEvents
    }

    override fun storeActiveState(state: String) {
        this.state = state
        audits.add(Audit(state))
        log.debug("Recording state change to [$state]")
    }

    override fun getActiveState(): String {
        return state
    }

    fun printAudits() {
        val buffer = StringBuffer("Transitions audit:\n")
        audits.map { buffer.append(" - ${it.timeStamp} - ${it.event}\n") }
        log.debug(buffer.toString())
    }
}

data class Audit(val event: String){
    val timeStamp: LocalDateTime = LocalDateTime.now()
}