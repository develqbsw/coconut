/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.configuration.dao.jpa;

import java.util.List;

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
		String strQuery = "select ap from CSystemParameter ap where ap.name = :name and ap.validFromDate < :currentDate and (ap.validToDate > :currentDate or ap.validToDate IS NULL)";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("name", name);
		query.setParameter("currentDate", validDateTime);

		return (CSystemParameter) query.getSingleResult();
	}

	@Override
	public List<CSystemParameter> findByModule (String module)
	{
		return findByModule(module, new DateTime());
	}
	
	@Override
	public List<CSystemParameter> findByModule (String module, DateTime validDateTime)
	{
		String strQuery = "select ap from CSystemParameter ap where ap.module = :module and ap.validFromDate < :currentDate and (ap.validToDate > :currentDate or ap.validToDate IS NULL)";

		Query query = getEntityManager().createQuery(strQuery);
		query.setParameter("module", module);
		query.setParameter("currentDate", validDateTime);

		return (List<CSystemParameter>) query.getResultList();
	}
}
