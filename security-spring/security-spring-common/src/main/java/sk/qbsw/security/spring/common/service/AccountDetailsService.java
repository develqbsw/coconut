package sk.qbsw.security.spring.common.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import sk.qbsw.security.spring.common.model.AccountDetails;

/**
 * The account details service.
 *
 * @param <T> the type parameter
 * @author Tomas Lauro
 * @version 2.1.0
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
