package sk.qbsw.security.authentication.spring.system.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import sk.qbsw.security.authentication.spring.model.AccountDetails;
import sk.qbsw.security.authentication.spring.service.AccountDetailsService;
import sk.qbsw.security.authentication.spring.system.model.SystemAuthenticationToken;

/**
 * The system authentication provider.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.6
 */
public class SystemAuthenticationProvider implements AuthenticationProvider
{
	private final AccountDetailsService<SystemAuthenticationToken> accountDetailsService;

	/**
	 * Instantiates a new System authentication provider.
	 *
	 * @param accountDetailsService the account details service
	 */
	public SystemAuthenticationProvider (AccountDetailsService<SystemAuthenticationToken> accountDetailsService)
	{
		this.accountDetailsService = accountDetailsService;
	}

	@Override
	public Authentication authenticate (Authentication authentication) throws AuthenticationException
	{
		if (!supports(authentication.getClass()))
		{
			return null;
		}

		AccountDetails accountDetails = accountDetailsService.loadUserDetails((SystemAuthenticationToken) authentication);

		SystemAuthenticationToken result = new SystemAuthenticationToken(accountDetails, accountDetails.getAuthorities());
		result.setDetails(authentication.getDetails());

		return result;
	}

	@Override
	public boolean supports (Class<?> authentication)
	{
		return (SystemAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
