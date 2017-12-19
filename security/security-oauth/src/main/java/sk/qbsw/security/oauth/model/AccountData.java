package sk.qbsw.security.oauth.model;

import sk.qbsw.security.core.model.domain.User;

import java.io.Serializable;
import java.util.Map;

/**
 * The account data.
 *
 * @author Tomas Lauro
 * @version 1.18.1
 * @since 1.18.1
 */
public class AccountData implements Serializable
{
	private static final long serialVersionUID = 2405145626358950571L;

	private User user;

	private Map<String, Object> additionalInformation;

	/**
	 * Instantiates a new Account data.
	 *
	 * @param user the user
	 * @param additionalInformation the additional information
	 */
	public AccountData (User user, Map<String, Object> additionalInformation)
	{
		this.user = user;
		this.additionalInformation = additionalInformation;
	}

	/**
	 * Gets user.
	 *
	 * @return the user
	 */
	public User getUser ()
	{
		return user;
	}

	/**
	 * Gets additional information.
	 *
	 * @return the additional information
	 */
	public Map<String, Object> getAdditionalInformation ()
	{
		return additionalInformation;
	}
}
