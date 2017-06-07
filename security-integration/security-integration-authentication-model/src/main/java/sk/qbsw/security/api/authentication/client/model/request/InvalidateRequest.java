package sk.qbsw.security.api.authentication.client.model.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import sk.qbsw.core.api.model.request.BaseRequest;

/**
 * The Invalidate request.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since   1.18.0
 */
public class InvalidateRequest extends BaseRequest
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4789897540369584317L;

	/** The master token. */
	@ApiModelProperty (required = true, value = "The master token")
	@NotNull
	private String masterToken;

	/** The authentication token. */
	@ApiModelProperty (required = true, value = "The authentication token")
	@NotNull
	private String authenticationToken;

	/**
	 * Master token.
	 *
	 * @param masterToken the master token
	 * @return the invalidate request
	 */
	public InvalidateRequest masterToken (String masterToken)
	{
		this.masterToken = masterToken;
		return this;
	}

	/**
	 * Authentication token.
	 *
	 * @param authenticationToken the authentication token
	 * @return the invalidate request
	 */
	public InvalidateRequest authenticationToken (String authenticationToken)
	{
		this.authenticationToken = authenticationToken;
		return this;
	}

	/**
	 * Gets the master token.
	 *
	 * @return the master token
	 */
	public String getMasterToken ()
	{
		return masterToken;
	}

	/**
	 * Sets the master token.
	 *
	 * @param masterToken the new master token
	 */
	public void setMasterToken (String masterToken)
	{
		this.masterToken = masterToken;
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
