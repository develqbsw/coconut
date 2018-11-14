package sk.qbsw.security.authorization.test.util;

import org.springframework.stereotype.Component;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.authorization.service.AuthorizationService;
import sk.qbsw.security.core.model.domain.Role;

/**
 * Provides test for authorization.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.6.0
 */
@Component
public class AuthorizationTestProvider
{
	/**
	 * Test authorization with default unit positive.
	 *
	 * @param authorizationService the authorization service
	 * @throws CSecurityException the c security exception
	 */
	public void testAuthorizationWithDefaultUnitPositive (AuthorizationService authorizationService) throws CSecurityException
	{
		Role role = new Role();
		role.setCode(DataGenerator.FIRST_ROLE_CODE);
		authorizationService.checkAccessRights(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, role, null, null);
	}

	/**
	 * Test unsuccessful authorization of account with default unit.
	 *
	 * @param authorizationService the authorization service
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithDefaultUnitNegative (AuthorizationService authorizationService) throws CSecurityException
	{
		Role role = new Role();
		role.setCode(DataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, role, null, null);
	}

	/**
	 * Test successful authorization of account with unit.
	 *
	 * @param authorizationService the authorization service
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithUnitPositive (AuthorizationService authorizationService) throws CSecurityException
	{
		Role role = new Role();
		role.setCode(DataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, role, DataGenerator.FIRST_UNIT_CODE, null);
	}

	/**
	 * Test unsuccessful authorization of account with unit.
	 *
	 * @param authorizationService the authorization service
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithUnitNegative (AuthorizationService authorizationService) throws CSecurityException
	{
		Role role = new Role();
		role.setCode(DataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, role, DataGenerator.DEFAULT_UNIT_CODE, null);
	}

	/**
	 * Test successful authorization of account with category.
	 *
	 * @param authorizationService the authorization service
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithCategoryPositive (AuthorizationService authorizationService) throws CSecurityException
	{
		Role role = new Role();
		role.setCode(DataGenerator.FIRST_ROLE_CODE);
		authorizationService.checkAccessRights(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, role, null, DataGenerator.FIRST_CATEGORY_CODE);
	}

	/**
	 * Test unsuccessful authorization of account with category.
	 *
	 * @param authorizationService the authorization service
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithCategoryNegative (AuthorizationService authorizationService) throws CSecurityException
	{
		Role role = new Role();
		role.setCode(DataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, role, null, DataGenerator.FIRST_CATEGORY_CODE);
	}

	/**
	 * Test successful authorization of account with unit and category.
	 *
	 * @param authorizationService the authorization service
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithUnitAndCategoryPositive (AuthorizationService authorizationService) throws CSecurityException
	{
		Role role = new Role();
		role.setCode(DataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, role, DataGenerator.SECOND_UNIT_CODE, DataGenerator.SECOND_CATEGORY_CODE);
	}

	/**
	 * Test unsuccessful authorization of account with unit and category.
	 *
	 * @param authorizationService the authorization service
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithUnitAndCategoryNegative (AuthorizationService authorizationService) throws CSecurityException
	{
		Role role = new Role();
		role.setCode(DataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, role, DataGenerator.SECOND_UNIT_CODE, DataGenerator.FIRST_CATEGORY_CODE);
	}
}
