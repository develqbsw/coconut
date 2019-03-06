package sk.qbsw.security.spring.oauth.common.configuration;

import lombok.Getter;

/**
 * The default oauth header configuration.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
@Getter
public class DefaultOAuthHeaderConfiguration implements OAuthHeaderConfiguration
{
	private static final long serialVersionUID = 4246984783455833196L;

	private String requestSecurityHeaderTokenName = "token";

	private String requestSecurityHeaderDeviceIdName = "device-id";
}
