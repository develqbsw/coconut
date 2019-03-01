package sk.qbsw.security.organization.spring.complex.iam.auth.base.service;

import org.springframework.security.core.userdetails.UserDetails;

import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.security.organization.complex.base.model.CXOUserOutputData;
import sk.qbsw.security.organization.spring.complex.common.model.CXOUserData;
import sk.qbsw.security.organization.spring.complex.iam.auth.common.model.CXOIAMAuthLoggedAccount;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.common.service.AuthorityConverter;
import sk.qbsw.security.spring.iam.auth.base.service.IAMAuthUserDetailsServiceCommon;
import sk.qbsw.security.spring.iam.auth.common.model.IAMAuthData;

/**
 * The IAM authentication pre authenticated user details service.
 *
 * @param <T> the type parameter
 * @author Tomas Leken
 * @version 2.1.0
 * @since 2.0.0
 */
public abstract class CXOIAMAuthUserDetailsServiceBase<T>extends IAMAuthUserDetailsServiceCommon<T>
{
	private final UserDataMapper<CXOUserOutputData> userDataMapper;

	/**
	 * Instantiates a new Iam auth user details service base.
	 *
	 * @param authorityConverter the authority converter
	 * @param userDataMapper the user data mapper
	 */
	public CXOIAMAuthUserDetailsServiceBase (AuthorityConverter authorityConverter, UserDataMapper<CXOUserOutputData> userDataMapper)
	{
		super(authorityConverter);
		this.userDataMapper = userDataMapper;
	}

	@Override
	protected UserDetails createUserDetails (AccountData accountData, IAMAuthData iamAuthData)
	{
		CXOUserData userData = (CXOUserData) userDataMapper.mapToUserData((CXOUserOutputData) accountData.getUser());
		return new CXOIAMAuthLoggedAccount(accountData.getId(), accountData.getLogin(), "N/A", userData, authorityConverter.convertRolesToAuthorities(accountData.getRoles()), iamAuthData);
	}
}
