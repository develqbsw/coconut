package sk.qbsw.security.organization.rest.oauth.client;

import org.springframework.web.client.RestTemplate;
import sk.qbsw.core.client.configuration.UrlConfiguration;
import sk.qbsw.security.organization.rest.oauth.client.model.CSSimpleOrganizationAccountData;
import sk.qbsw.security.rest.oauth.client.base.AuthenticationBaseClientIpIgnoredImpl;

/**
 * The type Authentication client ip ignored.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public class AuthenticationClientIpIgnoredImpl extends AuthenticationBaseClientIpIgnoredImpl<CSSimpleOrganizationAccountData>
{
	/**
	 * Instantiates a new Authentication client.
	 *
	 * @param authenticationRestTemplate the authentication rest template
	 * @param configuration              the configuration
	 */
	public AuthenticationClientIpIgnoredImpl (RestTemplate authenticationRestTemplate, UrlConfiguration configuration)
	{
		super(authenticationRestTemplate, configuration);
	}
}
