package sk.qbsw.security.service;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.logging.annotation.CLogged;
import sk.qbsw.security.dao.IUserDao;
import sk.qbsw.security.model.domain.CUser;
import sk.qbsw.security.model.spring.CUserDetails;

/**
 * User details service for Spring
 * 
 * @author Lukas Podmajersky
 * @author Dalibor Rak
 * @version 1.13.0
 * @since 1.13.0
 *
 */
@CLogged
@Service
public class CAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>
{

	/** The log. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CAuthenticationUserDetailsService.class);

	@Autowired
	private IUserDao userDao;

	@Override
	@Transactional (readOnly = true)
	public UserDetails loadUserDetails (PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException
	{
		final String login = (String) token.getPrincipal();
		CUser user = null;
		try
		{
			user = userDao.findOneByLogin(login);
		}
		catch (NonUniqueResultException | NoResultException | CSecurityException e)
		{
			LOGGER.error("User details not loaded", e);
			//ignore it
		}

		if (user == null)
		{
			throw new CSystemException(ECoreErrorResponse.ACCESS_DENIED);
		}
		else if (!Boolean.TRUE.equals(user.getFlagEnabled()))
		{
			throw new CSystemException(ECoreErrorResponse.ACCESS_DENIED);
		}

		final CUserDetails result = new CUserDetails();

		/*
		 * Ensure lazy load...
		 */
		user.exportRoles();
		result.setUser(user);

		return result;
	}

}
