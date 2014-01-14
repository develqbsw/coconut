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

	/**
	 * Generate data for authentication tests.
	 */
	@Transactional (readOnly = false)
	public void generateDataForDatabaseTests ()
	{
		/** Create data. */
		//organization
		COrganization organization = new COrganization();
		organization.setCode("organization");
		organization.setName("organization");
		organization.setEmail("organization@qbsw.sk");
		organization.setPhone("000000000");
		organization.setFlagEnabled(true);

		//roles
		CRole firstRole = new CRole();
		firstRole.setCode("role_1");

		CRole secondRole = new CRole();
		secondRole.setCode("role_2");

		//groups
		CGroup firstGroupInUnit = new CGroup();
		firstGroupInUnit.setCode("group_in_unit_1");
		firstGroupInUnit.setCategory("category_1");

		CGroup secondGroupInUnit = new CGroup();
		secondGroupInUnit.setCode("group_in_unit_2");
		secondGroupInUnit.setCategory("category_1");

		CGroup thirdGroupInUnit = new CGroup();
		thirdGroupInUnit.setCode("group_in_unit_3");
		thirdGroupInUnit.setCategory("category_2");

		CGroup firstGroupNotInUnit = new CGroup();
		firstGroupNotInUnit.setCode("group_not_in_unit_1");
		firstGroupNotInUnit.setCategory(null);

		CGroup secondGroupNotInUnit = new CGroup();
		secondGroupNotInUnit.setCode("group_not_in_unit_2");
		secondGroupNotInUnit.setCategory("category_2");

		//units
		CUnit defaultUnit = new CUnit();
		defaultUnit.setName("default_unit");

		CUnit firstUnit = new CUnit();
		firstUnit.setName("unit_1");

		CUnit secondUnit = new CUnit();
		secondUnit.setName("unit_2");

		//users
		CUser userWithDefaultUnit = new CUser();
		userWithDefaultUnit.setLogin("user_with_default_unit");
		userWithDefaultUnit.setPassword("user_with_default_unit");
		userWithDefaultUnit.setName("user_with_default_unit");
		userWithDefaultUnit.setSurname("user_with_default_unit");
		userWithDefaultUnit.setEmail("user_with_default_unit@qbsw.sk");
		userWithDefaultUnit.setFlagEnabled(true);

		CUser userWithoutDefaultUnit = new CUser();
		userWithoutDefaultUnit.setLogin("user_without_default_unit");
		userWithoutDefaultUnit.setPassword("user_without_default_unit");
		userWithoutDefaultUnit.setName("user_without_default_unit");
		userWithoutDefaultUnit.setSurname("user_without_default_unit");
		userWithoutDefaultUnit.setEmail("user_without_default_uni@qbsw.sk");
		userWithoutDefaultUnit.setFlagEnabled(true);

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
}
