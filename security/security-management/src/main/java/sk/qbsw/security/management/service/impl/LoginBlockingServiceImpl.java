package sk.qbsw.security.management.service.impl;

import java.time.OffsetDateTime;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.core.dao.BlockedLoginDao;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.BlockedLogin;
import sk.qbsw.security.management.service.LoginBlockingService;

/**
 * Abstract authentication service
 * 
 * @author Tomas Lauro
 * @author Peter Bozik
 * 
 * @version 1.12.4
 * @since 1.12.2
 */
public class LoginBlockingServiceImpl extends AService implements LoginBlockingService
{

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginBlockingServiceImpl.class);

	/** The block login limit. */
	private int blockLoginLimit = 5;

	/** The blocked login jpa dao. */
	@Autowired
	private BlockedLoginDao blockedLoginJpaDao;

	/** The user dao. */
	@Autowired
	private AccountDao userDao;

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IAuthenticationService#init(int)
	 */
	public void init (int blockLoginLimit)
	{
		this.blockLoginLimit = blockLoginLimit;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IAuthenticationService#init()
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
	 * @see sk.qbsw.security.core.core.service.IAuthenticationService#increaseInvalidLoginCounter(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void increaseInvalidLoginCounter (String login, String ip) throws CSecurityException
	{
		//define error message
		String errorMessage = "The invalid login counter cannot be increased";

		//get data from DB - if the user with login does not exist throw an exception
		getUserByLogin(login, errorMessage);
		BlockedLogin blockedLogin = getBlockedLogin(login, ip, errorMessage);
		if (blockedLogin == null)
		{
			blockedLogin = createBlockedLogin(login, ip);
		}

		increaseInvalidLoginCounterInner(blockedLogin);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IAuthenticationService#increaseInvalidLoginCounter(sk.qbsw.security.core.core.model.domain.CUser user, java.lang.String ip)
	 */
	@Override
	@Transactional (readOnly = false)
	public void increaseInvalidLoginCounterWithoutUserCheck (String login, String ip) throws CSecurityException
	{
		//define error message
		String errorMessage = "The invalid login counter cannot be increased";

		BlockedLogin blockedLogin = getBlockedLogin(login, ip, errorMessage);
		if (blockedLogin == null)
		{
			blockedLogin = createBlockedLogin(login, ip);
		}

		increaseInvalidLoginCounterInner(blockedLogin);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IAuthenticationService#resetInvalidLoginCounter(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void resetInvalidLoginCounter (String login, String ip) throws CSecurityException
	{
		//define error message
		String errorMessage = "The login counter cannot be reset";

		//get data from DB - if the user with login does not exist throw an exception
		getUserByLogin(login, errorMessage);
		BlockedLogin blockedLogin = getBlockedLogin(login, ip, errorMessage);

		resetInvalidLoginCounterInner(blockedLogin);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IAuthenticationService#resetInvalidLoginCounter(sk.qbsw.security.core.core.model.domain.CUser user, java.lang.String ip)
	 */
	@Override
	@Transactional
	public void resetInvalidLoginCounterWithoutUserCheck (String login, String ip) throws CSecurityException
	{
		//define error message
		String errorMessage = "The login counter cannot be reset";

		//get data from DB - if the user with login does not exist throw an exception
		BlockedLogin blockedLogin = getBlockedLogin(login, ip, errorMessage);

		//remove blocked login
		resetInvalidLoginCounterInner(blockedLogin);
	}

	private void resetInvalidLoginCounterInner (BlockedLogin blockedLogin)
	{
		if (blockedLogin != null)
		{
			blockedLoginJpaDao.remove(blockedLogin);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.service.IAuthenticationService#isLoginBlocked(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public boolean isLoginBlocked (String login, String ip) throws CSystemException
	{
		if (blockedLoginJpaDao.countCurrentlyBlockedByLoginAndIp(login, ip) > 0)
		{
			return true;
		}
		return false;
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
	private Account getUserByLogin (String login, String errorMessage) throws CSecurityException
	{
		try
		{
			return userDao.findOneByLogin(login);
		}
		catch (NoResultException ex)
		{
			LOGGER.debug(errorMessage + " - the user with login " + login + " dont exists", ex);
			throw new CSecurityException(errorMessage);
		}
		catch (Exception ex)
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
	private BlockedLogin getBlockedLogin (String login, String ip, String errorMessage) throws CSystemException
	{
		try
		{
			return blockedLoginJpaDao.findOneByLoginAndIp(login, ip);
		}
		catch (NoResultException ex)
		{
			LOGGER.debug(" Blocked login not found", ex);
			return null;
		}
		catch (Exception ex)
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
	private BlockedLogin createBlockedLogin (String login, String ip)
	{
		BlockedLogin blacklistRecord = new BlockedLogin();
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
		double blockingTime = Math.pow((double) blockLoginLimit, (double) invalidLoginCount - (double) blockLoginLimit);

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

	@Transactional (readOnly = true)
	@Override
	public BlockedLogin findBlockedLogin (String login, String ip) throws CSystemException
	{
		BlockedLogin result = null;
		try
		{
			result = blockedLoginJpaDao.findOneByLoginAndIp(login, ip);
		}
		catch (Exception e)
		{
			LOGGER.debug("Blocking for user " + login + " not found", e);
		}
		return result;
	}

	@Override
	@Transactional (readOnly = false)
	public BlockedLogin increaseInvalidLoginCounter (BlockedLogin blockedLogin, String login, String ip) throws CSecurityException
	{
		BlockedLogin block = blockedLogin;
		if (block == null)
		{
			block = createBlockedLogin(login, ip);
		}
		increaseInvalidLoginCounterInner(block);
		return block;
	}

	private void increaseInvalidLoginCounterInner (BlockedLogin blockedLogin)
	{


		//update blocked login
		blockedLogin.setInvalidLoginCount(blockedLogin.getInvalidLoginCount() + 1);
		if (blockedLogin.getInvalidLoginCount() >= blockLoginLimit)
		{
			blockedLogin.setBlockedFrom(OffsetDateTime.now());
			blockedLogin.setBlockedTo(OffsetDateTime.now().plusMinutes(countBlockLoginMinutes(blockedLogin.getInvalidLoginCount())));
		}
		blockedLoginJpaDao.update(blockedLogin);
	}

	@Transactional (readOnly = false)
	@Override
	public void resetInvalidLoginCounter (BlockedLogin blockedLogin) throws CSecurityException
	{
		resetInvalidLoginCounterInner(blockedLogin);

	}
}
