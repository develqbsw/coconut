package sk.qbsw.core.base.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * The simple state machine.
 *
 * @param <E> the element type
 * @param <S> the generic type
 * @author Tomas Lauro
 * @version 1.18.4
 * @since 1.18.4
 */
public class SimpleStateMachineImpl<E extends Stateful<S>, S extends Enum<?>> implements SimpleStateMachine<E, S>
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleStateMachineImpl.class);

	private SimpleTransitionManager<S> transitionManager;

	/**
	 * Instantiates a new state machine impl.
	 *
	 * @param transitionManager the transition manager
	 */
	public SimpleStateMachineImpl (SimpleTransitionManager<S> transitionManager)
	{
		this.transitionManager = transitionManager;
	}

	@Override
	public void changeState (E entity, S targetState)
	{
		// change state only if it is not null
		if (targetState == null)
		{
			LOGGER.debug("The state with null value is on input: entity: {}", entity);
		}
		else if (targetState.equals(entity.getState()))
		{
			// state is the same, just ignore
			LOGGER.debug("The state with same value is on input: entity: {}, state: {}", entity, targetState);
		}
		else
		{
			Set<S> transitions = transitionManager.getTransitions(entity.getState());

			if (transitions.contains(targetState))
			{
				entity.setState(targetState);
			}
			else
			{
				throw new IllegalStateException();
			}
		}
	}
}
