package sk.qbsw.security.organization.rest.oauth.simple.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;

import sk.qbsw.core.client.configuration.UrlConfiguration;
import sk.qbsw.security.organization.rest.oauth.simple.client.model.CSSPOAccountData;
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
public class AuthenticationClientIpIgnoredImpl extends AuthenticationClientIpIgnoredBase<CSSPOAccountData>
{
	/**
	 * Instantiates a new Authentication client.
	 *
	 * @param authenticationRestTemplate the authentication rest template
	 * @param configuration the configuration
	 */
	public AuthenticationClientIpIgnoredImpl (RestTemplate authenticationRestTemplate, UrlConfiguration configuration)
	{
		super(authenticationRestTemplate, configuration);
	}

	@Override
	protected ParameterizedTypeReference<AuthenticationResponseBody<CSSPOAccountData>> provideAuthenticationResponseBodyType ()
	{
		return new ParameterizedTypeReference<AuthenticationResponseBody<CSSPOAccountData>>()
		{
		};
	}

	@Override
	protected ParameterizedTypeReference<VerificationResponseBody<CSSPOAccountData>> provideVerificationResponseBodyType ()
	{
		return new ParameterizedTypeReference<VerificationResponseBody<CSSPOAccountData>>()
		{
		};
	}
}
