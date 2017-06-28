package sk.qbsw.security.api.authentication.client.model.response;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sk.qbsw.core.client.model.response.BaseResponseBody;

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

	@ApiModelProperty (required = true, value = "The authentication token")
	@NotNull
	private String authenticationToken;

	/**
	 * Instantiates a new Reauthentication response.
	 */
	public ReauthenticationResponseBody ()
	{
	}

	private ReauthenticationResponseBody (Builder builder)
	{
		setAuthenticationToken(builder.authenticationToken);
	}

	/**
	 * New builder builder.
	 *
	 * @return the builder
	 */
	public static Builder newBuilder ()
	{
		return new Builder();
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

	@Override
	public boolean equals (Object o)
	{
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		ReauthenticationResponseBody that = (ReauthenticationResponseBody) o;

		return new EqualsBuilder().append(authenticationToken, that.authenticationToken).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(17, 37).append(authenticationToken).toHashCode();
	}

	public static final class Builder
	{
		private String authenticationToken;

		private Builder ()
		{
		}

		public Builder authenticationToken (String val)
		{
			authenticationToken = val;
			return this;
		}

		public ReauthenticationResponseBody build ()
		{
			return new ReauthenticationResponseBody(this);
		}
	}
}
