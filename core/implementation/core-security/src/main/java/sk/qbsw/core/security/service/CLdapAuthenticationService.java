package sk.qbsw.core.security.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.exception.LdapInvalidAttributeValueException;
import org.apache.directory.api.ldap.model.message.ResultCodeEnum;
import org.apache.directory.api.ldap.model.password.PasswordUtil;
import org.apache.directory.api.util.exception.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.logging.annotation.CNotAuditLogged;
import sk.qbsw.core.base.logging.annotation.CNotLogged;
import sk.qbsw.core.base.service.CService;
import sk.qbsw.core.security.dao.IAuthenticationParamsDao;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.exception.CInvalidUserException;
import sk.qbsw.core.security.exception.CPasswordFormatException;
import sk.qbsw.core.security.exception.CSecurityException;
import sk.qbsw.core.security.exception.CUserDisabledException;
import sk.qbsw.core.security.exception.CWrongPasswordException;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.jmx.IAuthenticationConfigurator;
import sk.qbsw.core.security.model.jmx.ILdapAuthenticationConfigurator;
import sk.qbsw.core.security.service.ldap.CLdapProvider.EModificationOperation;
import sk.qbsw.core.security.service.ldap.ILdapProvider;

/**
 * The LDAP authentication service.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.11.10
 * @since 1.6.0
 */
@Service (value = "ldapAuthenticationService")
public class CLdapAuthenticationService extends CService implements IAuthenticationService
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(CLdapAuthenticationService.class);

	/** The data. */
	@Autowired
	private ILdapAuthenticationConfigurator data;

	/** The authentication configuration. */
	@Autowired
	private IAuthenticationConfigurator authenticationConfiguration;

	/** The unit dao. */
	@Autowired
	private IUnitDao unitDao;

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The authentication params dao. */
	@Autowired
	private IAuthenticationParamsDao authenticationParamsDao;

	/** The ldap provider. */
	@Autowired
	private ILdapProvider ldapProvider;

	/** The authentication data validation service. */
	@Autowired
	private IAuthDataValidationService authDataValidationService;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#canLogin(java.lang.String, java.lang.String, sk.qbsw.core.security.model.domain.CRole)
	 */
	@Override
	@Transactional (readOnly = true)
	public boolean canLogin (String login, @CNotLogged @CNotAuditLogged String password, CRole role)
	{
		try
		{
			return login(login, password, role) != null;
		}
		catch (CSecurityException ex)
		{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser login (String login, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		return loginUser(login, password, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String, sk.qbsw.core.security.model.domain.CRole)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser login (String login, @CNotLogged @CNotAuditLogged String password, CRole role) throws CSecurityException
	{
		CUser user = loginUser(login, password, null);

		//checks if the user has the role
		if (!user.hasRole(role))
		{
			throw new CSecurityException("User " + login + " has no such role");
		}

		return user;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#login(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = true)
	public CUser login (String login, @CNotLogged @CNotAuditLogged String password, String unit) throws CSecurityException
	{
		CUnit databaseUnit = unitDao.findByName(unit);
		CUser user = null;

		if (databaseUnit != null)
		{
			user = loginUser(login, password, databaseUnit);
		}
		else
		{
			user = loginUser(login, password, null);
		}

		if (user.isInUnit(databaseUnit) == true)
		{
			return user;
		}
		else
		{
			throw new CSecurityException("User is not is unit with name " + unit);
		}
	}

	/**
	 * Login user - if the unit is null, use user default unit (if not null) otherwise use unit.
	 *
	 * @param login the login
	 * @param password the password
	 * @param unit the unit - optional
	 * @return the user
	 * @throws CSecurityException the security exception
	 */
	private CUser loginUser (String login, String password, CUnit unit) throws CSecurityException
	{
		//gets user from ldap - all information in this object are now from ldap
		CUser user;

		try
		{
			user = userDao.findByLogin(login, unit);
		}
		catch (NoResultException nre)
		{
			user = null;
		}

		if (user != null)
		{
			// check if user is disabled
			if ( (user.getOrganization().getFlagEnabled() != null && user.getOrganization().getFlagEnabled().equals(false)) || (user.getFlagEnabled() != null && user.getFlagEnabled().equals(false)))
			{
				throw new CUserDisabledException("");
			}

			//authenticate user in ldap
			if (authenticateUser(login, password) == true)
			{
				return user;
			}
			else
			{
				throw new CWrongPasswordException("Password in ldap for user " + login + " doesn't match");
			}
		}
		else
		{
			throw new CInvalidUserException("The user with login " + login + " not found");
		}
	}

	/**
	 * Authenticate user in LDAP.
	 *
	 * @param login the login
	 * @param password the password
	 * @return true, if successful
	 * @throws CSecurityException the configuration is corrupted
	 */
	private boolean authenticateUser (String login, String password) throws CSecurityException
	{
		//the exceptions thrown in ldap authentication process
		List<Throwable> exceptions = new ArrayList<Throwable>();

		if (data.getUserSearchBaseDns() != null)
		{
			for (String userSearchDn : data.getUserSearchBaseDns())
			{
				try
				{
					//authenticate
					ldapProvider.authenticate(userSearchDn, String.format(data.getUserSearchFilter(), login), password);
					logger.debug("User " + login + " was authenticated by LDAP in tree " + userSearchDn);

					return true;
				}
				catch (Throwable ex)
				{
					exceptions.add(ex);
					continue;
				}
			}

			for (int i = 0; i < exceptions.size(); i++)
			{
				logger.error("LDAP authentication error in " + (i + 1) + ". baseDn: " + exceptions.get(i).toString());
			}

			return false;
		}
		else
		{
			throw new CSecurityException("Ldap configuration corrupted");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#changeEncryptedPassword(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = false)
	public void changeEncryptedPassword (String login, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		CUser user = userDao.findByLogin(login);

		//validate password, if not valid throw an exception
		authDataValidationService.validatePassword(password);

		//TODO: create user search
		//create dn
		String userDn = new StringBuilder().append("cn=").append(login).append(",").append(data.getUserSearchBaseDns()[0]).toString();

		try
		{
			if (ldapProvider.entryExists(userDn) == true)
			{
				//change password
				ldapProvider.modifyEntry(userDn, "userPassword", password, EModificationOperation.REPLACE_ATTRIBUTE);
			}
			else
			{
				//add auth data
				Map<String, byte[][]> attributes = new HashMap<String, byte[][]>();
				attributes.put("objectClass", new byte[][] {data.getUserObjectClass().getBytes()});
				attributes.put("cn", new byte[][] {login.getBytes()});
				attributes.put("sn", new byte[][] {user.getSurname() != null ? user.getSurname().getBytes() : login.getBytes()});
				attributes.put("userPassword", new byte[][] {PasswordUtil.createStoragePassword(password, authenticationConfiguration.getLdapPasswordHashMethod().getLdapAlgorithm())});

				//add entry
				ldapProvider.addEntry(userDn, attributes);
			}

			//set auth params
			CAuthenticationParams authParams = null;
			try
			{
				authParams = authenticationParamsDao.findByUserId(user.getId());
			}
			catch (NoResultException ex)
			{
				//create new because user has no auth params
				authParams = new CAuthenticationParams();
				authParams.setUser(user);
				authParams.setPassword(null);
				authParams.setPasswordDigest(null);
				authParams.setPin(null);
				authenticationParamsDao.save(authParams);
			}
		}
		catch (LdapInvalidAttributeValueException ex)
		{
			logger.error("The user password change failed", ex);
			if (ex.getResultCode().equals(ResultCodeEnum.CONSTRAINT_VIOLATION))
			{
				throw new CPasswordFormatException("The password format is invalid");
			}
			else
			{
				throw new CSecurityException("There is a invalid input value");
			}
		}
		catch (Throwable ex)
		{
			logger.error("The user password change failed", ex);
			throw new CSecurityException("Password change failed");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#changePlainPassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void changePlainPassword (String login, String email, @CNotLogged @CNotAuditLogged String password) throws CSecurityException
	{
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#changeLogin(java.lang.Long, java.lang.String)
	 */
	@Override
	@Transactional (readOnly = false)
	public void changeLogin (Long userId, String login) throws CSecurityException
	{
		CUser user = userDao.findById(userId);

		//TODO: create user search
		//create dn
		String userDn = new StringBuilder().append("cn=").append(user.getLogin()).append(",").append(data.getUserSearchBaseDns()[0]).toString();
		String newRdn = new StringBuilder().append("cn=").append(login).toString();

		try
		{
			//the record in LDAP is updated only if it had existed
			if (ldapProvider.entryExists(userDn) == true)
			{
				//change login
				ldapProvider.renameEntry(userDn, newRdn, true);
			}
		}
		catch (LdapException ex)
		{
			logger.error("The user password change failed", ex);
			throw new CSecurityException("The login cannot be changed");
		}

		//save user login in DB
		user.setLogin(login);
		userDao.save(user);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#isOnline()
	 */
	@Override
	public boolean isOnline ()
	{
		return ldapProvider.isConnected();
	}
}
