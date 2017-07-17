/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.authentication.spring.preauth.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The simplified organization.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public class OAuthData implements Serializable
{
	private static final long serialVersionUID = -5207755893924605999L;

	@NotNull
	private String token;

	@NotNull
	private String deviceId;

	private String ip;

	/**
	 * Instantiates a new Authentication data.
	 */
	public OAuthData ()
	{
	}

	private OAuthData (Builder builder)
	{
		setToken(builder.token);
		setDeviceId(builder.deviceId);
		setIp(builder.ip);
	}

	/**
	 * New builder builder.
	 *
	 * @return the builder
	 */
	public static Builder newBuilder ()
	{
		return new Builder();
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

	/**
	 * The type Builder.
	 */
	public static final class Builder
	{
		private String token;
		private String deviceId;
		private String ip;

		private Builder ()
		{
		}

		/**
		 * Token builder.
		 *
		 * @param val the val
		 * @return the builder
		 */
		public Builder token (String val)
		{
			token = val;
			return this;
		}

		/**
		 * Device id builder.
		 *
		 * @param val the val
		 * @return the builder
		 */
		public Builder deviceId (String val)
		{
			deviceId = val;
			return this;
		}

		/**
		 * Ip builder.
		 *
		 * @param val the val
		 * @return the builder
		 */
		public Builder ip (String val)
		{
			ip = val;
			return this;
		}

		/**
		 * Build o auth data.
		 *
		 * @return the o auth data
		 */
		public OAuthData build ()
		{
			return new OAuthData(this);
		}
	}
}
