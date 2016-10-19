package sk.qbsw.core.api.model.request;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import sk.qbsw.core.api.client.jackson.serializer.CDateJSonSerializer;

/**
 * Base request for API interface.
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.15.0
 * @since 1.2.0
 */
public class ABaseRequest extends ARequest
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6837231152329254832L;

	/**
	 * Identifier of client's device.
	 */
	private String deviceId;

	/** Identifier of user who requested operation. */
	private Long userId;

	/**
	 * Date of request.
	 */
	@JsonSerialize (using = CDateJSonSerializer.class)
	private Date requestDate;

	/**
	 * Instantiates a new a base request. Sets current date to local variable.
	 */
	public ABaseRequest ()
	{
		this.requestDate = new Date();
	}

	/**
	 * Gets the device id.
	 * 
	 * @return the device id
	 */
	public String getDeviceId ()
	{
		return deviceId;
	}

	/**
	 * Sets the device id.
	 * 
	 * @param deviceId
	 *            the new device id
	 */
	public void setDeviceId (String deviceId)
	{
		this.deviceId = deviceId;
	}

	/**
	 * Gets the user id.
	 * 
	 * @return the user id
	 */
	public Long getUserId ()
	{
		return userId;
	}

	/**
	 * Sets the user id.
	 * 
	 * @param userId
	 *            the new user id
	 */
	public void setUserId (Long userId)
	{
		this.userId = userId;
	}

	/**
	 * Gets the date.
	 * 
	 * @return the date
	 */
	public Date getRequestDate ()
	{
		return requestDate;
	}

	/**
	 * Sets the request date.
	 * 
	 * @param date
	 *            the new request date
	 */
	public void setRequestDate (Date date)
	{
		this.requestDate = date;
	}
}
