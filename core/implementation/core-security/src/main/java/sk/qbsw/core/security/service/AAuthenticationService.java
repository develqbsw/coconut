package sk.qbsw.core.security.service;

import javax.persistence.NoResultException;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.security.dao.IAuthenticationParamsDao;
import sk.qbsw.core.security.dao.IBlockedLoginDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CBlockedLogin;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Abstract authentication service
 * 
 * @author Tomas Lauro
 * 
 * @version 1.12.2
 * @since 1.12.2
 */
public abstract class AAuthenticationService extends AService implements IAuthenticationService
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	final static Logger LOGGER = LoggerFactory.getLogger(AAuthenticationService.class);

	/** The block login limit. */
	private int blockLoginLimit = 5;

	/** The blocked login jpa dao. */
	@Autowired
	private IBlockedLoginDao blockedLoginJpaDao;

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The authentication params dao. */
	@Autowired
	private IAuthenticationParamsDao authenticationParamsDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#init(int)
	 */
	public void init (int blockLoginLimit)
	{
		this.blockLoginLimit = blockLoginLimit;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#init()
	 */
	public void init ()
	{
		String keyFromSystem = System.getProperty("Authentication.blockLoginLimit");
		try
		{
			if (keyFromSystem != null)
			{
				int keyToInt = Integer.parseInt(keyFromSystem);
				this.blockLoginLimit = keyToInt;
			}

		}
		catch (NumberFormatException ex)
		{
			LOGGER.error("Authentication.blockLoginLimit not set", ex);
			//do nothing, use default
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#increaseInvalidLoginCounter(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void increaseInvalidLoginCounter (String login, String ip) throws CSystemException, CSecurityException
	{
		//define error message
		String errorMessage = "The invalid login counter cannot be increased";

		//get data from DB - if the user with login does not exist throw an exception
		getUserByLogin(login, errorMessage);
		CBlockedLogin blockedLogin = getBlockedLogin(login, ip, errorMessage);
		if (blockedLogin == null)
		{
			blockedLogin = createBlockedLogin(login, ip);
		}

		//update blocked login
		blockedLogin.setInvalidLoginCount(blockedLogin.getInvalidLoginCount() + 1);
		if (blockedLogin.getInvalidLoginCount() >= blockLoginLimit)
		{
			blockedLogin.setBlockedFrom(DateTime.now());
			blockedLogin.setBlockedTo(DateTime.now().plusMinutes(countBlockLoginMinutes(blockedLogin.getInvalidLoginCount())));
		}
		blockedLoginJpaDao.save(blockedLogin);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#resetInvalidLoginCounter(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void resetInvalidLoginCounter (String login, String ip) throws CSystemException, CSecurityException
	{
		//define error message
		String errorMessage = "The login counter cannot be reset";

		//get data from DB - if the user with login does not exist throw an exception
		getUserByLogin(login, errorMessage);
		CBlockedLogin blockedLogin = getBlockedLogin(login, ip, errorMessage);

		//remove blocked login
		if (blockedLogin != null)
		{
			blockedLoginJpaDao.remove(blockedLogin);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.service.IAuthenticationService#isLoginBlocked(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public boolean isLoginBlocked (String login, String ip) throws CSystemException
	{
		if (blockedLoginJpaDao.countCurrentlyBlocked(login, ip) > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Gets the user by login or throw an exception if not found.
	 *
	 * @param login the login
	 * @param errorMessage the error message
	 * @return the user by login
	 * @throws CSecurityException throws if user doesn't exists
	 * @throws CSystemException throws if there is any other error
	 */
	private CUser getUserByLogin (String login, String errorMessage) throws CSecurityException, CSystemException
	{
		try
		{
			return userDao.findByLogin(login);
		}
		catch (NoResultException ex)
		{
			LOGGER.debug(errorMessage + " - the user with login " + login + " dont exists", ex);
			throw new CSecurityException(errorMessage);
		}
		catch (Throwable ex)
		{
			LOGGER.debug(errorMessage + " - the system exception raised", ex);
			throw new CSystemException(errorMessage);
		}
	}

	/**
	 * Gets the auth black list record from DB if there is a record or return a null.
	 *
	 * @param login the login
	 * @param ip the ip
	 * @param errorMessage the error message
	 * @return the auth black list record or a null if there is no such record
	 * @throws CSystemException throws if there is any other error
	 */
	private CBlockedLogin getBlockedLogin (String login, String ip, String errorMessage) throws CSystemException
	{
		try
		{
			return blockedLoginJpaDao.findByLoginAndIp(login, ip);
		}
		catch (NoResultException ex)
		{
			return null;
		}
		catch (Throwable ex)
		{
			LOGGER.debug(errorMessage + " - the system exception raised", ex);
			throw new CSystemException(errorMessage);
		}
	}

	/**
	 * Creates the auth black list record.
	 *
	 * @param login the login
	 * @param ip the ip
	 * @return the auth blacklist record
	 */
	private CBlockedLogin createBlockedLogin (String login, String ip)
	{
		CBlockedLogin blacklistRecord = new CBlockedLogin();
		blacklistRecord.setLogin(login);
		blacklistRecord.setIp(ip);

		return blacklistRecord;
	}

	/**
	 * Count block login minutes - if the calculation fails the blockLoginLimit is returned.
	 *
	 * @param invalidLoginCount the invalid login count
	 * @return the int
	 */
	private int countBlockLoginMinutes (int invalidLoginCount)
	{
		double blockingTime = Math.pow((double) blockLoginLimit, ((double) invalidLoginCount - (double) blockLoginLimit));

		if (Double.isInfinite(blockingTime) || Double.isNaN(blockingTime))
		{
			//if the result is not a number or infinite, returns block login limit
			return blockLoginLimit;
		}
		else
		{
			//return computed time
			return (int) blockingTime;
		}
	}
}
