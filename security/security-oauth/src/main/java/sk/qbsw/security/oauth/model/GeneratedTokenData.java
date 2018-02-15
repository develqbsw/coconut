package sk.qbsw.security.oauth.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The result of token generation.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class GeneratedTokenData implements Serializable
{
	private static final long serialVersionUID = -7328502942202337268L;

	@NotNull
	private String generatedToken;

	private String invalidatedToken;

	/**
	 * Instantiates a new Generation data.
	 */
	public GeneratedTokenData ()
	{
	}

	/**
	 * Instantiates a new Generation data.
	 *
	 * @param generatedToken the generated token
	 * @param invalidatedToken the deleted token
	 */
	public GeneratedTokenData (String generatedToken, String invalidatedToken)
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
}
