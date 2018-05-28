package sk.qbsw.security.authentication.provider;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import sk.qbsw.security.authentication.model.spring.UsernamePasswordUnitAuthenticationToken;
import sk.qbsw.security.authentication.service.AuthenticationSecurityService;
import sk.qbsw.security.core.model.domain.Account;

/**
 * Provider for spring security.
 * 
 * @author Dalibor Rak
 * @author Tomas lauro
 * 
 * @version 1.13.4
 * @since 1.6.0
 * 
 */
public class UsernamePasswordUnitAuthenticationProvider extends BaseAuthenticationProvider
{
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
	protected Authentication populateAuthentication (Authentication authentication, Account authenticatedUser, List<GrantedAuthority> authorities)
	{
		//get supported authentication
		UsernamePasswordUnitAuthenticationToken usernameAuthentication = (UsernamePasswordUnitAuthenticationToken) authentication;

		//create fully populated authentication
		UsernamePasswordUnitAuthenticationToken populatedAuthentication = new UsernamePasswordUnitAuthenticationToken(usernameAuthentication.getName(), usernameAuthentication.getCredentials(), authorities, usernameAuthentication.getUnit());
		populatedAuthentication.setDetails(authenticatedUser);

		//return value
		return populatedAuthentication;
	}

}
