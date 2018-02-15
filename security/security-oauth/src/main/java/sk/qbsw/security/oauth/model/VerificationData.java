package sk.qbsw.security.oauth.model;

import sk.qbsw.security.core.model.domain.User;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * The verification data.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class VerificationData implements Serializable
{
	@NotNull
	private AccountData accountData;

	@NotNull
	private VerificationTypes verificationType;

	/**
	 * Instantiates a new Verification data.
	 */
	public VerificationData ()
	{
	}

	/**
	 * Instantiates a new Verification data.
	 *
	 * @param accountData the account data
	 * @param verificationType the verification type
	 */
	public VerificationData (AccountData accountData, VerificationTypes verificationType)
	{
		this.accountData = accountData;
		this.verificationType = verificationType;
	}

	/**
	 * Instantiates a new Verification data.
	 *
	 * @param user the user
	 * @param additionalInformation the additional information
	 * @param verificationType the verification type
	 */
	public VerificationData (User user, Map<String, Object> additionalInformation, VerificationTypes verificationType)
	{
		this.accountData = new AccountData(user, additionalInformation);
		this.verificationType = verificationType;
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

	/**
	 * Gets verification type.
	 *
	 * @return the verification type
	 */
	public VerificationTypes getVerificationType ()
	{
		return verificationType;
	}

	/**
	 * Sets verification type.
	 *
	 * @param verificationType the verification type
	 */
	public void setVerificationType (VerificationTypes verificationType)
	{
		this.verificationType = verificationType;
	}
}
