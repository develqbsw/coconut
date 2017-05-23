package sk.qbsw.core.security.ldap.test.util;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.security.dao.IAuthenticationParamsDao;
import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.ILicenseDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.dao.IRoleDao;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.dao.IXUserUnitGroupDao;
import sk.qbsw.core.security.ldap.test.util.domain.CLicenseFree;
import sk.qbsw.core.security.model.domain.CAddress;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;
import sk.qbsw.core.security.model.domain.CBlockedLogin;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CLicense;
import sk.qbsw.core.security.model.domain.COrganization;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.domain.CXUserUnitGroup;
import sk.qbsw.core.security.model.jmx.CLicensingRules;
import sk.qbsw.core.security.service.IUserService;

/**
 * Generate data in DB for tests.
 *
 * @autor Tomas Lauro
 * @version 1.13.0
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

	/** The license dao. */
	@Autowired
	private ILicenseDao licenseDao;

	/** The user service. */
	@Autowired
	private IUserService userService;

	/** The cross user unit group dao. */
	@Autowired
	private IXUserUnitGroupDao crossUserUnitGroupDao;

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

	/** The Constant TEST_IP_ONE. */
	public static final String TEST_IP_ONE = "192.168.0.1";

	/** The Constant TEST_IP_TWO. */
	public static final String TEST_IP_TWO = "192.168.0.2";

	/** The Constant LICENSE_KEY_ONE. */
	public static final String LICENSE_KEY_ONE = "unit_test_license_key_one";

	/** The Constant LICENSE_KEY_TWO. */
	public static final String LICENSE_KEY_TWO = "unit_test_license_key_two";

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
		CGroup firstGroupInUnit = createGroup(FIRST_GROUP_IN_UNIT_CODE, FIRST_CATEGORY_CODE, true);
		CGroup secondGroupInUnit = createGroup(SECOND_GROUP_IN_UNIT_CODE, FIRST_CATEGORY_CODE, true);
		CGroup thirdGroupInUnit = createGroup(THIRD_GROUP_IN_UNIT_CODE, SECOND_CATEGORY_CODE, false);

		CGroup firstGroupNotInUnit = createGroup(FIRST_GROUP_NOT_IN_UNIT_CODE, null, false);
		CGroup secondGroupNotInUnit = createGroup(SECOND_GROUP_NOT_IN_UNIT_CODE, SECOND_CATEGORY_CODE, false);

		//units
		CUnit defaultUnit = createUnit(DEFAULT_UNIT_CODE);
		CUnit firstUnit = createUnit(FIRST_UNIT_CODE);
		CUnit secondUnit = createUnit(SECOND_UNIT_CODE);

		//authentication params
		CAuthenticationParams authenticationParamWithDefaulUnit = createAuthenticationParams(USER_WITH_DEFAULT_UNIT_CODE, "1111", null, null);
		CAuthenticationParams authenticationParamWithoutDefaulUnit = createAuthenticationParams(USER_WITHOUT_DEFAULT_UNIT_CODE, OffsetDateTime.now().minusHours(2), null);
		CAuthenticationParams authenticationParamWithDefaulUnitNoGroup = createAuthenticationParams(USER_WITH_DEFAULT_UNIT_CODE_NO_GROUP, null, OffsetDateTime.now().plusHours(2));
		CAuthenticationParams authenticationParamWithoutDefaulUnitNoGroup = createAuthenticationParams(USER_WITHOUT_DEFAULT_UNIT_CODE_NO_GROUP, OffsetDateTime.now().minusHours(2), OffsetDateTime.now().plusHours(2));
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

		//license
		CLicense<CLicensingRules> licenseOne = createLicense(LICENSE_KEY_ONE, true, BigDecimal.ONE, "tax id one", Calendar.getInstance(), Calendar.getInstance());
		CLicense<CLicensingRules> licenseTwo = createLicense(LICENSE_KEY_TWO, false, BigDecimal.ONE, "tax id one", Calendar.getInstance(), Calendar.getInstance());

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

		//license -> organization
		licenseOne.setOrganization(organization);
		licenseTwo.setOrganization(organization2);

		//save data to DB
		orgDao.update(organization);
		orgDao.update(organization2);
		orgDao.update(organization2Clone);
		orgDao.update(organizationDisabled);
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
		userWithDefaultUnit = userDao.update(userWithDefaultUnit);
		userWithoutDefaultUnit = userDao.update(userWithoutDefaultUnit);
		userWithDefaultUnitNoGroup = userDao.update(userWithDefaultUnitNoGroup);
		userWithoutDefaultUnitNoGroup = userDao.update(userWithoutDefaultUnitNoGroup);
		userEnabledInDisabledOrganization = userDao.update(userEnabledInDisabledOrganization);
		userDisabledInDisabledOrganization = userDao.update(userDisabledInDisabledOrganization);
		userDisabledInEnabledOrganization = userDao.update(userDisabledInEnabledOrganization);
		authenticationParamsDao.update(authenticationParamWithDefaulUnit);
		authenticationParamsDao.update(authenticationParamWithoutDefaulUnit);
		authenticationParamsDao.update(authenticationParamWithDefaulUnitNoGroup);
		authenticationParamsDao.update(authenticationParamWithoutDefaulUnitNoGroup);
		authenticationParamsDao.update(authenticationParamEnabledInDisabledOrganization);
		authenticationParamsDao.update(authenticationParamDisabledInDisabledOrganization);
		authenticationParamsDao.update(authenticationParamDisabledInEnabledOrganization);
		licenseDao.update(licenseOne);
		licenseDao.update(licenseTwo);

		//group <-> user
		//       |
		//	unit
		try
		{
			setUserToGroup(userWithDefaultUnit, firstGroupInUnit, defaultUnit);
			setUserToGroup(userWithDefaultUnit, firstGroupInUnit, secondUnit);
			setUserToGroup(userWithDefaultUnit, secondGroupInUnit, defaultUnit);
			setUserToGroup(userWithDefaultUnit, secondGroupInUnit, firstUnit);
			setUserToGroup(userWithDefaultUnit, thirdGroupInUnit, firstUnit);
			setUserToGroup(userWithDefaultUnit, thirdGroupInUnit, secondUnit);

			setUserToGroup(userWithoutDefaultUnit, firstGroupNotInUnit, null);
			setUserToGroup(userWithoutDefaultUnit, secondGroupNotInUnit, null);
			setUserToGroup(userWithoutDefaultUnit, secondGroupInUnit, defaultUnit);
			setUserToGroup(userWithoutDefaultUnit, secondGroupInUnit, firstUnit);

			setUserToGroup(userWithDefaultUnitNoGroup, thirdGroupInUnit, firstUnit);
			setUserToGroup(userWithDefaultUnitNoGroup, thirdGroupInUnit, secondUnit);

			setUserToGroup(userWithoutDefaultUnitNoGroup, secondGroupInUnit, defaultUnit);
			setUserToGroup(userWithoutDefaultUnitNoGroup, secondGroupInUnit, firstUnit);

			setUserToGroup(userEnabledInDisabledOrganization, firstGroupNotInUnit, null);

			setUserToGroup(userDisabledInDisabledOrganization, secondGroupNotInUnit, null);

			setUserToGroup(userDisabledInEnabledOrganization, secondGroupNotInUnit, null);

		}
		catch (CBusinessException e)
		{
			throw new CSystemException("The data generator failed", e);
		}

		//flush data to hibernate cache
		orgDao.flush();
		roleDao.flush();
		groupDao.flush();
		unitDao.flush();
		authenticationParamsDao.flush();
		userDao.flush();
		licenseDao.flush();
		//clear cache
		orgDao.clear();
		roleDao.clear();
		groupDao.clear();
		unitDao.clear();
		authenticationParamsDao.clear();
		userDao.clear();
		licenseDao.clear();
	}

	/**
	 * Sets the user to group.
	 *
	 * @param user the user
	 * @param group the group
	 * @param unit the unit
	 * @throws CSecurityException the c security exception
	 * @throws CBusinessException the c business exception
	 */
	private void setUserToGroup (CUser user, CGroup group, CUnit unit) throws CSecurityException, CBusinessException
	{
		CXUserUnitGroup userUnitGroupRecord = new CXUserUnitGroup();
		userUnitGroupRecord.setUser(user);
		userUnitGroupRecord.setGroup(group);
		userUnitGroupRecord.setUnit(unit);
		crossUserUnitGroupDao.update(userUnitGroupRecord);
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
	 * @param flagSystem the flag system
	 * @return the c group
	 */
	public CGroup createGroup (String code, String category, Boolean flagSystem)
	{
		CGroup group = new CGroup();
		group.setCode(code);
		group.setCategory(category);
		group.setFlagSystem(flagSystem);

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
	 * @param pin the pin
	 * @return the c authentication params
	 */
	public CAuthenticationParams createAuthenticationParams (String code, String pin)
	{
		return createAuthenticationParams(code, pin, null, null);
	}

	/**
	 * Creates the authentication params.
	 *
	 * @param code the code
	 * @param pin the pin
	 * @param validFrom the valid from
	 * @param validTo the valid to
	 * @return the c authentication params
	 */
	public CAuthenticationParams createAuthenticationParams (String code, String pin, OffsetDateTime validFrom, OffsetDateTime validTo)
	{
		CAuthenticationParams userAuthParams = new CAuthenticationParams();
		userAuthParams.setPassword(code);
		userAuthParams.setPin(pin);
		userAuthParams.setValidFrom(validFrom);
		userAuthParams.setValidTo(validTo);

		return userAuthParams;
	}

	/**
	 * Creates the authentication params.
	 *
	 * @param code the code
	 * @return the c authentication params
	 */
	public CAuthenticationParams createAuthenticationParams (String code)
	{
		return createAuthenticationParams(code, null, null, null);
	}

	/**
	 * Creates the authentication params.
	 *
	 * @param code the code
	 * @param validFrom the valid from
	 * @param validTo the valid to
	 * @return the c authentication params
	 */
	public CAuthenticationParams createAuthenticationParams (String code, OffsetDateTime validFrom, OffsetDateTime validTo)
	{
		return createAuthenticationParams(code, null, validFrom, validTo);
	}

	/**
	 * Creates the blocked login.
	 *
	 * @param login the login
	 * @param ip the ip
	 * @param invalidLoginCount the invalid login count
	 * @param blockedFrom the blocked from
	 * @param blockedTo the blocked to
	 * @return the c blocked login
	 */
	public CBlockedLogin createBlockedLogin (String login, String ip, int invalidLoginCount, OffsetDateTime blockedFrom, OffsetDateTime blockedTo)
	{
		CBlockedLogin blockedLogin = new CBlockedLogin();
		blockedLogin.setLogin(login);
		blockedLogin.setIp(ip);
		blockedLogin.setInvalidLoginCount(invalidLoginCount);
		blockedLogin.setBlockedFrom(blockedFrom);
		blockedLogin.setBlockedTo(blockedTo);

		return blockedLogin;
	}

	/**
	 * Creates the license.
	 *
	 * @param key the key
	 * @param flagPayed the flag payed
	 * @param price the price
	 * @param taxId the tax id
	 * @param validFrom the valid from
	 * @param validTo the valid to
	 * @return the c license
	 */
	public CLicense<CLicensingRules> createLicense (String key, Boolean flagPayed, BigDecimal price, String taxId, Calendar validFrom, Calendar validTo)
	{
		CLicense<CLicensingRules> license = new CLicenseFree();
		license.setKey(key);
		license.setFlagPayed(flagPayed);
		license.setPrice(price);
		license.setTaxId(taxId);
		license.setValidFrom(validFrom);
		license.setValidTo(validTo);

		return license;
	}
}
