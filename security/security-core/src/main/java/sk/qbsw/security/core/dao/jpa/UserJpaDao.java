/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.core.dao.jpa;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.security.core.dao.UserDao;
import sk.qbsw.security.core.model.domain.*;
import sk.qbsw.security.core.model.filter.CUserAssociationsFilter;
import sk.qbsw.security.core.model.filter.CUserDetailFilter;
import sk.qbsw.security.core.model.order.*;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * User DAO implementation.
 * 
 * @author rosenberg
 * @author Tomas Lauro
 * @version 1.16.0
 * @since 1.0.0
 */
@Repository (value = "userDao")
public class UserJpaDao extends AEntityQDslDao<Long, User> implements UserDao
{
	/**
	 * Instantiates a new user jpa dao.
	 */
	public UserJpaDao ()
	{
		super(QUser.user, Long.class);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IUserDao#findForModification(java.lang.Long)
	 */
	@Override
	@Deprecated
	public User findForModification (Long id)
	{
		return findById(id);
	}

	/**
	 * Find by id with fetched organization, groups, units, roles and default groups - if there is no result throws an exception.
	 *
	 * @param id the id
	 * @return the user with given id
	 * @throws NoResultException the no result exception
	 * @see sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao#findById(java.lang.Object)
	 */
	@Override
	public User findById (Long id) throws NoResultException
	{
		// get hibernate session from entity manager to set filter
		Session session = getEntityManager().unwrap(Session.class);

		try
		{
			// set filter to get just groups with proper default units
			session.enableFilter("userDefaultUnitFilter");

			QUser qUser = QUser.user;
			QUserUnitGroup qUserUnitGroup = QUserUnitGroup.userUnitGroup;
			QGroup qGroup = QGroup.group;

			// create query
			JPAQuery<User> query = queryFactory.selectFrom(qUser).distinct().leftJoin(qUser.organization).fetchJoin().leftJoin(qUser.defaultUnit).fetchJoin().leftJoin(qUser.xUserUnitGroups, qUserUnitGroup).fetchJoin().leftJoin(qUserUnitGroup.group, qGroup).fetchJoin().leftJoin(qUserUnitGroup.unit).fetchJoin().leftJoin(qGroup.roles).fetchJoin().where(qUser.id.eq(id));
			return CQDslDaoHelper.handleUniqueResultQuery(query);
		}
		finally
		{
			// disable filter
			session.disableFilter("userDefaultUnitFilter");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IUserDao#findByLogin(java.lang.String)
	 */
	@Override
	public User findOneByLogin (String login) throws NoResultException, CSecurityException
	{
		return findUserByLoginAndUnit(login, null);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IUserDao#findByLoginAndUnit(java.lang.String, sk.qbsw.security.core.core.model.domain.CUnit)
	 */
	@Override
	public User findOneByLoginAndUnit (String login, Unit unit) throws CSecurityException
	{
		return findUserByLoginAndUnit(login, unit);
	}

	/**
	 * Find by login and unit - if there is no result throws an exception.
	 *
	 * @param login the login
	 * @param unit the unit
	 * @return the user
	 * @throws NoResultException there is no result
	 * @throws CSecurityException throws if the login is null
	 */
	private User findUserByLoginAndUnit (String login, Unit unit) throws CSecurityException
	{
		// login is mandatory
		if (login == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		// get hibernate session from entity manager to set filter
		Session session = getEntityManager().unwrap(Session.class);

		try
		{
			QUser qUser = QUser.user;
			QUserUnitGroup qUserUnitGroup = QUserUnitGroup.userUnitGroup;
			QGroup qGroup = QGroup.group;
			QUnit qUnit = QUnit.unit;

			// create where condition
			BooleanBuilder builder = new BooleanBuilder();
			// the login cannot be null
			builder.and(qUser.login.eq(login));

			// 1. The unit has been set
			if (unit != null)
			{
				builder.and(qUnit.eq(unit));
			}
			// 2. The unit has not been set
			else
			{
				// set filter to get just groups with proper default units
				session.enableFilter("userDefaultUnitFilter");
			}

			// create query
			JPAQuery<User> query = queryFactory.selectFrom(qUser).distinct().leftJoin(qUser.organization).fetchJoin().leftJoin(qUser.defaultUnit).fetchJoin().leftJoin(qUser.xUserUnitGroups, qUserUnitGroup).fetchJoin().leftJoin(qUserUnitGroup.group, qGroup).fetchJoin().leftJoin(qUserUnitGroup.unit, qUnit).fetchJoin().leftJoin(qGroup.roles).fetchJoin().where(builder);
			return CQDslDaoHelper.handleUniqueResultQuery(query);
		}
		finally
		{
			// disable filter
			session.disableFilter("userDefaultUnitFilter");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IUserDao#findByPinCode(java.lang.String)
	 */
	@Override
	public List<User> findByPinCode (String pinCode) throws CSecurityException
	{
		if (pinCode == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QUser qUser = QUser.user;
		QAuthenticationParams qAuthParams = QAuthenticationParams.authenticationParams;

		// create query
		JPAQuery<User> query = queryFactory.selectFrom(qUser).distinct().leftJoin(qUser.organization).fetchJoin().leftJoin(qUser.authenticationParams, qAuthParams).where(qAuthParams.pin.eq(pinCode));
		return query.fetch();
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IUserDao#countAll()
	 */
	@Override
	public long countAll ()
	{
		// create query
		JPAQuery<User> query = queryFactory.selectFrom(QUser.user);
		return query.fetchCount();
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao#findAll()
	 */
	@Override
	public List<User> findAll ()
	{
		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<EUserOrderByAttributeSpecifier>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return findByUserAssociationsFilter(null, orderModel);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IUserDao#findByUnitAndGroup(sk.qbsw.security.core.core.model.domain.CUnit, sk.qbsw.security.core.core.model.domain.CGroup, sk.qbsw.security.core.core.model.order.COrderModel)
	 */
	@Override
	public List<User> findByUnitAndGroup (Unit unit, Group group, COrderModel<? extends IOrderByAttributeSpecifier> orderModel) throws CSecurityException
	{
		if (unit == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QUser qUser = QUser.user;
		QUserUnitGroup qUserUnitGroup = QUserUnitGroup.userUnitGroup;
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
		JPAQuery<User> query = queryFactory.selectFrom(qUser).distinct().leftJoin(qUser.xUserUnitGroups, qUserUnitGroup).fetchJoin().leftJoin(qUserUnitGroup.unit, qUnit).fetchJoin().leftJoin(qUserUnitGroup.group, qGroup).fetchJoin().where(builder);

		// set order
		if (orderModel != null)
		{
			query = query.orderBy(orderModel.getOrderSpecifiers());
		}

		return query.fetch();
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IUserDao#findByUserDetailFilter(sk.qbsw.security.core.core.model.filter.CUserDetailFilter, sk.qbsw.security.core.core.model.order.COrderModel)
	 */
	@Override
	public List<User> findByUserDetailFilter (CUserDetailFilter filter, COrderModel<? extends IOrderByAttributeSpecifier> orderModel)
	{
		// get hibernate session from entity manager to set filter
		Session session = getEntityManager().unwrap(Session.class);

		try
		{
			// set filter to get just groups with proper default units
			session.enableFilter("userDefaultUnitFilter");

			QUser qUser = QUser.user;
			QUserUnitGroup qUserUnitGroup = QUserUnitGroup.userUnitGroup;
			QGroup qGroup = QGroup.group;

			// create where condition
			BooleanBuilder builder = new BooleanBuilder();
			if (filter != null)
			{
				if (filter.getName() != null)
				{
					builder.and(qUser.name.eq(filter.getName()));
				}

				if (filter.getSurname() != null)
				{
					builder.and(qUser.surname.eq(filter.getSurname()));
				}

				if (filter.getLogin() != null)
				{
					builder.and(qUser.login.eq(filter.getLogin()));
				}

				if (filter.getEmail() != null)
				{
					builder.and(qUser.email.eq(filter.getEmail()));
				}

				if (filter.getGroupCodePrefix() != null)
				{
					builder.and(qGroup.code.like("%" + filter.getGroupCodePrefix() + "%"));
				}

				if (filter.getEnabled() != null)
				{
					builder.and(qUser.flagEnabled.eq(filter.getEnabled()));
				}

				if (filter.getOrganization() != null)
				{
					builder.and(qUser.organization.eq(filter.getOrganization()));
				}
			}

			// create query
			JPAQuery<User> query = queryFactory.selectFrom(qUser).distinct().leftJoin(qUser.organization).leftJoin(qUser.defaultUnit).fetchJoin().leftJoin(qUser.xUserUnitGroups, qUserUnitGroup).fetchJoin().leftJoin(qUserUnitGroup.group, qGroup).fetchJoin().leftJoin(qUserUnitGroup.unit).fetchJoin().where(builder);

			// set order
			if (orderModel != null)
			{
				query = query.orderBy(orderModel.getOrderSpecifiers());
			}

			return query.fetch();
		}
		finally
		{
			// disable filter
			session.disableFilter("userDefaultUnitFilter");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IUserDao#findByUserAssociationsFilter(sk.qbsw.security.core.core.model.filter.CUserAssociationsFilter, sk.qbsw.security.core.core.model.order.COrderModel)
	 */
	@Override
	public List<User> findByUserAssociationsFilter (CUserAssociationsFilter filter, COrderModel<? extends IOrderByAttributeSpecifier> orderModel)
	{
		// get hibernate session from entity manager to set filter
		Session session = getEntityManager().unwrap(Session.class);

		try
		{
			// set filter to get just groups with proper default units
			session.enableFilter("userDefaultUnitFilter");

			QUser qUser = QUser.user;
			QUserUnitGroup qUserUnitGroup = QUserUnitGroup.userUnitGroup;
			QGroup qGroup = QGroup.group;
			QRole qRole = QRole.role;
			QOrganization qOrganization = QOrganization.organization;

			// create where condition
			BooleanBuilder builder = new BooleanBuilder();
			if (filter != null)
			{
				if (filter.getGroup() != null)
				{
					builder.and(qGroup.eq(filter.getGroup()));
				}

				if (filter.getRole() != null)
				{
					builder.and(qRole.eq(filter.getRole()));
				}

				if (filter.getOrganization() != null)
				{
					builder.and(qOrganization.eq(filter.getOrganization()));
				}

				if (filter.getEnabled() != null)
				{
					builder.and(qUser.flagEnabled.eq(filter.getEnabled()));
				}

				if (filter.getExcludedUser() != null)
				{
					builder.and(qUser.ne(filter.getExcludedUser()));
				}
			}

			// create query
			JPAQuery<User> query = queryFactory.selectFrom(qUser).distinct().leftJoin(qUser.organization, qOrganization).fetchJoin().leftJoin(qUser.defaultUnit).fetchJoin().leftJoin(qUser.xUserUnitGroups, qUserUnitGroup).fetchJoin().leftJoin(qUserUnitGroup.group, qGroup).fetchJoin().leftJoin(qUserUnitGroup.unit).fetchJoin().leftJoin(qGroup.roles, qRole).fetchJoin().where(builder);

			// set order
			if (orderModel != null)
			{
				query = query.orderBy(orderModel.getOrderSpecifiers());
			}

			return query.fetch();
		}
		finally
		{
			// disable filter
			session.disableFilter("userDefaultUnitFilter");
		}
	}
}
