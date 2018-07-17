package sk.qbsw.security.organization.complex.management.test;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.organization.complex.core.model.domain.User;
import sk.qbsw.organization.complex.management.service.UserService;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.AccountTypes;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.management.service.AccountManagementService;
import sk.qbsw.security.management.service.OrganizationService;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;
import sk.qbsw.security.organization.complex.management.service.UserAccountService;

import static org.junit.Assert.assertNotNull;

/**
 * The type User account test case.
 *
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.19.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
@Rollback
@Ignore
public class UserAccountTestCase
{
	private static final String ORGANIZATION_CODE = "unit_test_organization";

	private static final String ORGANIZATION_COMPLEX_CODE = "unit_test_organization_complex";

	private static final String ORGANIZATION_COMPLEX_DISABLED_CODE = "unit_test_organization_complex_disabled";

	private static final String ACCOUNT_CREATED = "unit_test_account_created";

	private static final String ACCOUNT_CREATED_1 = "unit_test_account_created_1";

	@Autowired
	private AccountManagementService accountManagementService;

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private UserService userService;

	@Autowired
	private OrganizationService organizationService;

	/**
	 * Test create user account with password.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testCreateUserAccountWithPassword () throws CSecurityException
	{
		Organization organization = organizationService.findByName(ORGANIZATION_CODE).get(0);

		User user = userService.findAll().get(0);

		UserAccount account = new UserAccount();
		account.setLogin(ACCOUNT_CREATED);
		account.setUser(user);

		userAccountService.create(ACCOUNT_CREATED, null, AccountTypes.PERSONAL, user, ACCOUNT_CREATED, organization);

		Account queryAccount = accountManagementService.findByByLogin(ACCOUNT_CREATED);

		// asserts
		assertNotNull("Account has not been created", queryAccount);
	}

	/**
	 * Test create user account without password.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testCreateUserAccountWithoutPassword () throws CSecurityException
	{
		Organization organization = organizationService.findByName(ORGANIZATION_CODE).get(0);

		User user = userService.findAll().get(1);

		UserAccount account = new UserAccount();
		account.setLogin(ACCOUNT_CREATED_1);
		account.setUser(user);

		userAccountService.create(ACCOUNT_CREATED_1, null, AccountTypes.PERSONAL, user, organization);

		Account queryAccount = accountManagementService.findByByLogin(ACCOUNT_CREATED_1);

		// asserts
		assertNotNull("Account has not been created", queryAccount);
	}
}
