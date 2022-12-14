package sk.qbsw.security.rest.oauth.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;

import sk.qbsw.core.client.configuration.UrlConfiguration;
import sk.qbsw.security.rest.oauth.client.base.AuthenticationClient;
import sk.qbsw.security.rest.oauth.client.base.AuthenticationClientIpValidatedBase;
import sk.qbsw.security.rest.oauth.client.model.CSAccountData;
import sk.qbsw.security.rest.oauth.client.model.response.AuthenticationResponseBody;
import sk.qbsw.security.rest.oauth.client.model.response.VerificationResponseBody;

/**
 * The type Authentication client ip validated.
 *
 * @author Jana Branisova
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 2.2.0
 * @since 1.18.0
 */
public class AuthenticationClientIpValidatedImpl extends AuthenticationClientIpValidatedBase<CSAccountData> implements AuthenticationClient<CSAccountData>
{
	/**
	 * Instantiates a new Authentication client.
	 *
	 * @param authenticationRestTemplate the authentication rest template
	 * @param configuration the configuration
	 */
	public AuthenticationClientIpValidatedImpl (RestTemplate authenticationRestTemplate, UrlConfiguration configuration)
	{
		super(authenticationRestTemplate, configuration);
	}

	@Override
	protected ParameterizedTypeReference<AuthenticationResponseBody<CSAccountData>> provideAuthenticationResponseBodyType ()
	{
		return new ParameterizedTypeReference<AuthenticationResponseBody<CSAccountData>>()
		{
		};
	}

	@Override
	protected ParameterizedTypeReference<VerificationResponseBody<CSAccountData>> provideVerificationResponseBodyType ()
	{
		return new ParameterizedTypeReference<VerificationResponseBody<CSAccountData>>()
		{
		};
	}
}
