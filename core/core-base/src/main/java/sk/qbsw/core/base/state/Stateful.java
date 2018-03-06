package sk.qbsw.core.base.state;

/**
 * The stateful entity.
 *
 * @param <S> the generic type
 * @author Tomas Lauro
 * @version 1.18.4
 * @since 1.18.4
 */
public interface Stateful<S extends Enum<?>>
{
	/**
	 * Change state.
	 *
	 * @param state the state
	 */
	void changeState (S state);

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	void setState (S state);

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	S getState ();
}
