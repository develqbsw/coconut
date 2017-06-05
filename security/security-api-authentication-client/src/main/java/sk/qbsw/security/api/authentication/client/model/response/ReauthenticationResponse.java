package sk.qbsw.security.api.authentication.client.model.response;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import sk.qbsw.core.api.model.response.BaseResponse;

/**
 * The reauthentication response.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.0.0
 * @since 1.0.0
 * 
 */
public class ReauthenticationResponse extends BaseResponse
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 9094797792039380937L;

	/** The authentication token. */
	@ApiModelProperty (required = true, value = "The authentication token")
	@NotNull
	private String authenticationToken;

	/**
	 * Instantiates a new reauthentication response.
	 */
	public ReauthenticationResponse ()
	{
		//default
	}

	/**
	 * Creates the.
	 *
	 * @param authenticationToken the authentication token
	 * @return the reauthentication response
	 */
	public static ReauthenticationResponse create (String authenticationToken)
	{
		ReauthenticationResponse response = new ReauthenticationResponse();
		response.setAuthenticationToken(authenticationToken);

		return response;
	}

	/**
	 * Authentication token.
	 *
	 * @param authenticationToken the authentication token
	 * @return the reauthentication response
	 */
	public ReauthenticationResponse authenticationToken (String authenticationToken)
	{
		this.authenticationToken = authenticationToken;
		return this;
	}

	/**
	 * Gets the authentication token.
	 *
	 * @return the authentication token
	 */
	public String getAuthenticationToken ()
	{
		return authenticationToken;
	}

	/**
	 * Sets the authentication token.
	 *
	 * @param authenticationToken the new authentication token
	 */
	public void setAuthenticationToken (String authenticationToken)
	{
		this.authenticationToken = authenticationToken;
	}
}
