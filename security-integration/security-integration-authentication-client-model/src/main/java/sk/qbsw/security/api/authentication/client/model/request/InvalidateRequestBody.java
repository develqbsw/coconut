package sk.qbsw.security.api.authentication.client.model.request;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sk.qbsw.core.client.model.request.BaseRequestBody;

import javax.validation.constraints.NotNull;

/**
 * The Invalidate request.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public class InvalidateRequestBody extends BaseRequestBody
{
	private static final long serialVersionUID = -4789897540369584317L;

	@ApiModelProperty (required = true, value = "The master token")
	@NotNull
	private String masterToken;

	@ApiModelProperty (required = true, value = "The authentication token")
	@NotNull
	private String authenticationToken;

	@ApiModelProperty (required = true, value = "The device id")
	@NotNull
	private String deviceId;

	/**
	 * Gets the master token.
	 *
	 * @return the master token
	 */
	public String getMasterToken ()
	{
		return masterToken;
	}

	/**
	 * Sets the master token.
	 *
	 * @param masterToken the new master token
	 */
	public void setMasterToken (String masterToken)
	{
		this.masterToken = masterToken;
	}

	/**
	 * Gets the authentication token.
	 *
	 * @return the authentication token
	 */
	public String getAuthenticationToken ()
	{
		return authenticationToken;
	}

	/**
	 * Sets the authentication token.
	 *
	 * @param authenticationToken the new authentication token
	 */
	public void setAuthenticationToken (String authenticationToken)
	{
		this.authenticationToken = authenticationToken;
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

		InvalidateRequestBody that = (InvalidateRequestBody) o;

		return new EqualsBuilder().append(masterToken, that.masterToken).append(authenticationToken, that.authenticationToken).append(deviceId, that.deviceId).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(17, 37).append(masterToken).append(authenticationToken).append(deviceId).toHashCode();
	}
}
