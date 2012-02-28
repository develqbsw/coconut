package sk.qbsw.core.security.service;

import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * 
 * @author Dalibor Rak
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public interface IAuthenticationService
{

	/**
	 * Authenticates the user
	 * @param login login of the user
	 * @param password password of the user
	 * @return
	 */
	@Transactional (readOnly = true)
	public abstract boolean canLogin (String login, String password);
	
	/**
	 * Authenticates the user
	 * @param login login of the user
	 * @param password password of the user
	 * @return
	 */
	@Transactional (readOnly = true)
	public abstract CUser login (String login, String password);
	
	
	/** Find by login and role user must have login and role
	 * @param login - login of the user
	 * @param role - role which must have user
	 * @param password password of the user
	 * @return user if user have login and role null otherwise
	 */
	public abstract CUser login (String login, String password, CRole role);
	
	/**
	 * Authenticates the user with his role
	 * @param login login of the user
	 * @param role - role which must have user
	 * @param password password of the user
	 * @return
	 */
	@Transactional (readOnly = true)
	public abstract boolean canLogin (String login, String password, CRole role);

}
