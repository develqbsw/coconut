package sk.qbsw.security.api.authentication.client.model;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The client expired token data.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class CSExpiredTokenData implements Serializable
{
	@ApiModelProperty (required = true, value = "The token value")
	@NotNull
	private String token;

	@ApiModelProperty (required = true, value = "The device id")
	@NotNull
	private String deviceId;

	@ApiModelProperty (value = "The ip address")
	private String ip;

	/**
	 * Instantiates a new Expired token data.
	 */
	public CSExpiredTokenData ()
	{
	}

	/**
	 * Instantiates a new Expired token data.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @param ip the ip
	 */
	public CSExpiredTokenData (String token, String deviceId, String ip)
	{
		this.token = token;
		this.deviceId = deviceId;
		this.ip = ip;
	}

	/**
	 * Gets token.
	 *
	 * @return the token
	 */
	public String getToken ()
	{
		return token;
	}

	/**
	 * Sets token.
	 *
	 * @param token the token
	 */
	public void setToken (String token)
	{
		this.token = token;
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

	/**
	 * Gets ip.
	 *
	 * @return the ip
	 */
	public String getIp ()
	{
		return ip;
	}

	/**
	 * Sets ip.
	 *
	 * @param ip the ip
	 */
	public void setIp (String ip)
	{
		this.ip = ip;
	}

	@Override
	public boolean equals (Object o)
	{
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		CSExpiredTokenData that = (CSExpiredTokenData) o;

		return new EqualsBuilder().append(token, that.token).append(deviceId, that.deviceId).append(ip, that.ip).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(17, 37).append(token).append(deviceId).append(ip).toHashCode();
	}
}
