/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.core.security.dao.IRoleDao;
import sk.qbsw.core.security.model.domain.CRole;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.domain.QCGroup;
import sk.qbsw.core.security.model.domain.QCRole;
import sk.qbsw.core.security.model.domain.QCUser;
import sk.qbsw.core.security.model.domain.QCXUserUnitGroup;

/**
 * The role jpa dao.
 *
 * @author rosenberg
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.0.0
 */
@Repository (value = "roleDao")
public class CRoleJpaDao extends AEntityQDslDao<Long, CRole> implements IRoleDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new role jpa dao.
	 *
	 */
	public CRoleJpaDao ()
	{
		super(QCRole.cRole, Long.class);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IRoleDao#findByUser(sk.qbsw.core.security.model.domain.CUser)
	 */
	@Override
	public List<CRole> findByUser (CUser user) throws CSecurityException
	{
		if (user == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QCRole qRole = QCRole.cRole;
		QCGroup qGroup = QCGroup.cGroup;
		QCXUserUnitGroup qUserUnitGroup = QCXUserUnitGroup.cXUserUnitGroup;
		QCUser qUser = QCUser.cUser;

		//create query
		JPAQuery<CRole> query = queryFactory.selectFrom(qRole).distinct().join(qRole.groups, qGroup).join(qGroup.xUserUnitGroups, qUserUnitGroup).join(qUserUnitGroup.user, qUser).where(qUser.eq(user));
		return query.fetch();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IRoleDao#findByCode(java.lang.String)
	 */
	@Deprecated
	@Override
	public List<CRole> findByCode (String code)
	{
		QCRole qRole = QCRole.cRole;

		//create query
		JPAQuery<CRole> query = queryFactory.selectFrom(qRole).where(qRole.code.eq(code));
		return query.fetch();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IRoleDao#findOneByCode(java.lang.String)
	 */
	@Override
	public CRole findOneByCode (String code) throws NonUniqueResultException, NoResultException, CSecurityException
	{
		if (code == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QCRole qRole = QCRole.cRole;

		//create query
		JPAQuery<CRole> query = queryFactory.selectFrom(qRole).where(qRole.code.eq(code));
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}
}
