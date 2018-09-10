package sk.qbsw.security.organization.complex.management.db.test;

import org.junit.Assert;
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
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.management.db.service.OrganizationService;
import sk.qbsw.security.management.service.AccountManagementService;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationAccountData;
import sk.qbsw.security.organization.complex.base.model.ComplexOrganizationAccountInputData;

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
public class UserAccountTestCase
{
	private static final String ORGANIZATION_CODE = "unit_test_organization";

	private static final String ACCOUNT_CREATED = "unit_test_account_created";

	private static final String ACCOUNT_CREATED_1 = "unit_test_account_created_1";

	@Autowired
	private AccountManagementService<ComplexOrganizationAccountInputData, ComplexOrganizationAccountData> accountManagementService;

	@Autowired
	private UserService userService;

	// TODO: remove after organization separation
	@Autowired
	private OrganizationDao organizationDao;

	/**
	 * Test create user account with password.
	 *
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testCreateUserAccountWithPassword () throws CSecurityException
	{
		Organization organization = organizationDao.findByName(ORGANIZATION_CODE).get(0);
		User user = userService.findAll().get(0);

		ComplexOrganizationAccountInputData inputData = new ComplexOrganizationAccountInputData();
		inputData.setOrganizationId(organization.getId());
		inputData.setUserId(user.getId());
		inputData.setLogin(ACCOUNT_CREATED);

		accountManagementService.register(inputData);

		ComplexOrganizationAccountData accountData = accountManagementService.findOneByLogin(ACCOUNT_CREATED);

		// asserts
		Assert.assertNotNull("Account has not been created", accountData);
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
		Organization organization = organizationDao.findByName(ORGANIZATION_CODE).get(0);

		User user = userService.findAll().get(1);

		ComplexOrganizationAccountInputData inputData = new ComplexOrganizationAccountInputData();
		inputData.setOrganizationId(organization.getId());
		inputData.setUserId(user.getId());
		inputData.setLogin(ACCOUNT_CREATED_1);

		accountManagementService.register(inputData, "password");

		ComplexOrganizationAccountData accountData = accountManagementService.findOneByLogin(ACCOUNT_CREATED_1);

		// asserts
		Assert.assertNotNull("Account has not been created", accountData);
	}
}
