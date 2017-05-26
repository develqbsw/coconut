package sk.qbsw.security.auth.service.spring;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.auth.model.spring.CUserDetails;
import sk.qbsw.security.dao.IUserDao;
import sk.qbsw.security.model.domain.CUser;

/**
 * Service for getting user details
 * 
 * @author Dalibor Rak
 * @author Marek Martinkovic
 * 
 * @version 1.14.3
 * @since 1.6.0
 */
public class CUserDetailsService extends AService implements UserDetailsService
{
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CUserDetailsService.class);

	/** The user service. */
	@Autowired
	private IUserDao userDao;

	@PostConstruct
	private void postCondtruct ()
	{
		LOGGER.info("User Details service initialized");
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public UserDetails loadUserByUsername (String username)
	{
		CUser user;

		try
		{
			user = userDao.findOneByLogin(username);
		}
		catch (NoResultException | CSecurityException ex)
		{
			LOGGER.debug("User not found", ex);
			user = null;
		}

		if (user == null)
		{
			throw new UsernameNotFoundException(String.format("User '%s' not found", username));
		}

		return constructUserDetails(user);
	}

	/**
	 * override creation 
	 * here you can do any lazy load
	 * @param user
	 * @return
	 */
	protected UserDetails constructUserDetails (CUser user)
	{
		//lazy load of special params
		//they will be needed
		user.exportRoles();
		user.getPassword();

		CUserDetails retVal = new CUserDetails();
		retVal.setUser(user);
		return retVal;
	}
}
