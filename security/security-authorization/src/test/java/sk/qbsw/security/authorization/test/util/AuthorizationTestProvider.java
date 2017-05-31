package sk.qbsw.security.authorization.test.util;

import org.springframework.stereotype.Component;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.authorization.service.AuthorizationService;
import sk.qbsw.security.core.model.domain.CRole;

/**
 * Provides test for authorization.
 * 
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@Component
public class AuthorizationTestProvider
{
	/**
	 * Test successful authorization of user with default unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithDefaultUnitPositive (AuthorizationService authorizationService) throws CSecurityException
	{
		CRole role = new CRole(DataGenerator.FIRST_ROLE_CODE);
		authorizationService.checkAccessRights(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, role, null, null);
	}

	/**
	 * Test unsuccessful authorization of user with default unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithDefaultUnitNegative (AuthorizationService authorizationService) throws CSecurityException
	{
		CRole role = new CRole(DataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, role, null, null);
	}

	/**
	 * Test successful authorization of user with unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithUnitPositive (AuthorizationService authorizationService) throws CSecurityException
	{
		CRole role = new CRole(DataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, role, DataGenerator.FIRST_UNIT_CODE, null);
	}

	/**
	 * Test unsuccessful authorization of user with unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithUnitNegative (AuthorizationService authorizationService) throws CSecurityException
	{
		CRole role = new CRole(DataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, role, DataGenerator.DEFAULT_UNIT_CODE, null);
	}

	/**
	 * Test successful authorization of user with category.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithCategoryPositive (AuthorizationService authorizationService) throws CSecurityException
	{
		CRole role = new CRole(DataGenerator.FIRST_ROLE_CODE);
		authorizationService.checkAccessRights(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, role, null, DataGenerator.FIRST_CATEGORY_CODE);
	}

	/**
	 * Test unsuccessful authorization of user with category.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithCategoryNegative (AuthorizationService authorizationService) throws CSecurityException
	{
		CRole role = new CRole(DataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, role, null, DataGenerator.FIRST_CATEGORY_CODE);
	}

	/**
	 * Test successful authorization of user with unit and category.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithUnitAndCategoryPositive (AuthorizationService authorizationService) throws CSecurityException
	{
		CRole role = new CRole(DataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, role, DataGenerator.SECOND_UNIT_CODE, DataGenerator.SECOND_CATEGORY_CODE);
	}

	/**
	 * Test unsuccessful authorization of user with unit and category.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithUnitAndCategoryNegative (AuthorizationService authorizationService) throws CSecurityException
	{
		CRole role = new CRole(DataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, role, DataGenerator.SECOND_UNIT_CODE, DataGenerator.FIRST_CATEGORY_CODE);
	}
}
