package org.suggs.fsm.behavior.builders

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.suggs.fsm.behavior.builders.BehaviorBuilder.Companion.aBehaviorCalled
import org.suggs.fsm.behavior.builders.EmptyBehaviourBuilder.Companion.anEmptyBehavior
import org.suggs.fsm.execution.BusinessEvent
import org.suggs.fsm.execution.BusinessObjectIdentifier


class BehaviorBuilderTest {

    @Test
    fun `builds behaviors that have functions associated with them`() {
        val behavior = aBehaviorCalled("TestBehavior").withAction { throw IllegalStateException() }.build()
        // this may look weird, I want to show that the behavior is executed
        assertThrows<IllegalStateException> { behavior.execute(BusinessEvent("", BusinessObjectIdentifier("", "", 0))) }
    }

    @Test
    fun `builds empty behaviors that have no side effects`() {
        val empty = anEmptyBehavior().build()
        empty.execute(BusinessEvent("", BusinessObjectIdentifier("", "", 0)))
    }
}