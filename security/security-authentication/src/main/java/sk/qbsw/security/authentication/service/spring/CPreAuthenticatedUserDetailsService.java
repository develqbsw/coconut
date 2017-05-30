package sk.qbsw.security.authentication.service.spring;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.logging.annotation.CLogged;
import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.security.authentication.model.spring.CUserDetails;
import sk.qbsw.security.core.dao.IUserDao;
import sk.qbsw.security.core.model.domain.CUser;

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

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CPreAuthenticatedUserDetailsService.class);

	/** The user service. */
	@Autowired
	private IUserDao userDao;

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

		CUser persistedUser;
		try
		{
			persistedUser = userDao.findOneByLogin( ((CUser) token.getPrincipal()).getLogin());
		}
		catch (NoResultException | CSecurityException ex)
		{
			throw new UsernameNotFoundException("The user not found", ex);
		}

		if (Boolean.FALSE.equals(persistedUser.getFlagEnabled()))
		{
			throw new UsernameNotFoundException("The user is disabled");
		}

		CUserDetails userDetails = new CUserDetails();
		userDetails.setUser(persistedUser);


		return userDetails;
	}

}
