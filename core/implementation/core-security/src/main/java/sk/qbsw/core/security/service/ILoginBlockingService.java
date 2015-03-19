package sk.qbsw.core.security.service;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The Interface ILoginBlockingService.
 * @author Dalibor Rak
 * @author Peter Bozik
 * @version 1.12.4
 * @since 1.12.2
 */
public interface ILoginBlockingService {

	/**
	 * Inits the authentication service - read blockLoginLimit property from system properties or use default.
	 */
	public void init();

	/**
	 * Inits the authentication service - set blockLoginLimit property from arguments.
	 *
	 * @param blockLoginLimit the block login limit
	 */
	public void init(int blockLoginLimit);

	/**
	 * Increase invalid login counter.
	 *
	 * @param login the login
	 * @param ip the ip
	 * @throws CSystemException throws if there is any other error
	 * @throws CSecurityException throws if the user with given login doesnt exist
	 */
	public abstract void increaseInvalidLoginCounter(String login, String ip) throws CSystemException, CSecurityException;

	/**
	 * Increase invalid login counter. The user parameter on input is not checked and should be obtained before calling this method
	 *
	 * @param user the user
	 * @param ip the ip
	 * @throws CSystemException throws if there is any other error
	 * @throws CSecurityException throws if the user with given login doesnt exist
	 */
	public abstract void increaseInvalidLoginCounter(CUser user, String ip) throws CSystemException, CSecurityException;
	
	/**
	 * Reset invalid login counter.
	 *
	 * @param login the login
	 * @param ip the ip
	 * @throws CSystemException throws if there is any other error
	 * @throws CSecurityException throws if the user with given login doesnt exist
	 */
	public abstract void resetInvalidLoginCounter(String login, String ip) throws CSystemException, CSecurityException;
	
	/**
	 * Reset invalid login counter. The user parameter on input is not checked and should be obtained before calling this method
	 *
	 * @param user the user
	 * @param ip the ip
	 * @throws CSystemException throws if there is any other error
	 * @throws CSecurityException throws if the user with given login doesnt exist
	 */
	public abstract void resetInvalidLoginCounter(CUser user, String ip) throws CSystemException, CSecurityException;

	/**
	 * Checks if is login blocked.
	 *
	 * @param login the login
	 * @param ip the ip
	 * @return true, if is login blocked
	 * @throws CSystemException throws if there is any other error
	 */
	public abstract boolean isLoginBlocked(String login, String ip) throws CSystemException;

}