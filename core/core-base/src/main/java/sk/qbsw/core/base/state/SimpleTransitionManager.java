package sk.qbsw.core.base.state;

import java.util.Set;

/**
 * The simple transition manager for state machine.
 *
 * @author Tomas Lauro
 * @param <S> the state
 * @version 1.18.4
 * @since 1.18.4
 */
public interface SimpleTransitionManager<S extends Enum<?>>
{
	/**
	 * Adds the transition.
	 *
	 * @param key the key
	 * @param value the value
	 */
	void addTransition (S key, S value);

	/**
	 * Gets the valid transitions for given state.
	 *
	 * @param state the state
	 * @return the valid transitions
	 */
	Set<S> getTransitions (S state);
}
