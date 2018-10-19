package sk.qbsw.security.spring.iam.auth.firebase.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.security.base.model.AccountData;
import sk.qbsw.core.security.base.model.AccountDataTypes;
import sk.qbsw.core.security.base.model.AccountInputData;
import sk.qbsw.security.management.service.AccountManagementService;
import sk.qbsw.security.management.service.AccountPermissionManagementService;
import sk.qbsw.security.spring.base.service.AuthorityConverter;
import sk.qbsw.security.spring.iam.auth.base.configuration.IAMAuthAccountPermissionConfiguration;
import sk.qbsw.security.spring.iam.auth.base.model.IAMAuthLoggedUser;
import sk.qbsw.security.spring.iam.auth.common.service.IAMAuthUserDetailsServiceBase;

/**
 * The oauth pre authenticated user details service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.18.0
 */
public class FirebaseAuthUserDetailsService extends IAMAuthUserDetailsServiceBase
{
	private final AccountManagementService<AccountInputData, AccountData> accountManagementService;

	private final AccountPermissionManagementService accountPermissionManagementService;

	private final AuthorityConverter authorityConverter;

	private final IAMAuthAccountPermissionConfiguration accountPermissionConfiguration;

	/**
	 * Instantiates a new O auth service user details service.
	 *
	 * @param accountManagementService the account management service
	 * @param accountPermissionManagementService the account permission management service
	 * @param authorityConverter the authority converter
	 * @param accountPermissionConfiguration the account permissions configuration
	 */
	public FirebaseAuthUserDetailsService (AccountManagementService<AccountInputData, AccountData> accountManagementService, AccountPermissionManagementService accountPermissionManagementService, AuthorityConverter authorityConverter, IAMAuthAccountPermissionConfiguration accountPermissionConfiguration)
	{
		super();
		this.accountManagementService = accountManagementService;
		this.accountPermissionManagementService = accountPermissionManagementService;
		this.authorityConverter = authorityConverter;
		this.accountPermissionConfiguration = accountPermissionConfiguration;
	}

	@Override
	protected UserDetails createUserDetails (PreAuthenticatedAuthenticationToken token)
	{
		try
		{
			FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken((String) token.getPrincipal());

			AccountData accountData = findOrCreateAccount(firebaseToken);

			return new IAMAuthLoggedUser(accountData.getId(), accountData.getLogin(), "N/A", authorityConverter.convertRolesToAuthorities(accountData.getRoles()), accountData.getUid(), (String) token.getPrincipal());

		}
		catch (IllegalArgumentException e)
		{
			throw new PreAuthenticatedCredentialsNotFoundException("The firebase token not found", e);
		}
		catch (FirebaseAuthException e)
		{
			throw new PreAuthenticatedCredentialsNotFoundException("The firebase token processing error", e);
		}
		catch (CBusinessException e)
		{
			throw new UsernameNotFoundException("The account with given uid not found");
		}
	}

	/**
	 * Find or create account account data.
	 *
	 * @param firebaseToken the firebase token
	 * @return the account data
	 * @throws CBusinessException the c business exception
	 */
	protected AccountData findOrCreateAccount (FirebaseToken firebaseToken) throws CBusinessException
	{
		AccountData accountData = accountManagementService.findOneByUid(firebaseToken.getUid());

		if (accountData == null)
		{
			accountData = accountManagementService.register(createAccountInputData(firebaseToken));
			accountPermissionManagementService.assignAccountToGroup(accountData.getLogin(), accountPermissionConfiguration.getAccountDefaultGroupCode());
		}

		return accountManagementService.findOneByLogin(accountData.getLogin());
	}

	/**
	 * Create account input data account input data.
	 *
	 * @param firebaseToken the firebase token
	 * @return the account input data
	 */
	protected AccountInputData createAccountInputData (FirebaseToken firebaseToken)
	{
		return new AccountInputData(null, firebaseToken.getUid(), firebaseToken.getEmail(), firebaseToken.getEmail(), AccountDataTypes.PERSONAL, null, AccountInputData.DEFAULT_ORGANIZATION_ID);
	}
}
