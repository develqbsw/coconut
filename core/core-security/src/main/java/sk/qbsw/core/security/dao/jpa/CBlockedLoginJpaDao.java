package sk.qbsw.core.security.dao.jpa;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.AEntityJpaDao;
import sk.qbsw.core.security.dao.IBlockedLoginDao;
import sk.qbsw.core.security.model.domain.CBlockedLogin;
import sk.qbsw.core.security.model.domain.QCBlockedLogin;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * Blocked login DAO implementation.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.0
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
	 * @see sk.qbsw.core.security.dao.IBlockedLoginDao#findOneByLoginAndIp(java.lang.String, java.lang.String)
	 */
	@Override
	public CBlockedLogin findOneByLoginAndIp (String login, String ip) throws CSecurityException, NonUniqueResultException, NoResultException
	{
		//checks mandatory params
		if (login == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QCBlockedLogin qBlockedLogin = QCBlockedLogin.cBlockedLogin;

		//create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qBlockedLogin.login.eq(login));
		if (ip != null)
		{
			builder.and(qBlockedLogin.ip.eq(ip));
		}
		else
		{
			builder.and(qBlockedLogin.ip.isNull());
		}

		//create query
		JPAQuery query = new JPAQuery(getEntityManager()).from(qBlockedLogin).where(builder);
		return CJpaDaoHelper.handleUniqueResultQuery(query, qBlockedLogin);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.dao.IBlockedLoginDao#countCurrentlyBlockedByLoginAndIp(java.lang.String, java.lang.String)
	 */
	@Override
	public long countCurrentlyBlockedByLoginAndIp (String login, String ip) throws CSystemException
	{
		if (login == null)
		{
			throw new CSystemException("The mandatory parameter not found");
		}

		QCBlockedLogin qBlockedLogin = QCBlockedLogin.cBlockedLogin;

		//create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qBlockedLogin.login.eq(login));
		builder.and(qBlockedLogin.blockedFrom.lt(DateTime.now()));
		builder.and(qBlockedLogin.blockedTo.goe(DateTime.now()));
		if (ip != null)
		{
			builder.and(qBlockedLogin.ip.eq(ip));
		}

		//create query
		JPAQuery query = new JPAQuery(getEntityManager());
		return query.from(qBlockedLogin).where(builder).count();
	}
}
