package sk.qbsw.security.authentication.spring.anonym.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import sk.qbsw.security.authentication.spring.model.SecurityUserDetails;

/**
 * The security user details service.
 *
 * @param <T> the type parameter
 * @author Tomas Lauro
 * @version 1.18.4
 * @since 1.18.4
 */
public interface SecurityUserDetailsService<T extends Authentication>
{
	/**
	 * Load user details security user details.
	 *
	 * @param token the token
	 * @return the security user details
	 * @throws UsernameNotFoundException the username not found exception
	 */
	SecurityUserDetails loadUserDetails (T token) throws UsernameNotFoundException;
}
