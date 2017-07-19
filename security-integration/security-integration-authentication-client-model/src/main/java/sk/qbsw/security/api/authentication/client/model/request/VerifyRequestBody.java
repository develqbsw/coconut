package sk.qbsw.security.api.authentication.client.model.request;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Request for verifying if user is authorized with token.
 *
 * @author Jana Branisova
 * @version 1.18.0
 * @since 1.18.0
 */
public class VerifyRequestBody implements Serializable
{
	private static final long serialVersionUID = -1247837645013896288L;

	@ApiModelProperty (required = true, value = "The security token")
	@NotNull
	private String token;

	@ApiModelProperty (required = true, value = "The device id")
	@NotNull
	private String deviceId;

	@ApiModelProperty (required = true, value = "The IP address")
	private String ip;

	public VerifyRequestBody ()
	{
	}

	private VerifyRequestBody (Builder builder)
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

	@Override
	public boolean equals (Object o)
	{
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		VerifyRequestBody that = (VerifyRequestBody) o;

		return new EqualsBuilder().append(token, that.token).append(deviceId, that.deviceId).append(ip, that.ip).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(17, 37).append(token).append(deviceId).append(ip).toHashCode();
	}

	public static final class Builder
	{
		private String token;
		private String deviceId;
		private String ip;

		private Builder ()
		{
		}

		public Builder token (String val)
		{
			token = val;
			return this;
		}

		public Builder deviceId (String val)
		{
			deviceId = val;
			return this;
		}

		public Builder ip (String val)
		{
			ip = val;
			return this;
		}

		public VerifyRequestBody build ()
		{
			return new VerifyRequestBody(this);
		}
	}
}
