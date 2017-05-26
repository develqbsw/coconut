package sk.qbsw.security.auth.service.spring;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import sk.qbsw.security.auth.model.spring.CUsernamePasswordUnitAuthentication;
import sk.qbsw.security.model.domain.CUser;

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
@Component ("customAuthenticationProvider")
public class CSecurityAuthenticationProvider extends ASecurityAuthenticationProvider
{
	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports (Class<? extends Object> authentication)
	{
		return CUsernamePasswordUnitAuthentication.class.isAssignableFrom(authentication);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.service.spring.ASecurityAuthenticationProvider#populateAuthentication(org.springframework.security.core.Authentication, sk.qbsw.security.model.domain.CUser, java.util.List)
	 */
	@Override
	protected Authentication populateAuthentication (Authentication authentication, CUser authenticatedUser, List<GrantedAuthority> authorities)
	{
		//get supported authentication
		CUsernamePasswordUnitAuthentication usernameAuthentication = (CUsernamePasswordUnitAuthentication) authentication;

		//create fully populated authentication
		CUsernamePasswordUnitAuthentication populatedAuthentication = new CUsernamePasswordUnitAuthentication(usernameAuthentication.getName(), usernameAuthentication.getCredentials(), authorities, usernameAuthentication.getUnit());
		populatedAuthentication.setDetails(authenticatedUser);

		//return value
		return populatedAuthentication;
	}

}
