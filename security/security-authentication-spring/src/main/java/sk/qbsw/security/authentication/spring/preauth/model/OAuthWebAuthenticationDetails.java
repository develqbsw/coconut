package sk.qbsw.security.authentication.spring.preauth.model;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * This WebAuthenticationDetails implementation allows for storing a device id and ip.
 *
 * @author Tomas Lauro
 * @version 1.18.0
 * @since 1.18.0
 */
public class OAuthWebAuthenticationDetails extends WebAuthenticationDetails
{
	private final String deviceId;

	private final String ip;

	/**
	 * Instantiates a new O auth pre authenticated web authentication details.
	 *
	 * @param request the request
	 * @param deviceId the device id
	 * @param ip the ip
	 */
	public OAuthWebAuthenticationDetails (HttpServletRequest request, String deviceId, String ip)
	{
		super(request);
		this.deviceId = deviceId;
		this.ip = ip;
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
	 * Gets ip.
	 *
	 * @return the ip
	 */
	public String getIp ()
	{
		return ip;
	}

	@Override
	public String toString ()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("; ");
		sb.append(deviceId);
		sb.append(ip);
		return sb.toString();
	}

	@Override
	public boolean equals (Object rhs)
	{
		return super.equals(rhs);
	}

	@Override
	public int hashCode ()
	{
		return super.hashCode();
	}
}
