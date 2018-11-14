package sk.qbsw.security.core.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.core.base.exception.CSystemException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.persistence.dao.jpa.qdsl.CQDslDaoHelper;
import sk.qbsw.security.core.model.domain.BlockedLogin;
import sk.qbsw.security.core.model.domain.QBlockedLogin;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.time.OffsetDateTime;

/**
 * Blocked login DAO implementation.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.12.2
 */
public class BlockedLoginJpaDao extends AEntityQDslDao<Long, BlockedLogin> implements BlockedLoginDao
{
	/**
	 * Instantiates a new Blocked login jpa dao.
	 */
	public BlockedLoginJpaDao ()
	{
		super(QBlockedLogin.blockedLogin, Long.class);
	}

	@Override
	public BlockedLogin findOneByLoginAndIp (String login, String ip) throws CSecurityException, NonUniqueResultException, NoResultException
	{
		// checks mandatory params
		if (login == null)
		{
			throw new CSecurityException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QBlockedLogin qBlockedLogin = QBlockedLogin.blockedLogin;

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
		JPAQuery<BlockedLogin> query = queryFactory.selectFrom(qBlockedLogin).where(builder);
		return CQDslDaoHelper.handleUniqueResultQuery(query);
	}

	@Override
	public long countCurrentlyBlockedByLoginAndIp (String login, String ip) throws CSystemException
	{
		if (login == null)
		{
			throw new CSystemException("The mandatory parameter not found");
		}

		QBlockedLogin qBlockedLogin = QBlockedLogin.blockedLogin;

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
		JPAQuery<BlockedLogin> query = queryFactory.selectFrom(qBlockedLogin).where(builder);
		return query.fetchCount();
	}
}
