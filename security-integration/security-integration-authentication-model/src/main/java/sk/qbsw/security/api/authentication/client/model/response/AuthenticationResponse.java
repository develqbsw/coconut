package sk.qbsw.security.api.authentication.client.model.response;

import io.swagger.annotations.ApiModelProperty;
import sk.qbsw.core.client.model.response.BaseResponseBody;
import sk.qbsw.security.api.authentication.client.model.AuthenticationData;
import sk.qbsw.security.api.authentication.client.model.UserData;

import javax.validation.constraints.NotNull;

/**
 * The authentication response.
 * 
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public class AuthenticationResponse extends BaseResponseBody
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7533946963382197126L;

	/** The authentication data. */
	@ApiModelProperty (required = true, value = "The authentication data")
	@NotNull
	private AuthenticationData authenticationData;

	/** The user data. */
	@ApiModelProperty (required = true, value = "The user data")
	@NotNull
	private UserData userData;

	/**
	 * Creates the.
	 *
	 * @param authenticationData the authentication data
	 * @param userData the user data
	 * @return the authentication response
	 */
	public static AuthenticationResponse create (AuthenticationData authenticationData, UserData userData)
	{
		AuthenticationResponse response = new AuthenticationResponse();
		response.setAuthenticationData(authenticationData);
		response.setUserData(userData);
		return response;
	}

	/**
	 * Authentication data.
	 *
	 * @param authenticationData the authentication data
	 * @return the authentication response
	 */
	public AuthenticationResponse authenticationData (AuthenticationData authenticationData)
	{
		this.authenticationData = authenticationData;
		return this;
	}

	/**
	 * User data.
	 *
	 * @param userData the user data
	 * @return the authentication response
	 */
	public AuthenticationResponse userData (UserData userData)
	{
		this.userData = userData;
		return this;
	}

	/**
	 * Gets the authentication data.
	 *
	 * @return the authentication data
	 */
	public AuthenticationData getAuthenticationData ()
	{
		return authenticationData;
	}

	/**
	 * Sets the authentication data.
	 *
	 * @param authenticationData the new authentication data
	 */
	public void setAuthenticationData (AuthenticationData authenticationData)
	{
		this.authenticationData = authenticationData;
	}

	/**
	 * Gets the user data.
	 *
	 * @return the user data
	 */
	public UserData getUserData ()
	{
		return userData;
	}

	/**
	 * Sets the user data.
	 *
	 * @param userData the new user data
	 */
	public void setUserData (UserData userData)
	{
		this.userData = userData;
	}
}
