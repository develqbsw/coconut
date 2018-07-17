package sk.qbsw.organization.complex.management.test.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.organization.complex.core.dao.OrganizationDao;
import sk.qbsw.organization.complex.core.dao.UnitDao;
import sk.qbsw.organization.complex.core.dao.UserDao;
import sk.qbsw.organization.complex.core.model.domain.Organization;
import sk.qbsw.organization.complex.core.model.domain.Unit;
import sk.qbsw.organization.complex.core.model.domain.User;

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
	private final OrganizationDao orgDao;

	private final UnitDao unitDao;

	private final UserDao userDao;

	public static final String ORGANIZATION_CODE = "unit_test_organization";

	public static final String ORGANIZATION_2_CODE = "unit_test_organization_2";

	public static final String ORGANIZATION_2_CLONE_CODE = "unit_test_organization_2_clone";

	public static final String ORGANIZATION_DISABLED_CODE = "unit_test_organization_disabled";

	public static final String DEFAULT_UNIT_CODE = "unit_test_unit_default";

	public static final String FIRST_UNIT_CODE = "unit_test_unit_1";

	public static final String SECOND_UNIT_CODE = "unit_test_unit_2";

	@Autowired
	public DataGenerator (OrganizationDao orgDao, UnitDao unitDao, UserDao userDao)
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
		Organization organization = createOrganization(ORGANIZATION_CODE);
		Organization organization2 = createOrganization(ORGANIZATION_2_CODE);
		Organization organization2Clone = createOrganization(ORGANIZATION_2_CLONE_CODE);
		Organization organizationDisabled = createOrganization(ORGANIZATION_DISABLED_CODE, ActivityStates.INACTIVE);

		// units
		Unit defaultUnit = createUnit(DEFAULT_UNIT_CODE);
		Unit firstUnit = createUnit(FIRST_UNIT_CODE);
		Unit secondUnit = createUnit(SECOND_UNIT_CODE);

		User firstUser = createUser();
		User secondUser = createUser();

		/** Create connections. */
		// organization entity -> organization
		defaultUnit.setOrganization(organization);
		firstUnit.setOrganization(organization);
		secondUnit.setOrganization(organization);

		// organization entity <-> user
		Set<User> usersForDefaultUnit = new HashSet<>();
		usersForDefaultUnit.add(firstUser);

		Set<User> usersForFirstUnit = new HashSet<>();
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

	public Organization createOrganization (String code)
	{
		return createOrganization(code, ActivityStates.ACTIVE);
	}

	public Organization createOrganization (String code, ActivityStates state)
	{
		Organization organization = new Organization();
		organization.setCode(code);
		organization.setName(code);
		organization.setEmail(code + "@qbsw.sk");
		organization.setState(state);

		return organization;
	}

	public Unit createUnit (String code)
	{
		Unit unit = new Unit();
		unit.setCode(code);
		unit.setName(code);

		return unit;
	}

	public User createUser ()
	{
		return createUser(ActivityStates.ACTIVE);
	}


	public User createUser (ActivityStates state)
	{
		User user = new User();
		user.setState(state);

		return user;
	}
}
