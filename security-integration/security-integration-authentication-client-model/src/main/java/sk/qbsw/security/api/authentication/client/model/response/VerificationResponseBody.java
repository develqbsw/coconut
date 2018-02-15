package sk.qbsw.security.api.authentication.client.model.response;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sk.qbsw.core.client.model.response.BaseResponseBody;
import sk.qbsw.security.api.authentication.client.model.CSAccountData;
import sk.qbsw.security.api.authentication.client.model.CSVerificationTypes;

import javax.validation.constraints.NotNull;

/**
 * The authentication response.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class VerificationResponseBody extends BaseResponseBody
{
	private static final long serialVersionUID = 7533946963382197126L;

	@ApiModelProperty (required = true, value = "The account data")
	@NotNull
	private CSAccountData accountData;

	@ApiModelProperty (required = true, value = "The verification type")
	@NotNull
	private CSVerificationTypes verificationType;

	/**
	 * Instantiates a new Authentication response body.
	 */
	public VerificationResponseBody ()
	{
	}

	/**
	 * Instantiates a new Authentication response body.
	 *
	 * @param accountData the account data
	 * @param verificationType the verification type
	 */
	public VerificationResponseBody (CSAccountData accountData, CSVerificationTypes verificationType)
	{
		this.accountData = accountData;
		this.verificationType = verificationType;
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

	/**
	 * Gets verification type.
	 *
	 * @return the verification type
	 */
	public CSVerificationTypes getVerificationType ()
	{
		return verificationType;
	}

	/**
	 * Sets verification type.
	 *
	 * @param verificationType the verification type
	 */
	public void setVerificationType (CSVerificationTypes verificationType)
	{
		this.verificationType = verificationType;
	}

	@Override
	public boolean equals (Object o)
	{
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		VerificationResponseBody that = (VerificationResponseBody) o;

		return new EqualsBuilder().append(accountData, that.accountData).append(verificationType, that.verificationType).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(16656587, 46548987).append(accountData).append(verificationType).toHashCode();
	}
}
