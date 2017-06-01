package sk.qbsw.security.oauth.test.util;

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
import sk.qbsw.security.core.dao.AuthenticationParamsDao;
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.dao.LicenseDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.dao.RoleDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.dao.UserUnitGroupDao;
import sk.qbsw.security.core.model.domain.Address;
import sk.qbsw.security.core.model.domain.AuthenticationParams;
import sk.qbsw.security.core.model.domain.BlockedLogin;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.License;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.domain.Role;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.domain.User;
import sk.qbsw.security.core.model.domain.UserUnitGroup;
import sk.qbsw.security.core.model.jmx.CLicensingRules;
import sk.qbsw.security.management.service.UserManagementService;
import sk.qbsw.security.oauth.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.dao.MasterTokenDao;
import sk.qbsw.security.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.model.domain.MasterToken;
import sk.qbsw.security.oauth.test.util.domain.LicenseFree;

/**
 * Generate data in DB for tests.
 *
 * @autor Tomas Lauro
 * 
 * @version 1.13.1
 * @since 1.13.1
 * 
 */
@Component (value = "oauthDataGenerator")
public class DataGenerator
{
	/** The role dao. */
	@Autowired
	private RoleDao roleDao;

	/** The user dao. */
	@Autowired
	private UserDao userDao;

	/** The org dao. */
	@Autowired
	private OrganizationDao orgDao;

	/** The unit dao. */
	@Autowired
	private UnitDao unitDao;

	/** The group dao. */
	@Autowired
	private GroupDao groupDao;

	/** The authentication params dao. */
	@Autowired
	private AuthenticationParamsDao authenticationParamsDao;

	/** The license dao. */
	@Autowired
	private LicenseDao licenseDao;

	/** The user service. */
	@Autowired
	private UserManagementService userService;

	/** The cross user unit group dao. */
	@Autowired
	private UserUnitGroupDao crossUserUnitGroupDao;

	/** The master token dao. */
	@Autowired
	private MasterTokenDao masterTokenDao;

	/** The authentication token dao. */
	@Autowired
	private AuthenticationTokenDao authenticationTokenDao;

	/** The Constant ORGANIZATION_CODE. */
	public static final String ORGANIZATION_CODE = "unit_test_organization";

	/** The Constant FIRST_ROLE_CODE. */
	public static final String FIRST_ROLE_CODE = "unit_test_role_1";

	/** The Constant FIRST_GROUP_IN_UNIT_CODE. */
	public static final String FIRST_GROUP_IN_UNIT_CODE = "unit_test_group_in_unit_1";

	/** The Constant FIRST_CATEGORY_CODE. */
	public static final String FIRST_CATEGORY_CODE = "unit_test_category_1";

	/** The Constant DEFAULT_UNIT_CODE. */
	public static final String DEFAULT_UNIT_CODE = "unit_test_unit_default";

	/** The Constant USER. */
	public static final String FIRST_USER = "unit_test_user_1";

	/** The Constant SECOND_USER. */
	public static final String SECOND_USER = "unit_test_user_2";

	/** The Constant TEST_IP_ONE. */
	public static final String TEST_IP_ONE = "192.168.0.1";

	/** The Constant LICENSE_KEY_ONE. */
	public static final String LICENSE_KEY_ONE = "unit_test_license_key_one";

	/** The Constant MASTER_TOKEN. */
	public static final String MASTER_TOKEN = "unit_test_master_token";

	/** The Constant AUTHENTICATION_TOKEN. */
	public static final String AUTHENTICATION_TOKEN = "unit_test_authentication_token";

	/** The Constant DEVICE_ID. */
	public static final String DEVICE_ID = "unit_test_device_id";

	/**
	 * Generate data for database tests.
	 */
	@Transactional (readOnly = false)
	public void generateDatabaseDataForDatabaseTests ()
	{
		/** Create data. */
		//organization
		Organization organization = createOrganization(ORGANIZATION_CODE);

		//roles
		Role firstRole = createRole(FIRST_ROLE_CODE);

		//groups
		Group firstGroupInUnit = createGroup(FIRST_GROUP_IN_UNIT_CODE, FIRST_CATEGORY_CODE, true);

		//units
		Unit defaultUnit = createUnit(DEFAULT_UNIT_CODE);

		//authentication params
		AuthenticationParams firstUserAuthenticationParams = createAuthenticationParams(FIRST_USER, "1111", null, null);
		AuthenticationParams secondUserAuthenticationParams = createAuthenticationParams(SECOND_USER, "1111", null, null);

		//users
		User firstUser = createUser(FIRST_USER);
		User secondUser = createUser(SECOND_USER);

		//license
		License<CLicensingRules> licenseOne = createLicense(LICENSE_KEY_ONE, true, BigDecimal.ONE, "tax id one", Calendar.getInstance(), Calendar.getInstance());

		//tokens
		AuthenticationToken authenticationToken = createAuthenticationToken(AUTHENTICATION_TOKEN, OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), DEVICE_ID, TEST_IP_ONE);
		MasterToken masterToken = createMasterToken(MASTER_TOKEN, OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), DEVICE_ID, TEST_IP_ONE);

		/** Create connections. */
		//unit -> organization
		defaultUnit.setOrganization(organization);

		//group <-> unit
		Set<Group> groupsForDefaultUnit = new HashSet<Group>();
		groupsForDefaultUnit.add(firstGroupInUnit);

		//group <-> role
		Set<Group> groupsForFirstRole = new HashSet<Group>();
		groupsForFirstRole.add(firstGroupInUnit);

		firstRole.setGroups(groupsForFirstRole);

		//user -> organization
		firstUser.setOrganization(organization);
		secondUser.setOrganization(organization);

		//user -> defaultUnit
		firstUser.setDefaultUnit(defaultUnit);
		secondUser.setDefaultUnit(defaultUnit);

		//user -> authenticationParams
		firstUserAuthenticationParams.setUser(firstUser);
		secondUserAuthenticationParams.setUser(secondUser);

		//license -> organization
		licenseOne.setOrganization(organization);

		//token -> user
		masterToken.setUser(firstUser);
		authenticationToken.setUser(firstUser);

		//save data to DB
		orgDao.update(organization);
		roleDao.update(firstRole);
		firstGroupInUnit = groupDao.update(firstGroupInUnit);
		defaultUnit = unitDao.update(defaultUnit);
		firstUser = userDao.update(firstUser);
		secondUser = userDao.update(secondUser);
		authenticationParamsDao.update(firstUserAuthenticationParams);
		authenticationParamsDao.update(secondUserAuthenticationParams);
		licenseDao.update(licenseOne);
		masterTokenDao.update(masterToken);
		authenticationTokenDao.update(authenticationToken);

		//group <-> user
		//       |
		//	unit
		try
		{
			setUserToGroup(firstUser, firstGroupInUnit, defaultUnit);
			setUserToGroup(secondUser, firstGroupInUnit, defaultUnit);
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
		masterTokenDao.flush();
		authenticationTokenDao.flush();
		//clear cache
		orgDao.clear();
		roleDao.clear();
		groupDao.clear();
		unitDao.clear();
		authenticationParamsDao.clear();
		userDao.clear();
		licenseDao.clear();
		masterTokenDao.clear();
		authenticationTokenDao.clear();
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
	private void setUserToGroup (User user, Group group, Unit unit) throws CSecurityException, CBusinessException
	{
		UserUnitGroup userUnitGroupRecord = new UserUnitGroup();
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
	public Organization createOrganization (String code)
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
	public Organization createOrganization (String code, boolean enabled)
	{
		Organization organization = new Organization();
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
	public Address createAddress ()
	{
		Address address = new Address();
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
	public Role createRole (String code)
	{
		Role role = new Role();
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
	public Group createGroup (String code, String category, Boolean flagSystem)
	{
		Group group = new Group();
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
	public Unit createUnit (String code)
	{
		Unit unit = new Unit();
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
	public User createUser (String code)
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
	public User createUser (String code, boolean enabled)
	{
		User user = new User();
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
	public AuthenticationParams createAuthenticationParams (String code, String pin)
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
	public AuthenticationParams createAuthenticationParams (String code, String pin, OffsetDateTime validFrom, OffsetDateTime validTo)
	{
		AuthenticationParams userAuthParams = new AuthenticationParams();
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
	public AuthenticationParams createAuthenticationParams (String code)
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
	public AuthenticationParams createAuthenticationParams (String code, OffsetDateTime validFrom, OffsetDateTime validTo)
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
	public License<CLicensingRules> createLicense (String key, Boolean flagPayed, BigDecimal price, String taxId, Calendar validFrom, Calendar validTo)
	{
		License<CLicensingRules> license = new LicenseFree();
		license.setKey(key);
		license.setFlagPayed(flagPayed);
		license.setPrice(price);
		license.setTaxId(taxId);
		license.setValidFrom(validFrom);
		license.setValidTo(validTo);

		return license;
	}

	/**
	 * Creates the authentication token.
	 *
	 * @param token the token
	 * @param createDate the create date
	 * @param lastAccess the last access
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the c authentication token
	 */
	public AuthenticationToken createAuthenticationToken (String token, OffsetDateTime createDate, OffsetDateTime lastAccess, String deviceId, String ip)
	{
		AuthenticationToken tokenObject = new AuthenticationToken();
		tokenObject.setCreateDate(createDate);
		tokenObject.setDeviceId(deviceId);
		tokenObject.setIp(ip);
		tokenObject.setLastAccessDate(lastAccess);
		tokenObject.setToken(token);

		return tokenObject;
	}

	/**
	 * Creates the master token.
	 *
	 * @param token the token
	 * @param createDate the create date
	 * @param lastAccess the last access
	 * @param deviceId the device id
	 * @param ip the ip
	 * @return the c master token
	 */
	public MasterToken createMasterToken (String token, OffsetDateTime createDate, OffsetDateTime lastAccess, String deviceId, String ip)
	{
		MasterToken tokenObject = new MasterToken();
		tokenObject.setCreateDate(createDate);
		tokenObject.setDeviceId(deviceId);
		tokenObject.setIp(ip);
		tokenObject.setLastAccessDate(lastAccess);
		tokenObject.setToken(token);

		return tokenObject;
	}
}
