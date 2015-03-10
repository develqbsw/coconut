package sk.qbsw.core.security.service;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.logging.annotation.CLogged;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.spring.CUserDetails;

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
public class CAuthenticationUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	@Autowired
	private IUserDao userDao;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
		final String login = (String) token.getPrincipal();
		CUser user = null;
		try {
			user = userDao.findByLogin(login);
		} catch (NonUniqueResultException | NoResultException e) {
			//ignore it
		}

		if (user == null) {
			throw new CSystemException(ECoreErrorResponse.ACCESS_DENIED);
		}
		else if (!Boolean.TRUE.equals(user.getFlagEnabled())) {
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
