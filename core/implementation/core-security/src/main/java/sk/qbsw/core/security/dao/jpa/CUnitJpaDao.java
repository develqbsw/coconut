/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.QCUnit;
import sk.qbsw.core.security.model.domain.QCUser;
import sk.qbsw.core.security.model.domain.QCXUserUnitGroup;

import com.mysema.query.jpa.impl.JPAQuery;

/**
 * The unit jpa dao.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.6.0
 */
@Repository (value = "unitDao")
public class CUnitJpaDao extends AEntityJpaDao<Long, CUnit> implements IUnitDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new unit jpa dao.
	 */
	public CUnitJpaDao ()
	{
		super(CUnit.class);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUnitDao#findOneByName(java.lang.String)
	 */
	@Override
	public CUnit findOneByName (String name) throws NoResultException
	{
		QCUnit qUnit = QCUnit.cUnit;

		//create query
		JPAQuery query = new JPAQuery(getEntityManager()).distinct().from(qUnit).leftJoin(qUnit.groups).fetch().leftJoin(qUnit.organization).fetch().where(qUnit.name.eq(name));
		return CJpaDaoHelper.handleUniqueResultQuery(query, qUnit);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IUnitDao#findByUserId(java.lang.Long)
	 */
	@Override
	public List<CUnit> findByUserId (Long userId)
	{
		QCUnit qUnit = QCUnit.cUnit;
		QCXUserUnitGroup qUserUnitGroup = QCXUserUnitGroup.cXUserUnitGroup;
		QCUser qUser = QCUser.cUser;

		//create query
		JPAQuery query = new JPAQuery(getEntityManager());
		return query.distinct().from(qUnit).join(qUnit.xUserUnitGroups, qUserUnitGroup).join(qUserUnitGroup.user, qUser).where(qUser.id.eq(userId)).orderBy(qUnit.name.asc()).list(qUnit);
	}
}
