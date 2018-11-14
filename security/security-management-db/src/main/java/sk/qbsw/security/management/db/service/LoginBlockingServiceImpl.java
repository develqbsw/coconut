package sk.qbsw.security.management.db.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.BlockedLoginDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.BlockedLogin;

import javax.persistence.NoResultException;
import java.time.OffsetDateTime;

/**
 * The login blocking service.
 *
 * @author Tomas Lauro
 * @author Peter Bozik
 * @version 2.0.0
 * @since 1.12.2
 */
public class LoginBlockingServiceImpl extends AService implements LoginBlockingService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginBlockingServiceImpl.class);

	private final BlockedLoginDao blockedLoginJpaDao;

	private final AccountDao accountDao;

	private int blockLoginLimit = 5;

	/**
	 * Instantiates a new Login blocking service.
	 *
	 * @param blockedLoginJpaDao the blocked login jpa dao
	 * @param accountDao the account dao
	 */
	public LoginBlockingServiceImpl (BlockedLoginDao blockedLoginJpaDao, AccountDao accountDao)
	{
		this.blockedLoginJpaDao = blockedLoginJpaDao;
		this.accountDao = accountDao;
	}

	public void init (int blockLoginLimit)
	{
		this.blockLoginLimit = blockLoginLimit;
	}

	public void init ()
	{
		String keyFromSystem = System.getProperty("Authentication.blockLoginLimit");
		try
		{
			if (keyFromSystem != null)
			{
				this.blockLoginLimit = Integer.parseInt(keyFromSystem);
			}

		}
		catch (NumberFormatException ex)
		{
			LOGGER.error("Authentication.blockLoginLimit not set", ex);
			// do nothing, use default
		}
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public BlockedLogin increaseInvalidLoginCounter (BlockedLogin blockedLogin, String login, String ip)
	{
		BlockedLogin block = blockedLogin;
		if (block == null)
		{
			block = createBlockedLogin(login, ip);
		}
		increaseInvalidLoginCounterInner(block);
		return block;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void increaseInvalidLoginCounter (String login, String ip) throws CSecurityException
	{
		// define error message
		String errorMessage = "The invalid login counter cannot be increased";

		// read data from DB - if the account with login does not exist throw an exception
		findAccountByLogin(login, errorMessage);
		BlockedLogin blockedLogin = findBlockedLoginByLoginAndIp(login, ip, errorMessage);
		if (blockedLogin == null)
		{
			blockedLogin = createBlockedLogin(login, ip);
		}

		increaseInvalidLoginCounterInner(blockedLogin);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void increaseInvalidLoginCounterWithoutAccountCheck (String login, String ip)
	{
		// define error message
		String errorMessage = "The invalid login counter cannot be increased";

		BlockedLogin blockedLogin = findBlockedLoginByLoginAndIp(login, ip, errorMessage);
		if (blockedLogin == null)
		{
			blockedLogin = createBlockedLogin(login, ip);
		}

		increaseInvalidLoginCounterInner(blockedLogin);
	}

	private void increaseInvalidLoginCounterInner (BlockedLogin blockedLogin)
	{
		// update blocked login
		blockedLogin.setInvalidLoginCount(blockedLogin.getInvalidLoginCount() + 1);
		if (blockedLogin.getInvalidLoginCount() >= blockLoginLimit)
		{
			blockedLogin.setBlockedFrom(OffsetDateTime.now());
			blockedLogin.setBlockedTo(OffsetDateTime.now().plusMinutes(countBlockLoginMinutes(blockedLogin.getInvalidLoginCount())));
		}

		blockedLoginJpaDao.update(blockedLogin);
	}

	private BlockedLogin createBlockedLogin (String login, String ip)
	{
		BlockedLogin blacklistRecord = new BlockedLogin();
		blacklistRecord.setLogin(login);
		blacklistRecord.setIp(ip);

		return blacklistRecord;
	}

	private int countBlockLoginMinutes (int invalidLoginCount)
	{
		double blockingTime = Math.pow((double) blockLoginLimit, (double) invalidLoginCount - (double) blockLoginLimit);

		if (Double.isInfinite(blockingTime) || Double.isNaN(blockingTime))
		{
			// if the result is not a number or infinite, returns block login limit
			return blockLoginLimit;
		}
		else
		{
			// return computed time
			return (int) blockingTime;
		}
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void resetInvalidLoginCounter (BlockedLogin blockedLogin)
	{
		resetInvalidLoginCounterInner(blockedLogin);

	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void resetInvalidLoginCounter (String login, String ip) throws CSecurityException
	{
		// define error message
		String errorMessage = "The login counter cannot be reset";

		// read data from DB - if the account with login does not exist throw an exception
		findAccountByLogin(login, errorMessage);
		BlockedLogin blockedLogin = findBlockedLoginByLoginAndIp(login, ip, errorMessage);

		resetInvalidLoginCounterInner(blockedLogin);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public void resetInvalidLoginCounterWithoutAccountCheck (String login, String ip)
	{
		// define error message
		String errorMessage = "The login counter cannot be reset";

		// read data from DB - if the account with login does not exist throw an exception
		BlockedLogin blockedLogin = findBlockedLoginByLoginAndIp(login, ip, errorMessage);

		// delete blocked login
		resetInvalidLoginCounterInner(blockedLogin);
	}

	private void resetInvalidLoginCounterInner (BlockedLogin blockedLogin)
	{
		if (blockedLogin != null)
		{
			blockedLoginJpaDao.remove(blockedLogin);
		}
	}

	private Account findAccountByLogin (String login, String errorMessage) throws CSecurityException
	{
		try
		{
			return accountDao.findOneByLogin(login);
		}
		catch (NoResultException ex)
		{
			LOGGER.debug(errorMessage + " - the account with login " + login + " don't exists", ex);
			throw new CSecurityException(errorMessage);
		}
		catch (Exception ex)
		{
			LOGGER.debug(errorMessage + " - the system exception raised", ex);
			throw new CSystemException(errorMessage);
		}
	}

	private BlockedLogin findBlockedLoginByLoginAndIp (String login, String ip, String errorMessage) throws CSystemException
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

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public boolean isLoginBlocked (String login, String ip) throws CSystemException
	{
		return blockedLoginJpaDao.countCurrentlyBlockedByLoginAndIp(login, ip) > 0;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public BlockedLogin findByLoginAndIp (String login, String ip) throws CSystemException
	{
		BlockedLogin result = null;
		try
		{
			result = blockedLoginJpaDao.findOneByLoginAndIp(login, ip);
		}
		catch (Exception e)
		{
			LOGGER.debug("Blocking for account " + login + " not found", e);
		}
		return result;
	}
}
