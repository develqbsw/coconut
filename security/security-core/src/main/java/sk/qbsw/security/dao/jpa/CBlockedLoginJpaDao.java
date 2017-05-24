package sk.qbsw.security.dao.jpa;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.security.dao.IBlockedLoginDao;
import sk.qbsw.security.model.domain.CBlockedLogin;
import sk.qbsw.security.model.domain.QCBlockedLogin;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.time.OffsetDateTime;

/**
 * Blocked login DAO implementation.
 * 
 * @author Tomas Lauro
 * @version 1.16.0
 * @since 1.12.2
 */
@Repository (value = "blockedLoginJpaDao")
public class CBlockedLoginJpaDao extends AEntityQDslDao<Long, CBlockedLogin> implements IBlockedLoginDao
{
	/**
	 * Instantiates a new authentication black list jpa dao.
	 */
	public CBlockedLoginJpaDao ()
	{
		super(QCBlockedLogin.cBlockedLogin, Long.class);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.dao.IBlockedLoginDao#findOneByLoginAndIp(java.lang.String, java.lang.String)
	 */
	@Override
	public CBlockedLogin findOneByLoginAndIp (String login, String ip) throws CSecurityException, NonUniqueResultException, NoResultException
	{
		// checks mandatory params
		if (login == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QCBlockedLogin qBlockedLogin = QCBlockedLogin.cBlockedLogin;

		// create where condition
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

		// create query
		JPAQuery<CBlockedLogin> query = queryFactory.selectFrom(qBlockedLogin).where(builder);
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}

	/*
	 * (non-Javadoc)
	 * @see sk.qbsw.security.dao.IBlockedLoginDao#countCurrentlyBlockedByLoginAndIp(java.lang.String, java.lang.String)
	 */
	@Override
	public long countCurrentlyBlockedByLoginAndIp (String login, String ip) throws CSystemException
	{
		if (login == null)
		{
			throw new CSystemException("The mandatory parameter not found");
		}

		QCBlockedLogin qBlockedLogin = QCBlockedLogin.cBlockedLogin;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qBlockedLogin.login.eq(login));
		builder.and(qBlockedLogin.blockedFrom.lt(OffsetDateTime.now()));
		builder.and(qBlockedLogin.blockedTo.goe(OffsetDateTime.now()));
		if (ip != null)
		{
			builder.and(qBlockedLogin.ip.eq(ip));
		}

		// create query
		JPAQuery<CBlockedLogin> query = queryFactory.selectFrom(qBlockedLogin).where(builder);
		return query.fetchCount();
	}
}
