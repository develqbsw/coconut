package sk.qbsw.security.api.authentication.client.model.request;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sk.qbsw.core.client.model.request.BaseRequestBody;

import javax.validation.constraints.NotNull;

/**
 * The reauthentication request.
 *
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.18.2
 */
public class ReauthenticationRequestBody extends BaseRequestBody
{
	private static final long serialVersionUID = 7750372943261168607L;

	@ApiModelProperty (required = true, value = "The master token")
	@NotNull
	private String masterToken;

	@ApiModelProperty (required = true, value = "The device id")
	@NotNull
	private String deviceId;

	/**
	 * Gets master token.
	 *
	 * @return the master token
	 */
	public String getMasterToken ()
	{
		return masterToken;
	}

	/**
	 * Sets master token.
	 *
	 * @param masterToken the master token
	 */
	public void setMasterToken (String masterToken)
	{
		this.masterToken = masterToken;
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

		ReauthenticationRequestBody that = (ReauthenticationRequestBody) o;

		return new EqualsBuilder().append(masterToken, that.masterToken).append(deviceId, that.deviceId).isEquals();
	}

	@Override
	public int hashCode ()
	{
		return new HashCodeBuilder(17, 37).append(masterToken).append(deviceId).toHashCode();
	}
}
