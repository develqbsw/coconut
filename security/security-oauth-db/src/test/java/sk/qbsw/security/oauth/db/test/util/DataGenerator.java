package sk.qbsw.security.oauth.db.test.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.core.dao.*;
import sk.qbsw.security.core.model.domain.*;
import sk.qbsw.security.oauth.base.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.base.dao.MasterTokenDao;
import sk.qbsw.security.oauth.db.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.db.model.domain.MasterToken;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Generate data in DB for tests.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.1
 */
@Component (value = "oauthDataGenerator")
public class DataGenerator
{
	private final RoleDao roleDao;

	private final AccountDao accountDao;

	private final OrganizationDao orgDao;

	private final UnitDao unitDao;

	private final GroupDao groupDao;

	private final AuthenticationParamsDao authenticationParamsDao;

	private final AccountUnitGroupDao accountUnitGroupDao;

	private final MasterTokenDao masterTokenDao;

	private final AuthenticationTokenDao authenticationTokenDao;

	/**
	 * The constant ORGANIZATION_CODE.
	 */
	public static final String ORGANIZATION_CODE = "unit_test_organization";

	/**
	 * The constant FIRST_ROLE_CODE.
	 */
	public static final String FIRST_ROLE_CODE = "unit_test_role_1";

	/**
	 * The constant FIRST_GROUP_IN_UNIT_CODE.
	 */
	public static final String FIRST_GROUP_IN_UNIT_CODE = "unit_test_group_in_unit_1";

	/**
	 * The constant FIRST_CATEGORY_CODE.
	 */
	public static final String FIRST_CATEGORY_CODE = "unit_test_category_1";

	/**
	 * The constant DEFAULT_UNIT_CODE.
	 */
	public static final String DEFAULT_UNIT_CODE = "unit_test_unit_default";

	/**
	 * The constant FIRST_USER.
	 */
	public static final String FIRST_USER = "unit_test_user_1";

	/**
	 * The constant SECOND_USER.
	 */
	public static final String SECOND_USER = "unit_test_user_2";

	/**
	 * The constant TEST_IP_ONE.
	 */
	public static final String TEST_IP_ONE = "192.168.0.1";

	/**
	 * The constant MASTER_TOKEN.
	 */
	public static final String MASTER_TOKEN = "unit_test_master_token";

	/**
	 * The constant AUTHENTICATION_TOKEN.
	 */
	public static final String AUTHENTICATION_TOKEN = "unit_test_authentication_token";

	/**
	 * The constant EXPIRE_LIMIT_AUTHENTICATION_TOKEN.
	 */
	public static final String EXPIRE_LIMIT_AUTHENTICATION_TOKEN = "unit_test_expire_limit_authentication_token";

	/**
	 * The constant CHANGE_LIMIT_AUTHENTICATION_TOKEN.
	 */
	public static final String CHANGE_LIMIT_AUTHENTICATION_TOKEN = "unit_test_change_limit_authentication_token";

	/**
	 * The constant DEVICE_ID.
	 */
	public static final String DEVICE_ID = "unit_test_device_id";

	/**
	 * Instantiates a new Data generator.
	 *
	 * @param roleDao the role dao
	 * @param accountDao the account dao
	 * @param orgDao the org dao
	 * @param unitDao the unit dao
	 * @param groupDao the group dao
	 * @param authenticationParamsDao the authentication params dao
	 * @param accountUnitGroupDao the account unit group dao
	 * @param masterTokenDao the master token dao
	 * @param authenticationTokenDao the authentication token dao
	 */
	@Autowired
	public DataGenerator (RoleDao roleDao, AccountDao accountDao, OrganizationDao orgDao, UnitDao unitDao, GroupDao groupDao, AuthenticationParamsDao authenticationParamsDao, AccountUnitGroupDao accountUnitGroupDao, MasterTokenDao masterTokenDao, AuthenticationTokenDao authenticationTokenDao)
	{
		this.roleDao = roleDao;
		this.accountDao = accountDao;
		this.orgDao = orgDao;
		this.unitDao = unitDao;
		this.groupDao = groupDao;
		this.authenticationParamsDao = authenticationParamsDao;
		this.accountUnitGroupDao = accountUnitGroupDao;
		this.masterTokenDao = masterTokenDao;
		this.authenticationTokenDao = authenticationTokenDao;
	}

	/**
	 * Generate data for database tests.
	 */
	@Transactional
	public void generateDatabaseDataForDatabaseTests ()
	{
		/** Create data. */
		// organization
		Organization organization = createOrganization(ORGANIZATION_CODE);

		// roles
		Role firstRole = createRole(FIRST_ROLE_CODE);

		// groups
		Group firstGroupInUnit = createGroup(FIRST_GROUP_IN_UNIT_CODE, FIRST_CATEGORY_CODE, GroupTypes.TECHNICAL);

		// units
		Unit defaultUnit = createUnit(DEFAULT_UNIT_CODE);

		// authentication params
		AuthenticationParams firstUserAuthenticationParams = createAuthenticationParams(FIRST_USER, "1111", null, null);
		AuthenticationParams secondUserAuthenticationParams = createAuthenticationParams(SECOND_USER, "1111", null, null);

		// users
		Account firstUser = createUser(FIRST_USER);
		Account secondUser = createUser(SECOND_USER);

		// tokens
		AuthenticationToken authenticationToken = createAuthenticationToken(AUTHENTICATION_TOKEN, OffsetDateTime.now(), OffsetDateTime.now().minusMinutes(30), DEVICE_ID, TEST_IP_ONE);
		AuthenticationToken authenticationTokenExpireLimit = createAuthenticationToken(EXPIRE_LIMIT_AUTHENTICATION_TOKEN, OffsetDateTime.now().minusHours(2), OffsetDateTime.now().minusHours(1).minusMinutes(30), DEVICE_ID, TEST_IP_ONE);
		AuthenticationToken authenticationTokenChangeLimit = createAuthenticationToken(CHANGE_LIMIT_AUTHENTICATION_TOKEN, OffsetDateTime.now().minusHours(4), OffsetDateTime.now(), DEVICE_ID, TEST_IP_ONE);
		MasterToken masterToken = createMasterToken(MASTER_TOKEN, OffsetDateTime.now(), OffsetDateTime.now().plusHours(1), DEVICE_ID, TEST_IP_ONE);

		/* Create connections. */
		// unit -> organization
		defaultUnit.setOrganization(organization);

		// group <-> unit
		Set<Group> groupsForDefaultUnit = new HashSet<>();
		groupsForDefaultUnit.add(firstGroupInUnit);

		// group <-> role
		Set<Group> groupsForFirstRole = new HashSet<>();
		groupsForFirstRole.add(firstGroupInUnit);

		firstRole.setGroups(groupsForFirstRole);

		// user -> organization
		firstUser.setOrganization(organization);
		secondUser.setOrganization(organization);

		// user -> defaultUnit
		firstUser.setDefaultUnit(defaultUnit);
		secondUser.setDefaultUnit(defaultUnit);

		// user -> authenticationParams
		firstUserAuthenticationParams.setAccount(firstUser);
		secondUserAuthenticationParams.setAccount(secondUser);

		// token -> user
		masterToken.setAccount(firstUser);
		authenticationToken.setAccount(firstUser);
		authenticationTokenExpireLimit.setAccount(firstUser);
		authenticationTokenChangeLimit.setAccount(firstUser);

		// save data to DB
		orgDao.update(organization);
		roleDao.update(firstRole);
		firstGroupInUnit = groupDao.update(firstGroupInUnit);
		defaultUnit = unitDao.update(defaultUnit);
		firstUser = accountDao.update(firstUser);
		secondUser = accountDao.update(secondUser);
		authenticationParamsDao.update(firstUserAuthenticationParams);
		authenticationParamsDao.update(secondUserAuthenticationParams);
		masterTokenDao.update(masterToken);
		authenticationTokenDao.update(authenticationToken);
		authenticationTokenDao.update(authenticationTokenExpireLimit);
		authenticationTokenDao.update(authenticationTokenChangeLimit);

		// group <-> user
		// |
		// unit
		try
		{
			setUserToGroup(firstUser, firstGroupInUnit, defaultUnit);
			setUserToGroup(secondUser, firstGroupInUnit, defaultUnit);
		}
		catch (Exception e)
		{
			throw new CSystemException("The data generator failed", e);
		}

		// flush data to hibernate cache
		orgDao.flush();
		roleDao.flush();
		groupDao.flush();
		unitDao.flush();
		authenticationParamsDao.flush();
		accountDao.flush();
		masterTokenDao.flush();
		authenticationTokenDao.flush();
		// clear cache
		orgDao.clear();
		roleDao.clear();
		groupDao.clear();
		unitDao.clear();
		authenticationParamsDao.clear();
		accountDao.clear();
		masterTokenDao.clear();
		authenticationTokenDao.clear();
	}

	private void setUserToGroup (Account user, Group group, Unit unit)
	{
		AccountUnitGroup userUnitGroupRecord = new AccountUnitGroup();
		userUnitGroupRecord.setAccount(user);
		userUnitGroupRecord.setGroup(group);
		userUnitGroupRecord.setUnit(unit);
		accountUnitGroupDao.update(userUnitGroupRecord);
	}

	private Organization createOrganization (String code)
	{
		return createOrganization(code, ActivityStates.ACTIVE);
	}

	private Organization createOrganization (String code, ActivityStates state)
	{
		Organization organization = new Organization();
		organization.setCode(code);
		organization.setName(code);
		organization.setEmail(code + "@qbsw.sk");
		organization.setState(state);

		return organization;
	}

	private Role createRole (String code)
	{
		Role role = new Role();
		role.setCode(code);

		return role;
	}

	private Group createGroup (String code, String category, GroupTypes type)
	{
		Group group = new Group();
		group.setCode(code);
		group.setCategory(category);
		group.setType(type);

		return group;
	}

	private Unit createUnit (String code)
	{
		Unit unit = new Unit();
		unit.setName(code);

		return unit;
	}

	private Account createUser (String code)
	{
		return createUser(code, ActivityStates.ACTIVE);
	}

	private Account createUser (String code, ActivityStates state)
	{
		Account user = new Account();
		user.setLogin(code);
		user.setEmail(code + "@qbsw.sk");
		user.setState(state);

		return user;
	}

	private AuthenticationParams createAuthenticationParams (String code, String pin, OffsetDateTime validFrom, OffsetDateTime validTo)
	{
		AuthenticationParams userAuthParams = new AuthenticationParams();
		userAuthParams.setPassword(code);
		userAuthParams.setPin(pin);
		userAuthParams.setValidFrom(validFrom);
		userAuthParams.setValidTo(validTo);

		return userAuthParams;
	}

	private AuthenticationToken createAuthenticationToken (String token, OffsetDateTime createDate, OffsetDateTime lastAccess, String deviceId, String ip)
	{
		AuthenticationToken tokenObject = new AuthenticationToken();
		tokenObject.setCreateDate(createDate);
		tokenObject.setDeviceId(deviceId);
		tokenObject.setIp(ip);
		tokenObject.setLastAccessDate(lastAccess);
		tokenObject.setToken(token);

		return tokenObject;
	}

	private MasterToken createMasterToken (String token, OffsetDateTime createDate, OffsetDateTime lastAccess, String deviceId, String ip)
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
