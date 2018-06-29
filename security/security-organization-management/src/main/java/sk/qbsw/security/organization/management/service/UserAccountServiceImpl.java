package sk.qbsw.security.organization.management.service;

import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.service.AService;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.organization.core.model.domain.User;
import sk.qbsw.security.core.model.domain.AccountTypes;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.management.service.AccountManagementService;
import sk.qbsw.security.organization.core.dao.UserAccountDao;
import sk.qbsw.security.organization.core.model.domain.UserAccount;

/**
 * The type User account service.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
public class UserAccountServiceImpl extends AService implements UserAccountService
{
	private final AccountManagementService accountManagementService;

	private final UserAccountDao userAccountDao;

	/**
	 * Instantiates a new User account service.
	 *
	 * @param accountManagementService the account management service
	 * @param userAccountDao           the user account dao
	 */
	public UserAccountServiceImpl (AccountManagementService accountManagementService, UserAccountDao userAccountDao)
	{
		this.accountManagementService = accountManagementService;
		this.userAccountDao = userAccountDao;
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public UserAccount create (String login, String email, AccountTypes type, User user, String password, Organization organization) throws CSecurityException
	{
		UserAccount account = new UserAccount();
		account.setLogin(login);
		account.setEmail(email);
		account.setType(type);
		account.setState(ActivityStates.ACTIVE);
		account.setUser(user);

		return (UserAccount) accountManagementService.register(account, password, organization);
	}

	@Override
	@Transactional (rollbackFor = CBusinessException.class)
	public UserAccount create (String login, String email, AccountTypes type, User user, Organization organization) throws CSecurityException
	{
		UserAccount account = new UserAccount();
		account.setLogin(login);
		account.setEmail(email);
		account.setType(type);
		account.setState(ActivityStates.ACTIVE);
		account.setUser(user);

		return (UserAccount) accountManagementService.register(account, organization);
	}
}
