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
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountDataTypes;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.spring.base.service.AuthorityConverter;
import sk.qbsw.security.spring.iam.auth.base.model.IAMAuthLoggedUser;
import sk.qbsw.security.spring.iam.auth.base.model.TokenData;

/**
 * The IAM authentication pre authenticated user details service.
 *
 * @param <T> the type parameter
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public abstract class IAMAuthUserDetailsServiceBase<T> implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken>
{
	/**
	 * The Logger.
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private final AuthorityConverter authorityConverter;

	/**
	 * Instantiates a new Iam auth user details service base.
	 *
	 * @param authorityConverter the authority converter
	 */
	public IAMAuthUserDetailsServiceBase (AuthorityConverter authorityConverter) {
		this.authorityConverter = authorityConverter;
	}

	public final UserDetails loadUserDetails (PreAuthenticatedAuthenticationToken token) throws AuthenticationException
	{
		try
		{
			TokenData<T> tokenData = verifyToken((String) token.getPrincipal());

			AccountData accountData = findOrCreateAccount(tokenData);

			return new IAMAuthLoggedUser(accountData.getId(), accountData.getLogin(), "N/A", authorityConverter.convertRolesToAuthorities(accountData.getRoles()), accountData.getUid(), (String) token.getPrincipal());
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
	 * Create account input data account input data.
	 *
	 * @param tokenData the token data
	 * @return the account input data
	 */
	protected AccountInputData createAccountInputData (TokenData<T> tokenData)
	{
		return new AccountInputData(null, tokenData.getUid(), tokenData.getLogin(), tokenData.getEmail(), AccountDataTypes.PERSONAL, null, AccountInputData.DEFAULT_ORGANIZATION_ID);
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
	 * Find or create account account data.
	 *
	 * @param tokenData the token data
	 * @return the account data
	 * @throws CBusinessException the c business exception
	 */
	protected abstract AccountData findOrCreateAccount (TokenData<T> tokenData) throws CBusinessException;
}
