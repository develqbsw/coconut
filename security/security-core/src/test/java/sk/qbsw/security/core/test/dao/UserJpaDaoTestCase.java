package sk.qbsw.security.core.test.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.dao.GroupDao;
import sk.qbsw.security.core.dao.OrganizationDao;
import sk.qbsw.security.core.dao.RoleDao;
import sk.qbsw.security.core.dao.UnitDao;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.CGroup;
import sk.qbsw.security.core.model.domain.CUnit;
import sk.qbsw.security.core.model.domain.CUser;
import sk.qbsw.security.core.model.filter.CUserAssociationsFilter;
import sk.qbsw.security.core.model.filter.CUserDetailFilter;
import sk.qbsw.security.core.model.order.COrderModel;
import sk.qbsw.security.core.model.order.COrderSpecification;
import sk.qbsw.security.core.model.order.EOrderSpecifier;
import sk.qbsw.security.core.model.order.EUserOrderByAttributeSpecifier;
import sk.qbsw.security.core.model.order.IOrderByAttributeSpecifier;
import sk.qbsw.security.core.test.util.DataGenerator;

/**
 * Checks user dao.
 *
 * @version 1.13.0
 * @since 1.13.0
 * @autor Tomas Lauro
 */
public class UserJpaDaoTestCase extends BaseDatabaseTestCase
{
	/** The user dao. */
	@Autowired
	private UserDao userDao;

	/** The unit dao. */
	@Autowired
	private UnitDao unitDao;

	/** The group dao. */
	@Autowired
	private GroupDao groupDao;

	/** The organization dao. */
	@Autowired
	private OrganizationDao organizationDao;

	/** The role dao. */
	@Autowired
	private RoleDao roleDao;

	/**
	 * Test initialization.
	 */
	@Before
	public void testInitialization ()
	{
		super.testInitialization();
		assertNotNull("Could not find user dao", userDao);
	}

	/**
	 * Test find by id positive.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByIdPositive () throws NoResultException, CSecurityException
	{
		initTest();

		CUser testUser = userDao.findOneByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		CUser user = userDao.findById(testUser.getId());

		//asserts
		assertNotNull("No user found", user);
		Assert.assertEquals("Returns invalid user", DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, user.getLogin());
	}

	/**
	 * Test find by id negative.
	 */
	@Test (expected = NoResultException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindByIdNegative ()
	{
		initTest();

		userDao.findById(89218423498l);
	}

	/**
	 * Test find one by login positive with default unit.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByLoginPositiveWithDefaultUnit () throws NoResultException, CSecurityException
	{
		initTest();

		CUser user = userDao.findOneByLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);

		//asserts
		assertNotNull("No user found", user);
		Assert.assertEquals("Returns invalid user", DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, user.getLogin());
	}

	/**
	 * Test find one by login positive without default unit.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByLoginPositiveWithoutDefaultUnit () throws NoResultException, CSecurityException
	{
		initTest();

		CUser user = userDao.findOneByLogin(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE);

		//asserts
		assertNotNull("No user found", user);
		Assert.assertEquals("Returns invalid user", DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, user.getLogin());
	}

	/**
	 * Test find one by login negative no result.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = NoResultException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByLoginNegativeNoResult () throws NoResultException, CSecurityException
	{
		initTest();

		userDao.findOneByLogin("No result");
	}

	/**
	 * Test find one by login negative no login.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByLoginNegativeNoLogin () throws NoResultException, CSecurityException
	{
		initTest();

		userDao.findOneByLogin(null);
	}

	/**
	 * Test find one by login and unit positive with default unit.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByLoginAndUnitPositiveWithDefaultUnit () throws NoResultException, CSecurityException
	{
		initTest();

		CUnit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);
		CUser user = userDao.findOneByLoginAndUnit(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, unit);

		//asserts
		assertNotNull("No user found", user);
		Assert.assertEquals("Returns invalid user", DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, user.getLogin());
	}

	/**
	 * Test find one by login and unit positive without default unit.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByLoginAndUnitPositiveWithoutDefaultUnit () throws NoResultException, CSecurityException
	{
		initTest();

		CUnit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);
		CUser user = userDao.findOneByLoginAndUnit(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, unit);

		//asserts
		assertNotNull("No user found", user);
		Assert.assertEquals("Returns invalid user", DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, user.getLogin());
	}

	/**
	 * Test find one by login and unit negative user not in unit.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = NoResultException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByLoginAndUnitNegativeUserNotInUnit () throws NoResultException, CSecurityException
	{
		initTest();

		CUnit unit = unitDao.findOneByName(DataGenerator.SECOND_UNIT_CODE);
		userDao.findOneByLoginAndUnit(DataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, unit);
	}

	/**
	 * Test find one by login and unit negative no result.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = NoResultException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByLoginAndUnitNegativeNoResult () throws NoResultException, CSecurityException
	{
		initTest();

		CUnit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);
		userDao.findOneByLoginAndUnit("No result", unit);
	}

	/**
	 * Test find one by login and unit negative no login.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByLoginAndUnitNegativeNoLogin () throws NoResultException, CSecurityException
	{
		initTest();

		CUnit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);
		userDao.findOneByLoginAndUnit(null, unit);
	}

	/**
	 * Test find by pin code positive.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByPinCodePositive () throws NoResultException, CSecurityException
	{
		initTest();

		List<CUser> users = userDao.findByPinCode("1111");

		//asserts
		assertNotNull("No users found", users);
		Assert.assertEquals("Returns invalid users", 1, users.size());
		Assert.assertEquals("Returns invalid user", DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, users.get(0).getLogin());
	}

	/**
	 * Test find by pin code positive no result.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByPinCodePositiveNoResult () throws NoResultException, CSecurityException
	{
		initTest();

		List<CUser> users = userDao.findByPinCode("1234");

		//asserts
		assertNotNull("No users found", users);
		Assert.assertEquals("Returns invalid users", 0, users.size());
	}

	/**
	 * Test find by pin code negative no pin.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindByPinCodeNegativeNoPin () throws NoResultException, CSecurityException
	{
		initTest();

		userDao.findByPinCode(null);
	}

	/**
	 * Test count all positive.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testCountAllPositive ()
	{
		initTest();

		long userNumber = userDao.countAll();

		//asserts
		Assert.assertEquals("Returns invalid users number", 7, userNumber);
	}

	/**
	 * Test find all positive.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindAllPositive ()
	{
		initTest();

		List<CUser> users = userDao.findAll();

		//asserts
		assertNotNull("No users found", users);
		Assert.assertEquals("Returns invalid users", 7, users.size());
	}

	/**
	 * Test find all positive no data.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindAllPositiveNoData ()
	{
		List<CUser> users = userDao.findAll();

		//asserts
		assertNotNull("No users found", users);
		Assert.assertEquals("Returns invalid users", 0, users.size());
	}

	/**
	 * Test find by unit and group positive both with order model.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUnitAndGroupPositiveBothWithOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		//order model
		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<EUserOrderByAttributeSpecifier>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		//unit and group
		CUnit unit = unitDao.findOneByName(DataGenerator.FIRST_UNIT_CODE);
		CGroup group = groupDao.findOneByCode(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);


		List<CUser> users = userDao.findByUnitAndGroup(unit, group, orderModel);

		//asserts
		assertNotNull("No users found", users);
		Assert.assertEquals("Returns invalid users", 3, users.size());
	}

	/**
	 * Test find by unit and group negative with unit with order model.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUnitAndGroupNegativeWithUnitWithOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		//order model
		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<EUserOrderByAttributeSpecifier>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		//unit and group
		CGroup group = groupDao.findOneByCode(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);


		userDao.findByUnitAndGroup(null, group, orderModel);
	}

	/**
	 * Test find by unit and group positive with group with order model.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUnitAndGroupPositiveWithGroupWithOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		//order model
		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<EUserOrderByAttributeSpecifier>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.EMAIL, EOrderSpecifier.ASC));

		//unit and group
		CUnit unit = unitDao.findOneByName(DataGenerator.FIRST_UNIT_CODE);


		List<CUser> users = userDao.findByUnitAndGroup(unit, null, orderModel);

		//asserts
		assertNotNull("No users found", users);
		Assert.assertEquals("Returns invalid users", 4, users.size());
	}

	/**
	 * Test find by unit and group negative none without order model.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUnitAndGroupNegativeNoneWithoutOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		userDao.findByUnitAndGroup(null, null, null);
	}

	/**
	 * Test find by unit and group negative none no result.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUnitAndGroupNegativeNoneNoResult () throws NoResultException, CSecurityException
	{
		userDao.findByUnitAndGroup(null, null, null);
	}

	/**
	 * Test find by user detail filter positive with order model.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserDetailFilterPositiveWithOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		//filter
		CUserDetailFilter filter = new CUserDetailFilter();
		filter.setName(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		filter.setSurname(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		filter.setLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		filter.setEmail(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk");
		filter.setGroupCodePrefix(DataGenerator.FIRST_GROUP_IN_UNIT_CODE.substring(0, 5));
		filter.setEnabled(true);
		filter.setOrganization(organizationDao.findByName(DataGenerator.ORGANIZATION_CODE).get(0));

		//order model
		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<EUserOrderByAttributeSpecifier>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.EMAIL, EOrderSpecifier.ASC));

		List<CUser> users = userDao.findByUserDetailFilter(filter, orderModel);

		//asserts
		assertNotNull("No users found", users);
		Assert.assertEquals("Returns invalid users", 1, users.size());
		Assert.assertEquals("Returns invalid users", DataGenerator.USER_WITH_DEFAULT_UNIT_CODE, users.get(0).getLogin());
	}

	/**
	 * Test find by user detail filter positive no result one with order model.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserDetailFilterPositiveNoResultOneWithOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		//filter
		CUserDetailFilter filter = new CUserDetailFilter();
		filter.setName(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE_NO_GROUP); //wrong name
		filter.setSurname(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		filter.setLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		filter.setEmail(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk");
		filter.setGroupCodePrefix(DataGenerator.FIRST_GROUP_IN_UNIT_CODE.substring(0, 5));
		filter.setEnabled(true);
		filter.setOrganization(organizationDao.findByName(DataGenerator.ORGANIZATION_CODE).get(0));

		//order model
		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<EUserOrderByAttributeSpecifier>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.EMAIL, EOrderSpecifier.ASC));

		List<CUser> users = userDao.findByUserDetailFilter(filter, orderModel);

		//asserts
		assertNotNull("No users found", users);
		Assert.assertEquals("Returns invalid users", 0, users.size());
	}

	/**
	 * Test find by user detail filter positive no data with order model.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserDetailFilterPositiveNoDataWithOrderModel () throws NoResultException, CSecurityException
	{
		//without initialization

		//filter
		CUserDetailFilter filter = new CUserDetailFilter();
		filter.setName(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		filter.setSurname(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		filter.setLogin(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		filter.setEmail(DataGenerator.USER_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk");
		filter.setGroupCodePrefix(DataGenerator.FIRST_GROUP_IN_UNIT_CODE.substring(0, 5));
		filter.setEnabled(true);

		//order model
		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<EUserOrderByAttributeSpecifier>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.EMAIL, EOrderSpecifier.ASC));

		List<CUser> users = userDao.findByUserDetailFilter(filter, orderModel);

		//asserts
		assertNotNull("No users found", users);
		Assert.assertEquals("Returns invalid users", 0, users.size());
	}

	/**
	 * Test find by user detail filter positive none without order model.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserDetailFilterPositiveNoneWithoutOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		//order model
		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<EUserOrderByAttributeSpecifier>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.EMAIL, EOrderSpecifier.ASC));

		List<CUser> users = userDao.findByUserDetailFilter(null, null);

		//asserts
		assertNotNull("No users found", users);
		Assert.assertEquals("Returns invalid users", 7, users.size());
	}


	/**
	 * Test find by user associations filter positive with order model.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAssociationsFilterPositiveWithOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		//filter
		CUserAssociationsFilter filter = new CUserAssociationsFilter();
		filter.setGroup(groupDao.findOneByCode(DataGenerator.SECOND_GROUP_IN_UNIT_CODE));
		filter.setRole(roleDao.findOneByCode(DataGenerator.FIRST_ROLE_CODE));
		filter.setOrganization(organizationDao.findByName(DataGenerator.ORGANIZATION_CODE).get(0));
		filter.setEnabled(true);

		//order model
		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<EUserOrderByAttributeSpecifier>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.EMAIL, EOrderSpecifier.ASC));

		List<CUser> users = userDao.findByUserAssociationsFilter(filter, orderModel);

		//asserts
		assertNotNull("No users found", users);
		Assert.assertEquals("Returns invalid users", 1, users.size()); //this fits the users with correct default unit
	}

	/**
	 * Test find by user associations filter positive no result one with order model.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAssociationsFilterPositiveNoResultOneWithOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		//filter
		CUserAssociationsFilter filter = new CUserAssociationsFilter();
		filter.setGroup(groupDao.findOneByCode(DataGenerator.SECOND_GROUP_IN_UNIT_CODE));
		filter.setRole(roleDao.findOneByCode(DataGenerator.SECOND_ROLE_CODE)); //incorrect role
		filter.setOrganization(organizationDao.findByName(DataGenerator.ORGANIZATION_CODE).get(0));
		filter.setEnabled(true);

		//order model
		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<EUserOrderByAttributeSpecifier>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.EMAIL, EOrderSpecifier.ASC));

		List<CUser> users = userDao.findByUserAssociationsFilter(filter, orderModel);

		//asserts
		assertNotNull("No users found", users);
		Assert.assertEquals("Returns invalid users", 0, users.size()); //this fits the users with correct default unit
	}

	/**
	 * Test find by user associations filter positive none without order model.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByUserAssociationsFilterPositiveNoneWithoutOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		List<CUser> users = userDao.findByUserAssociationsFilter(null, null);

		//asserts
		assertNotNull("No users found", users);
		Assert.assertEquals("Returns invalid users", 7, users.size());
	}
}
