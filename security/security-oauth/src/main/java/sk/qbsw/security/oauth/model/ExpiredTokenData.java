package sk.qbsw.security.oauth.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The expired token data.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class ExpiredTokenData implements Serializable
{
	@NotNull
	private String token;

	@NotNull
	private String deviceId;

	private String ip;

	/**
	 * Instantiates a new Expired token data.
	 */
	public ExpiredTokenData ()
	{
	}

	/**
	 * Instantiates a new Expired token data.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @param ip the ip
	 */
	public ExpiredTokenData (String token, String deviceId, String ip)
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
}
