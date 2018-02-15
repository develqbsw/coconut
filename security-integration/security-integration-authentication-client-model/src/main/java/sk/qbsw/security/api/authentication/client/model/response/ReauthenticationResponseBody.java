package sk.qbsw.security.api.authentication.client.model.response;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sk.qbsw.core.client.model.response.BaseResponseBody;
import sk.qbsw.security.api.authentication.client.model.CSGeneratedTokenData;

import javax.validation.constraints.NotNull;

/**
 * The reauthentication response.
 *
 * @author Tomas Lauro
 * @version 1.0.0
 * @since 1.0.0
 */
public class ReauthenticationResponseBody extends BaseResponseBody
{
	private static final long serialVersionUID = 9094797792039380937L;

	@ApiModelProperty (required = true, value = "The authentication token data")
	@NotNull
	private CSGeneratedTokenData authenticationTokenData;

	/**
	 * Instantiates a new Reauthentication response.
	 */
	public ReauthenticationResponseBody ()
	{
	}

	/**
	 * Instantiates a new Reauthentication response body.
	 *
	 * @param authenticationTokenData the authentication token data
	 */
	public ReauthenticationResponseBody (CSGeneratedTokenData authenticationTokenData)
	{
		this.authenticationTokenData = authenticationTokenData;
	}

	/**
	 * Gets authentication token data.
	 *
	 * @return the authentication token data
	 */
	public CSGeneratedTokenData getAuthenticationTokenData ()
	{
		return authenticationTokenData;
	}

	/**
	 * Sets authentication token data.
	 *
	 * @param authenticationTokenData the authentication token data
	 */
	public void setAuthenticationTokenData (CSGeneratedTokenData authenticationTokenData)
	{
		this.authenticationTokenData = authenticationTokenData;
	}

	@Override
	public boolean equals (Object o)
	{
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		ReauthenticationResponseBody that = (ReauthenticationResponseBody) o;

		return new EqualsBuilder().append(authenticationTokenData, that.authenticationTokenData).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(17, 37).append(authenticationTokenData).toHashCode();
	}
}
