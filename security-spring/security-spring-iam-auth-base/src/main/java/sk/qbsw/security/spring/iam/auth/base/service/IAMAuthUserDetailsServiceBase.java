package sk.qbsw.security.spring.iam.auth.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.spring.base.service.AuthorityConverter;
import sk.qbsw.security.spring.iam.auth.base.model.IAMAuthLoggedUser;
import sk.qbsw.security.spring.iam.auth.common.model.IAMAuthData;
import sk.qbsw.security.spring.iam.auth.common.service.IAMAuthUserDetailsServiceCommon;

/**
 * The IAM authentication pre authenticated user details service.
 *
 * @param <T> the type parameter
 * @author Tomas Leken
 * @version 2.1.0
 * @since 2.0.0
 */
public abstract class IAMAuthUserDetailsServiceBase<T> extends IAMAuthUserDetailsServiceCommon<T>
{
	/**
	 * The Logger.
	 */
	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	/**
	 * Instantiates a new Iam auth user details service base.
	 *
	 * @param authorityConverter the authority converter
	 */
	public IAMAuthUserDetailsServiceBase (AuthorityConverter authorityConverter)
	{
		super(authorityConverter);
	}

	@Override
	protected UserDetails createUserDetails (AccountData accountData, IAMAuthData iamAuthData)
	{
		return new IAMAuthLoggedUser(accountData.getId(), accountData.getLogin(), "N/A", authorityConverter.convertRolesToAuthorities(accountData.getRoles()), iamAuthData);
	}
}
