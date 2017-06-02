package sk.qbsw.security.authentication.service;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.logging.annotation.CLogged;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.security.authentication.model.spring.CustomUserDetails;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.User;

/**
 * The user detail service for token authentication.
 *
 * @author Tomas Lauro
 * @version 1.13.1
 * @since 1.13.1
 */
@CLogged
public class PreAuthenticatedUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>
{
	/** The user service. */
	@Autowired
	private UserDao userDao;

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.AuthenticationUserDetailsService#loadUserDetails(org.springframework.security.core.Authentication)
	 */
	@Override
	public UserDetails loadUserDetails (@CNotLogged @CNotAuditLogged PreAuthenticatedAuthenticationToken token)
	{
		//checks if there is a user
		if (token.getPrincipal() == null)
		{
			throw new UsernameNotFoundException("The user not found");
		}

		User persistedUser;
		try
		{
			persistedUser = userDao.findOneByLogin( ((User) token.getPrincipal()).getLogin());
		}
		catch (NoResultException | CSecurityException ex)
		{
			throw new UsernameNotFoundException("The user not found", ex);
		}

		if (Boolean.FALSE.equals(persistedUser.getFlagEnabled()))
		{
			throw new UsernameNotFoundException("The user is disabled");
		}

		CustomUserDetails userDetails = new CustomUserDetails();
		userDetails.setUser(persistedUser);


		return userDetails;
	}

}
