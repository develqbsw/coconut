/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.IUnitDao;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;

/**
 * The Class CUnitJpaDao.
 *
 * @author Tomas Lauro
 * @version 1.6.0
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
	 * @see sk.qbsw.core.security.dao.IUnitDao#findByNameNull(java.lang.String)
	 */
	@SuppressWarnings ("unchecked")
	public CUnit findByName (String name)
	{
		String strQuery = "select distinct(un) from CUnit un left join fetch un.groups left join fetch un.organization where un.name = :name";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("name", name);

		List<CUnit> units = query.getResultList();

		if (units.isEmpty() || units.size() != 1)
		{
			return null;
		}
		else
		{
			return units.get(0);
		}
	}
	
	@SuppressWarnings ("unchecked")
	@Override
	public List<CUnit> findAllByUser(CUser user)
	{
		String strQuery = "select distinct(un) from CUnit un left join fetch un.users where :user in elements(un.users)";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("user", user);

		return query.getResultList();
	}
}
