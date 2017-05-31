package sk.qbsw.security.oauth.dao.jpa;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.security.oauth.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.model.domain.QAuthenticationToken;

/**
 * The authentication token dao.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.14.2
 * @since 1.13.1
 *
 */
@Repository ("authenticationTokenJpaDao")
public class AuthenticationTokenJpaDaoImpl extends AEntityQDslDao<Long, AuthenticationToken> implements AuthenticationTokenDao
{
	
	/**
	 * Instantiates a new c authentication token dao.
	 */
	public AuthenticationTokenJpaDaoImpl ()
	{
		super(QAuthenticationToken.authenticationToken, Long.class);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.oauth.dao.IAuthenticationTokenDao#findByUserAndDevice(java.lang.Long, java.lang.String)
	 */
	@Override
	public AuthenticationToken findByUserAndDevice (Long userId, String deviceId) throws CBusinessException
	{
		if (userId == null || deviceId == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QAuthenticationToken qAuthenticationToken = QAuthenticationToken.authenticationToken;

		//create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qAuthenticationToken.user.id.eq(userId));
		builder.and(qAuthenticationToken.deviceId.eq(deviceId));

		//create query
		return queryFactory.selectFrom(qAuthenticationToken).leftJoin(qAuthenticationToken.user).fetchJoin().where(builder).fetchFirst();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.oauth.dao.IAuthenticationTokenDao#findByUserAndToken(java.lang.Long, java.lang.String)
	 */
	@Override
	public AuthenticationToken findByUserAndToken (Long userId, String token) throws CBusinessException
	{
		if (userId == null || token == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QAuthenticationToken qAuthenticationToken = QAuthenticationToken.authenticationToken;

		//create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qAuthenticationToken.user.id.eq(userId));
		builder.and(qAuthenticationToken.token.eq(token));

		//create query
		return queryFactory.selectFrom(qAuthenticationToken).leftJoin(qAuthenticationToken.user).fetchJoin().where(builder).fetchFirst();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.security.core.core.oauth.dao.IAuthenticationTokenDao#findByTokenAndDeviceId(java.lang.String, java.lang.String)
	 */
	@Override
	public AuthenticationToken findByTokenAndDeviceId (String token, String deviceId) throws CBusinessException
	{
		if (token == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QAuthenticationToken qAuthenticationToken = QAuthenticationToken.authenticationToken;

		//create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qAuthenticationToken.token.eq(token));
		builder.and(qAuthenticationToken.deviceId.eq(deviceId));

		//create query
		return queryFactory.selectFrom(qAuthenticationToken).leftJoin(qAuthenticationToken.user).fetchJoin().where(builder).fetchFirst();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.querydsl.AEntityQueryDslDao#remove(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public void remove (AuthenticationToken token)
	{
		QAuthenticationToken qAuthenticationToken = QAuthenticationToken.authenticationToken;

		queryFactory.delete(qAuthenticationToken).where(qAuthenticationToken.id.eq(token.getId())).execute();
	}
}
