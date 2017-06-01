/*
 * Developed by QBSW a.s.
 */

package sk.qbsw.security.authentication.service.spring;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.authentication.model.spring.LoggedUser;
import sk.qbsw.security.core.dao.RoleDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.License;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.domain.Role;
import sk.qbsw.security.core.model.domain.User;


/**
 * Class for authentication using Spring and Hibernate with QBSW model.
 * 
 * Consider using the {@link UserDetailsServiceImpl}.
 *
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.13.4
 * @since 1.0.0
 */
@Service (value = "userAuthenticationService")
public class LoggedUserDetailsServiceImpl extends AService implements UserDetailsService
{
	/** The role dao. */
	@Autowired
	private RoleDao roleDao;

	/** The user dao. */
	@Autowired
	private UserDao userDao;

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
	public UserDetails loadUserByUsername (String username)
	{
		try
		{
			User user = userDao.findOneByLogin(username);

			return buildUserFromEntity(user);
		}
		catch (NoResultException | CSecurityException ex)
		{
			throw new UsernameNotFoundException("The user entity is corrupted", ex);
		}
	}

	/**
	 * Builds the user from entity.
	 *
	 * @param entity the entity
	 * @return the user
	 * @throws CSecurityException throws if the entity is null
	 */
	private org.springframework.security.core.userdetails.User buildUserFromEntity (User entity) throws CSecurityException
	{
		String username = entity.getLogin();
		String password = entity.getPassword();
		boolean enabled = entity.getFlagEnabled();
		boolean accountNonExpired = true;

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Role role : roleDao.findByUser(entity))
		{
			authorities.add(new SimpleGrantedAuthority(role.getCode()));
		}

		// validity of organization
		Organization org = entity.getOrganization();
		boolean credentialsNonExpired = org.getFlagEnabled();

		// check existence of at least one licence
		License<?> actualLicense = org.getMainLicense();
		boolean accountNonLocked = actualLicense == null ? false : true;

		LoggedUser user = new LoggedUser(org, actualLicense, username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		return user;
	}
}