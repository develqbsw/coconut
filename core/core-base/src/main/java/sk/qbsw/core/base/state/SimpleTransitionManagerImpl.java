package sk.qbsw.core.base.state;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The simple transition manager.
 *
 * @param <S> the type parameter
 * @author Tomas Lauro
 * @version 1.18.4
 * @since 1.18.4
 */
public class SimpleTransitionManagerImpl<S extends Enum<?>> implements SimpleTransitionManager<S>
{
	private final Map<S, Set<S>> transitions = new HashMap<>();

	@Override
	public void addTransition (S key, S value)
	{
		if (transitions.containsKey(key))
		{
			transitions.get(key).add(value);
		}
		else
		{
			Set<S> newValue = new HashSet<>();
			newValue.add(value);
			transitions.put(key, newValue);
		}
	}

	@Override
	public Set<S> getTransitions (S state)
	{
		if (transitions.containsKey(state))
		{
			return transitions.get(state);
		}
		else
		{
			return new HashSet<>();
		}
	}
}
