package sk.qbsw.security.authentication.spring.anonym.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import sk.qbsw.security.authentication.spring.anonym.model.AnonymousAuthenticationToken;
import sk.qbsw.security.authentication.spring.anonym.service.SecurityUserDetailsService;
import sk.qbsw.security.authentication.spring.model.SecurityUserDetails;

/**
 * The anonymous authentication provider.
 *
 * @author Tomas Lauro
 * @version 1.18.5
 * @since 1.18.4
 */
public class AnonymousAuthenticationProvider extends org.springframework.security.authentication.AnonymousAuthenticationProvider
{
	private static final String DEFAULT_KEY = "anonymousKey";

	private final SecurityUserDetailsService<AnonymousAuthenticationToken> userDetailsService;

	/**
	 * Instantiates a new Security anonymous authentication provider.
	 *
	 * @param userDetailsService the user details service
	 */
	public AnonymousAuthenticationProvider (SecurityUserDetailsService<AnonymousAuthenticationToken> userDetailsService)
	{
		super(DEFAULT_KEY);
		this.userDetailsService = userDetailsService;
	}

	public Authentication authenticate (Authentication authentication) throws AuthenticationException
	{
		if (!supports(authentication.getClass()))
		{
			return null;
		}

		SecurityUserDetails userDetails = userDetailsService.loadUserDetails((AnonymousAuthenticationToken) authentication);

		AnonymousAuthenticationToken result = new AnonymousAuthenticationToken(DEFAULT_KEY, userDetails, userDetails.getAuthorities(), true);
		result.setDetails(authentication.getDetails());

		return result;
	}

	@Override
	public boolean supports (Class<?> authentication)
	{
		return (AnonymousAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
