package sk.qbsw.security.api.authentication.client.model.response;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sk.qbsw.core.client.model.response.BaseResponseBody;
import sk.qbsw.security.api.authentication.client.model.CSAuthenticationData;
import sk.qbsw.security.api.authentication.client.model.CSUserData;

import javax.validation.constraints.NotNull;

/**
 * The authentication response.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public class AuthenticationResponseBody extends BaseResponseBody
{
	private static final long serialVersionUID = 7533946963382197126L;

	@ApiModelProperty (required = true, value = "The authentication data")
	@NotNull
	private CSAuthenticationData authenticationData;

	@ApiModelProperty (required = true, value = "The user data")
	@NotNull
	private CSUserData userData;

	public AuthenticationResponseBody ()
	{
	}

	private AuthenticationResponseBody (Builder builder)
	{
		setAuthenticationData(builder.authenticationData);
		setUserData(builder.userData);
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
	 * Gets the authentication data.
	 *
	 * @return the authentication data
	 */
	public CSAuthenticationData getAuthenticationData ()
	{
		return authenticationData;
	}

	/**
	 * Sets the authentication data.
	 *
	 * @param authenticationData the new authentication data
	 */
	public void setAuthenticationData (CSAuthenticationData authenticationData)
	{
		this.authenticationData = authenticationData;
	}

	/**
	 * Gets the user data.
	 *
	 * @return the user data
	 */
	public CSUserData getUserData ()
	{
		return userData;
	}

	/**
	 * Sets the user data.
	 *
	 * @param userData the new user data
	 */
	public void setUserData (CSUserData userData)
	{
		this.userData = userData;
	}

	@Override
	public boolean equals (Object o)
	{
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		AuthenticationResponseBody that = (AuthenticationResponseBody) o;

		return new EqualsBuilder().append(authenticationData, that.authenticationData).append(userData, that.userData).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(16656587, 46548987).append(authenticationData).append(userData).toHashCode();
	}

	public static final class Builder
	{
		private CSAuthenticationData authenticationData;
		private CSUserData userData;

		private Builder ()
		{
		}

		public Builder authenticationData (CSAuthenticationData val)
		{
			authenticationData = val;
			return this;
		}

		public Builder userData (CSUserData val)
		{
			userData = val;
			return this;
		}

		public AuthenticationResponseBody build ()
		{
			return new AuthenticationResponseBody(this);
		}
	}
}
