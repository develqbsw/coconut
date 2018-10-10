package sk.qbsw.security.spring.iam.auth.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * The IAM authentication pre authenticated user details service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public abstract class IAMAuthUserDetailsServiceBase implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>
{
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	public IAMAuthUserDetailsServiceBase () {
	}

	public final UserDetails loadUserDetails (PreAuthenticatedAuthenticationToken token) throws AuthenticationException
	{
		return createUserDetails(token);
	}

	/**
	 * Create user details user details.
	 *
	 * @param token the token
	 * @return the user details
	 */
	protected abstract UserDetails createUserDetails (PreAuthenticatedAuthenticationToken token);
}
