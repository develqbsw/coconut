package sk.qbsw.security.management.service;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.security.core.model.domain.BlockedLogin;

/**
 * The Interface LoginBlockingService.
 * @author Dalibor Rak
 * @author Peter Bozik
 * @version 1.12.5
 * @since 1.12.2
 */
public interface LoginBlockingService
{

	/**
	 * Inits the authentication service - read blockLoginLimit property from system properties or use default.
	 */
	public void init ();

	/**
	 * Inits the authentication service - set blockLoginLimit property from arguments.
	 *
	 * @param blockLoginLimit the block login limit
	 */
	public void init (int blockLoginLimit);

	/**
	 * Increase invalid login counter. Primary the blockedLogin is used. If blockedLogin does not exists, it will be created from login and password parameters
	 * 
	 * @param blockedLogin
	 * @param login the login
	 * @param ip the ip
	 * @return updated or inserted {@link BlockedLogin}
	 * @throws CSystemException
	 * @throws CSecurityException
	 */
	public abstract BlockedLogin increaseInvalidLoginCounter (BlockedLogin blockedLogin,String login,String ip) throws CSystemException, CSecurityException;

	/**
	 * Increase invalid login counter.
	 *
	 * @param login the login
	 * @param ip the ip
	 * @throws CSystemException throws if there is any other error
	 * @throws CSecurityException throws if the user with given login doesnt exist
	 */
	public abstract void increaseInvalidLoginCounter (String login, String ip) throws CSystemException, CSecurityException;

	/**
	 * Increase invalid login counter. The user parameter on input is not checked and should be obtained before calling this method. Login is not checked for valid user
	 *
	 * @param login the login of the user
	 * @param ip the ip
	 * @throws CSystemException throws if there is any other error
	 * @throws CSecurityException throws if the user with given login doesnt exist
	 */
	public abstract void increaseInvalidLoginCounterWithoutUserCheck (String login, String ip) throws CSystemException, CSecurityException;

	/**
	 * Reset invalid login counter.
	 *
	 * @param login the login
	 * @param ip the ip
	 * @throws CSystemException throws if there is any other error
	 * @throws CSecurityException throws if the user with given login doesnt exist
	 */
	public abstract void resetInvalidLoginCounter (String login, String ip) throws CSystemException, CSecurityException;

	
	/**
	 * Reset invalid login counter.
	 * @param blockedLogin
	 * @throws CSystemException
	 * @throws CSecurityException
	 */
	public abstract void resetInvalidLoginCounter (BlockedLogin blockedLogin) throws CSystemException, CSecurityException;

	/**
	 * Reset invalid login counter. The user parameter on input is not checked and should be obtained before calling this method. Login is not checked for valid user
	 *
	 * @param login the login of the user
	 * @param ip the ip
	 * @throws CSystemException throws if there is any other error
	 * @throws CSecurityException throws if the user with given login doesnt exist
	 */
	public abstract void resetInvalidLoginCounterWithoutUserCheck (String login, String ip) throws CSystemException, CSecurityException;

	/**
	 * Checks if is login blocked.
	 *
	 * @param login the login
	 * @param ip the ip
	 * @return true, if is login blocked
	 * @throws CSystemException throws if there is any other error
	 */
	public abstract boolean isLoginBlocked (String login, String ip) throws CSystemException;


	/**
	 * finds blocked login by login and ip
	 * @param login
	 * @param ip
	 * @return {@link BlockedLogin} or null if entity does not exist
	 * @throws CSystemException
	 */
	public abstract BlockedLogin findBlockedLogin (String login, String ip) throws CSystemException;

}
