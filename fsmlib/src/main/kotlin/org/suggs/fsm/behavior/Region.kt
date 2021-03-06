package org.suggs.fsm.behavior

import org.suggs.fsm.behavior.traits.RegionContainer
import org.suggs.fsm.execution.NamespaceObjectMapper

class Region(name: String,
             var container: RegionContainer,
             var vertices: Map<String, Vertex> = HashMap(),
             var transitions: Map<String, Transition> = HashMap())
    : NamedElementContainer(name, container) {

    fun getInitialState(): Vertex {
        val pseudoState = vertices.values.find { it is PseudoState && it.isInitialPseudoState() }
        if (pseudoState != null) return pseudoState
        else throw IllegalStateException("No initial state defined for region $name")
    }

    fun findStateCalled(stateName: String): State {
        return vertices[stateName] as State
    }

    override fun registerMembersWithNamespace(namespaceContext: NamespaceObjectMapper) {
        vertices.values.map { it.registerWithNamespace(namespaceContext) }
        transitions.values.map { it.registerWithNamespace(namespaceContext) }
    }

    fun findTransitionCalled(transitionName: String): Transition {
        return transitions[transitionName] as Transition
    }

}
