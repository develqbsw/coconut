package sk.qbsw.core.security.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.IXUserUnitGroupDao;
import sk.qbsw.core.security.model.domain.CUnit;
import sk.qbsw.core.security.model.domain.CUser;
import sk.qbsw.core.security.model.domain.CXUserUnitGroup;

/**
 * DAO for cross entities between user, unit and group
 * 
 * @author farkas.roman
 * @Since 1.7.0
 */
@Repository
public class CXUserUnitGroupDao  extends AEntityJpaDao<Long, CXUserUnitGroup> implements IXUserUnitGroupDao
{

	private static final long serialVersionUID = 1L;

	public CXUserUnitGroupDao ()
	{
		super(CXUserUnitGroup.class);
	}

	@SuppressWarnings ("unchecked")
	@Override
	public List<CXUserUnitGroup> findByUserNotInUnit (CUser user, CUnit unit)
	{
		String q = "select xuug from CXUserUnitGroup xuug left join fetch xuug.user us left join fetch xuug.unit un left join fetch xuug.group gr where 1=1";
		
		if(user != null)
		{
			q += " and us = :user";
		}
		if(unit != null)
		{
			q += " and un != :unit";
		}
		
		Query query = getEntityManager().createQuery(q);
		
		if(user != null)
		{
			query.setParameter("user", user);
		}
		if(unit != null)
		{
			query.setParameter("unit", unit);
		}
		
		return query.getResultList();
	}
}
