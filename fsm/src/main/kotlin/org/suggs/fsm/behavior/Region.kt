package org.suggs.fsm.behavior

import org.suggs.fsm.execution.NamespaceObjectMapper

class Region(name: String,
             var vertices: Map<String, Vertex> = HashMap(),
             var transitions: Map<String, Transition> = HashMap())
    : Namespace(name) {

    fun getInitialState(): Vertex {
        val pseudoState = vertices.values.find { it -> it is PseudoState && it.isInitialPseudoState() }
        if (pseudoState != null) return pseudoState
        else throw IllegalStateException("No initial state defined for region $name")
    }

    fun findStateCalled(stateName: String): State {
        return vertices[stateName] as State
    }

    fun getAncestorList(): List<Region> {
        // TODO: if this is a composite state we need to traverse to owning region
        return ArrayList()
    }

    override fun registerMembersWithNamespace(namespaceContext: NamespaceObjectMapper) {
        vertices.values.map { it.registerWithNamespace(namespaceContext) }
        transitions.values.map { it.registerWithNamespace(namespaceContext) }
    }

    fun findTransitionCalled(transitionName: String): Transition {
        return transitions[transitionName] as Transition
    }
}