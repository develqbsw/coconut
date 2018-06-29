package sk.qbsw.security.spring.auth.provider;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import sk.qbsw.security.spring.auth.model.UsernamePasswordUnitAuthenticationToken;
import sk.qbsw.security.spring.auth.service.AuthenticationSecurityService;
import sk.qbsw.security.spring.base.model.LoggedUser;

import java.util.Collection;

/**
 * The username password and unit authentication provider.
 *
 * @author Dalibor Rak
 * @author Tomas lauro
 * @version 1.19.0
 * @since 1.6.0
 */
public class UsernamePasswordUnitAuthenticationProvider extends BaseAuthenticationProvider
{
	/**
	 * Instantiates a new Username password unit authentication provider.
	 *
	 * @param authenticationSecurityService the authentication security service
	 */
	public UsernamePasswordUnitAuthenticationProvider (AuthenticationSecurityService authenticationSecurityService)
	{
		super(authenticationSecurityService);
	}

	@Override
	public boolean supports (Class<? extends Object> authentication)
	{
		return UsernamePasswordUnitAuthenticationToken.class.isAssignableFrom(authentication);
	}

	@Override
	protected Authentication populateAuthentication (Authentication authentication, LoggedUser authenticatedUser, Collection<GrantedAuthority> authorities)
	{
		// read supported authentication
		UsernamePasswordUnitAuthenticationToken usernameAuthentication = (UsernamePasswordUnitAuthenticationToken) authentication;

		// create fully populated authentication
		UsernamePasswordUnitAuthenticationToken populatedAuthentication = new UsernamePasswordUnitAuthenticationToken(authenticatedUser, usernameAuthentication.getCredentials(), authorities, usernameAuthentication.getUnit());
		populatedAuthentication.setDetails(usernameAuthentication.getDetails());

		// return value
		return populatedAuthentication;
	}
}
