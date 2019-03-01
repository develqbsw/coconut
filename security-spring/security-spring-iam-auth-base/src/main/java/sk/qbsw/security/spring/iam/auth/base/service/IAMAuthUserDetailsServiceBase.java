package sk.qbsw.security.spring.iam.auth.base.service;

import org.springframework.security.core.userdetails.UserDetails;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.UserOutputData;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.common.service.AuthorityConverter;
import sk.qbsw.security.spring.iam.auth.common.model.IAMAuthData;
import sk.qbsw.security.spring.iam.auth.common.model.IAMAuthLoggedAccount;

/**
 * The IAM authentication pre authenticated user details service.
 *
 * @param <T> the type parameter
 * @author Tomas Leken
 * @version 2.1.0
 * @since 2.0.0
 */
public abstract class IAMAuthUserDetailsServiceBase<T>extends IAMAuthUserDetailsServiceCommon<T>
{
	protected final UserDataMapper<UserOutputData> userDataMapper;

	/**
	 * Instantiates a new Iam auth user details service base.
	 *
	 * @param authorityConverter the authority converter
	 */
	public IAMAuthUserDetailsServiceBase (AuthorityConverter authorityConverter, UserDataMapper<UserOutputData> userDataMapper)
	{
		super(authorityConverter);
		this.userDataMapper = userDataMapper;
	}

	@Override
	protected UserDetails createUserDetails (AccountData accountData, IAMAuthData iamAuthData)
	{
		return new IAMAuthLoggedAccount(accountData.getId(), accountData.getLogin(), "N/A", userDataMapper.mapToUserData(accountData.getUser()), authorityConverter.convertRolesToAuthorities(accountData.getRoles()), iamAuthData);
	}
}
