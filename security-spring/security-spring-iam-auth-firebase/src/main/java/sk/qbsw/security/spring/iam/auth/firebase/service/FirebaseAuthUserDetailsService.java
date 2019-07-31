package sk.qbsw.security.spring.iam.auth.firebase.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.security.base.model.*;
import sk.qbsw.security.management.service.AccountManagementService;
import sk.qbsw.security.management.service.AccountPermissionManagementService;
import sk.qbsw.security.spring.base.mapper.UserDataMapper;
import sk.qbsw.security.spring.common.service.AuthorityConverter;
import sk.qbsw.security.spring.iam.auth.base.service.IAMAuthUserDetailsServiceBase;
import sk.qbsw.security.spring.iam.auth.common.configuration.IAMAuthAccountPermissionConfiguration;
import sk.qbsw.security.spring.iam.auth.common.model.IAMAuthData;
import sk.qbsw.security.spring.iam.auth.common.model.IAMAuthLoggedAccount;
import sk.qbsw.security.spring.iam.auth.common.model.TokenData;

/**
 * The oauth pre authenticated user details service.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 1.18.0
 */
public class FirebaseAuthUserDetailsService extends IAMAuthUserDetailsServiceBase<FirebaseToken>
{
	private final AccountManagementService<AccountInputData, AccountData> accountManagementService;

	private final AccountPermissionManagementService accountPermissionManagementService;

	private final IAMAuthAccountPermissionConfiguration accountPermissionConfiguration;

	private final UserDataMapper<UserOutputData> userDataMapper;

	/**
	 * Instantiates a new O auth service user details service.
	 *
	 * @param accountManagementService the account management service
	 * @param accountPermissionManagementService the account permission management service
	 * @param authorityConverter the authority converter
	 * @param accountPermissionConfiguration the account permissions configuration
	 * @param userDataMapper the user data mapper
	 */
	public FirebaseAuthUserDetailsService (AccountManagementService<AccountInputData, AccountData> accountManagementService, AccountPermissionManagementService accountPermissionManagementService, //
		AuthorityConverter authorityConverter, IAMAuthAccountPermissionConfiguration accountPermissionConfiguration, UserDataMapper<UserOutputData> userDataMapper)
	{
		super(authorityConverter);
		this.userDataMapper = userDataMapper;
		this.accountManagementService = accountManagementService;
		this.accountPermissionManagementService = accountPermissionManagementService;
		this.accountPermissionConfiguration = accountPermissionConfiguration;
	}

	@Override
	protected TokenData<FirebaseToken> verifyToken (String token) throws PreAuthenticatedCredentialsNotFoundException
	{
		try
		{
			FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token);
			return new TokenData<>(firebaseToken.getUid(), firebaseToken.getEmail(), firebaseToken.getEmail(), firebaseToken, token);
		}
		catch (FirebaseAuthException e)
		{
			throw new PreAuthenticatedCredentialsNotFoundException("The firebase token processing error", e);
		}
	}

	@Override
	protected UserDetails createUserDetails (TokenData<FirebaseToken> tokenData) throws CBusinessException
	{
		AccountData accountData = accountManagementService.findOneByUid(tokenData.getUid());

		if (accountData == null)
		{
			accountData = accountManagementService.register(createAccountInputData(tokenData));
			accountPermissionManagementService.assignAccountToGroup(accountData.getLogin(), accountPermissionConfiguration.getAccountDefaultGroupCode());
		}

		AccountData currentAccountData = accountManagementService.findOneByLogin(accountData.getLogin());
		IAMAuthData iamAuthData = new IAMAuthData(currentAccountData.getUid(), tokenData.getValue());

		return new IAMAuthLoggedAccount(currentAccountData.getId(), currentAccountData.getLogin(), "N/A", accountData.getState().equals(AccountDataStates.ACTIVE), //
			userDataMapper.mapToUserData(currentAccountData.getUser()), authorityConverter.convertRolesToAuthorities(currentAccountData.getRoles()), iamAuthData);
	}

	/**
	 * Create account input data account input data.
	 *
	 * @param tokenData the token data
	 * @return the account input data
	 */
	protected AccountInputData createAccountInputData (TokenData<FirebaseToken> tokenData)
	{
		return new AccountInputData(null, tokenData.getUid(), tokenData.getLogin(), tokenData.getEmail(), AccountDataTypes.PERSONAL, null, AccountInputData.DEFAULT_ORGANIZATION_ID);
	}
}
