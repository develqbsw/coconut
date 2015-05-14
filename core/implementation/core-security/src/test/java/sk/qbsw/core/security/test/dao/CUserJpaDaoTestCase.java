package sk.qbsw.core.security.test.dao;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.security.dao.IGroupDao;
import sk.qbsw.core.security.dao.IOrganizationDao;
import sk.qbsw.core.security.dao.IRoleDao;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.filter.CUserAssociationsFilter;
import sk.qbsw.core.security.model.filter.CUserDetailFilter;
import sk.qbsw.core.security.model.order.COrderModel;
import sk.qbsw.core.security.model.order.COrderSpecification;
import sk.qbsw.core.security.model.order.EOrderSpecifier;
import sk.qbsw.core.security.model.order.EUserOrderByAttributeSpecifier;
import sk.qbsw.core.security.model.order.IOrderByAttributeSpecifier;
import sk.qbsw.core.security.test.util.CDataGenerator;

/**
 * Checks user dao.
 *
 * @version 1.13.0
 * @since 1.13.0
 * @autor Tomas Lauro
 */
public class CUserJpaDaoTestCase extends ADatabaseTestCase
{
	/** The user dao. */
	@Autowired
	private IUserDao userDao;

	/** The unit dao. */
	@Autowired
	private IUnitDao unitDao;

	/** The group dao. */
	@Autowired
	private IGroupDao groupDao;

	/** The organization dao. */
	@Autowired
	private IOrganizationDao organizationDao;

	/** The role dao. */
	@Autowired
	private IRoleDao roleDao;

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
	@Transactional
	@Rollback (true)
	public void testFindByIdPositive () throws NoResultException, CSecurityException
	{
		initTest();

		CUser testUser = userDao.findOneByLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		CUser user = userDao.findById(testUser.getId());

		//asserts
		assertNotNull("No user found", user);
		Assert.assertEquals("Returns invalid user", CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, user.getLogin());
	}

	/**
	 * Test find by id negative.
	 */
	@Test (expected = NoResultException.class)
	@Transactional
	@Rollback (true)
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
	@Transactional
	@Rollback (true)
	public void testFindOneByLoginPositiveWithDefaultUnit () throws NoResultException, CSecurityException
	{
		initTest();

		CUser user = userDao.findOneByLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);

		//asserts
		assertNotNull("No user found", user);
		Assert.assertEquals("Returns invalid user", CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, user.getLogin());
	}

	/**
	 * Test find one by login positive without default unit.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testFindOneByLoginPositiveWithoutDefaultUnit () throws NoResultException, CSecurityException
	{
		initTest();

		CUser user = userDao.findOneByLogin(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE);

		//asserts
		assertNotNull("No user found", user);
		Assert.assertEquals("Returns invalid user", CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, user.getLogin());
	}

	/**
	 * Test find one by login negative no result.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = NoResultException.class)
	@Transactional
	@Rollback (true)
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
	@Transactional
	@Rollback (true)
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
	@Transactional
	@Rollback (true)
	public void testFindOneByLoginAndUnitPositiveWithDefaultUnit () throws NoResultException, CSecurityException
	{
		initTest();

		CUnit unit = unitDao.findOneByName(CDataGenerator.DEFAULT_UNIT_CODE);
		CUser user = userDao.findOneByLoginAndUnit(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, unit);

		//asserts
		assertNotNull("No user found", user);
		Assert.assertEquals("Returns invalid user", CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, user.getLogin());
	}

	/**
	 * Test find one by login and unit positive without default unit.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testFindOneByLoginAndUnitPositiveWithoutDefaultUnit () throws NoResultException, CSecurityException
	{
		initTest();

		CUnit unit = unitDao.findOneByName(CDataGenerator.DEFAULT_UNIT_CODE);
		CUser user = userDao.findOneByLoginAndUnit(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, unit);

		//asserts
		assertNotNull("No user found", user);
		Assert.assertEquals("Returns invalid user", CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, user.getLogin());
	}

	/**
	 * Test find one by login and unit negative user not in unit.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = NoResultException.class)
	@Transactional
	@Rollback (true)
	public void testFindOneByLoginAndUnitNegativeUserNotInUnit () throws NoResultException, CSecurityException
	{
		initTest();

		CUnit unit = unitDao.findOneByName(CDataGenerator.SECOND_UNIT_CODE);
		userDao.findOneByLoginAndUnit(CDataGenerator.USER_WITHOUT_DEFAULT_UNIT_CODE, unit);
	}

	/**
	 * Test find one by login and unit negative no result.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = NoResultException.class)
	@Transactional
	@Rollback (true)
	public void testFindOneByLoginAndUnitNegativeNoResult () throws NoResultException, CSecurityException
	{
		initTest();

		CUnit unit = unitDao.findOneByName(CDataGenerator.DEFAULT_UNIT_CODE);
		userDao.findOneByLoginAndUnit("No result", unit);
	}

	/**
	 * Test find one by login and unit negative no login.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = CSecurityException.class)
	@Transactional
	@Rollback (true)
	public void testFindOneByLoginAndUnitNegativeNoLogin () throws NoResultException, CSecurityException
	{
		initTest();

		CUnit unit = unitDao.findOneByName(CDataGenerator.DEFAULT_UNIT_CODE);
		userDao.findOneByLoginAndUnit(null, unit);
	}

	/**
	 * Test find by pin code positive.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testFindByPinCodePositive () throws NoResultException, CSecurityException
	{
		initTest();

		List<CUser> users = userDao.findByPinCode("1111");

		//asserts
		assertNotNull("No users found", users);
		Assert.assertEquals("Returns invalid users", 1, users.size());
		Assert.assertEquals("Returns invalid user", CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, users.get(0).getLogin());
	}

	/**
	 * Test find by pin code positive no result.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
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
	@Transactional
	@Rollback (true)
	public void testFindByPinCodeNegativeNoPin () throws NoResultException, CSecurityException
	{
		initTest();

		userDao.findByPinCode(null);
	}

	/**
	 * Test count all positive.
	 */
	@Test
	@Transactional
	@Rollback (true)
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
	@Transactional
	@Rollback (true)
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
	@Transactional
	@Rollback (true)
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
	@Transactional
	@Rollback (true)
	public void testFindByUnitAndGroupPositiveBothWithOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		//order model
		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<EUserOrderByAttributeSpecifier>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		//unit and group
		CUnit unit = unitDao.findOneByName(CDataGenerator.FIRST_UNIT_CODE);
		CGroup group = groupDao.findOneByCode(CDataGenerator.SECOND_GROUP_IN_UNIT_CODE);


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
	@Transactional
	@Rollback (true)
	public void testFindByUnitAndGroupNegativeWithUnitWithOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		//order model
		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<EUserOrderByAttributeSpecifier>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		//unit and group
		CGroup group = groupDao.findOneByCode(CDataGenerator.SECOND_GROUP_IN_UNIT_CODE);


		userDao.findByUnitAndGroup(null, group, orderModel);
	}

	/**
	 * Test find by unit and group positive with group with order model.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testFindByUnitAndGroupPositiveWithGroupWithOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		//order model
		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<EUserOrderByAttributeSpecifier>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.EMAIL, EOrderSpecifier.ASC));

		//unit and group
		CUnit unit = unitDao.findOneByName(CDataGenerator.FIRST_UNIT_CODE);


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
	@Transactional
	@Rollback (true)
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
	@Transactional
	@Rollback (true)
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
	@Transactional
	@Rollback (true)
	public void testFindByUserDetailFilterPositiveWithOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		//filter
		CUserDetailFilter filter = new CUserDetailFilter();
		filter.setName(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		filter.setSurname(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		filter.setLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		filter.setEmail(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk");
		filter.setGroupCodePrefix(CDataGenerator.FIRST_GROUP_IN_UNIT_CODE.substring(0, 5));
		filter.setEnabled(true);
		filter.setOrganization(organizationDao.findByName(CDataGenerator.ORGANIZATION_CODE).get(0));

		//order model
		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<EUserOrderByAttributeSpecifier>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.EMAIL, EOrderSpecifier.ASC));

		List<CUser> users = userDao.findByUserDetailFilter(filter, orderModel);

		//asserts
		assertNotNull("No users found", users);
		Assert.assertEquals("Returns invalid users", 1, users.size());
		Assert.assertEquals("Returns invalid users", CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE, users.get(0).getLogin());
	}

	/**
	 * Test find by user detail filter positive no result one with order model.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional
	@Rollback (true)
	public void testFindByUserDetailFilterPositiveNoResultOneWithOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		//filter
		CUserDetailFilter filter = new CUserDetailFilter();
		filter.setName(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE_NO_GROUP); //wrong name
		filter.setSurname(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		filter.setLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		filter.setEmail(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk");
		filter.setGroupCodePrefix(CDataGenerator.FIRST_GROUP_IN_UNIT_CODE.substring(0, 5));
		filter.setEnabled(true);
		filter.setOrganization(organizationDao.findByName(CDataGenerator.ORGANIZATION_CODE).get(0));

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
	@Transactional
	@Rollback (true)
	public void testFindByUserDetailFilterPositiveNoDataWithOrderModel () throws NoResultException, CSecurityException
	{
		//without initialization

		//filter
		CUserDetailFilter filter = new CUserDetailFilter();
		filter.setName(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		filter.setSurname(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		filter.setLogin(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE);
		filter.setEmail(CDataGenerator.USER_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk");
		filter.setGroupCodePrefix(CDataGenerator.FIRST_GROUP_IN_UNIT_CODE.substring(0, 5));
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
	@Transactional
	@Rollback (true)
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
	@Transactional
	@Rollback (true)
	public void testFindByUserAssociationsFilterPositiveWithOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		//filter
		CUserAssociationsFilter filter = new CUserAssociationsFilter();
		filter.setGroup(groupDao.findOneByCode(CDataGenerator.SECOND_GROUP_IN_UNIT_CODE));
		filter.setRole(roleDao.findOneByCode(CDataGenerator.FIRST_ROLE_CODE));
		filter.setOrganization(organizationDao.findByName(CDataGenerator.ORGANIZATION_CODE).get(0));
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
	@Transactional
	@Rollback (true)
	public void testFindByUserAssociationsFilterPositiveNoResultOneWithOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		//filter
		CUserAssociationsFilter filter = new CUserAssociationsFilter();
		filter.setGroup(groupDao.findOneByCode(CDataGenerator.SECOND_GROUP_IN_UNIT_CODE));
		filter.setRole(roleDao.findOneByCode(CDataGenerator.SECOND_ROLE_CODE)); //incorrect role
		filter.setOrganization(organizationDao.findByName(CDataGenerator.ORGANIZATION_CODE).get(0));
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
	@Transactional
	@Rollback (true)
	public void testFindByUserAssociationsFilterPositiveNoneWithoutOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		List<CUser> users = userDao.findByUserAssociationsFilter(null, null);

		//asserts
		assertNotNull("No users found", users);
		Assert.assertEquals("Returns invalid users", 7, users.size());
	}
}
