/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.configuration.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import sk.qbsw.core.configuration.dao.ISystemParameterDao;
import sk.qbsw.core.configuration.model.domain.CSystemParameter;
import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;

/**
 * The Class CSystemParameter
 *
 * @author Michal Lacko
 * @version 1.8.0
 * @since 1.8.0
 */
@Repository (value = "systemParameterDao")
public class CSystemParameterDao extends AEntityJpaDao<Long, CSystemParameter> implements ISystemParameterDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new unit jpa dao.
	 */
	public CSystemParameterDao ()
	{
		super(CSystemParameter.class);
	}


	@Override
	public CSystemParameter findByName (String name)
	{
		return findByName(name, new DateTime());
	}

	@Override
	public CSystemParameter findByName (String name, DateTime validDateTime)
	{
		CSystemParameter parameter;

		StringBuilder strQuery = new StringBuilder("select ap from CSystemParameter ap where ap.name = :name");

		if (validDateTime != null)
		{
			strQuery.append(" and (ap.validFromDate < :currentDate or ap.validFromDate IS NULL) and (ap.validToDate > :currentDate or ap.validToDate IS NULL)");
		}


		Query query = getEntityManager().createQuery(strQuery.toString());
		query.setParameter("name", name);

		if (validDateTime != null)
		{
			query.setParameter("currentDate", validDateTime);
		}

		try
		{
			parameter = (CSystemParameter) query.getSingleResult();
		}
		catch (NoResultException e)
		{
			parameter = null;
		}
		return parameter;
	}

	@Override
	public List<CSystemParameter> findByModule (String module)
	{
		return findByModule(module, new DateTime());
	}

	@Override
	@SuppressWarnings ("unchecked")
	public List<CSystemParameter> findByModule (String module, DateTime validDateTime)
	{
		StringBuilder strQuery = new StringBuilder("select ap from CSystemParameter ap where ap.module = :module ");

		if (validDateTime != null)
		{
			strQuery.append(" and (ap.validFromDate < :currentDate or ap.validFromDate IS NULL) and (ap.validToDate > :currentDate or ap.validToDate IS NULL)");
		}

		Query query = getEntityManager().createQuery(strQuery.toString());
		query.setParameter("module", module);

		if (validDateTime != null)
		{
			query.setParameter("currentDate", validDateTime);
		}

		return (List<CSystemParameter>) query.getResultList();
	}
}
