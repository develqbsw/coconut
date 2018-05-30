package sk.qbsw.security.oauth.service;

import java.util.UUID;

/**
 * Base support class for Id generator service
 * 
 * @author Dalibor Rak
 * @version 1.13.3
 * @since 1.13.3
 */
public abstract class BaseIdGeneratorService
{
	/**
	 * Instantiates a new a id generator service.
	 */
	public BaseIdGeneratorService ()
	{
		super();
	}

	/**
	 * Clear uid.
	 *
	 * @param uuid the uuid
	 * @return the string
	 */
	protected String clearUID (UUID uuid)
	{
		return uuid.toString().replaceAll("-", "").toUpperCase();
	}
}
