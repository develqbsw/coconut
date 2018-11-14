package sk.qbsw.security.management.db.service;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.model.domain.BlockedLogin;

/**
 * The login blocked service.
 *
 * @author Dalibor Rak
 * @author Peter Bozik
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.12.2
 */
public interface LoginBlockingService
{
	/**
	 * Init.
	 */
	void init ();

	/**
	 * Init.
	 *
	 * @param blockLoginLimit the block login limit
	 */
	void init (int blockLoginLimit);

	/**
	 * Increase invalid login counter blocked login.
	 *
	 * @param blockedLogin the blocked login
	 * @param login the login
	 * @param ip the ip
	 * @return the blocked login
	 */
	BlockedLogin increaseInvalidLoginCounter (BlockedLogin blockedLogin, String login, String ip);

	/**
	 * Increase invalid login counter.
	 *
	 * @param login the login
	 * @param ip the ip
	 * @throws CSecurityException the c security exception
	 */
	void increaseInvalidLoginCounter (String login, String ip) throws CSecurityException;

	/**
	 * Increase invalid login counter without account check.
	 *
	 * @param login the login
	 * @param ip the ip
	 */
	void increaseInvalidLoginCounterWithoutAccountCheck (String login, String ip);

	/**
	 * Reset invalid login counter.
	 *
	 * @param blockedLogin the blocked login
	 */
	void resetInvalidLoginCounter (BlockedLogin blockedLogin);

	/**
	 * Reset invalid login counter.
	 *
	 * @param login the login
	 * @param ip the ip
	 * @throws CSecurityException the c security exception
	 */
	void resetInvalidLoginCounter (String login, String ip) throws CSecurityException;

	/**
	 * Reset invalid login counter without account check.
	 *
	 * @param login the login
	 * @param ip the ip
	 */
	void resetInvalidLoginCounterWithoutAccountCheck (String login, String ip);

	/**
	 * Is login blocked boolean.
	 *
	 * @param login the login
	 * @param ip the ip
	 * @return the boolean
	 */
	boolean isLoginBlocked (String login, String ip);

	/**
	 * Find by login and ip blocked login.
	 *
	 * @param login the login
	 * @param ip the ip
	 * @return the blocked login
	 */
	BlockedLogin findByLoginAndIp (String login, String ip);

}
