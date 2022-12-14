package sk.qbsw.security.spring.oauth.common.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import sk.qbsw.security.spring.common.model.RequestSecurityHeader;

/**
 * The request security base.
 *
 * @author Tomas Lauro
 * @version 2.4.0
 * @since 2.0.0
 */
@Getter
@EqualsAndHashCode (callSuper = true)
@ToString
public class OAuthRequestSecurityHeader extends RequestSecurityHeader
{
	private final String deviceId;

	private final String ip;

	/**
	 * Instantiates a new O auth request security header.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @param ip the ip
	 */
	public OAuthRequestSecurityHeader (String token, String deviceId, String ip)
	{
		super(token);
		this.deviceId = deviceId;
		this.ip = ip;
	}
}
