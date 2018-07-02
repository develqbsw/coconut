package sk.qbsw.security.spring.auth.base.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.spring.auth.base.service.AuthenticationSecurityService;
import sk.qbsw.security.spring.base.model.LoggedUser;

import java.util.Collection;

/**
 * The abstract provider for user authentication purposes.
 *
 * @author Dalibor Rak
 * @author Tomas lauro
 * @version 1.19.0
 * @since 1.6.0
 */
public abstract class BaseAuthenticationProvider implements AuthenticationProvider
{
	private final AuthenticationSecurityService authenticationSecurityService;

	/**
	 * Instantiates a new Base authentication provider.
	 *
	 * @param authenticationSecurityService the authentication security service
	 */
	public BaseAuthenticationProvider (AuthenticationSecurityService authenticationSecurityService)
	{
		this.authenticationSecurityService = authenticationSecurityService;
	}

	@Override
	public Authentication authenticate (Authentication authentication) throws AuthenticationException
	{
		Authentication retVal = null;
		try
		{
			if (this.supports(authentication.getClass()) && authenticationSecurityService.supports(authentication.getClass()))
			{
				LoggedUser authenticatedUser = authenticationSecurityService.authenticate(authentication);
				retVal = populateAuthentication(authentication, authenticatedUser, authenticatedUser.getAuthorities());
			}
		}
		catch (CSecurityException e)
		{
			throw new AuthenticationServiceException("User not authenticated", e);
		}

		return retVal;
	}

	/**
	 * Populate authentication token with security data. The result should be the fully populated authentication to be used in security context.
	 *
	 * @param authentication the input authentication token
	 * @param authenticatedUser the authenticated user from security service
	 * @param authorities the authorities list of authorities
	 * @return the fully populated authentication token
	 */
	protected abstract Authentication populateAuthentication (Authentication authentication, LoggedUser authenticatedUser, Collection<GrantedAuthority> authorities);
}
