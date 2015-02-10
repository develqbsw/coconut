package sk.qbsw.core.security.dao.jpa;

import javax.persistence.Query;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.IBlockedLoginDao;
import sk.qbsw.core.security.model.domain.CBlockedLogin;

/**
 * Blocked login DAO implementation.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.12.2
 * @since 1.12.2
 */
@Repository (value = "blockedLoginJpaDao")
public class CBlockedLoginJpaDao extends AEntityJpaDao<Long, CBlockedLogin> implements IBlockedLoginDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new authentication black list jpa dao.
	 * 
	 */
	public CBlockedLoginJpaDao ()
	{
		super(CBlockedLogin.class);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IBlockedLoginDao#findByLoginAndIp(java.lang.String, java.lang.String)
	 */
	@Override
	public CBlockedLogin findByLoginAndIp (String login, String ip) throws CSystemException
	{
		if (login == null)
		{
			throw new CSystemException("The mandatory parameter not found");
		}

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select bl from CBlockedLogin bl where bl.login=:login");
		if (ip != null)
		{
			strQuery.append(" and bl.ip=:ip");
		}
		else
		{
			strQuery.append(" and bl.ip is null");
		}

		Query query = getEntityManager().createQuery(strQuery.toString());
		query.setParameter("login", login);
		if (ip != null)
		{
			query.setParameter("ip", ip);
		}

		return (CBlockedLogin) query.getSingleResult();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IBlockedLoginDao#countCurrentlyBlocked(java.lang.String, java.lang.String)
	 */
	@Override
	public long countCurrentlyBlocked (String login, String ip) throws CSystemException
	{
		if (login == null)
		{
			throw new CSystemException("The mandatory parameter not found");
		}

		StringBuilder strQuery = new StringBuilder();
		strQuery.append("select count(bl) from CBlockedLogin bl where bl.login=:login and bl.blockedFrom < :now and :now <= bl.blockedTo");
		if (ip != null)
		{
			strQuery.append(" and bl.ip=:ip");
		}

		Query query = getEntityManager().createQuery(strQuery.toString());
		query.setParameter("login", login);
		query.setParameter("now", DateTime.now());
		if (ip != null)
		{
			query.setParameter("ip", ip);
		}

		return (long) query.getSingleResult();
	}
}
