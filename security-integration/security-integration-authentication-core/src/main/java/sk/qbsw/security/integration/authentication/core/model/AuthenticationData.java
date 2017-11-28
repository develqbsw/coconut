package sk.qbsw.security.integration.authentication.core.model;

import sk.qbsw.security.core.model.domain.User;

import java.io.Serializable;

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
	 */
	public AuthenticationData (String masterToken, String authenticationToken, User user)
	{
		this.masterToken = masterToken;
		this.authenticationToken = authenticationToken;
		this.user = user;
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
}
