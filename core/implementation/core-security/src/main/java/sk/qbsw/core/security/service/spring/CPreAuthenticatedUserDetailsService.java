package sk.qbsw.core.security.service.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import sk.qbsw.core.base.logging.annotation.CLogged;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.spring.CUserDetails;
import sk.qbsw.core.security.service.IUserService;

/**
 * The user detail service for token authentication.
 *
 * @author Tomas Lauro
 * @version 1.13.1
 * @since 1.13.1
 */
@CLogged
@Service
public class CPreAuthenticatedUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>
{
	/** The user service. */
	@Autowired
	private IUserService userService;

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.AuthenticationUserDetailsService#loadUserDetails(org.springframework.security.core.Authentication)
	 */
	@Override
	public UserDetails loadUserDetails (@CNotLogged @CNotAuditLogged PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException
	{
		//checks if there is a user
		if (token.getPrincipal() == null)
		{
			throw new UsernameNotFoundException("The user not found");
		}

		CUser persistedUser = userService.get(((CUser) token.getPrincipal()).getId());

		if (persistedUser == null)
		{
			throw new UsernameNotFoundException("The user not found");
		}
		else if (Boolean.FALSE.equals(persistedUser.getFlagEnabled()))
		{
			throw new UsernameNotFoundException("The user is disabled");
		}

		CUserDetails userDetails = new CUserDetails();
		userDetails.setUser(persistedUser);


		return userDetails;
	}

}
