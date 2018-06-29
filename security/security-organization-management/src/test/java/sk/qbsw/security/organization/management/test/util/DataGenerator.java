package sk.qbsw.security.organization.management.test.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.organization.core.dao.UserDao;
import sk.qbsw.organization.core.model.domain.User;
import sk.qbsw.security.core.dao.AccountDao;
import sk.qbsw.security.core.dao.AccountUnitGroupDao;
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.dao.RoleDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.AccountUnitGroup;
import sk.qbsw.security.core.model.domain.AuthenticationParams;
import sk.qbsw.security.core.model.domain.BlockedLogin;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.GroupTypes;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.domain.Role;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.organization.core.model.domain.UserAccount;

import java.time.OffsetDateTime;
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
	private final UserDao userDao;

	private final RoleDao roleDao;

	private final AccountDao accountDao;

	private final OrganizationDao organizationDao;

	private final UnitDao unitDao;

	private final GroupDao groupDao;

	private final AuthenticationParamsDao authenticationParamsDao;

	private final AccountUnitGroupDao accountUnitGroupDao;

	public static final String ORGANIZATION_CODE = "unit_test_organization";

	public static final String ORGANIZATION_2_CODE = "unit_test_organization_2";

	public static final String ORGANIZATION_2_CLONE_CODE = "unit_test_organization_2_clone";

	public static final String ORGANIZATION_DISABLED_CODE = "unit_test_organization_disabled";

	public static final String FIRST_ROLE_CODE = "unit_test_role_1";

	public static final String SECOND_ROLE_CODE = "unit_test_role_2";

	public static final String FIRST_GROUP_IN_UNIT_CODE = "unit_test_group_in_unit_1";

	public static final String SECOND_GROUP_IN_UNIT_CODE = "unit_test_group_in_unit_2";

	public static final String THIRD_GROUP_IN_UNIT_CODE = "unit_test_group_in_unit_3";

	public static final String FIRST_GROUP_NOT_IN_UNIT_CODE = "unit_test_group_not_in_unit_1";

	public static final String SECOND_GROUP_NOT_IN_UNIT_CODE = "unit_test_group_not_in_unit_2";

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
	public DataGenerator (UserDao userDao, RoleDao roleDao, AccountDao accountDao, OrganizationDao organizationDao, UnitDao unitDao, GroupDao groupDao, AuthenticationParamsDao authenticationParamsDao, AccountUnitGroupDao accountUnitGroupDao)
	{
		this.userDao = userDao;
		this.roleDao = roleDao;
		this.accountDao = accountDao;
		this.organizationDao = organizationDao;
		this.unitDao = unitDao;
		this.groupDao = groupDao;
		this.authenticationParamsDao = authenticationParamsDao;
		this.accountUnitGroupDao = accountUnitGroupDao;
	}

	@Transactional
	public void generateDatabaseDataForDatabaseTests ()
	{
		/** Create data. */
		// user
		User firstUser = createUser();
		User secondUser = createUser();

		// organization
		Organization organization = createOrganization(ORGANIZATION_CODE);
		Organization organization2 = createOrganization(ORGANIZATION_2_CODE);
		Organization organization2Clone = createOrganization(ORGANIZATION_2_CLONE_CODE);
		Organization organizationDisabled = createOrganization(ORGANIZATION_DISABLED_CODE, ActivityStates.INACTIVE);

		// roles
		Role firstRole = createRole(FIRST_ROLE_CODE);
		Role secondRole = createRole(SECOND_ROLE_CODE);

		// groups
		Group firstGroupInUnit = createGroup(FIRST_GROUP_IN_UNIT_CODE, FIRST_CATEGORY_CODE, GroupTypes.TECHNICAL);
		Group secondGroupInUnit = createGroup(SECOND_GROUP_IN_UNIT_CODE, FIRST_CATEGORY_CODE, GroupTypes.TECHNICAL);
		Group thirdGroupInUnit = createGroup(THIRD_GROUP_IN_UNIT_CODE, SECOND_CATEGORY_CODE, GroupTypes.STANDARD);

		Group firstGroupNotInUnit = createGroup(FIRST_GROUP_NOT_IN_UNIT_CODE, null, GroupTypes.STANDARD);
		Group secondGroupNotInUnit = createGroup(SECOND_GROUP_NOT_IN_UNIT_CODE, SECOND_CATEGORY_CODE, GroupTypes.STANDARD);

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
		UserAccount accountWithDefaultUnit = createAccount(ACCOUNT_WITH_DEFAULT_UNIT_CODE, firstUser);
		UserAccount accountWithoutDefaultUnit = createAccount(ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, firstUser);
		UserAccount accountWithDefaultUnitNoGroup = createAccount(ACCOUNT_WITH_DEFAULT_UNIT_CODE_NO_GROUP, firstUser);
		UserAccount accountWithoutDefaultUnitNoGroup = createAccount(ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP, firstUser);
		UserAccount accountEnabledInDisabledOrganization = createAccount(ACCOUNT_ENABLED_IN_DISABLED_ORGANIZATION, firstUser);
		UserAccount accountDisabledInDisabledOrganization = createAccount(ACCOUNT_DISABLED_IN_DISABLED_ORGANIZATION, ActivityStates.INACTIVE, secondUser);
		UserAccount accountDisabledInEnabledOrganization = createAccount(ACCOUNT_DISABLED_IN_ENABLED_ORGANIZATION, ActivityStates.INACTIVE, secondUser);

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
		Set<Group> groupsForFirstRole = new HashSet<>();
		groupsForFirstRole.add(firstGroupInUnit);
		groupsForFirstRole.add(secondGroupInUnit);
		groupsForFirstRole.add(firstGroupNotInUnit);
		groupsForFirstRole.add(secondGroupNotInUnit);

		Set<Group> groupsForSecondRole = new HashSet<>();
		groupsForSecondRole.add(thirdGroupInUnit);

		firstRole.setGroups(groupsForFirstRole);
		secondRole.setGroups(groupsForSecondRole);

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
		userDao.update(firstUser);
		userDao.update(secondUser);

		organization = organizationDao.update(organization);
		organizationDao.update(organization2);
		organizationDao.update(organization2Clone);
		organizationDao.update(organizationDisabled);
		roleDao.update(firstRole);
		roleDao.update(secondRole);
		firstGroupInUnit = groupDao.update(firstGroupInUnit);
		secondGroupInUnit = groupDao.update(secondGroupInUnit);
		thirdGroupInUnit = groupDao.update(thirdGroupInUnit);
		firstGroupNotInUnit = groupDao.update(firstGroupNotInUnit);
		secondGroupNotInUnit = groupDao.update(secondGroupNotInUnit);
		defaultUnit = unitDao.update(defaultUnit);
		firstUnit = unitDao.update(firstUnit);
		secondUnit = unitDao.update(secondUnit);
		accountWithDefaultUnit = (UserAccount) accountDao.update(accountWithDefaultUnit);
		accountWithoutDefaultUnit = (UserAccount) accountDao.update(accountWithoutDefaultUnit);
		accountWithDefaultUnitNoGroup = (UserAccount) accountDao.update(accountWithDefaultUnitNoGroup);
		accountWithoutDefaultUnitNoGroup = (UserAccount) accountDao.update(accountWithoutDefaultUnitNoGroup);
		accountEnabledInDisabledOrganization = (UserAccount) accountDao.update(accountEnabledInDisabledOrganization);
		accountDisabledInDisabledOrganization = (UserAccount) accountDao.update(accountDisabledInDisabledOrganization);
		accountDisabledInEnabledOrganization = (UserAccount) accountDao.update(accountDisabledInEnabledOrganization);
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
		userDao.flush();
		organizationDao.flush();
		roleDao.flush();
		groupDao.flush();
		unitDao.flush();
		authenticationParamsDao.flush();
		accountDao.flush();
		// clear cache
		userDao.clear();
		organizationDao.clear();
		roleDao.clear();
		groupDao.clear();
		unitDao.clear();
		authenticationParamsDao.clear();
		accountDao.clear();
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

	public Role createRole (String code)
	{
		Role role = new Role();
		role.setCode(code);

		return role;
	}

	public Group createGroup (String code, String category, GroupTypes type)
	{
		Group group = new Group();
		group.setCode(code);
		group.setCategory(category);
		group.setType(type);

		return group;
	}

	public Unit createUnit (String code)
	{
		Unit unit = new Unit();
		unit.setName(code);

		return unit;
	}

	public UserAccount createAccount (String code, User user)
	{
		return createAccount(code, ActivityStates.ACTIVE, user);
	}


	public UserAccount createAccount (String code, ActivityStates state, User user)
	{
		UserAccount account = new UserAccount();
		account.setLogin(code);
		account.setEmail(code + "@qbsw.sk");
		account.setState(state);
		account.setUser(user);

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
