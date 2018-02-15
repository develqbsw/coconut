package sk.qbsw.security.api.authentication.client.model;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The client side generated token data.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class CSGeneratedTokenData implements Serializable
{
	private static final long serialVersionUID = -3487173802800870802L;

	@ApiModelProperty (required = true, value = "The generated token")
	@NotNull
	private String generatedToken;

	@ApiModelProperty (value = "The invalidated token")
	private String invalidatedToken;

	/**
	 * Instantiates a new Cs generated token data.
	 */
	public CSGeneratedTokenData ()
	{
	}

	/**
	 * Instantiates a new Cs generated token data.
	 *
	 * @param generatedToken the generated token
	 * @param invalidatedToken the invalidated token
	 */
	public CSGeneratedTokenData (String generatedToken, String invalidatedToken)
	{
		this.generatedToken = generatedToken;
		this.invalidatedToken = invalidatedToken;
	}

	/**
	 * Gets generated token.
	 *
	 * @return the generated token
	 */
	public String getGeneratedToken ()
	{
		return generatedToken;
	}

	/**
	 * Sets generated token.
	 *
	 * @param generatedToken the generated token
	 */
	public void setGeneratedToken (String generatedToken)
	{
		this.generatedToken = generatedToken;
	}

	/**
	 * Gets invalidated token.
	 *
	 * @return the invalidated token
	 */
	public String getInvalidatedToken ()
	{
		return invalidatedToken;
	}

	/**
	 * Sets invalidated token.
	 *
	 * @param invalidatedToken the invalidated token
	 */
	public void setInvalidatedToken (String invalidatedToken)
	{
		this.invalidatedToken = invalidatedToken;
	}

	@Override
	public boolean equals (Object o)
	{
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		CSGeneratedTokenData that = (CSGeneratedTokenData) o;

		return new EqualsBuilder().append(generatedToken, that.generatedToken).append(invalidatedToken, that.invalidatedToken).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(12315197, 121654987).append(generatedToken).append(invalidatedToken).toHashCode();
	}
}
