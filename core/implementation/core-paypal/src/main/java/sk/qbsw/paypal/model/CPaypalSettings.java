package sk.qbsw.paypal.model;

import java.io.Serializable;

import org.springframework.jmx.export.annotation.ManagedOperation;

import sk.qbsw.paypal.core.PayPal.Environment;

/**
 * CPaypalSettings class.
 *
 * @author rosenberg
 * @version 1.0.0
 * @since 1.0.0
 */
public class CPaypalSettings implements Serializable
{
	private static final long serialVersionUID = 1L;


	// TODO: ENCRYPT THE DATA SOME WAY !!!
	/** The username. */
	private String username;// = "dr01_1304429817_biz_api1.pobox.sk";

	/** The password. */
	private String password;// = "1304429831";

	/** The signature. */
	private String signature;// = "Ak7oWugjZOwjTnx61Z3gGtbRtvaIAqO7PlwHjE9vG6nv1ZM8iHIL9mjW";

	/** The environment. */
	private Environment environment = Environment.SANDBOX;

	/**
	 * Switch to live.
	 */
	@ManagedOperation
	public void switchToLive ()
	{
		environment = Environment.LIVE;
	}

	/**
	 * Switch to sandbox.
	 */
	@ManagedOperation
	public void switchToSandbox ()
	{
		environment = Environment.SANDBOX;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername ()
	{
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername (String username)
	{
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword ()
	{
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword (String password)
	{
		this.password = password;
	}

	/**
	 * Gets the signature.
	 *
	 * @return the signature
	 */
	public String getSignature ()
	{
		return signature;
	}

	/**
	 * Sets the signature.
	 *
	 * @param signature the new signature
	 */
	public void setSignature (String signature)
	{
		this.signature = signature;
	}

	/**
	 * Gets the environment.
	 *
	 * @return the environment
	 */
	public String getEnvironment ()
	{
		return environment.getEnvironmentPartUrl();
	}

	/**
	 * Gets the mode.
	 *
	 * @return the mode
	 */
	public Environment getMode ()
	{
		return environment;
	}

}
