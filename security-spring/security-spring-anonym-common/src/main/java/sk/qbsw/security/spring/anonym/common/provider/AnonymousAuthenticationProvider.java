package sk.qbsw.security.spring.anonym.common.provider;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import sk.qbsw.security.spring.anonym.common.model.AnonymousAuthenticationToken;
import sk.qbsw.security.spring.base.model.AccountDetails;
import sk.qbsw.security.spring.base.service.AccountDetailsService;

/**
 * The anonymous authentication provider.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.4
 */
public class AnonymousAuthenticationProvider extends org.springframework.security.authentication.AnonymousAuthenticationProvider
{
	private static final String DEFAULT_KEY = "anonymousKey";

	private final AccountDetailsService<AnonymousAuthenticationToken> accountDetailsService;

	/**
	 * Instantiates a new Security anonymous authentication provider.
	 *
	 * @param accountDetailsService the account details service
	 */
	public AnonymousAuthenticationProvider (AccountDetailsService<AnonymousAuthenticationToken> accountDetailsService)
	{
		super(DEFAULT_KEY);
		this.accountDetailsService = accountDetailsService;
	}

	public Authentication authenticate (Authentication authentication) throws AuthenticationException
	{
		if (!supports(authentication.getClass()))
		{
			return null;
		}

		AccountDetails accountDetails = accountDetailsService.loadUserDetails((AnonymousAuthenticationToken) authentication);

		AnonymousAuthenticationToken result = new AnonymousAuthenticationToken(DEFAULT_KEY, accountDetails, accountDetails.getAuthorities(), true);
		result.setDetails(authentication.getDetails());

		return result;
	}

	@Override
	public boolean supports (Class<?> authentication)
	{
		return (AnonymousAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
