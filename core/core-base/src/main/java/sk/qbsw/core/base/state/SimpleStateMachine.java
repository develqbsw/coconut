package sk.qbsw.core.base.state;

/**
 * The simple state machine.
 *
 * @param <E> the type parameter
 * @param <S> the type parameter
 * @author Tomas Lauro
 * @version 1.18.4
 * @since 1.18.4
 */
public interface SimpleStateMachine<E extends Stateful<S>, S extends Enum<?>>
{
	/**
	 * Change state.
	 *
	 * @param entity the entity
	 * @param targetState the target state
	 */
	void changeState (E entity, S targetState);
}
