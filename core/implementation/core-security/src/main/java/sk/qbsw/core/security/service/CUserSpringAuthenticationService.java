/*
 * Developed by QBSW a.s.
 */

package sk.qbsw.core.security.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IRoleDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CLicense;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.session.CLoggedUser;


/**
 * Class for authentication using Spring and Hibernate with QBSW model.
 *
 * @author Dalibor Rak
 * @version 1.0
 * @since 1.0
 */
@Service (value = "userAuthenticationService")
public class CUserSpringAuthenticationService implements UserDetailsService
{

	/** The role dao. */
	@Autowired
	private IRoleDao roleDao;

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/**
	 * Load user by username.
	 *
	 * @param username the username
	 * @return the user details
	 * @throws UsernameNotFoundException the username not found exception
	 * @throws DataAccessException the data access exception
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Transactional (readOnly = true)
	public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException, DataAccessException
	{
		CUser user = userDao.findByLogin(username);
		return buildUserFromEntity(user);
	}

	/**
	 * Builds the user from entity.
	 *
	 * @param entity the entity
	 * @return the user
	 */
	private User buildUserFromEntity (CUser entity)
	{
		String username = entity.getLogin();
		String password = entity.getAuthenticationParams().getPassword();
		boolean enabled = entity.getFlagEnabled();
		boolean accountNonExpired = true;

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (CRole role : roleDao.findAllByUser(entity))
		{
			authorities.add(new GrantedAuthorityImpl(role.getCode()));
		}

		// validity of organization
		COrganization org = entity.getOrganization();
		boolean credentialsNonExpired = org.getFlagEnabled();

		// check existence of at least one licence
		CLicense<?> actualLicense = org.getMainLicense();
		boolean accountNonLocked = actualLicense == null ? false : true;

		CLoggedUser user = new CLoggedUser(org, actualLicense, username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		return user;
	}
}
