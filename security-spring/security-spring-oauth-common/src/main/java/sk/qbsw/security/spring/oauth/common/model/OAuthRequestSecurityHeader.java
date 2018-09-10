package sk.qbsw.security.spring.oauth.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import sk.qbsw.security.spring.base.model.RequestSecurityHeader;

/**
 * The request security base.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
@Data
@ToString
@EqualsAndHashCode (callSuper = true)
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
