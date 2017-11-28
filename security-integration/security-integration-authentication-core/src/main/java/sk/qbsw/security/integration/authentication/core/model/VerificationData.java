package sk.qbsw.security.integration.authentication.core.model;

import sk.qbsw.security.core.model.domain.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The verification data.
 *
 * @author Tomas Lauro
 * @version 1.0.0
 * @since 1.0.0
 */
public class VerificationData implements Serializable
{
	private User user;

	private Map<String, Object> additionalInformation = new HashMap<>();

	/**
	 * Instantiates a new Verification data.
	 */
	public VerificationData ()
	{
	}

	/**
	 * Instantiates a new Verification data.
	 *
	 * @param user the user
	 * @param additionalInformation the additional information
	 */
	public VerificationData (User user, Map<String, Object> additionalInformation)
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
