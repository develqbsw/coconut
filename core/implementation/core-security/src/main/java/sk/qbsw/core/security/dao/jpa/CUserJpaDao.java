/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.PropertyResolver;
import org.springframework.stereotype.Repository;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.IUserDao;
import sk.qbsw.core.security.model.domain.CGroup;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.domain.QCAuthenticationParams;
import sk.qbsw.core.security.model.domain.QCGroup;
import sk.qbsw.core.security.model.domain.QCOrganization;
import sk.qbsw.core.security.model.domain.QCRole;
import sk.qbsw.core.security.model.domain.QCUnit;
import sk.qbsw.core.security.model.domain.QCUser;
import sk.qbsw.core.security.model.domain.QCXUserUnitGroup;
import sk.qbsw.core.security.model.filter.CUserAssociationsFilter;
import sk.qbsw.core.security.model.filter.CUserDetailFilter;
import sk.qbsw.core.security.model.order.COrderModel;
import sk.qbsw.core.security.model.order.COrderSpecification;
import sk.qbsw.core.security.model.order.EOrderSpecifier;
import sk.qbsw.core.security.model.order.EUserOrderByAttributeSpecifier;
import sk.qbsw.core.security.model.order.IOrderByAttributeSpecifier;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * User DAO implementation.
 * 
 * @author rosenberg
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.0.0
 */
@Repository (value = "userDao")
public class CUserJpaDao extends AEntityJpaDao<Long, CUser> implements IUserDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Autowired
	private PropertyResolver resolver;

	/**
	 * Instantiates a new user jpa dao.
	 * 
	 */
	public CUserJpaDao ()
	{
		super(CUser.class);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findForModification(java.lang.Long)
	 */
	@Deprecated
	@Override
	public CUser findForModification (Long id)
	{
		return findById(id);
	}

	/**
	 * Find by id  with fetched organization, groups, units, roles and default groups - if there is no result throws an exception.
	 *
	 * @param id the id
	 * @return the user with given id
	 * 
	 * @throws NoResultException the no result exception
	 * 
	 * @see sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao#findById(java.lang.Object)
	 */
	@Override
	public CUser findById (Long id) throws NoResultException
	{
		//get hibernate session from entity manager to set filter
		Session session = getEntityManager().unwrap(Session.class);

		try
		{
			//set filter to get just groups with proper default units
			session.enableFilter("userDefaultUnitFilter");

			QCUser qUser = QCUser.cUser;
			QCXUserUnitGroup qUserUnitGroup = QCXUserUnitGroup.cXUserUnitGroup;
			QCGroup qGroup = QCGroup.cGroup;

			//create query
			JPAQuery query = new JPAQuery(getEntityManager()).distinct().from(qUser).leftJoin(qUser.organization).fetch().leftJoin(qUser.defaultUnit).fetch().leftJoin(qUser.xUserUnitGroups, qUserUnitGroup).fetch().leftJoin(qUserUnitGroup.group, qGroup).fetch().leftJoin(qUserUnitGroup.unit).fetch().leftJoin(qGroup.roles).fetch().where(qUser.id.eq(id));
			return CJpaDaoHelper.handleUniqueResultQuery(query, qUser);
		}
		finally
		{
			//disable filter
			session.disableFilter("userDefaultUnitFilter");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findByLogin(java.lang.String)
	 */
	@Override
	public CUser findOneByLogin (String login) throws NoResultException, CSecurityException
	{
		return findUserByLoginAndUnit(login, null);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findByLoginAndUnit(java.lang.String, sk.qbsw.core.security.model.domain.CUnit)
	 */
	@Override
	public CUser findOneByLoginAndUnit (String login, CUnit unit) throws NoResultException, CSecurityException
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
	private CUser findUserByLoginAndUnit (String login, CUnit unit) throws NoResultException, CSecurityException
	{
		//login is mandatory
		if (login == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		//get hibernate session from entity manager to set filter
		Session session = getEntityManager().unwrap(Session.class);

		try
		{
			QCUser qUser = QCUser.cUser;
			QCXUserUnitGroup qUserUnitGroup = QCXUserUnitGroup.cXUserUnitGroup;
			QCGroup qGroup = QCGroup.cGroup;
			QCUnit qUnit = QCUnit.cUnit;

			//create where condition
			BooleanBuilder builder = new BooleanBuilder();
			//the login cannot be null
			builder.and(qUser.login.eq(login));

			// 1. The unit has been set
			if (unit != null)
			{
				builder.and(qUnit.eq(unit));
			}
			// 2. The unit has not been set
			else
			{
				//set filter to get just groups with proper default units
				session.enableFilter("userDefaultUnitFilter");
			}

			//create query
			JPAQuery query = new JPAQuery(getEntityManager()).distinct().from(qUser).leftJoin(qUser.organization).fetch().leftJoin(qUser.defaultUnit).fetch().leftJoin(qUser.xUserUnitGroups, qUserUnitGroup).fetch().leftJoin(qUserUnitGroup.group, qGroup).fetch().leftJoin(qUserUnitGroup.unit, qUnit).fetch().leftJoin(qGroup.roles).fetch().where(builder);
			return CJpaDaoHelper.handleUniqueResultQuery(query, qUser);
		}
		finally
		{
			//disable filter
			session.disableFilter("userDefaultUnitFilter");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findByPinCode(java.lang.String)
	 */
	@Override
	public List<CUser> findByPinCode (String pinCode) throws CSecurityException
	{
		if (pinCode == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QCUser qUser = QCUser.cUser;
		QCAuthenticationParams qAuthParams = QCAuthenticationParams.cAuthenticationParams;

		//create query
		JPAQuery query = new JPAQuery(getEntityManager());
		return query.distinct().from(qUser).leftJoin(qUser.organization).fetch().leftJoin(qUser.authenticationParams, qAuthParams).where(qAuthParams.pin.eq(pinCode)).list(qUser);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#countAll()
	 */
	@Override
	public long countAll ()
	{
		//create query
		JPAQuery query = new JPAQuery(getEntityManager());
		return query.from(QCUser.cUser).count();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao#findAll()
	 */
	@Override
	public List<CUser> findAll ()
	{
		COrderModel<EUserOrderByAttributeSpecifier> orderModel = new COrderModel<EUserOrderByAttributeSpecifier>();
		orderModel.getOrderSpecification().add(new COrderSpecification<IOrderByAttributeSpecifier>(EUserOrderByAttributeSpecifier.LOGIN, EOrderSpecifier.ASC));

		return findByUserAssociationsFilter(null, orderModel);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findByUnitAndGroup(sk.qbsw.core.security.model.domain.CUnit, sk.qbsw.core.security.model.domain.CGroup, sk.qbsw.core.security.model.order.COrderModel)
	 */
	@Override
	public List<CUser> findByUnitAndGroup (CUnit unit, CGroup group, COrderModel<? extends IOrderByAttributeSpecifier> orderModel) throws CSecurityException
	{
		if (unit == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}
		
		QCUser qUser = QCUser.cUser;
		QCXUserUnitGroup qUserUnitGroup = QCXUserUnitGroup.cXUserUnitGroup;
		QCGroup qGroup = QCGroup.cGroup;
		QCUnit qUnit = QCUnit.cUnit;

		//create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qUnit.eq(unit));
		
		if (group != null)
		{
			builder.and(qGroup.eq(group));
		}

		//create query
		JPAQuery query = new JPAQuery(getEntityManager()).distinct().from(qUser).leftJoin(qUser.xUserUnitGroups, qUserUnitGroup).fetch().leftJoin(qUserUnitGroup.unit, qUnit).fetch().leftJoin(qUserUnitGroup.group, qGroup).fetch().where(builder);

		//set order
		if (orderModel != null)
		{
			query = query.orderBy(orderModel.getOrderSpecifiers());
		}

		return query.list(qUser);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findByUserDetailFilter(sk.qbsw.core.security.model.filter.CUserDetailFilter, sk.qbsw.core.security.model.order.COrderModel)
	 */
	@Override
	public List<CUser> findByUserDetailFilter (CUserDetailFilter filter, COrderModel<? extends IOrderByAttributeSpecifier> orderModel)
	{
		//get hibernate session from entity manager to set filter
		Session session = getEntityManager().unwrap(Session.class);

		try
		{
			//set filter to get just groups with proper default units
			session.enableFilter("userDefaultUnitFilter");

			QCUser qUser = QCUser.cUser;
			QCXUserUnitGroup qUserUnitGroup = QCXUserUnitGroup.cXUserUnitGroup;
			QCGroup qGroup = QCGroup.cGroup;

			//create where condition
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

			//create query
			JPAQuery query = new JPAQuery(getEntityManager()).distinct().from(qUser).leftJoin(qUser.organization).leftJoin(qUser.defaultUnit).fetch().leftJoin(qUser.xUserUnitGroups, qUserUnitGroup).fetch().leftJoin(qUserUnitGroup.group, qGroup).fetch().leftJoin(qUserUnitGroup.unit).fetch().where(builder);

			//set order
			if (orderModel != null)
			{
				query = query.orderBy(orderModel.getOrderSpecifiers());
			}

			return query.list(qUser);
		}
		finally
		{
			//disable filter
			session.disableFilter("userDefaultUnitFilter");
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUserDao#findByUserAssociationsFilter(sk.qbsw.core.security.model.filter.CUserAssociationsFilter, sk.qbsw.core.security.model.order.COrderModel)
	 */
	@Override
	public List<CUser> findByUserAssociationsFilter (CUserAssociationsFilter filter, COrderModel<? extends IOrderByAttributeSpecifier> orderModel)
	{
		//get hibernate session from entity manager to set filter
		Session session = getEntityManager().unwrap(Session.class);

		try
		{
			//set filter to get just groups with proper default units
			session.enableFilter("userDefaultUnitFilter");

			QCUser qUser = QCUser.cUser;
			QCXUserUnitGroup qUserUnitGroup = QCXUserUnitGroup.cXUserUnitGroup;
			QCGroup qGroup = QCGroup.cGroup;
			QCRole qRole = QCRole.cRole;
			QCOrganization qOrganization = QCOrganization.cOrganization;

			//create where condition
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

			//create query
			JPAQuery query = new JPAQuery(getEntityManager()).distinct().from(qUser).leftJoin(qUser.organization, qOrganization).fetch().leftJoin(qUser.defaultUnit).fetch().leftJoin(qUser.xUserUnitGroups, qUserUnitGroup).fetch().leftJoin(qUserUnitGroup.group, qGroup).fetch().leftJoin(qUserUnitGroup.unit).fetch().leftJoin(qGroup.roles, qRole).fetch().where(builder);

			//set order
			if (orderModel != null)
			{
				query = query.orderBy(orderModel.getOrderSpecifiers());
			}

			return query.list(qUser);
		}
		finally
		{
			//disable filter
			session.disableFilter("userDefaultUnitFilter");
		}
	}
}
