package sk.qbsw.security.integration.authentication.core.model;

import sk.qbsw.security.core.model.domain.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The authentication data.
 *
 * @author Tomas Lauro
 * @version 1.0.0
 * @since 1.0.0
 */
public class AuthenticationData implements Serializable
{
	private String masterToken;

	private String authenticationToken;

	private User user;

	private Map<String, Object> additionalInformation = new HashMap<>();

	/**
	 * Instantiates a new Authentication data.
	 */
	public AuthenticationData ()
	{
	}

	/**
	 * Instantiates a new Authentication data.
	 *
	 * @param masterToken the master token
	 * @param authenticationToken the authentication token
	 * @param user the user
	 * @param additionalInformation the additional information
	 */
	public AuthenticationData (String masterToken, String authenticationToken, User user, Map<String, Object> additionalInformation)
	{
		this.masterToken = masterToken;
		this.authenticationToken = authenticationToken;
		this.user = user;
		this.additionalInformation = additionalInformation;
	}

	/**
	 * Gets master token.
	 *
	 * @return the master token
	 */
	public String getMasterToken ()
	{
		return masterToken;
	}

	/**
	 * Sets master token.
	 *
	 * @param masterToken the master token
	 */
	public void setMasterToken (String masterToken)
	{
		this.masterToken = masterToken;
	}

	/**
	 * Gets authentication token.
	 *
	 * @return the authentication token
	 */
	public String getAuthenticationToken ()
	{
		return authenticationToken;
	}

	/**
	 * Sets authentication token.
	 *
	 * @param authenticationToken the authentication token
	 */
	public void setAuthenticationToken (String authenticationToken)
	{
		this.authenticationToken = authenticationToken;
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
