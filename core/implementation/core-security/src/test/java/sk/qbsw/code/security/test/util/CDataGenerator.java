package sk.qbsw.code.security.test.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.dao.IRoleDao;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Generate data in DB for tests.
 *
 * @autor Tomas Lauro
 * @version 1.6.0
 * @since 1.6.0
 */
@Component (value = "dataGenerator")
public class CDataGenerator
{
	/** The role dao. */
	@Autowired
	private IRoleDao roleDao;

	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The org dao. */
	@Autowired
	private IOrganizationDao orgDao;

	/** The unit dao. */
	@Autowired
	private IUnitDao unitDao;

	/** The group dao. */
	@Autowired
	private IGroupDao groupDao;

	/** The Constant ORGANIZATION_CODE. */
	public static final String ORGANIZATION_CODE = "unit_test_organization";

	/** The Constant FIRST_ROLE_CODE. */
	public static final String FIRST_ROLE_CODE = "unit_test_role_1";

	/** The Constant SECOND_ROLE_CODE. */
	public static final String SECOND_ROLE_CODE = "unit_test_role_2";

	/** The Constant FIRST_GROUP_IN_UNIT_CODE. */
	public static final String FIRST_GROUP_IN_UNIT_CODE = "unit_test_group_in_unit_1";

	/** The Constant SECOND_GROUP_IN_UNIT_CODE. */
	public static final String SECOND_GROUP_IN_UNIT_CODE = "unit_test_group_in_unit_2";

	/** The Constant THIRD_GROUP_IN_UNIT_CODE. */
	public static final String THIRD_GROUP_IN_UNIT_CODE = "unit_test_group_in_unit_3";

	/** The Constant FIRST_GROUP_NOT_IN_UNIT_CODE. */
	public static final String FIRST_GROUP_NOT_IN_UNIT_CODE = "unit_test_group_not_in_unit_1";

	/** The Constant SECOND_GROUP_NOT_IN_UNIT_CODE. */
	public static final String SECOND_GROUP_NOT_IN_UNIT_CODE = "unit_test_group_not_in_unit_2";

	/** The Constant FIRST_CATEGORY_CODE. */
	public static final String FIRST_CATEGORY_CODE = "unit_test_category_1";

	/** The Constant SECOND_CATEGORY_CODE. */
	public static final String SECOND_CATEGORY_CODE = "unit_test_category_2";

	/** The Constant DEFAULT_UNIT_CODE. */
	public static final String DEFAULT_UNIT_CODE = "unit_test_unit_default";

	/** The Constant FIRST_UNIT_CODE. */
	public static final String FIRST_UNIT_CODE = "unit_test_unit_1";

	/** The Constant SECOND_UNIT_CODE. */
	public static final String SECOND_UNIT_CODE = "unit_test_unit_2";

	/** The Constant USER_WITH_DEFAULT_UNIT_CODE. */
	public static final String USER_WITH_DEFAULT_UNIT_CODE = "unit_test_user_with_default_unit";

	/** The Constant USER_WITHOUT_DEFAULT_UNIT_CODE. */
	public static final String USER_WITHOUT_DEFAULT_UNIT_CODE = "unit_test_user_without_default_unit";

	/**
	 * Generate data for database tests.
	 */
	@Transactional (readOnly = false)
	public void generateDatabaseDataForDatabaseTests ()
	{
		/** Create data. */
		//organization
		COrganization organization = createOrganization(ORGANIZATION_CODE);

		//roles
		CRole firstRole = createRole(FIRST_ROLE_CODE);
		CRole secondRole = createRole(SECOND_ROLE_CODE);

		//groups
		CGroup firstGroupInUnit = createGroup(FIRST_GROUP_IN_UNIT_CODE, FIRST_CATEGORY_CODE);
		CGroup secondGroupInUnit = createGroup(SECOND_GROUP_IN_UNIT_CODE, FIRST_CATEGORY_CODE);
		CGroup thirdGroupInUnit = createGroup(THIRD_GROUP_IN_UNIT_CODE, SECOND_CATEGORY_CODE);

		CGroup firstGroupNotInUnit = createGroup(FIRST_GROUP_NOT_IN_UNIT_CODE, null);
		CGroup secondGroupNotInUnit = createGroup(SECOND_GROUP_NOT_IN_UNIT_CODE, SECOND_CATEGORY_CODE);

		//units
		CUnit defaultUnit = createUnit(DEFAULT_UNIT_CODE);
		CUnit firstUnit = createUnit(FIRST_UNIT_CODE);
		CUnit secondUnit = createUnit(SECOND_UNIT_CODE);

		//users
		CUser userWithDefaultUnit = createUser(USER_WITH_DEFAULT_UNIT_CODE);
		CUser userWithoutDefaultUnit = createUser(USER_WITHOUT_DEFAULT_UNIT_CODE);

		/** Create connections. */
		//for units
		Set<CGroup> groupsForDefaultUnit = new HashSet<CGroup>();
		groupsForDefaultUnit.add(firstGroupInUnit);
		groupsForDefaultUnit.add(secondGroupInUnit);

		Set<CGroup> groupsForFirstUnit = new HashSet<CGroup>();
		groupsForFirstUnit.add(secondGroupInUnit);
		groupsForFirstUnit.add(thirdGroupInUnit);

		Set<CGroup> groupsForSecondUnit = new HashSet<CGroup>();
		groupsForSecondUnit.add(firstGroupInUnit);
		groupsForSecondUnit.add(thirdGroupInUnit);

		defaultUnit.setOrganization(organization);
		firstUnit.setOrganization(organization);
		secondUnit.setOrganization(organization);

		defaultUnit.setGroups(groupsForDefaultUnit);
		firstUnit.setGroups(groupsForFirstUnit);
		secondUnit.setGroups(groupsForSecondUnit);

		//for groups
		Set<CUnit> unitsForFirstGroupInUnit = new HashSet<CUnit>();
		unitsForFirstGroupInUnit.add(defaultUnit);
		unitsForFirstGroupInUnit.add(secondUnit);

		Set<CUnit> unitsForSecondGroupInUnit = new HashSet<CUnit>();
		unitsForSecondGroupInUnit.add(defaultUnit);
		unitsForSecondGroupInUnit.add(firstUnit);

		Set<CUnit> unitsForThirdGroupInUnit = new HashSet<CUnit>();
		unitsForThirdGroupInUnit.add(firstUnit);
		unitsForThirdGroupInUnit.add(secondUnit);

		Set<CRole> rolesForFirstGroupInUnit = new HashSet<CRole>();
		rolesForFirstGroupInUnit.add(firstRole);

		Set<CRole> rolesForSecondGroupInUnit = new HashSet<CRole>();
		rolesForSecondGroupInUnit.add(firstRole);

		Set<CRole> rolesForThirdGroupInUnit = new HashSet<CRole>();
		rolesForThirdGroupInUnit.add(secondRole);

		Set<CRole> rolesForFirstGroupNotInUnit = new HashSet<CRole>();
		rolesForFirstGroupNotInUnit.add(firstRole);

		Set<CRole> rolesForSecondGroupNotInUnit = new HashSet<CRole>();
		rolesForSecondGroupNotInUnit.add(firstRole);

		Set<CUser> usersForFirstGroupsInUnit = new HashSet<CUser>();
		usersForFirstGroupsInUnit.add(userWithDefaultUnit);

		Set<CUser> usersForSecondGroupsInUnit = new HashSet<CUser>();
		usersForSecondGroupsInUnit.add(userWithDefaultUnit);
		usersForSecondGroupsInUnit.add(userWithoutDefaultUnit);

		Set<CUser> usersForThirdGroupsInUnit = new HashSet<CUser>();
		usersForThirdGroupsInUnit.add(userWithDefaultUnit);

		Set<CUser> usersForFirstGroupsNotInUnit = new HashSet<CUser>();
		usersForFirstGroupsNotInUnit.add(userWithoutDefaultUnit);

		Set<CUser> usersForSecondGroupsNotInUnit = new HashSet<CUser>();
		usersForSecondGroupsNotInUnit.add(userWithoutDefaultUnit);

		firstGroupInUnit.setUnits(unitsForFirstGroupInUnit);
		secondGroupInUnit.setUnits(unitsForSecondGroupInUnit);
		thirdGroupInUnit.setUnits(unitsForThirdGroupInUnit);

		firstGroupInUnit.setRoles(rolesForFirstGroupInUnit);
		secondGroupInUnit.setRoles(rolesForSecondGroupInUnit);
		thirdGroupInUnit.setRoles(rolesForThirdGroupInUnit);

		firstGroupNotInUnit.setRoles(rolesForFirstGroupNotInUnit);
		secondGroupNotInUnit.setRoles(rolesForSecondGroupNotInUnit);

		firstGroupInUnit.setUsers(usersForFirstGroupsInUnit);
		secondGroupInUnit.setUsers(usersForSecondGroupsInUnit);
		thirdGroupInUnit.setUsers(usersForThirdGroupsInUnit);

		firstGroupNotInUnit.setUsers(usersForFirstGroupsNotInUnit);
		secondGroupNotInUnit.setUsers(usersForSecondGroupsNotInUnit);

		//for roles
		Set<CGroup> groupsForFirstRole = new HashSet<CGroup>();
		groupsForFirstRole.add(firstGroupInUnit);
		groupsForFirstRole.add(secondGroupInUnit);
		groupsForFirstRole.add(firstGroupNotInUnit);
		groupsForFirstRole.add(secondGroupNotInUnit);

		Set<CGroup> groupsForSecondRole = new HashSet<CGroup>();
		groupsForSecondRole.add(thirdGroupInUnit);

		firstRole.setGroups(groupsForFirstRole);
		secondRole.setGroups(groupsForSecondRole);

		//for user
		Set<CGroup> groupsForUserWithDefaultUnit = new HashSet<CGroup>();
		groupsForUserWithDefaultUnit.add(firstGroupInUnit);
		groupsForUserWithDefaultUnit.add(secondGroupInUnit);
		groupsForUserWithDefaultUnit.add(thirdGroupInUnit);

		Set<CGroup> groupsForUserWithoutDefaultUnit = new HashSet<CGroup>();
		groupsForUserWithoutDefaultUnit.add(firstGroupNotInUnit);
		groupsForUserWithoutDefaultUnit.add(secondGroupNotInUnit);
		groupsForUserWithoutDefaultUnit.add(secondGroupInUnit);

		userWithDefaultUnit.setOrganization(organization);
		userWithoutDefaultUnit.setOrganization(organization);

		userWithDefaultUnit.setDefaultUnit(defaultUnit);
		userWithoutDefaultUnit.setDefaultUnit(null);

		userWithDefaultUnit.setGroups(groupsForUserWithDefaultUnit);
		userWithoutDefaultUnit.setGroups(groupsForUserWithoutDefaultUnit);

		//save data to DB
		orgDao.save(organization);
		roleDao.save(firstRole);
		roleDao.save(secondRole);
		groupDao.save(firstGroupInUnit);
		groupDao.save(secondGroupInUnit);
		groupDao.save(thirdGroupInUnit);
		groupDao.save(firstGroupNotInUnit);
		groupDao.save(secondGroupNotInUnit);
		unitDao.save(defaultUnit);
		unitDao.save(firstUnit);
		unitDao.save(secondUnit);
		userDao.save(userWithDefaultUnit);
		userDao.save(userWithoutDefaultUnit);
		//flush data to hibernate cache
		orgDao.flush();
		roleDao.flush();
		groupDao.flush();
		unitDao.flush();
		userDao.flush();
		//clear cache
		orgDao.clear();
		roleDao.clear();
		groupDao.clear();
		unitDao.clear();
		userDao.clear();
	}

	/**
	 * Generate data for database tests.
	 */
	@Transactional (readOnly = false)
	public void generateDatabaseDataForLdapTests ()
	{
		/** Create data. */
		//organization
		COrganization organization = createOrganization(ORGANIZATION_CODE);

		//roles
		CRole firstRole = createRole(FIRST_ROLE_CODE);
		CRole secondRole = createRole(SECOND_ROLE_CODE);

		//groups
		CGroup firstGroupInUnit = createGroup(FIRST_GROUP_IN_UNIT_CODE, FIRST_CATEGORY_CODE);
		CGroup secondGroupInUnit = createGroup(SECOND_GROUP_IN_UNIT_CODE, FIRST_CATEGORY_CODE);
		CGroup thirdGroupInUnit = createGroup(THIRD_GROUP_IN_UNIT_CODE, SECOND_CATEGORY_CODE);

		CGroup firstGroupNotInUnit = createGroup(FIRST_GROUP_NOT_IN_UNIT_CODE, null);
		CGroup secondGroupNotInUnit = createGroup(SECOND_GROUP_NOT_IN_UNIT_CODE, SECOND_CATEGORY_CODE);

		//units
		CUnit defaultUnit = createUnit(DEFAULT_UNIT_CODE);
		CUnit firstUnit = createUnit(FIRST_UNIT_CODE);
		CUnit secondUnit = createUnit(SECOND_UNIT_CODE);

		/** Create connections. */
		//for units
		Set<CGroup> groupsForDefaultUnit = new HashSet<CGroup>();
		groupsForDefaultUnit.add(firstGroupInUnit);
		groupsForDefaultUnit.add(secondGroupInUnit);

		Set<CGroup> groupsForFirstUnit = new HashSet<CGroup>();
		groupsForFirstUnit.add(secondGroupInUnit);
		groupsForFirstUnit.add(thirdGroupInUnit);

		Set<CGroup> groupsForSecondUnit = new HashSet<CGroup>();
		groupsForSecondUnit.add(firstGroupInUnit);
		groupsForSecondUnit.add(thirdGroupInUnit);

		defaultUnit.setOrganization(organization);
		firstUnit.setOrganization(organization);
		secondUnit.setOrganization(organization);

		defaultUnit.setGroups(groupsForDefaultUnit);
		firstUnit.setGroups(groupsForFirstUnit);
		secondUnit.setGroups(groupsForSecondUnit);

		//for groups
		Set<CUnit> unitsForFirstGroupInUnit = new HashSet<CUnit>();
		unitsForFirstGroupInUnit.add(defaultUnit);
		unitsForFirstGroupInUnit.add(secondUnit);

		Set<CUnit> unitsForSecondGroupInUnit = new HashSet<CUnit>();
		unitsForSecondGroupInUnit.add(defaultUnit);
		unitsForSecondGroupInUnit.add(firstUnit);

		Set<CUnit> unitsForThirdGroupInUnit = new HashSet<CUnit>();
		unitsForThirdGroupInUnit.add(firstUnit);
		unitsForThirdGroupInUnit.add(secondUnit);

		Set<CRole> rolesForFirstGroupInUnit = new HashSet<CRole>();
		rolesForFirstGroupInUnit.add(firstRole);

		Set<CRole> rolesForSecondGroupInUnit = new HashSet<CRole>();
		rolesForSecondGroupInUnit.add(firstRole);

		Set<CRole> rolesForThirdGroupInUnit = new HashSet<CRole>();
		rolesForThirdGroupInUnit.add(secondRole);

		Set<CRole> rolesForFirstGroupNotInUnit = new HashSet<CRole>();
		rolesForFirstGroupNotInUnit.add(firstRole);

		Set<CRole> rolesForSecondGroupNotInUnit = new HashSet<CRole>();
		rolesForSecondGroupNotInUnit.add(firstRole);

		firstGroupInUnit.setUnits(unitsForFirstGroupInUnit);
		secondGroupInUnit.setUnits(unitsForSecondGroupInUnit);
		thirdGroupInUnit.setUnits(unitsForThirdGroupInUnit);

		firstGroupInUnit.setRoles(rolesForFirstGroupInUnit);
		secondGroupInUnit.setRoles(rolesForSecondGroupInUnit);
		thirdGroupInUnit.setRoles(rolesForThirdGroupInUnit);

		firstGroupNotInUnit.setRoles(rolesForFirstGroupNotInUnit);
		secondGroupNotInUnit.setRoles(rolesForSecondGroupNotInUnit);

		//for roles
		Set<CGroup> groupsForFirstRole = new HashSet<CGroup>();
		groupsForFirstRole.add(firstGroupInUnit);
		groupsForFirstRole.add(secondGroupInUnit);
		groupsForFirstRole.add(firstGroupNotInUnit);
		groupsForFirstRole.add(secondGroupNotInUnit);

		Set<CGroup> groupsForSecondRole = new HashSet<CGroup>();
		groupsForSecondRole.add(thirdGroupInUnit);

		firstRole.setGroups(groupsForFirstRole);
		secondRole.setGroups(groupsForSecondRole);

		//save data to DB
		orgDao.save(organization);
		roleDao.save(firstRole);
		roleDao.save(secondRole);
		groupDao.save(firstGroupInUnit);
		groupDao.save(secondGroupInUnit);
		groupDao.save(thirdGroupInUnit);
		groupDao.save(firstGroupNotInUnit);
		groupDao.save(secondGroupNotInUnit);
		unitDao.save(defaultUnit);
		unitDao.save(firstUnit);
		unitDao.save(secondUnit);
		//flush data to hibernate cache
		orgDao.flush();
		roleDao.flush();
		groupDao.flush();
		unitDao.flush();
		//clear cache
		orgDao.clear();
		roleDao.clear();
		groupDao.clear();
		unitDao.clear();
	}

	/**
	 * Generate user with default unit for ldap tests.
	 *
	 * @return the c user
	 */
	public CUser generateUserWithDefaultUnitForLdapTests ()
	{
		/** Create data. */
		//organization
		COrganization organization = createOrganization(ORGANIZATION_CODE);

		//groups
		CGroup firstGroupInUnit = createGroup(FIRST_GROUP_IN_UNIT_CODE, FIRST_CATEGORY_CODE);
		CGroup secondGroupInUnit = createGroup(SECOND_GROUP_IN_UNIT_CODE, FIRST_CATEGORY_CODE);
		CGroup thirdGroupInUnit = createGroup(THIRD_GROUP_IN_UNIT_CODE, SECOND_CATEGORY_CODE);

		//units
		CUnit defaultUnit = createUnit(DEFAULT_UNIT_CODE);

		//users
		CUser userWithDefaultUnit = createUser(USER_WITH_DEFAULT_UNIT_CODE);

		/** Create connections. */
		//for units
		defaultUnit.setOrganization(organization);

		//for groups
		Set<CUser> usersForFirstGroupsInUnit = new HashSet<CUser>();
		usersForFirstGroupsInUnit.add(userWithDefaultUnit);

		Set<CUser> usersForSecondGroupsInUnit = new HashSet<CUser>();
		usersForSecondGroupsInUnit.add(userWithDefaultUnit);

		Set<CUser> usersForThirdGroupsInUnit = new HashSet<CUser>();
		usersForThirdGroupsInUnit.add(userWithDefaultUnit);

		firstGroupInUnit.setUsers(usersForFirstGroupsInUnit);
		secondGroupInUnit.setUsers(usersForSecondGroupsInUnit);
		thirdGroupInUnit.setUsers(usersForThirdGroupsInUnit);

		//for user
		Set<CGroup> groupsForUserWithDefaultUnit = new HashSet<CGroup>();
		groupsForUserWithDefaultUnit.add(firstGroupInUnit);
		groupsForUserWithDefaultUnit.add(secondGroupInUnit);
		groupsForUserWithDefaultUnit.add(thirdGroupInUnit);

		userWithDefaultUnit.setOrganization(organization);
		userWithDefaultUnit.setDefaultUnit(defaultUnit);
		userWithDefaultUnit.setGroups(groupsForUserWithDefaultUnit);

		return userWithDefaultUnit;
	}

	/**
	 * Generate user without default unit for ldap tests.
	 *
	 * @return the c user
	 */
	public CUser generateUserWithoutDefaultUnitForLdapTests ()
	{
		/** Create data. */
		//organization
		COrganization organization = createOrganization(ORGANIZATION_CODE);

		//groups
		CGroup secondGroupInUnit = createGroup(SECOND_GROUP_IN_UNIT_CODE, FIRST_CATEGORY_CODE);
		CGroup firstGroupNotInUnit = createGroup(FIRST_GROUP_NOT_IN_UNIT_CODE, null);
		CGroup secondGroupNotInUnit = createGroup(SECOND_GROUP_NOT_IN_UNIT_CODE, SECOND_CATEGORY_CODE);

		//users
		CUser userWithoutDefaultUnit = createUser(USER_WITHOUT_DEFAULT_UNIT_CODE);

		/** Create connections. */
		//for groups
		Set<CUser> usersForSecondGroupsInUnit = new HashSet<CUser>();
		usersForSecondGroupsInUnit.add(userWithoutDefaultUnit);

		Set<CUser> usersForFirstGroupsNotInUnit = new HashSet<CUser>();
		usersForFirstGroupsNotInUnit.add(userWithoutDefaultUnit);

		Set<CUser> usersForSecondGroupsNotInUnit = new HashSet<CUser>();
		usersForSecondGroupsNotInUnit.add(userWithoutDefaultUnit);

		secondGroupInUnit.setUsers(usersForSecondGroupsInUnit);
		firstGroupNotInUnit.setUsers(usersForFirstGroupsNotInUnit);
		secondGroupNotInUnit.setUsers(usersForSecondGroupsNotInUnit);

		//for user
		Set<CGroup> groupsForUserWithoutDefaultUnit = new HashSet<CGroup>();
		groupsForUserWithoutDefaultUnit.add(firstGroupNotInUnit);
		groupsForUserWithoutDefaultUnit.add(secondGroupNotInUnit);
		groupsForUserWithoutDefaultUnit.add(secondGroupInUnit);

		userWithoutDefaultUnit.setOrganization(organization);
		userWithoutDefaultUnit.setDefaultUnit(null);
		userWithoutDefaultUnit.setGroups(groupsForUserWithoutDefaultUnit);

		return userWithoutDefaultUnit;
	}

	/**
	 * Creates the organization.
	 *
	 * @param code the code
	 * @return the c organization
	 */
	private COrganization createOrganization (String code)
	{
		COrganization organization = new COrganization();
		organization.setCode(code);
		organization.setName(code);
		organization.setEmail(code + "@qbsw.sk");
		organization.setPhone("000000000");
		organization.setFlagEnabled(true);

		return organization;
	}

	/**
	 * Creates the role.
	 *
	 * @param code the code
	 * @return the c role
	 */
	private CRole createRole (String code)
	{
		CRole role = new CRole();
		role.setCode(code);

		return role;
	}

	/**
	 * Creates the group.
	 *
	 * @param code the code
	 * @param category the category
	 * @return the c group
	 */
	private CGroup createGroup (String code, String category)
	{
		CGroup group = new CGroup();
		group.setCode(code);
		group.setCategory(category);

		return group;
	}

	/**
	 * Creates the unit.
	 *
	 * @param code the code
	 * @return the c unit
	 */
	private CUnit createUnit (String code)
	{
		CUnit unit = new CUnit();
		unit.setName(code);

		return unit;
	}

	/**
	 * Creates the user.
	 *
	 * @param code the code
	 * @return the c user
	 */
	private CUser createUser (String code)
	{
		CUser user = new CUser();
		user.setLogin(code);
		user.setPassword(code);
		user.setName(code);
		user.setSurname(code);
		user.setEmail(code + "@qbsw.sk");
		user.setFlagEnabled(true);

		return user;
	}
}
