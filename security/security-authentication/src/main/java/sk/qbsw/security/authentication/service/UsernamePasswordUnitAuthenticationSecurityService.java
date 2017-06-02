package sk.qbsw.security.authentication.service;

import org.springframework.security.core.Authentication;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.authentication.base.service.AuthenticationService;
import sk.qbsw.security.authentication.model.spring.UsernamePasswordUnitAuthenticationToken;
import sk.qbsw.security.core.model.domain.User;

/**
 * The spring authentication service uses the authentication service to authenticate user.
 *
 * @author Tomas Lauro
 * @author Dalibor Rak
 * 
 * @version 1.13.4
 * @since 1.13.4
 */
public class UsernamePasswordUnitAuthenticationSecurityService extends AService implements AuthenticationSecurityService
{
	private AuthenticationService authenticationService;
	
	public UsernamePasswordUnitAuthenticationSecurityService (AuthenticationService authenticationService)
	{
		this.authenticationService = authenticationService;
	}
	
	@Override
	public User login (Authentication authentication) throws CSecurityException
	{
		//checks token support
		if (authentication == null || this.supports(authentication.getClass()) == false)
		{
			throw new CSecurityException(ECoreErrorResponse.UNSUPPORTED_AUTHENTICATION_TOKEN);
		}

		UsernamePasswordUnitAuthenticationToken usernamePasswordAuthentication = (UsernamePasswordUnitAuthenticationToken) authentication;

		if (usernamePasswordAuthentication.getUnit() != null)
		{
			return authenticationService.login((String) usernamePasswordAuthentication.getPrincipal(), (String) usernamePasswordAuthentication.getCredentials(), usernamePasswordAuthentication.getUnit());
		}
		else
		{
			return authenticationService.login((String) usernamePasswordAuthentication.getPrincipal(), (String) usernamePasswordAuthentication.getCredentials());
		}
	}

	@Override
	public boolean supports (Class<? extends Authentication> authentication)
	{
		return UsernamePasswordUnitAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
