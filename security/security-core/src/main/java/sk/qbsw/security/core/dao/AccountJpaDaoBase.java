package sk.qbsw.security.core.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.security.core.model.domain.*;
import sk.qbsw.security.core.model.filter.AccountAssociationsFilter;
import sk.qbsw.security.core.model.filter.AccountDetailFilter;
import sk.qbsw.security.core.model.order.*;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * User DAO implementation.
 *
 * @param <A> the type parameter
 * @author rosenberg
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.0.0
 */
public abstract class AccountJpaDaoBase<A extends Account>extends AEntityQDslDao<Long, A> implements AccountDao<A>
{
	/**
	 * Instantiates a new Account jpa dao base.
	 *
	 * @param entityPathCRUD the entity path crud
	 */
	public AccountJpaDaoBase (EntityPathBase<A> entityPathCRUD)
	{
		super(entityPathCRUD, Long.class);
	}

	@Override
	@SuppressWarnings ("unchecked")
	public A findById (Long id) throws NoResultException
	{
		// get hibernate session from entity manager to set filter
		Session session = getEntityManager().unwrap(Session.class);

		try
		{
			// set filter to get just groups with proper default units
			session.enableFilter(Account.DEFAULT_UNIT_FILTER_NAME);

			QAccount qAccount = instantiateAccountEntityPath();
			QAccountUnitGroup qAccountUnitGroup = QAccountUnitGroup.accountUnitGroup;
			QGroup qGroup = QGroup.group;

			// create query
			JPAQuery<Account> query = queryFactory.selectFrom(qAccount).distinct() //
				.leftJoin(qAccount.organization).fetchJoin() //
				.leftJoin(qAccount.defaultUnit).fetchJoin() //
				.leftJoin(qAccount.accountUnitGroups, qAccountUnitGroup).fetchJoin() //
				.leftJoin(qAccountUnitGroup.group, qGroup).fetchJoin() //
				.leftJoin(qAccountUnitGroup.unit).fetchJoin() //
				.leftJoin(qGroup.roles).fetchJoin() //
				.where(qAccount.id.eq(id));
			return (A) CQDslDaoHelper.handleUniqueResultQuery(query);
		}
		finally
		{
			// disable filter
			session.disableFilter(Account.DEFAULT_UNIT_FILTER_NAME);
		}
	}

	@Override
	@SuppressWarnings ("unchecked")
	public A findByUid (String uid)
	{
		// get hibernate session from entity manager to set filter
		Session session = getEntityManager().unwrap(Session.class);

		try
		{
			// set filter to get just groups with proper default units
			session.enableFilter(Account.DEFAULT_UNIT_FILTER_NAME);

			QAccount qAccount = instantiateAccountEntityPath();
			QAccountUnitGroup qAccountUnitGroup = QAccountUnitGroup.accountUnitGroup;
			QGroup qGroup = QGroup.group;

			// create query
			JPAQuery<Account> query = queryFactory.selectFrom(qAccount).distinct() //
				.leftJoin(qAccount.organization).fetchJoin() //
				.leftJoin(qAccount.defaultUnit).fetchJoin() //
				.leftJoin(qAccount.accountUnitGroups, qAccountUnitGroup).fetchJoin() //
				.leftJoin(qAccountUnitGroup.group, qGroup).fetchJoin() //
				.leftJoin(qAccountUnitGroup.unit).fetchJoin() //
				.leftJoin(qGroup.roles).fetchJoin() //
				.where(qAccount.uid.eq(uid));
			return (A) CQDslDaoHelper.handleUniqueResultQueryByNull(query);
		}
		finally
		{
			// disable filter
			session.disableFilter(Account.DEFAULT_UNIT_FILTER_NAME);
		}
	}

	@Override
	public A findOneByLogin (String login) throws NoResultException, CSecurityException
	{
		return findAccountByLoginAndUnit(login, null);
	}

	@Override
	public A findOneByLoginAndUnit (String login, Unit unit) throws CSecurityException
	{
		return findAccountByLoginAndUnit(login, unit);
	}

	@SuppressWarnings ("unchecked")
	private A findAccountByLoginAndUnit (String login, Unit unit) throws CSecurityException
	{
		if (login == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		// get hibernate session from entity manager to set filter
		Session session = getEntityManager().unwrap(Session.class);

		try
		{
			QAccount qAccount = instantiateAccountEntityPath();
			QAccountUnitGroup qAccountUnitGroup = QAccountUnitGroup.accountUnitGroup;
			QGroup qGroup = QGroup.group;
			QUnit qUnit = QUnit.unit;

			// create where condition
			BooleanBuilder builder = new BooleanBuilder();
			// the login cannot be null
			builder.and(qAccount.login.eq(login));

			// 1. The unit has been set
			if (unit != null)
			{
				builder.and(qUnit.eq(unit));
			}
			// 2. The unit has not been set
			else
			{
				// set filter to get just groups with proper default units
				session.enableFilter(Account.DEFAULT_UNIT_FILTER_NAME);
			}

			// create query
			JPAQuery<Account> query = queryFactory.selectFrom(qAccount).distinct() //
				.leftJoin(qAccount.organization).fetchJoin() //
				.leftJoin(qAccount.defaultUnit).fetchJoin() //
				.leftJoin(qAccount.accountUnitGroups, qAccountUnitGroup) //
				.fetchJoin().leftJoin(qAccountUnitGroup.group, qGroup) //
				.fetchJoin().leftJoin(qAccountUnitGroup.unit, qUnit).fetchJoin() //
				.leftJoin(qGroup.roles).fetchJoin() //
				.where(builder);
			return (A) CQDslDaoHelper.handleUniqueResultQuery(query);
		}
		finally
		{
			// disable filter
			session.disableFilter(Account.DEFAULT_UNIT_FILTER_NAME);
		}
	}

	@Override
	@SuppressWarnings ("unchecked")
	public List<A> findByPinCode (String pinCode) throws CSecurityException
	{
		if (pinCode == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QAccount qAccount = instantiateAccountEntityPath();
		QAuthenticationParams qAuthParams = QAuthenticationParams.authenticationParams;

		// create query
		JPAQuery<Account> query = queryFactory.selectFrom(qAccount).distinct() //
			.leftJoin(qAccount.organization).fetchJoin() //
			.leftJoin(qAccount.authenticationParams, qAuthParams) //
			.where(qAuthParams.pin.eq(pinCode));
		return (List<A>) query.fetch();
	}

	@Override
	public long countAll ()
	{
		// create query
		JPAQuery<Account> query = queryFactory.selectFrom(QAccount.account);
		return query.fetchCount();
	}

	@Override
	public List<A> findAll ()
	{
		OrderModel<AccountOrderByAttributeSpecifiers> orderModel = new OrderModel<>();
		orderModel.getOrderSpecification().add(new OrderSpecification<>(AccountOrderByAttributeSpecifiers.LOGIN, OrderSpecifiers.ASC));

		return findByAccountAssociationsFilter(null, orderModel);
	}

	@Override
	@SuppressWarnings ("unchecked")
	public List<A> findByUnitAndGroup (Unit unit, Group group, OrderModel<? extends OrderByAttributeSpecifier> orderModel) throws CSecurityException
	{
		if (unit == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QAccount qAccount = instantiateAccountEntityPath();
		QAccountUnitGroup qAccountUnitGroup = QAccountUnitGroup.accountUnitGroup;
		QGroup qGroup = QGroup.group;
		QUnit qUnit = QUnit.unit;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qUnit.eq(unit));

		if (group != null)
		{
			builder.and(qGroup.eq(group));
		}

		// create query
		JPAQuery<Account> query = queryFactory.selectFrom(qAccount).distinct() //
			.leftJoin(qAccount.accountUnitGroups, qAccountUnitGroup).fetchJoin() //
			.leftJoin(qAccountUnitGroup.unit, qUnit).fetchJoin() //
			.leftJoin(qAccountUnitGroup.group, qGroup).fetchJoin() //
			.where(builder);

		// set order
		if (orderModel != null)
		{
			query = query.orderBy(orderModel.getOrderSpecifiers());
		}

		return (List<A>) query.fetch();
	}

	@Override
	@SuppressWarnings ("unchecked")
	public List<A> findByAccountDetailFilter (AccountDetailFilter filter, OrderModel<? extends OrderByAttributeSpecifier> orderModel)
	{
		// get hibernate session from entity manager to set filter
		Session session = getEntityManager().unwrap(Session.class);

		try
		{
			// set filter to get just groups with proper default units
			session.enableFilter(Account.DEFAULT_UNIT_FILTER_NAME);

			QAccount qAccount = instantiateAccountEntityPath();
			QAccountUnitGroup qAccountUnitGroup = QAccountUnitGroup.accountUnitGroup;
			QGroup qGroup = QGroup.group;

			// create where condition
			BooleanBuilder builder = new BooleanBuilder();
			if (filter != null)
			{
				if (filter.getLogin() != null)
				{
					builder.and(qAccount.login.eq(filter.getLogin()));
				}

				if (filter.getEmail() != null)
				{
					builder.and(qAccount.email.eq(filter.getEmail()));
				}

				if (filter.getGroupCodePrefix() != null)
				{
					builder.and(qGroup.code.like("%" + filter.getGroupCodePrefix() + "%"));
				}

				if (filter.getState() != null)
				{
					builder.and(qAccount.state.eq(filter.getState()));
				}

				if (filter.getOrganizationCode() != null)
				{
					builder.and(qAccount.organization.code.eq(filter.getOrganizationCode()));
				}
			}

			// create query
			JPAQuery<Account> query = queryFactory.selectFrom(qAccount).distinct() //
				.leftJoin(qAccount.organization) //
				.leftJoin(qAccount.defaultUnit).fetchJoin() //
				.leftJoin(qAccount.accountUnitGroups, qAccountUnitGroup).fetchJoin() //
				.leftJoin(qAccountUnitGroup.group, qGroup).fetchJoin() //
				.leftJoin(qAccountUnitGroup.unit).fetchJoin() //
				.where(builder);

			// set order
			if (orderModel != null)
			{
				query = query.orderBy(orderModel.getOrderSpecifiers());
			}

			return (List<A>) query.fetch();
		}
		finally
		{
			// disable filter
			session.disableFilter(Account.DEFAULT_UNIT_FILTER_NAME);
		}
	}

	@Override
	@SuppressWarnings ("unchecked")
	public List<A> findByAccountAssociationsFilter (AccountAssociationsFilter filter, OrderModel<? extends OrderByAttributeSpecifier> orderModel)
	{
		// get hibernate session from entity manager to set filter
		Session session = getEntityManager().unwrap(Session.class);

		try
		{
			// set filter to get just groups with proper default units
			session.enableFilter(Account.DEFAULT_UNIT_FILTER_NAME);

			QAccount qAccount = instantiateAccountEntityPath();
			QAccountUnitGroup qAccountUnitGroup = QAccountUnitGroup.accountUnitGroup;
			QGroup qGroup = QGroup.group;
			QRole qRole = QRole.role;
			QOrganization qOrganization = QOrganization.organization;

			// create where condition
			BooleanBuilder builder = new BooleanBuilder();
			if (filter != null)
			{
				if (filter.getGroupCode() != null)
				{
					builder.and(qGroup.code.eq(filter.getGroupCode()));
				}

				if (filter.getRoleCode() != null)
				{
					builder.and(qRole.code.eq(filter.getRoleCode()));
				}

				if (filter.getOrganizationCode() != null)
				{
					builder.and(qOrganization.code.eq(filter.getOrganizationCode()));
				}

				if (filter.getState() != null)
				{
					builder.and(qAccount.state.eq(filter.getState()));
				}

				if (filter.getExcludedLogin() != null)
				{
					builder.and(qAccount.login.ne(filter.getExcludedLogin()));
				}
			}

			// create query
			JPAQuery<Account> query = queryFactory.selectFrom(qAccount).distinct() //
				.leftJoin(qAccount.organization, qOrganization).fetchJoin() //
				.leftJoin(qAccount.defaultUnit).fetchJoin() //
				.leftJoin(qAccount.accountUnitGroups, qAccountUnitGroup).fetchJoin() //
				.leftJoin(qAccountUnitGroup.group, qGroup).fetchJoin() //
				.leftJoin(qAccountUnitGroup.unit).fetchJoin() //
				.leftJoin(qGroup.roles, qRole).fetchJoin() //
				.where(builder);

			// set order
			if (orderModel != null)
			{
				query = query.orderBy(orderModel.getOrderSpecifiers());
			}

			return (List<A>) query.fetch();
		}
		finally
		{
			// disable filter
			session.disableFilter(Account.DEFAULT_UNIT_FILTER_NAME);
		}
	}

	protected abstract QAccount instantiateAccountEntityPath ();
}
