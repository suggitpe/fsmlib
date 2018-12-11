package org.suggs.fsm.uml2.basicbehaviors;

import org.suggs.fsm.uml2.kernel.IClassifier;
import org.suggs.fsm.uml2.scribe.optimisation.IOptimisable;

/**
 * Defines an implementation of the UML 2.0 BehavioredClassifier
 * class. BehavioredClassifiers provide context for Behaviors.
 * <p>
 * Note that the interface may not define a complete representation of
 * the UML class - only those attributes relevant to Scribe state
 * machines are accessible and additional attributes may have been
 * added to support implementation of the state machine runtime.
 */
public interface IBehavioredClassifier extends IClassifier, IOptimisable {

    /**
     * Gets the behavior owned by this classifier.
     */
    IBehavior getOwnedBehavior();

    /**
     * Sets the behavior owned by this classifier.
     */
    void setOwnedBehavior(IBehavior ownedBehavior);

}
