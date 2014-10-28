package sk.qbsw.core.security.test.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.security.dao.IAuthenticationParamsDao;
import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.dao.IRoleDao;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CAddress;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * Generate data in DB for tests.
 *
 * @autor Tomas Lauro
 * @version 1.11.7
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

	/** The authentication params dao. */
	@Autowired
	private IAuthenticationParamsDao authenticationParamsDao;

	/** The Constant ORGANIZATION_CODE. */
	public static final String ORGANIZATION_CODE = "unit_test_organization";

	/** The Constant ORGANIZATION_2_CODE. */
	public static final String ORGANIZATION_2_CODE = "unit_test_organization_2";

	/** The Constant ORGANIZATION_2_CLONE_CODE. */
	public static final String ORGANIZATION_2_CLONE_CODE = "unit_test_organization_2";

	/** The Constant ORGANIZATION_DISABLED_CODE. */
	public static final String ORGANIZATION_DISABLED_CODE = "unit_test_organization_disabled";

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

	/** The Constant USER_CREATED. */
	public static final String USER_CREATED = "unit_test_user_created";

	/** The Constant USER_WITH_DEFAULT_UNIT_CODE. */
	public static final String USER_WITH_DEFAULT_UNIT_CODE = "unit_test_user_with_default_unit";

	/** The Constant USER_WITHOUT_DEFAULT_UNIT_CODE. */
	public static final String USER_WITHOUT_DEFAULT_UNIT_CODE = "unit_test_user_without_default_unit";

	/** The Constant USER_WITH_DEFAULT_UNIT_CODE_NO_GROUP. */
	public static final String USER_WITH_DEFAULT_UNIT_CODE_NO_GROUP = "unit_test_user_with_default_unit_no_group";

	/** The Constant USER_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP. */
	public static final String USER_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP = "unit_test_user_without_default_unit_no_group";

	/** The Constant USER_WITHOUT_PASSWORD. */
	public static final String USER_WITHOUT_PASSWORD = "unit_test_user_without_password";

	/** The Constant USER_ENABLED_IN_DISABLED_ORGANIZATION. */
	public static final String USER_ENABLED_IN_DISABLED_ORGANIZATION = "unit_test_user_enabled_in_disabled_organization";

	/** The Constant USER_DISABLED_IN_DISABLED_ORGANIZATION. */
	public static final String USER_DISABLED_IN_DISABLED_ORGANIZATION = "unit_test_user_disabled_in_disabled_organization";

	/** The Constant USER_DISABLED_IN_ENABLED_ORGANIZATION. */
	public static final String USER_DISABLED_IN_ENABLED_ORGANIZATION = "unit_test_user_disabled_in_enabled_organization";

	/**
	 * Generate data for database tests.
	 */
	@Transactional (readOnly = false)
	public void generateDatabaseDataForDatabaseTests ()
	{
		/** Create data. */
		//organization
		COrganization organization = createOrganization(ORGANIZATION_CODE);
		COrganization organization2 = createOrganization(ORGANIZATION_2_CODE);
		COrganization organization2Clone = createOrganization(ORGANIZATION_2_CLONE_CODE);
		COrganization organizationDisabled = createOrganization(ORGANIZATION_DISABLED_CODE, false);

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

		//authentication params
		CAuthenticationParams authenticationParamWithDefaulUnit = createAuthenticationParams(USER_WITH_DEFAULT_UNIT_CODE);
		CAuthenticationParams authenticationParamWithoutDefaulUnit = createAuthenticationParams(USER_WITHOUT_DEFAULT_UNIT_CODE);
		CAuthenticationParams authenticationParamWithDefaulUnitNoGroup = createAuthenticationParams(USER_WITH_DEFAULT_UNIT_CODE_NO_GROUP);
		CAuthenticationParams authenticationParamWithoutDefaulUnitNoGroup = createAuthenticationParams(USER_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP);
		CAuthenticationParams authenticationParamEnabledInDisabledOrganization = createAuthenticationParams(USER_ENABLED_IN_DISABLED_ORGANIZATION);
		CAuthenticationParams authenticationParamDisabledInDisabledOrganization = createAuthenticationParams(USER_DISABLED_IN_DISABLED_ORGANIZATION);
		CAuthenticationParams authenticationParamDisabledInEnabledOrganization = createAuthenticationParams(USER_DISABLED_IN_ENABLED_ORGANIZATION);

		//users
		CUser userWithDefaultUnit = createUser(USER_WITH_DEFAULT_UNIT_CODE);
		CUser userWithoutDefaultUnit = createUser(USER_WITHOUT_DEFAULT_UNIT_CODE);
		CUser userWithDefaultUnitNoGroup = createUser(USER_WITH_DEFAULT_UNIT_CODE_NO_GROUP);
		CUser userWithoutDefaultUnitNoGroup = createUser(USER_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP);
		CUser userEnabledInDisabledOrganization = createUser(USER_ENABLED_IN_DISABLED_ORGANIZATION);
		CUser userDisabledInDisabledOrganization = createUser(USER_DISABLED_IN_DISABLED_ORGANIZATION, false);
		CUser userDisabledInEnabledOrganization = createUser(USER_DISABLED_IN_ENABLED_ORGANIZATION, false);

		/** Create connections. */
		//unit -> organization
		defaultUnit.setOrganization(organization);
		firstUnit.setOrganization(organization);
		secondUnit.setOrganization(organization);

		//group <-> unit
		Set<CGroup> groupsForDefaultUnit = new HashSet<CGroup>();
		groupsForDefaultUnit.add(firstGroupInUnit);
		groupsForDefaultUnit.add(secondGroupInUnit);

		Set<CGroup> groupsForFirstUnit = new HashSet<CGroup>();
		groupsForFirstUnit.add(secondGroupInUnit);
		groupsForFirstUnit.add(thirdGroupInUnit);

		Set<CGroup> groupsForSecondUnit = new HashSet<CGroup>();
		groupsForSecondUnit.add(firstGroupInUnit);
		groupsForSecondUnit.add(thirdGroupInUnit);

		defaultUnit.setGroups(groupsForDefaultUnit);
		firstUnit.setGroups(groupsForFirstUnit);
		secondUnit.setGroups(groupsForSecondUnit);

		//group <-> role
		Set<CGroup> groupsForFirstRole = new HashSet<CGroup>();
		groupsForFirstRole.add(firstGroupInUnit);
		groupsForFirstRole.add(secondGroupInUnit);
		groupsForFirstRole.add(firstGroupNotInUnit);
		groupsForFirstRole.add(secondGroupNotInUnit);

		Set<CGroup> groupsForSecondRole = new HashSet<CGroup>();
		groupsForSecondRole.add(thirdGroupInUnit);

		firstRole.setGroups(groupsForFirstRole);
		secondRole.setGroups(groupsForSecondRole);

		//group <-> user
		//       |
		//	unit
		userWithDefaultUnit.addGroupUnit(firstGroupInUnit, defaultUnit);
		userWithDefaultUnit.addGroupUnit(firstGroupInUnit, secondUnit);
		userWithDefaultUnit.addGroupUnit(secondGroupInUnit, defaultUnit);
		userWithDefaultUnit.addGroupUnit(secondGroupInUnit, firstUnit);
		userWithDefaultUnit.addGroupUnit(thirdGroupInUnit, firstUnit);
		userWithDefaultUnit.addGroupUnit(thirdGroupInUnit, secondUnit);

		userWithoutDefaultUnit.addGroup(firstGroupNotInUnit);
		userWithoutDefaultUnit.addGroup(secondGroupNotInUnit);
		userWithoutDefaultUnit.addGroupUnit(secondGroupInUnit, defaultUnit);
		userWithoutDefaultUnit.addGroupUnit(secondGroupInUnit, firstUnit);

		userWithDefaultUnitNoGroup.addGroupUnit(thirdGroupInUnit, firstUnit);
		userWithDefaultUnitNoGroup.addGroupUnit(thirdGroupInUnit, secondUnit);

		userWithoutDefaultUnitNoGroup.addGroupUnit(secondGroupInUnit, defaultUnit);
		userWithoutDefaultUnitNoGroup.addGroupUnit(secondGroupInUnit, firstUnit);

		userEnabledInDisabledOrganization.addGroup(firstGroupNotInUnit);

		userDisabledInDisabledOrganization.addGroup(secondGroupNotInUnit);

		userDisabledInEnabledOrganization.addGroup(secondGroupNotInUnit);
		//user -> organization
		userWithDefaultUnit.setOrganization(organization);
		userWithoutDefaultUnit.setOrganization(organization);
		userWithDefaultUnitNoGroup.setOrganization(organization);
		userWithoutDefaultUnitNoGroup.setOrganization(organization);
		userEnabledInDisabledOrganization.setOrganization(organizationDisabled);
		userDisabledInDisabledOrganization.setOrganization(organizationDisabled);
		userDisabledInEnabledOrganization.setOrganization(organization);

		//user -> defaultUnit
		userWithDefaultUnit.setDefaultUnit(defaultUnit);
		userWithoutDefaultUnit.setDefaultUnit(null);
		userWithDefaultUnitNoGroup.setDefaultUnit(defaultUnit);
		userWithoutDefaultUnitNoGroup.setDefaultUnit(null);
		userEnabledInDisabledOrganization.setDefaultUnit(null);
		userDisabledInDisabledOrganization.setDefaultUnit(null);
		userDisabledInEnabledOrganization.setDefaultUnit(null);

		//user -> authenticationParams
		authenticationParamWithDefaulUnit.setUser(userWithDefaultUnit);
		authenticationParamWithoutDefaulUnit.setUser(userWithoutDefaultUnit);
		authenticationParamWithDefaulUnitNoGroup.setUser(userWithDefaultUnitNoGroup);
		authenticationParamWithoutDefaulUnitNoGroup.setUser(userWithoutDefaultUnitNoGroup);
		authenticationParamEnabledInDisabledOrganization.setUser(userEnabledInDisabledOrganization);
		authenticationParamDisabledInDisabledOrganization.setUser(userDisabledInDisabledOrganization);
		authenticationParamDisabledInEnabledOrganization.setUser(userDisabledInEnabledOrganization);

		//save data to DB
		orgDao.save(organization);
		orgDao.save(organization2);
		orgDao.save(organization2Clone);
		orgDao.save(organizationDisabled);
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
		userDao.save(userWithDefaultUnitNoGroup);
		userDao.save(userWithoutDefaultUnitNoGroup);
		userDao.save(userEnabledInDisabledOrganization);
		userDao.save(userDisabledInDisabledOrganization);
		userDao.save(userDisabledInEnabledOrganization);
		authenticationParamsDao.save(authenticationParamWithDefaulUnit);
		authenticationParamsDao.save(authenticationParamWithoutDefaulUnit);
		authenticationParamsDao.save(authenticationParamWithDefaulUnitNoGroup);
		authenticationParamsDao.save(authenticationParamWithoutDefaulUnitNoGroup);
		authenticationParamsDao.save(authenticationParamEnabledInDisabledOrganization);
		authenticationParamsDao.save(authenticationParamDisabledInDisabledOrganization);
		authenticationParamsDao.save(authenticationParamDisabledInEnabledOrganization);
		//flush data to hibernate cache
		orgDao.flush();
		roleDao.flush();
		groupDao.flush();
		unitDao.flush();
		authenticationParamsDao.flush();
		userDao.flush();
		//clear cache
		orgDao.clear();
		roleDao.clear();
		groupDao.clear();
		unitDao.clear();
		authenticationParamsDao.clear();
		userDao.clear();
	}

	/**
	 * Creates the organization.
	 *
	 * @param code the code
	 * @return the c organization
	 */
	public COrganization createOrganization (String code)
	{
		return createOrganization(code, true);
	}

	/**
	 * Creates the organization.
	 *
	 * @param code the code
	 * @param enabled the enabled
	 * @return the c organization
	 */
	public COrganization createOrganization (String code, boolean enabled)
	{
		COrganization organization = new COrganization();
		organization.setCode(code);
		organization.setName(code);
		organization.setEmail(code + "@qbsw.sk");
		organization.setPhone("000000000");
		organization.setFlagEnabled(enabled);
		organization.setAddress(createAddress());

		return organization;
	}

	/**
	 * Creates the address.
	 *
	 * @return the c address
	 */
	public CAddress createAddress ()
	{
		CAddress address = new CAddress();
		address.setCity("Bratislava");
//		address.setHouseNumber("123456789");
		address.setState("Slovakia");
//		address.setStreet("Prievozska");
		address.setZipCode("97101");
		return address;
	}

	/**
	 * Creates the role.
	 *
	 * @param code the code
	 * @return the c role
	 */
	public CRole createRole (String code)
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
	public CGroup createGroup (String code, String category)
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
	public CUnit createUnit (String code)
	{
		CUnit unit = new CUnit();
		unit.setName(code);
		unit.setAddress(createAddress());

		return unit;
	}

	/**
	 * Creates the user.
	 *
	 * @param code the code
	 * @return the c user
	 */
	public CUser createUser (String code)
	{
		return createUser(code, true);
	}

	/**
	 * Creates the user.
	 *
	 * @param code the code
	 * @param enabled the enabled
	 * @return the c user
	 */
	public CUser createUser (String code, boolean enabled)
	{
		CUser user = new CUser();
		user.setLogin(code);
		user.setName(code);
		user.setSurname(code);
		user.setEmail(code + "@qbsw.sk");
		user.setFlagEnabled(enabled);
		user.setAddress(createAddress());

		return user;
	}

	/**
	 * Creates the authentication params.
	 *
	 * @param code the code
	 * @return the c authentication params
	 */
	public CAuthenticationParams createAuthenticationParams (String code)
	{
		CAuthenticationParams userAuthParams = new CAuthenticationParams();
		userAuthParams.setPassword(code);

		return userAuthParams;
	}
}
