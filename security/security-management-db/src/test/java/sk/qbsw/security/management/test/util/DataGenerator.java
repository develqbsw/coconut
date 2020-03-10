package sk.qbsw.security.management.test.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.core.dao.*;
import sk.qbsw.security.core.model.domain.*;
import sk.qbsw.security.management.db.dao.GroupManageableDao;
import sk.qbsw.security.management.db.model.domain.GroupManageable;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Generate data in DB for tests.
 *
 * @author Tomas Lauro
 * @author Michal Slezák
 * @version 2.5.0
 * @since 1.6.0
 */
@Component (value = "dataGenerator")
public class DataGenerator
{
	private final RoleDao roleDao;

	private final AccountDao<Account> accountDao;

	private final OrganizationDao orgDao;

	private final UnitDao unitDao;

	private final GroupDao groupDao;

	private final GroupManageableDao groupManageableDao;

	private final UserDao userDao;

	private final AuthenticationParamsDao authenticationParamsDao;

	private final AccountUnitGroupDao accountUnitGroupDao;

	public static final String ORGANIZATION_CODE = "unit_test_organization";

	public static final String ORGANIZATION_2_CODE = "unit_test_organization_2";

	public static final String ORGANIZATION_2_CLONE_CODE = "unit_test_organization_2_clone";

	public static final String ORGANIZATION_DISABLED_CODE = "unit_test_organization_disabled";

	public static final String FIRST_ROLE_CODE = "unit_test_role_1";

	public static final String SECOND_ROLE_CODE = "unit_test_role_2";

	public static final String FIRST_ROLE_MANAGEABLE_GROUP_CODE = "unit_test_role_manageable_group_1";

	public static final String SECOND_ROLE_MANAGEABLE_GROUP_CODE = "unit_test_role_manageable_group_2";

	public static final String FIRST_GROUP_IN_UNIT_CODE = "unit_test_group_in_unit_1";

	public static final String SECOND_GROUP_IN_UNIT_CODE = "unit_test_group_in_unit_2";

	public static final String THIRD_GROUP_IN_UNIT_CODE = "unit_test_group_in_unit_3";

	public static final String FIRST_GROUP_NOT_IN_UNIT_CODE = "unit_test_group_not_in_unit_1";

	public static final String SECOND_GROUP_NOT_IN_UNIT_CODE = "unit_test_group_not_in_unit_2";

	public static final String FIRST_GROUP_MANAGEABLE_NOT_IN_UNIT_CODE = "unit_test_group_manageable_not_in_unit_1";

	public static final String FIRST_CATEGORY_CODE = "unit_test_category_1";

	public static final String SECOND_CATEGORY_CODE = "unit_test_category_2";

	public static final String DEFAULT_UNIT_CODE = "unit_test_unit_default";

	public static final String FIRST_UNIT_CODE = "unit_test_unit_1";

	public static final String SECOND_UNIT_CODE = "unit_test_unit_2";

	public static final String ACCOUNT_CREATED = "unit_test_account_created";

	public static final String ACCOUNT_WITH_DEFAULT_UNIT_CODE = "unit_test_account_with_default_unit";

	public static final String ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE = "unit_test_account_without_default_unit";

	public static final String ACCOUNT_WITH_DEFAULT_UNIT_CODE_NO_GROUP = "unit_test_account_with_default_unit_no_group";

	public static final String ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP = "unit_test_account_without_default_unit_no_group";

	public static final String ACCOUNT_WITHOUT_PASSWORD = "unit_test_account_without_password";

	public static final String ACCOUNT_ENABLED_IN_DISABLED_ORGANIZATION = "unit_test_account_enabled_in_disabled_organization";

	public static final String ACCOUNT_DISABLED_IN_DISABLED_ORGANIZATION = "unit_test_account_disabled_in_disabled_organization";

	public static final String ACCOUNT_DISABLED_IN_ENABLED_ORGANIZATION = "unit_test_account_disabled_in_enabled_organization";

	public static final String TEST_IP_ONE = "192.168.0.1";

	public static final String TEST_IP_TWO = "192.168.0.2";

	@Autowired
	public DataGenerator (RoleDao roleDao, AccountDao accountDao, OrganizationDao orgDao, UnitDao unitDao, GroupDao groupDao, GroupManageableDao groupManageableDao, UserDao userDao, AuthenticationParamsDao authenticationParamsDao, AccountUnitGroupDao accountUnitGroupDao)
	{
		this.roleDao = roleDao;
		this.accountDao = accountDao;
		this.orgDao = orgDao;
		this.unitDao = unitDao;
		this.groupDao = groupDao;
		this.groupManageableDao = groupManageableDao;
		this.userDao = userDao;
		this.authenticationParamsDao = authenticationParamsDao;
		this.accountUnitGroupDao = accountUnitGroupDao;
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

		// roles
		Role firstRole = createRole(FIRST_ROLE_CODE);
		Role secondRole = createRole(SECOND_ROLE_CODE);

		// users
		User user = createUser();

		// groups
		GroupManageable firstGroupInUnit = createGroupManageable(user, FIRST_GROUP_IN_UNIT_CODE, FIRST_CATEGORY_CODE, GroupTypes.TECHNICAL, ActivityStates.ACTIVE);
		GroupManageable secondGroupInUnit = createGroupManageable(user, SECOND_GROUP_IN_UNIT_CODE, FIRST_CATEGORY_CODE, GroupTypes.TECHNICAL, ActivityStates.ACTIVE);
		GroupManageable thirdGroupInUnit = createGroupManageable(user, THIRD_GROUP_IN_UNIT_CODE, SECOND_CATEGORY_CODE, GroupTypes.STANDARD, ActivityStates.ACTIVE);

		GroupManageable firstGroupNotInUnit = createGroupManageable(user, FIRST_GROUP_NOT_IN_UNIT_CODE, null, GroupTypes.STANDARD, ActivityStates.ACTIVE);
		GroupManageable secondGroupNotInUnit = createGroupManageable(user, SECOND_GROUP_NOT_IN_UNIT_CODE, SECOND_CATEGORY_CODE, GroupTypes.STANDARD, ActivityStates.ACTIVE);

		// units
		Unit defaultUnit = createUnit(DEFAULT_UNIT_CODE);
		Unit firstUnit = createUnit(FIRST_UNIT_CODE);
		Unit secondUnit = createUnit(SECOND_UNIT_CODE);

		// authentication params
		AuthenticationParams authenticationParamWithDefaultUnit = createAuthenticationParams(ACCOUNT_WITH_DEFAULT_UNIT_CODE, "1111", null, null);
		AuthenticationParams authenticationParamWithoutDefaultUnit = createAuthenticationParams(ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, OffsetDateTime.now().minusHours(2), null);
		AuthenticationParams authenticationParamWithDefaultUnitNoGroup = createAuthenticationParams(ACCOUNT_WITH_DEFAULT_UNIT_CODE_NO_GROUP, null, OffsetDateTime.now().plusHours(2));
		AuthenticationParams authenticationParamWithoutDefaultUnitNoGroup = createAuthenticationParams(ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP, OffsetDateTime.now().minusHours(2), OffsetDateTime.now().plusHours(2));
		AuthenticationParams authenticationParamEnabledInDisabledOrganization = createAuthenticationParams(ACCOUNT_ENABLED_IN_DISABLED_ORGANIZATION);
		AuthenticationParams authenticationParamDisabledInDisabledOrganization = createAuthenticationParams(ACCOUNT_DISABLED_IN_DISABLED_ORGANIZATION);
		AuthenticationParams authenticationParamDisabledInEnabledOrganization = createAuthenticationParams(ACCOUNT_DISABLED_IN_ENABLED_ORGANIZATION);

		// accounts
		Account accountWithDefaultUnit = createAccount(ACCOUNT_WITH_DEFAULT_UNIT_CODE);
		Account accountWithoutDefaultUnit = createAccount(ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE);
		Account accountWithDefaultUnitNoGroup = createAccount(ACCOUNT_WITH_DEFAULT_UNIT_CODE_NO_GROUP);
		Account accountWithoutDefaultUnitNoGroup = createAccount(ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP);
		Account accountEnabledInDisabledOrganization = createAccount(ACCOUNT_ENABLED_IN_DISABLED_ORGANIZATION);
		Account accountDisabledInDisabledOrganization = createAccount(ACCOUNT_DISABLED_IN_DISABLED_ORGANIZATION, ActivityStates.INACTIVE);
		Account accountDisabledInEnabledOrganization = createAccount(ACCOUNT_DISABLED_IN_ENABLED_ORGANIZATION, ActivityStates.INACTIVE);

		/** Create connections. */
		// unit -> organization
		defaultUnit.setOrganization(organization);
		firstUnit.setOrganization(organization);
		secondUnit.setOrganization(organization);

		// group <-> unit
		Set<Group> groupsForDefaultUnit = new HashSet<>();
		groupsForDefaultUnit.add(firstGroupInUnit);
		groupsForDefaultUnit.add(secondGroupInUnit);

		Set<Group> groupsForFirstUnit = new HashSet<>();
		groupsForFirstUnit.add(secondGroupInUnit);
		groupsForFirstUnit.add(thirdGroupInUnit);

		Set<Group> groupsForSecondUnit = new HashSet<>();
		groupsForSecondUnit.add(firstGroupInUnit);
		groupsForSecondUnit.add(thirdGroupInUnit);

		defaultUnit.setGroups(groupsForDefaultUnit);
		firstUnit.setGroups(groupsForFirstUnit);
		secondUnit.setGroups(groupsForSecondUnit);

		// group <-> role
		Set<Role> firstRoleSet = new HashSet<>();
		firstRoleSet.add(firstRole);

		Set<Role> secondRoleSet = new HashSet<>();
		secondRoleSet.add(secondRole);

		firstGroupInUnit.setRoles(firstRoleSet);
		secondGroupInUnit.setRoles(firstRoleSet);
		firstGroupNotInUnit.setRoles(firstRoleSet);
		secondGroupNotInUnit.setRoles(firstRoleSet);

		thirdGroupInUnit.setRoles(secondRoleSet);

		// account -> organization
		accountWithDefaultUnit.setOrganization(organization);
		accountWithoutDefaultUnit.setOrganization(organization);
		accountWithDefaultUnitNoGroup.setOrganization(organization);
		accountWithoutDefaultUnitNoGroup.setOrganization(organization);
		accountEnabledInDisabledOrganization.setOrganization(organizationDisabled);
		accountDisabledInDisabledOrganization.setOrganization(organizationDisabled);
		accountDisabledInEnabledOrganization.setOrganization(organization);

		// account -> defaultUnit
		accountWithDefaultUnit.setDefaultUnit(defaultUnit);
		accountWithoutDefaultUnit.setDefaultUnit(null);
		accountWithDefaultUnitNoGroup.setDefaultUnit(defaultUnit);
		accountWithoutDefaultUnitNoGroup.setDefaultUnit(null);
		accountEnabledInDisabledOrganization.setDefaultUnit(null);
		accountDisabledInDisabledOrganization.setDefaultUnit(null);
		accountDisabledInEnabledOrganization.setDefaultUnit(null);

		// account -> authenticationParams
		authenticationParamWithDefaultUnit.setAccount(accountWithDefaultUnit);
		authenticationParamWithoutDefaultUnit.setAccount(accountWithoutDefaultUnit);
		authenticationParamWithDefaultUnitNoGroup.setAccount(accountWithDefaultUnitNoGroup);
		authenticationParamWithoutDefaultUnitNoGroup.setAccount(accountWithoutDefaultUnitNoGroup);
		authenticationParamEnabledInDisabledOrganization.setAccount(accountEnabledInDisabledOrganization);
		authenticationParamDisabledInDisabledOrganization.setAccount(accountDisabledInDisabledOrganization);
		authenticationParamDisabledInEnabledOrganization.setAccount(accountDisabledInEnabledOrganization);

		// save data to DB
		orgDao.update(organization);
		orgDao.update(organization2);
		orgDao.update(organization2Clone);
		orgDao.update(organizationDisabled);
		roleDao.update(firstRole);
		roleDao.update(secondRole);
		userDao.update(user);
		firstGroupInUnit = groupManageableDao.update(firstGroupInUnit);
		secondGroupInUnit = groupManageableDao.update(secondGroupInUnit);
		thirdGroupInUnit = groupManageableDao.update(thirdGroupInUnit);
		firstGroupNotInUnit = groupManageableDao.update(firstGroupNotInUnit);
		secondGroupNotInUnit = groupManageableDao.update(secondGroupNotInUnit);
		defaultUnit = unitDao.update(defaultUnit);
		firstUnit = unitDao.update(firstUnit);
		secondUnit = unitDao.update(secondUnit);
		accountWithDefaultUnit = accountDao.update(accountWithDefaultUnit);
		accountWithoutDefaultUnit = accountDao.update(accountWithoutDefaultUnit);
		accountWithDefaultUnitNoGroup = accountDao.update(accountWithDefaultUnitNoGroup);
		accountWithoutDefaultUnitNoGroup = accountDao.update(accountWithoutDefaultUnitNoGroup);
		accountEnabledInDisabledOrganization = accountDao.update(accountEnabledInDisabledOrganization);
		accountDisabledInDisabledOrganization = accountDao.update(accountDisabledInDisabledOrganization);
		accountDisabledInEnabledOrganization = accountDao.update(accountDisabledInEnabledOrganization);
		authenticationParamsDao.update(authenticationParamWithDefaultUnit);
		authenticationParamsDao.update(authenticationParamWithoutDefaultUnit);
		authenticationParamsDao.update(authenticationParamWithDefaultUnitNoGroup);
		authenticationParamsDao.update(authenticationParamWithoutDefaultUnitNoGroup);
		authenticationParamsDao.update(authenticationParamEnabledInDisabledOrganization);
		authenticationParamsDao.update(authenticationParamDisabledInDisabledOrganization);
		authenticationParamsDao.update(authenticationParamDisabledInEnabledOrganization);

		// group <-> account
		// |
		// unit
		try
		{
			setAccountToGroup(accountWithDefaultUnit, firstGroupInUnit, defaultUnit);
			setAccountToGroup(accountWithDefaultUnit, firstGroupInUnit, secondUnit);
			setAccountToGroup(accountWithDefaultUnit, secondGroupInUnit, defaultUnit);
			setAccountToGroup(accountWithDefaultUnit, secondGroupInUnit, firstUnit);
			setAccountToGroup(accountWithDefaultUnit, thirdGroupInUnit, firstUnit);
			setAccountToGroup(accountWithDefaultUnit, thirdGroupInUnit, secondUnit);

			setAccountToGroup(accountWithoutDefaultUnit, firstGroupNotInUnit, null);
			setAccountToGroup(accountWithoutDefaultUnit, secondGroupNotInUnit, null);
			setAccountToGroup(accountWithoutDefaultUnit, secondGroupInUnit, defaultUnit);
			setAccountToGroup(accountWithoutDefaultUnit, secondGroupInUnit, firstUnit);

			setAccountToGroup(accountWithDefaultUnitNoGroup, thirdGroupInUnit, firstUnit);
			setAccountToGroup(accountWithDefaultUnitNoGroup, thirdGroupInUnit, secondUnit);

			setAccountToGroup(accountWithoutDefaultUnitNoGroup, secondGroupInUnit, defaultUnit);
			setAccountToGroup(accountWithoutDefaultUnitNoGroup, secondGroupInUnit, firstUnit);

			setAccountToGroup(accountEnabledInDisabledOrganization, firstGroupNotInUnit, null);

			setAccountToGroup(accountDisabledInDisabledOrganization, secondGroupNotInUnit, null);

			setAccountToGroup(accountDisabledInEnabledOrganization, secondGroupNotInUnit, null);

		}
		catch (Exception e)
		{
			throw new CSystemException("The data generator failed", e);
		}

		// flush data to hibernate cache
		orgDao.flush();
		roleDao.flush();
		userDao.flush();
		groupDao.flush();
		unitDao.flush();
		authenticationParamsDao.flush();
		accountDao.flush();
		// clear cache
		orgDao.clear();
		roleDao.clear();
		userDao.clear();
		groupDao.clear();
		unitDao.clear();
		authenticationParamsDao.clear();
		accountDao.clear();
	}

	@Transactional
	public void generateDatabaseDataWithGroupManageableForDatabaseTests ()
	{
		// users
		User user = createUser();

		// units
		Unit firstUnit = createUnit("UNIT");

		// groups manageable
		GroupManageable firstGroupManageable = createGroupManageable(user, "CODE", "CATEGORY", GroupTypes.STANDARD, ActivityStates.ACTIVE);

		// roles
		Role firstRole = createRole(FIRST_ROLE_MANAGEABLE_GROUP_CODE);
		Role secondRole = createRole(SECOND_ROLE_MANAGEABLE_GROUP_CODE);

		// organizations
		Organization organization = createOrganization("ORAGANIZATION");

		// set units
		Set<Unit> units = new HashSet<>();
		units.add(firstUnit);
		firstGroupManageable.setUnits(units);

		firstUnit.setOrganization(organization);

		orgDao.update(organization);
		userDao.update(user);
		groupManageableDao.update(firstGroupManageable);
		roleDao.update(firstRole);
		roleDao.update(secondRole);
		unitDao.update(firstUnit);

		userDao.flush();
		groupManageableDao.flush();
		roleDao.flush();
		unitDao.flush();
		orgDao.flush();

		userDao.clear();
		groupManageableDao.clear();
		roleDao.clear();
		unitDao.clear();
		orgDao.clear();
	}

	private void setAccountToGroup (Account account, Group group, Unit unit)
{
	AccountUnitGroup accountUnitGroup = new AccountUnitGroup();
	accountUnitGroup.setAccount(account);
	accountUnitGroup.setGroup(group);
	accountUnitGroup.setUnit(unit);
	accountUnitGroupDao.update(accountUnitGroup);
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

	public User createUser()
	{
		return new User();
	}

	public Role createRole (String code)
	{
		Role role = new Role();
		role.setCode(code);

		return role;
	}

	public Group createGroup (String code, String category, GroupTypes type, ActivityStates state)
	{
		Group group = new Group();
		group.setCode(code);
		group.setCategory(category);
		group.setType(type);
		group.setState(state);

		return group;
	}

	public GroupManageable createGroupManageable (User user, String code, String category, GroupTypes type, ActivityStates state)
	{
		GroupManageable groupManageable = new GroupManageable();
		groupManageable.setCode(code);
		groupManageable.setCategory(category);
		groupManageable.setType(type);
		groupManageable.setUpdated(OffsetDateTime.now());
		groupManageable.setUpdatedBy(user);
		groupManageable.setState(state);

		return groupManageable;
	}

	public Unit createUnit (String code)
	{
		Unit unit = new Unit();
		unit.setName(code);

		return unit;
	}

	public Account createAccount (String code)
	{
		return createAccount(code, ActivityStates.ACTIVE);
	}


	public Account createAccount (String code, ActivityStates state)
	{
		Account account = new Account();
		account.setLogin(code);
		account.setEmail(code + "@qbsw.sk");
		account.setState(state);

		return account;
	}

	public AuthenticationParams createAuthenticationParams (String code, String pin)
	{
		return createAuthenticationParams(code, pin, null, null);
	}

	public AuthenticationParams createAuthenticationParams (String code, String pin, OffsetDateTime validFrom, OffsetDateTime validTo)
	{
		AuthenticationParams authenticationParams = new AuthenticationParams();
		authenticationParams.setPassword(code);
		authenticationParams.setPin(pin);
		authenticationParams.setValidFrom(validFrom);
		authenticationParams.setValidTo(validTo);

		return authenticationParams;
	}

	public AuthenticationParams createAuthenticationParams (String code)
	{
		return createAuthenticationParams(code, null, null, null);
	}

	public AuthenticationParams createAuthenticationParams (String code, OffsetDateTime validFrom, OffsetDateTime validTo)
	{
		return createAuthenticationParams(code, null, validFrom, validTo);
	}

	public BlockedLogin createBlockedLogin (String login, String ip, int invalidLoginCount, OffsetDateTime blockedFrom, OffsetDateTime blockedTo)
	{
		BlockedLogin blockedLogin = new BlockedLogin();
		blockedLogin.setLogin(login);
		blockedLogin.setIp(ip);
		blockedLogin.setInvalidLoginCount(invalidLoginCount);
		blockedLogin.setBlockedFrom(blockedFrom);
		blockedLogin.setBlockedTo(blockedTo);

		return blockedLogin;
	}
}
