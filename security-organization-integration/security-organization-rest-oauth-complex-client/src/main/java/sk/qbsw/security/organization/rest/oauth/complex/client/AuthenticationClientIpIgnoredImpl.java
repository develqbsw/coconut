package sk.qbsw.security.organization.rest.oauth.complex.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;

import sk.qbsw.core.client.configuration.UrlConfiguration;
import sk.qbsw.security.organization.rest.oauth.complex.client.model.CSCXOAccountData;
import sk.qbsw.security.rest.oauth.client.base.AuthenticationClientIpIgnoredBase;
import sk.qbsw.security.rest.oauth.client.model.response.AuthenticationResponseBody;
import sk.qbsw.security.rest.oauth.client.model.response.VerificationResponseBody;

/**
 * The type Authentication client ip ignored.
 *
 * @author Tomas Leken
 * @version 2.2.0
 * @since 2.0.0
 */
public class AuthenticationClientIpIgnoredImpl extends AuthenticationClientIpIgnoredBase<CSCXOAccountData>
{
	/**
	 * Instantiates a new Authentication client ip ignored.
	 *
	 * @param authenticationRestTemplate the authentication rest template
	 * @param configuration the configuration
	 */
	public AuthenticationClientIpIgnoredImpl (RestTemplate authenticationRestTemplate, UrlConfiguration configuration)
	{
		super(authenticationRestTemplate, configuration);
	}

	@Override
	protected ParameterizedTypeReference<AuthenticationResponseBody<CSCXOAccountData>> provideAuthenticationResponseBodyType ()
	{
		return new ParameterizedTypeReference<AuthenticationResponseBody<CSCXOAccountData>>()
		{
		};
	}

	@Override
	protected ParameterizedTypeReference<VerificationResponseBody<CSCXOAccountData>> provideVerificationResponseBodyType ()
	{
		return new ParameterizedTypeReference<VerificationResponseBody<CSCXOAccountData>>()
		{
		};
	}
}
