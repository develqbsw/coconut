package sk.qbsw.security.api.authentication.client.model;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sk.qbsw.core.client.model.BaseClientEntity;

import javax.validation.constraints.NotNull;

/**
 * The authentication data.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public class CSAuthenticationData extends BaseClientEntity
{
	private static final long serialVersionUID = 3587158519832722672L;

	@ApiModelProperty (required = true, value = "The master token")
	@NotNull
	private String masterToken;

	@ApiModelProperty (required = true, value = "The authentication token")
	@NotNull
	private String authenticationToken;

	public CSAuthenticationData ()
	{
	}

	private CSAuthenticationData (Builder builder)
	{
		setMasterToken(builder.masterToken);
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

	@Override
	public boolean equals (final Object other)
	{
		if (this == other)
		{
			return true;
		}
		if (other == null)
		{
			return false;
		}
		if (!getClass().equals(other.getClass()))
		{
			return false;
		}
		CSAuthenticationData castOther = (CSAuthenticationData) other;
		return new EqualsBuilder().append(masterToken, castOther.masterToken).append(authenticationToken, castOther.authenticationToken).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(-1779125485, 1569105867).append(masterToken).append(authenticationToken).toHashCode();
	}

	public static final class Builder
	{
		private String masterToken;
		private String authenticationToken;

		private Builder ()
		{
		}

		public Builder masterToken (String val)
		{
			masterToken = val;
			return this;
		}

		public Builder authenticationToken (String val)
		{
			authenticationToken = val;
			return this;
		}

		public CSAuthenticationData build ()
		{
			return new CSAuthenticationData(this);
		}
	}
}
