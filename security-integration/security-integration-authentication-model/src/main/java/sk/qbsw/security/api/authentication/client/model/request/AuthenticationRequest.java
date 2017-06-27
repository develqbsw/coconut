package sk.qbsw.security.api.authentication.client.model.request;

import io.swagger.annotations.ApiModelProperty;
import sk.qbsw.core.client.model.request.BaseRequestBody;

import javax.validation.constraints.NotNull;

/**
 * The authentication request.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public class AuthenticationRequest extends BaseRequestBody
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8124532358965918386L;

	/** The email. */
	@ApiModelProperty (required = true, value = "The login")
	@NotNull
	private String login;

	/** The password. */
	@ApiModelProperty (required = true, value = "The password")
	@NotNull
	private String password;

	/**
	 * Login.
	 *
	 * @param login the login
	 * @return the authentication request
	 */
	public AuthenticationRequest login (String login)
	{
		this.login = login;
		return this;
	}

	/**
	 * Password.
	 *
	 * @param password the password
	 * @return the authentication request
	 */
	public AuthenticationRequest password (String password)
	{
		this.password = password;
		return this;
	}

	/**
	 * Gets the login.
	 *
	 * @return the login
	 */
	public String getLogin ()
	{
		return login;
	}

	/**
	 * Sets the login.
	 *
	 * @param login the new login
	 */
	public void setLogin (String login)
	{
		this.login = login;
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
}
