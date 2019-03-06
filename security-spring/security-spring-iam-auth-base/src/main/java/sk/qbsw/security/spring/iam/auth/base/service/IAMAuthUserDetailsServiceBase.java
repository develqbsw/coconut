package sk.qbsw.security.spring.iam.auth.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.spring.common.service.AuthorityConverter;
import sk.qbsw.security.spring.iam.auth.common.model.TokenData;

/**
 * The IAM authentication pre authenticated user details service.
 *
 * @param <T> the type parameter
 * @author Tomas Leken
 * @version 2.1.0
 * @since 2.1.0
 */
public abstract class IAMAuthUserDetailsServiceBase<T> implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>
{
	/**
	 * The Logger.
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	/**
	 * The Authority converter.
	 */
	protected final AuthorityConverter authorityConverter;

	/**
	 * Instantiates a new Iam auth user details service base.
	 *
	 * @param authorityConverter the authority converter
	 */
	public IAMAuthUserDetailsServiceBase (AuthorityConverter authorityConverter)
	{
		this.authorityConverter = authorityConverter;
	}

	public final UserDetails loadUserDetails (PreAuthenticatedAuthenticationToken token) throws AuthenticationException
	{
		try
		{
			TokenData<T> tokenData = verifyToken((String) token.getPrincipal());
			return createUserDetails(tokenData);
		}
		catch (IllegalArgumentException e)
		{
			throw new PreAuthenticatedCredentialsNotFoundException("The token not found", e);
		}
		catch (CBusinessException e)
		{
			throw new UsernameNotFoundException("The account with given uid not found");
		}
	}

	/**
	 * Verify token token data.
	 *
	 * @param token the token
	 * @return the token data
	 * @throws PreAuthenticatedCredentialsNotFoundException the pre authenticated credentials not found exception
	 */
	protected abstract TokenData<T> verifyToken (String token) throws PreAuthenticatedCredentialsNotFoundException;

	/**
	 * Create user details user details.
	 *
	 * @param tokenData the token data
	 * @return the user details
	 * @throws CBusinessException the c business exception
	 */
	protected abstract UserDetails createUserDetails (TokenData<T> tokenData) throws CBusinessException;
}
