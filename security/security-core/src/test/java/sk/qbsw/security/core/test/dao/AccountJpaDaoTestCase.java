package sk.qbsw.security.core.test.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.security.core.dao.*;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Group;
import sk.qbsw.security.core.model.domain.Organization;
import sk.qbsw.security.core.model.domain.Unit;
import sk.qbsw.security.core.model.filter.AccountAssociationsFilter;
import sk.qbsw.security.core.model.filter.AccountDetailFilter;
import sk.qbsw.security.core.model.order.AccountOrderByAttributeSpecifiers;
import sk.qbsw.security.core.model.order.OrderModel;
import sk.qbsw.security.core.model.order.OrderSpecification;
import sk.qbsw.security.core.model.order.OrderSpecifiers;
import sk.qbsw.security.core.test.util.DataGenerator;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

/**
 * Checks account dao.
 *
 * @version 2.5.0
 * @since 1.13.0
 * @author Tomas Lauro
 * @author Michal Slezák
 */
public class AccountJpaDaoTestCase extends BaseDatabaseTestCase
{
	@Autowired
	private AccountDao<Account> accountDao;

	@Autowired
	private UnitDao unitDao;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private OrganizationDao organizationDao;

	/**
	 * Test initialization.
	 */
	@Before
	public void testInitialization ()
	{
		super.testInitialization();
		assertNotNull("Could not find account dao", accountDao);
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

		Account testAccount = accountDao.findOneByLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);
		Account account = accountDao.findById(testAccount.getId());

		// asserts
		assertNotNull("No account found", account);
		Assert.assertEquals("Returns invalid account", DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, account.getLogin());
	}

	/**
	 * Test find by id negative.
	 */
	@Test (expected = NoResultException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindByIdNegative ()
	{
		initTest();

		accountDao.findById(89218423498l);
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

		Account account = accountDao.findOneByLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);

		// asserts
		assertNotNull("No account found", account);
		Assert.assertEquals("Returns invalid account", DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, account.getLogin());
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

		Account account = accountDao.findOneByLogin(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE);

		// asserts
		assertNotNull("No account found", account);
		Assert.assertEquals("Returns invalid account", DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, account.getLogin());
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

		accountDao.findOneByLogin("No result");
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

		accountDao.findOneByLogin(null);
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

		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);
		Account account = accountDao.findOneByLoginAndUnit(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, unit);

		// asserts
		assertNotNull("No account found", account);
		Assert.assertEquals("Returns invalid account", DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, account.getLogin());
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

		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);
		Account account = accountDao.findOneByLoginAndUnit(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, unit);

		// asserts
		assertNotNull("No account found", account);
		Assert.assertEquals("Returns invalid account", DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, account.getLogin());
	}

	/**
	 * Test find one by login and unit negative account not in unit.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test (expected = NoResultException.class)
	@Transactional (transactionManager = "transactionManager")
	public void testFindOneByLoginAndUnitNegativeAccountNotInUnit () throws NoResultException, CSecurityException
	{
		initTest();

		Unit unit = unitDao.findOneByName(DataGenerator.SECOND_UNIT_CODE);
		accountDao.findOneByLoginAndUnit(DataGenerator.ACCOUNT_WITHOUT_DEFAULT_UNIT_CODE, unit);
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

		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);
		accountDao.findOneByLoginAndUnit("No result", unit);
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

		Unit unit = unitDao.findOneByName(DataGenerator.DEFAULT_UNIT_CODE);
		accountDao.findOneByLoginAndUnit(null, unit);
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

		List<Account> accounts = accountDao.findByPinCode("1111");

		// asserts
		assertNotNull("No accounts found", accounts);
		Assert.assertEquals("Returns invalid accounts", 1, accounts.size());
		Assert.assertEquals("Returns invalid account", DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, accounts.get(0).getLogin());
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

		List<Account> accounts = accountDao.findByPinCode("1234");

		// asserts
		assertNotNull("No accounts found", accounts);
		Assert.assertEquals("Returns invalid accounts", 0, accounts.size());
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

		accountDao.findByPinCode(null);
	}

	/**
	 * Test count all positive.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testCountAllPositive ()
	{
		initTest();

		long countAll = accountDao.countAll();

		// asserts
		Assert.assertEquals("Returns invalid accounts number", 8, countAll);
	}

	/**
	 * Test find all positive.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindAllPositive ()
	{
		initTest();

		List<Account> accounts = accountDao.findAll();

		// asserts
		assertNotNull("No accounts found", accounts);
		Assert.assertEquals("Returns invalid accounts", 8, accounts.size());
	}

	/**
	 * Test find all positive no data.
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindAllPositiveNoData ()
	{
		List<Account> accounts = accountDao.findAll();

		// asserts
		assertNotNull("No accounts found", accounts);
		Assert.assertEquals("Returns invalid accounts", 0, accounts.size());
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

		// order model
		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		// unit and group
		Unit unit = unitDao.findOneByName(DataGenerator.FIRST_UNIT_CODE);
		Group group = groupDao.findOneByCode(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);


		List<Account> accounts = accountDao.findByUnitAndGroup(unit, group, orderModel);

		// asserts
		assertNotNull("No accounts found", accounts);
		Assert.assertEquals("Returns invalid accounts", 3, accounts.size());
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

		// order model
		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		// unit and group
		Group group = groupDao.findOneByCode(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);


		accountDao.findByUnitAndGroup(null, group, orderModel);
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

		// order model
		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.EMAIL, OrderSpecifiers.ASC));

		// unit and group
		Unit unit = unitDao.findOneByName(DataGenerator.FIRST_UNIT_CODE);


		List<Account> accounts = accountDao.findByUnitAndGroup(unit, null, orderModel);

		// asserts
		assertNotNull("No accounts found", accounts);
		Assert.assertEquals("Returns invalid accounts", 4, accounts.size());
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

		accountDao.findByUnitAndGroup(null, null, null);
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
		accountDao.findByUnitAndGroup(null, null, null);
	}

	/**
	 * Test find by account detail filter positive with order model.
	 *
	 * @throws NoResultException the no result exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByAccountDetailFilterPositiveWithOrderModel () throws NoResultException
	{
		initTest();

		// filter
		AccountDetailFilter filter = new AccountDetailFilter();
		filter.setLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);
		filter.setEmail(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk");
		filter.setGroupCodePrefix(DataGenerator.FIRST_GROUP_IN_UNIT_CODE.substring(0, 5));
		filter.setState(ActivityStates.ACTIVE);
		filter.setOrganizationCode(DataGenerator.ORGANIZATION_CODE);

		// order model
		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.EMAIL, OrderSpecifiers.ASC));

		List<Account> accounts = accountDao.findByAccountDetailFilter(filter, orderModel);

		// asserts
		assertNotNull("No accounts found", accounts);
		Assert.assertEquals("Returns invalid accounts", 1, accounts.size());
		Assert.assertEquals("Returns invalid accounts", DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE, accounts.get(0).getLogin());
	}

	/**
	 * Test find by account detail filter positive no result one with order model.
	 *
	 * @throws NoResultException the no result exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByAccountDetailFilterPositiveNoResultOneWithOrderModel () throws NoResultException
	{
		initTest();

		// filter
		AccountDetailFilter filter = new AccountDetailFilter();
		filter.setLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE_NO_GROUP);
		filter.setEmail(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE_NO_GROUP + "@qbsw.sk");
		filter.setGroupCodePrefix(DataGenerator.FIRST_GROUP_IN_UNIT_CODE.substring(0, 5));
		filter.setState(ActivityStates.ACTIVE);
		filter.setOrganizationCode(DataGenerator.ORGANIZATION_CODE);

		// order model
		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.EMAIL, OrderSpecifiers.ASC));

		List<Account> accounts = accountDao.findByAccountDetailFilter(filter, orderModel);

		// asserts
		assertNotNull("No accounts found", accounts);
		Assert.assertEquals("Returns invalid accounts", 0, accounts.size());
	}

	/**
	 * Test find by account detail filter positive no data with order model.
	 *
	 * @throws NoResultException the no result exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByAccountDetailFilterPositiveNoDataWithOrderModel () throws NoResultException
	{
		// without initialization

		// filter
		AccountDetailFilter filter = new AccountDetailFilter();
		filter.setLogin(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE);
		filter.setEmail(DataGenerator.ACCOUNT_WITH_DEFAULT_UNIT_CODE + "@qbsw.sk");
		filter.setGroupCodePrefix(DataGenerator.FIRST_GROUP_IN_UNIT_CODE.substring(0, 5));
		filter.setState(ActivityStates.ACTIVE);

		// order model
		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.EMAIL, OrderSpecifiers.ASC));

		List<Account> accounts = accountDao.findByAccountDetailFilter(filter, orderModel);

		// asserts
		assertNotNull("No accounts found", accounts);
		Assert.assertEquals("Returns invalid accounts", 0, accounts.size());
	}

	/**
	 * Test find by account detail filter positive none without order model.
	 *
	 * @throws NoResultException the no result exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByAccountDetailFilterPositiveNoneWithoutOrderModel () throws NoResultException
	{
		initTest();

		// order model
		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.EMAIL, OrderSpecifiers.ASC));

		List<Account> accounts = accountDao.findByAccountDetailFilter(null, null);

		// asserts
		assertNotNull("No accounts found", accounts);
		Assert.assertEquals("Returns invalid accounts", 8, accounts.size());
	}


	/**
	 * Test find by account associations filter positive with order model.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByAccountAssociationsFilterPositiveWithOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		// filter
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setGroupCode(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);
		filter.setRoleCode(DataGenerator.FIRST_ROLE_CODE);
		filter.setOrganizationCode(DataGenerator.ORGANIZATION_CODE);
		filter.setState(ActivityStates.ACTIVE);

		// order model
		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.EMAIL, OrderSpecifiers.ASC));

		List<Account> accounts = accountDao.findByAccountAssociationsFilter(filter, orderModel);

		// asserts
		assertNotNull("No accounts found", accounts);
		Assert.assertEquals("Returns invalid accounts", 1, accounts.size()); // this fits the accounts with correct default unit
	}

	/**
	 * Test find by account associations filter positive no result one with order model.
	 *
	 * @throws NoResultException the no result exception
	 * @throws CSecurityException the c security exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByAccountAssociationsFilterPositiveNoResultOneWithOrderModel () throws NoResultException, CSecurityException
	{
		initTest();

		// filter
		AccountAssociationsFilter filter = new AccountAssociationsFilter();
		filter.setGroupCode(DataGenerator.SECOND_GROUP_IN_UNIT_CODE);
		filter.setRoleCode(DataGenerator.SECOND_ROLE_CODE); // incorrect role
		filter.setOrganizationCode(DataGenerator.ORGANIZATION_CODE);
		filter.setState(ActivityStates.ACTIVE);

		// order model
		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.EMAIL, OrderSpecifiers.ASC));

		List<Account> accounts = accountDao.findByAccountAssociationsFilter(filter, orderModel);

		// asserts
		assertNotNull("No accounts found", accounts);
		Assert.assertEquals("Returns invalid accounts", 0, accounts.size()); // this fits the accounts with correct default unit
	}

	/**
	 * Test find by account associations filter positive none without order model.
	 *
	 * @throws NoResultException the no result exception
	 */
	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testFindByAccountAssociationsFilterPositiveNoneWithoutOrderModel () throws NoResultException
	{
		initTest();

		List<Account> accounts = accountDao.findByAccountAssociationsFilter(null, null);

		// asserts
		assertNotNull("No accounts found", accounts);
		Assert.assertEquals("Returns invalid accounts", 8, accounts.size());
	}

	@Test
	@Transactional (transactionManager = "transactionManager")
	public void testAccountUniquenessPositive () {

		initTest();

		Organization o1 = organizationDao.findOneByCode(DataGenerator.ORGANIZATION_CODE);
		Organization o2 = organizationDao.findOneByCode(DataGenerator.ORGANIZATION_2_CODE);

		String uniqueUid1 = UUID.randomUUID().toString();


		Account a1O1 = dataGenerator.createAccount(uniqueUid1, uniqueUid1, o1, ActivityStates.ACTIVE);

		Long a1O1Id = accountDao.create(a1O1);
		accountDao.flush();

		assertNotNull("Created account a1O1 id is null", a1O1Id);


		Account a1O2 = dataGenerator.createAccount(uniqueUid1, uniqueUid1, o2, ActivityStates.ACTIVE);

		Long a1O2Id = accountDao.create(a1O2);
		accountDao.flush();

		assertNotNull("Created account a1O1 id is null", a1O2Id);

	}
}
