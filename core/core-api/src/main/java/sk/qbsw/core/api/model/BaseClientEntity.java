package sk.qbsw.core.api.model;

import java.io.Serializable;

import sk.qbsw.core.api.exception.DataValidationException;

/**
 * The base client entity.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.18.0
 * @since 1.18.0
 * 
 */
public abstract class BaseClientEntity implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7679369899156719547L;

	/**
	 * Validate.
	 *
	 * @throws DataValidationException the data validation exception
	 */
	public void validate () throws DataValidationException
	{
		//nothing to validate
	}
}
