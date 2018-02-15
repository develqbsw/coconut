package sk.qbsw.security.oauth.model;

import sk.qbsw.security.core.model.domain.User;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * The authentication data.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class AuthenticationData implements Serializable
{
	@NotNull
	private GeneratedTokenData masterTokenData;

	@NotNull
	private GeneratedTokenData authenticationTokenData;

	@NotNull
	private AccountData accountData;

	/**
	 * Instantiates a new Authentication data.
	 */
	public AuthenticationData ()
	{
	}

	/**
	 * Instantiates a new Authentication data.
	 *
	 * @param masterTokenData the master token data
	 * @param authenticationTokenData the authentication token data
	 * @param accountData the account data
	 */
	public AuthenticationData (GeneratedTokenData masterTokenData, GeneratedTokenData authenticationTokenData, AccountData accountData)
	{
		this.masterTokenData = masterTokenData;
		this.authenticationTokenData = authenticationTokenData;
		this.accountData = accountData;
	}

	/**
	 * Instantiates a new Authentication data.
	 *
	 * @param masterTokenData the master token data
	 * @param authenticationTokenData the authentication token data
	 * @param user the user
	 * @param additionalInformation the additional information
	 */
	public AuthenticationData (GeneratedTokenData masterTokenData, GeneratedTokenData authenticationTokenData, User user, Map<String, Object> additionalInformation)
	{
		this.masterTokenData = masterTokenData;
		this.authenticationTokenData = authenticationTokenData;
		this.accountData = new AccountData(user, additionalInformation);
	}

	/**
	 * Gets master token result.
	 *
	 * @return the master token result
	 */
	public GeneratedTokenData getMasterTokenData ()
	{
		return masterTokenData;
	}

	/**
	 * Sets master token result.
	 *
	 * @param masterTokenData the master token result
	 */
	public void setMasterTokenData (GeneratedTokenData masterTokenData)
	{
		this.masterTokenData = masterTokenData;
	}

	/**
	 * Gets authentication token result.
	 *
	 * @return the authentication token result
	 */
	public GeneratedTokenData getAuthenticationTokenData ()
	{
		return authenticationTokenData;
	}

	/**
	 * Sets authentication token result.
	 *
	 * @param authenticationTokenData the authentication token result
	 */
	public void setAuthenticationTokenData (GeneratedTokenData authenticationTokenData)
	{
		this.authenticationTokenData = authenticationTokenData;
	}

	/**
	 * Gets account data.
	 *
	 * @return the account data
	 */
	public AccountData getAccountData ()
	{
		return accountData;
	}

	/**
	 * Sets account data.
	 *
	 * @param accountData the account data
	 */
	public void setAccountData (AccountData accountData)
	{
		this.accountData = accountData;
	}
}
