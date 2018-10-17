package sk.qbsw.security.organization.complex.management.db.test.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.organization.complex.core.dao.CXOOrganizationDao;
import sk.qbsw.security.organization.complex.core.dao.CXOUnitDao;
import sk.qbsw.security.organization.complex.core.dao.CXOUserDao;
import sk.qbsw.security.organization.complex.core.model.domain.CXOOrganization;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUnit;
import sk.qbsw.security.organization.complex.core.model.domain.CXOUser;

import java.util.HashSet;
import java.util.Set;

/**
 * Generate data in DB for tests.
 *
 * @author Tomas Lauro
 * @author Tomas Leken
 * @version 1.19.0
 * @since 1.6.0
 */
@Component (value = "dataGenerator")
public class DataGenerator
{
	private final CXOOrganizationDao orgDao;

	private final CXOUnitDao unitDao;

	private final CXOUserDao userDao;

	public static final String ORGANIZATION_CODE = "unit_test_organization";

	public static final String ORGANIZATION_2_CODE = "unit_test_organization_2";

	public static final String ORGANIZATION_2_CLONE_CODE = "unit_test_organization_2_clone";

	public static final String ORGANIZATION_DISABLED_CODE = "unit_test_organization_disabled";

	public static final String DEFAULT_UNIT_CODE = "unit_test_unit_default";

	public static final String FIRST_UNIT_CODE = "unit_test_unit_1";

	public static final String SECOND_UNIT_CODE = "unit_test_unit_2";

	@Autowired
	public DataGenerator (CXOOrganizationDao orgDao, CXOUnitDao unitDao, CXOUserDao userDao)
	{
		this.orgDao = orgDao;
		this.unitDao = unitDao;
		this.userDao = userDao;
	}

	@Transactional
	public void generateDatabaseDataForDatabaseTests ()
	{
		/** Create data. */
		// organization
		CXOOrganization organization = createOrganization(ORGANIZATION_CODE);
		CXOOrganization organization2 = createOrganization(ORGANIZATION_2_CODE);
		CXOOrganization organization2Clone = createOrganization(ORGANIZATION_2_CLONE_CODE);
		CXOOrganization organizationDisabled = createOrganization(ORGANIZATION_DISABLED_CODE, ActivityStates.INACTIVE);

		// units
		CXOUnit defaultUnit = createUnit(DEFAULT_UNIT_CODE);
		CXOUnit firstUnit = createUnit(FIRST_UNIT_CODE);
		CXOUnit secondUnit = createUnit(SECOND_UNIT_CODE);

		CXOUser firstUser = createUser();
		CXOUser secondUser = createUser();

		/** Create connections. */
		// organization entity -> organization
		defaultUnit.setOrganization(organization);
		firstUnit.setOrganization(organization);
		secondUnit.setOrganization(organization);

		// organization entity <-> user
		Set<CXOUser> usersForDefaultUnit = new HashSet<>();
		usersForDefaultUnit.add(firstUser);

		Set<CXOUser> usersForFirstUnit = new HashSet<>();
		usersForFirstUnit.add(firstUser);
		usersForFirstUnit.add(secondUser);

		defaultUnit.setUsers(usersForDefaultUnit);
		firstUnit.setUsers(usersForFirstUnit);

		// save data to DB
		orgDao.update(organization);
		orgDao.update(organization2);
		orgDao.update(organization2Clone);
		orgDao.update(organizationDisabled);
		userDao.update(firstUser);
		userDao.update(secondUser);
		unitDao.update(defaultUnit);
		unitDao.update(firstUnit);
		unitDao.update(secondUnit);

		// flush data to hibernate cache
		orgDao.flush();
		userDao.flush();
		unitDao.flush();
		// clear cache
		orgDao.clear();
		userDao.clear();
		unitDao.clear();
	}

	public CXOOrganization createOrganization (String code)
	{
		return createOrganization(code, ActivityStates.ACTIVE);
	}

	public CXOOrganization createOrganization (String code, ActivityStates state)
	{
		CXOOrganization organization = new CXOOrganization();
		organization.setCode(code);
		organization.setName(code);
		organization.setEmail(code + "@qbsw.sk");
		organization.setState(state);

		return organization;
	}

	public CXOUnit createUnit (String code)
	{
		CXOUnit unit = new CXOUnit();
		unit.setCode(code);
		unit.setName(code);

		return unit;
	}

	public CXOUser createUser ()
	{
		return createUser(ActivityStates.ACTIVE);
	}


	public CXOUser createUser (ActivityStates state)
	{
		CXOUser user = new CXOUser();
		user.setState(state);

		return user;
	}
}
