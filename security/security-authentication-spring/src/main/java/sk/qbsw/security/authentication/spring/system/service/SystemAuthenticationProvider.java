package sk.qbsw.security.authentication.spring.system.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import sk.qbsw.security.authentication.spring.model.SecurityUserDetails;
import sk.qbsw.security.authentication.spring.service.SecurityUserDetailsService;
import sk.qbsw.security.authentication.spring.system.model.SystemAuthenticationToken;

/**
 * The system authentication provider.
 *
 * @author Tomas Lauro
 * @version 1.18.6
 * @since 1.18.6
 */
public class SystemAuthenticationProvider implements AuthenticationProvider
{
	private final SecurityUserDetailsService<SystemAuthenticationToken> userDetailsService;

	/**
	 * Instantiates a new System authentication provider.
	 *
	 * @param userDetailsService the user details service
	 */
	public SystemAuthenticationProvider (SecurityUserDetailsService<SystemAuthenticationToken> userDetailsService)
	{
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Authentication authenticate (Authentication authentication) throws AuthenticationException
	{
		if (!supports(authentication.getClass()))
		{
			return null;
		}

		SecurityUserDetails userDetails = userDetailsService.loadUserDetails((SystemAuthenticationToken) authentication);

		SystemAuthenticationToken result = new SystemAuthenticationToken(userDetails, userDetails.getAuthorities());
		result.setDetails(authentication.getDetails());

		return result;
	}

	@Override
	public boolean supports (Class<?> authentication)
	{
		return (SystemAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
