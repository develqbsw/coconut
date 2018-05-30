package sk.qbsw.security.authentication.spring.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import sk.qbsw.security.authentication.spring.model.AccountDetails;

/**
 * The account details service.
 *
 * @param <T> the type parameter
 * @author Tomas Lauro
 * @version 1.18.4
 * @since 1.18.4
 */
public interface AccountDetailsService<T extends Authentication>
{
	/**
	 * Load account security details.
	 *
	 * @param token the token
	 * @return the security account details
	 * @throws UsernameNotFoundException the username not found exception
	 */
	AccountDetails loadUserDetails (T token) throws UsernameNotFoundException;
}
