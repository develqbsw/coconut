package sk.qbsw.security.oauth.model;

import sk.qbsw.security.core.model.domain.User;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The account data.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class AccountData implements Serializable
{
	@NotNull
	private User user;

	@NotNull
	private Map<String, Object> additionalInformation = new HashMap<>();

	/**
	 * Instantiates a new Verification data.
	 */
	public AccountData ()
	{
	}

	/**
	 * Instantiates a new Verification data.
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
	 * Sets user.
	 *
	 * @param user the user
	 */
	public void setUser (User user)
	{
		this.user = user;
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

	/**
	 * Sets additional information.
	 *
	 * @param additionalInformation the additional information
	 */
	public void setAdditionalInformation (Map<String, Object> additionalInformation)
	{
		this.additionalInformation = additionalInformation;
	}
}
