/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.core.dao.jpa;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.security.core.dao.RoleDao;
import sk.qbsw.security.core.model.domain.*;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

/**
 * The role jpa dao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * @version 1.16.0
 * @since 1.0.0
 */
@Repository (value = "roleDao")
public class RoleJpaDao extends AEntityQDslDao<Long, Role> implements RoleDao
{
	/**
	 * Instantiates a new role jpa dao.
	 */
	public RoleJpaDao ()
	{
		super(QRole.role, Long.class);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IRoleDao#findByUser(sk.qbsw.security.core.core.model.domain.CUser)
	 */
	@Override
	public List<Role> findByUser (User user) throws CSecurityException
	{
		if (user == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QRole qRole = QRole.role;
		QGroup qGroup = QGroup.group;
		QUserUnitGroup qUserUnitGroup = QUserUnitGroup.userUnitGroup;
		QUser qUser = QUser.user;

		// create query
		JPAQuery<Role> query = queryFactory.selectFrom(qRole).distinct().join(qRole.groups, qGroup).join(qGroup.xUserUnitGroups, qUserUnitGroup).join(qUserUnitGroup.user, qUser).where(qUser.eq(user));
		return query.fetch();
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IRoleDao#findByCode(java.lang.String)
	 */
	@Deprecated
	@Override
	public List<Role> findByCode (String code)
	{
		QRole qRole = QRole.role;

		// create query
		JPAQuery<Role> query = queryFactory.selectFrom(qRole).where(qRole.code.eq(code));
		return query.fetch();
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.core.core.dao.IRoleDao#findOneByCode(java.lang.String)
	 */
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
