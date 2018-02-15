package sk.qbsw.security.api.authentication.client.model.response;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sk.qbsw.core.client.model.response.BaseResponseBody;
import sk.qbsw.security.api.authentication.client.model.CSGeneratedTokenData;
import sk.qbsw.security.api.authentication.client.model.CSAccountData;

import javax.validation.constraints.NotNull;

/**
 * The authentication response.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.0
 */
public class AuthenticationResponseBody extends BaseResponseBody
{
	private static final long serialVersionUID = 7533946963382197126L;

	@ApiModelProperty (required = true, value = "The master token data")
	@NotNull
	private CSGeneratedTokenData masterTokenData;

	@ApiModelProperty (required = true, value = "The authentication token data")
	@NotNull
	private CSGeneratedTokenData authenticationTokenData;

	@ApiModelProperty (required = true, value = "The account data")
	@NotNull
	private CSAccountData accountData;

	/**
	 * Instantiates a new Authentication response body.
	 */
	public AuthenticationResponseBody ()
	{
	}

	/**
	 * Instantiates a new Authentication response body.
	 *
	 * @param masterTokenData the master token data
	 * @param authenticationTokenData the authentication token data
	 * @param accountData the account data
	 */
	public AuthenticationResponseBody (CSGeneratedTokenData masterTokenData, CSGeneratedTokenData authenticationTokenData, CSAccountData accountData)
	{
		this.masterTokenData = masterTokenData;
		this.authenticationTokenData = authenticationTokenData;
		this.accountData = accountData;
	}

	/**
	 * Gets master token data.
	 *
	 * @return the master token data
	 */
	public CSGeneratedTokenData getMasterTokenData ()
	{
		return masterTokenData;
	}

	/**
	 * Sets master token data.
	 *
	 * @param masterTokenData the master token data
	 */
	public void setMasterTokenData (CSGeneratedTokenData masterTokenData)
	{
		this.masterTokenData = masterTokenData;
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

	/**
	 * Gets account data.
	 *
	 * @return the account data
	 */
	public CSAccountData getAccountData ()
	{
		return accountData;
	}

	/**
	 * Sets account data.
	 *
	 * @param accountData the account data
	 */
	public void setAccountData (CSAccountData accountData)
	{
		this.accountData = accountData;
	}

	@Override
	public boolean equals (Object o)
	{
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		AuthenticationResponseBody that = (AuthenticationResponseBody) o;

		return new EqualsBuilder().append(masterTokenData, that.masterTokenData).append(authenticationTokenData, that.authenticationTokenData).append(accountData, that.accountData).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(16656587, 46548987).append(masterTokenData).append(authenticationTokenData).append(accountData).toHashCode();
	}
}
