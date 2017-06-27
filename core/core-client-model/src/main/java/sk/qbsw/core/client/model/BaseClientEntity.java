package sk.qbsw.core.client.model;

import sk.qbsw.core.client.model.exception.DataValidationException;
import java.io.Serializable;

/**
 * The base client entity.
 * 
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public abstract class BaseClientEntity implements Serializable
{
	private static final long serialVersionUID = -7679369899156719547L;

	/**
	 * Validate.
	 *
	 * @throws DataValidationException the data validation exception
	 */
	public void validate () throws DataValidationException
	{
		// nothing to validate
	}
}
