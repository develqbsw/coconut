package sk.qbsw.security.core.service.mapper;

import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.security.base.model.DataActivityStates;

/**
 * The output data mapper base.
 *
 * @author Tomas Lauro
 * @version 2.5.0
 * @since 2.5.0
 */
public abstract class OutputDataMapperBase
{
	/**
	 * Map to data activity state data activity states.
	 *
	 * @param state the state
	 * @return the data activity states
	 */
	protected DataActivityStates mapToDataActivityState (ActivityStates state)
	{
		switch (state)
		{
			case ACTIVE:
				return DataActivityStates.ACTIVE;
			case INACTIVE:
				return DataActivityStates.INACTIVE;
			default:
				throw new IllegalArgumentException("Invalid activity  state: " + state.name());
		}
	}
}
