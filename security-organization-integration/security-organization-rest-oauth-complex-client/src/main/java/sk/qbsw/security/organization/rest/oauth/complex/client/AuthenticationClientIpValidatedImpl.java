package sk.qbsw.security.organization.rest.oauth.complex.client;

import org.springframework.web.client.RestTemplate;
import sk.qbsw.core.client.configuration.UrlConfiguration;
import sk.qbsw.security.organization.rest.oauth.complex.client.model.CSComplexOrganizationAccountData;
import sk.qbsw.security.rest.oauth.client.base.AuthenticationClientIpValidatedBase;

/**
 * The type Authentication client ip validated.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public class AuthenticationClientIpValidatedImpl extends AuthenticationClientIpValidatedBase<CSComplexOrganizationAccountData>
{

	/**
	 * Instantiates a new Authentication client.
	 *
	 * @param authenticationRestTemplate the authentication rest template
	 * @param configuration              the configuration
	 */
	public AuthenticationClientIpValidatedImpl (RestTemplate authenticationRestTemplate, UrlConfiguration configuration)
	{
		super(authenticationRestTemplate, configuration);
	}
}