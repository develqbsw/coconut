package sk.qbsw.security.spring.oauth.common.model;

import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * This WebAuthenticationDetails implementation allows for storing a device id and ip.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.18.0
 */
@Getter
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
