package sk.qbsw.security.authorization.test.util;

import org.springframework.stereotype.Component;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.authorization.service.IAuthorizationService;
import sk.qbsw.security.core.model.domain.CRole;

/**
 * Provides test for authorization.
 * 
 * @author Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@Component
public class CAuthorizationTestProvider
{
	/**
	 * Test successful authorization of user with default unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithDefaultUnitPositive (IAuthorizationService authorizationService) throws CSecurityException
	{
		CRole role = new CRole(CDataGenerator.FIRST_ROLE_CODE);
		authorizationService.checkAccessRights(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, role, null, null);
	}

	/**
	 * Test unsuccessful authorization of user with default unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithDefaultUnitNegative (IAuthorizationService authorizationService) throws CSecurityException
	{
		CRole role = new CRole(CDataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, role, null, null);
	}

	/**
	 * Test successful authorization of user with unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithUnitPositive (IAuthorizationService authorizationService) throws CSecurityException
	{
		CRole role = new CRole(CDataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, role, CDataGenerator.FIRST_UNIT_CODE, null);
	}

	/**
	 * Test unsuccessful authorization of user with unit.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithUnitNegative (IAuthorizationService authorizationService) throws CSecurityException
	{
		CRole role = new CRole(CDataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, role, CDataGenerator.DEFAULT_UNIT_CODE, null);
	}

	/**
	 * Test successful authorization of user with category.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithCategoryPositive (IAuthorizationService authorizationService) throws CSecurityException
	{
		CRole role = new CRole(CDataGenerator.FIRST_ROLE_CODE);
		authorizationService.checkAccessRights(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, role, null, CDataGenerator.FIRST_CATEGORY_CODE);
	}

	/**
	 * Test unsuccessful authorization of user with category.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithCategoryNegative (IAuthorizationService authorizationService) throws CSecurityException
	{
		CRole role = new CRole(CDataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, role, null, CDataGenerator.FIRST_CATEGORY_CODE);
	}

	/**
	 * Test successful authorization of user with unit and category.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithUnitAndCategoryPositive (IAuthorizationService authorizationService) throws CSecurityException
	{
		CRole role = new CRole(CDataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, role, CDataGenerator.SECOND_UNIT_CODE, CDataGenerator.SECOND_CATEGORY_CODE);
	}

	/**
	 * Test unsuccessful authorization of user with unit and category.
	 *
	 * @throws CSecurityException the security exception
	 */
	public void testAuthorizationWithUnitAndCategoryNegative (IAuthorizationService authorizationService) throws CSecurityException
	{
		CRole role = new CRole(CDataGenerator.SECOND_ROLE_CODE);
		authorizationService.checkAccessRights(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, role, CDataGenerator.SECOND_UNIT_CODE, CDataGenerator.FIRST_CATEGORY_CODE);
	}
}
