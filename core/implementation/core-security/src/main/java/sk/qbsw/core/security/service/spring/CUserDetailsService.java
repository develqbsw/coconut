package sk.qbsw.core.security.service.spring;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.spring.CUserDetails;
import sk.qbsw.core.security.service.IUserService;

/**
 * Service for getting user details
 * 
 * @author Dalibor Rak
 * @version 1.6.0
 * @since 1.6.0
 */
public class CUserDetailsService implements UserDetailsService
{
	private static final Logger LOGGER = Logger.getLogger(CUserDetailsService.class);

	/** The user service. */
	@Autowired
	private IUserService userService;

	@PostConstruct
	private void postCondtruct ()
	{
		LOGGER.info("User Details service initialized");
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException, DataAccessException
	{

		CUser user = userService.getUserByLogin(username);

		if (user == null)
		{
			throw new UsernameNotFoundException(String.format("User '%s' not found", username));
		}

		CUserDetails retVal = new CUserDetails();
		retVal.setUser(user);
		return retVal;
	}

}
