package sk.qbsw.security.core.dao;

import com.querydsl.jpa.impl.JPAQuery;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.base.state.ActivityStates;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.security.core.model.domain.*;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

/**
 * The role jpa dao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * @author Michal Slezák
 * @version 2.5.0
 * @since 1.0.0
 */
public class RoleJpaDao extends AEntityQDslDao<Long, Role> implements RoleDao
{
	/**
	 * Instantiates a new Role jpa dao.
	 */
	public RoleJpaDao ()
	{
		super(QRole.role, Long.class);
	}

	@Override
	public List<Role> findByAccount (Account account) throws CSecurityException
	{
		if (account == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QRole qRole = QRole.role;
		QGroup qGroup = QGroup.group;
		QAccountUnitGroup qAccountUnitGroup = QAccountUnitGroup.accountUnitGroup;
		QAccount qAccount = QAccount.account;

		// create query
		JPAQuery<Role> query = queryFactory.selectFrom(qRole).distinct()
			.join(qRole.groups, qGroup)
			.join(qGroup.accountUnitGroups, qAccountUnitGroup)
			.join(qAccountUnitGroup.account, qAccount)
			.where(qAccount.eq(account).and(qGroup.state.eq(ActivityStates.ACTIVE)));
		return query.fetch();
	}

	@Override
	public Role findOneByCode (String code) throws NonUniqueResultException, NoResultException, CSecurityException
	{
		if (code == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QRole qRole = QRole.role;

		// create query
		JPAQuery<Role> query = queryFactory.selectFrom(qRole).where(qRole.code.eq(code));
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}
}
