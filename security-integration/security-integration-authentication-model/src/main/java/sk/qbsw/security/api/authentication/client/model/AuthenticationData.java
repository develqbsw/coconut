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
public class AuthenticationData extends BaseClientEntity
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3587158519832722672L;

	/** The master token. */
	@ApiModelProperty (required = true, value = "The master token")
	@NotNull
	private String masterToken;

	/** The authentication token. */
	@ApiModelProperty (required = true, value = "The authentication token")
	@NotNull
	private String authenticationToken;

	/**
	 * Instantiates a new ICS authentication data.
	 */
	public AuthenticationData ()
	{
		// default
	}

	/**
	 * Creates the.
	 *
	 * @param masterToken the master token
	 * @param authenticationToken the authentication token
	 * @return the ICS authentication data
	 */
	public static AuthenticationData create (String masterToken, String authenticationToken)
	{
		AuthenticationData data = new AuthenticationData();
		data.setMasterToken(masterToken);
		data.setAuthenticationToken(authenticationToken);

		return data;
	}

	/**
	 * Master token.
	 *
	 * @param masterToken the master token
	 * @return the ICS authentication data
	 */
	public AuthenticationData masterToken (String masterToken)
	{
		this.masterToken = masterToken;
		return this;
	}

	/**
	 * Authentication token.
	 *
	 * @param authenticationToken the authentication token
	 * @return the ICS authentication data
	 */
	public AuthenticationData authenticationToken (String authenticationToken)
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

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		AuthenticationData castOther = (AuthenticationData) other;
		return new EqualsBuilder().append(masterToken, castOther.masterToken).append(authenticationToken, castOther.authenticationToken).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(-1779125485, 1569105867).append(masterToken).append(authenticationToken).toHashCode();
	}
}
