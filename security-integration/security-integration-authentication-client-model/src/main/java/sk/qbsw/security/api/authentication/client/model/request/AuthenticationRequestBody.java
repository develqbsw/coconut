package sk.qbsw.security.api.authentication.client.model.request;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sk.qbsw.core.client.model.request.BaseRequestBody;

import javax.validation.constraints.NotNull;

/**
 * The authentication request.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.0
 */
public class AuthenticationRequestBody extends BaseRequestBody
{
	private static final long serialVersionUID = -8124532358965918386L;

	@ApiModelProperty (required = true, value = "The login")
	@NotNull
	private String login;

	@ApiModelProperty (required = true, value = "The password")
	@NotNull
	private String password;

	@ApiModelProperty (required = true, value = "The device id")
	@NotNull
	private String deviceId;

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

	/**
	 * Gets device id.
	 *
	 * @return the device id
	 */
	public String getDeviceId ()
	{
		return deviceId;
	}

	/**
	 * Sets device id.
	 *
	 * @param deviceId the device id
	 */
	public void setDeviceId (String deviceId)
	{
		this.deviceId = deviceId;
	}

	@Override
	public boolean equals (Object o)
	{
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		AuthenticationRequestBody that = (AuthenticationRequestBody) o;

		return new EqualsBuilder().append(login, that.login).append(password, that.password).append(deviceId, that.deviceId).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(17, 37).append(login).append(password).append(deviceId).toHashCode();
	}
}
