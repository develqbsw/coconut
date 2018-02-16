package sk.qbsw.security.oauth.dao.jpa;

import com.querydsl.core.BooleanBuilder;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.security.core.model.domain.QGroup;
import sk.qbsw.security.core.model.domain.QUser;
import sk.qbsw.security.core.model.domain.QUserUnitGroup;
import sk.qbsw.security.oauth.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.model.domain.AuthenticationToken;
import sk.qbsw.security.oauth.model.domain.QAuthenticationToken;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * The authentication token dao.
 * 
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.13.1
 */
public class AuthenticationTokenJpaDaoImpl extends AEntityQDslDao<Long, AuthenticationToken> implements AuthenticationTokenDao
{
	/**
	 * Instantiates a new c authentication token dao.
	 */
	public AuthenticationTokenJpaDaoImpl ()
	{
		super(QAuthenticationToken.authenticationToken, Long.class);
	}

	@Override
	public AuthenticationToken findByUserAndDevice (Long userId, String deviceId) throws CBusinessException
	{
		if (userId == null || deviceId == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QAuthenticationToken qAuthenticationToken = QAuthenticationToken.authenticationToken;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qAuthenticationToken.user.id.eq(userId));
		builder.and(qAuthenticationToken.deviceId.eq(deviceId));

		// create query
		return queryFactory.selectFrom(qAuthenticationToken) //
			.leftJoin(qAuthenticationToken.user).fetchJoin() //
			.where(builder).fetchFirst();
	}

	@Override
	public AuthenticationToken findByUserAndToken (Long userId, String token) throws CBusinessException
	{
		if (userId == null || token == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QAuthenticationToken qAuthenticationToken = QAuthenticationToken.authenticationToken;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qAuthenticationToken.user.id.eq(userId));
		builder.and(qAuthenticationToken.token.eq(token));

		// create query
		return queryFactory.selectFrom(qAuthenticationToken) //
			.leftJoin(qAuthenticationToken.user).fetchJoin() //
			.where(builder).fetchFirst();
	}

	@Override
	public AuthenticationToken findByTokenAndDeviceId (String token, String deviceId) throws CBusinessException
	{
		if (token == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QAuthenticationToken qAuthenticationToken = QAuthenticationToken.authenticationToken;
		QUser qUser = QUser.user;
		QUserUnitGroup qUserUnitGroup = QUserUnitGroup.userUnitGroup;
		QGroup qGroup = QGroup.group;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qAuthenticationToken.token.eq(token));
		builder.and(qAuthenticationToken.deviceId.eq(deviceId));

		// create query
		return queryFactory.selectFrom(qAuthenticationToken) //
			.leftJoin(qAuthenticationToken.user, qUser).fetchJoin() //
			.leftJoin(qUser.organization).fetchJoin() //
			.leftJoin(qUser.xUserUnitGroups, qUserUnitGroup).fetchJoin() //
			.leftJoin(qUserUnitGroup.group, qGroup).fetchJoin() //
			.leftJoin(qGroup.roles).fetchJoin() //
			.where(builder).fetchFirst();
	}

	@Override
	public void remove (AuthenticationToken token)
	{
		QAuthenticationToken qAuthenticationToken = QAuthenticationToken.authenticationToken;

		queryFactory.delete(qAuthenticationToken).where(qAuthenticationToken.id.eq(token.getId())).execute();
	}

	@Override
	public List<AuthenticationToken> findByExpireLimitOrChangeLimit (Integer expireLimit, Integer changeLimit)
	{
		QAuthenticationToken qAuthenticationToken = QAuthenticationToken.authenticationToken;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		if (expireLimit != null)
		{
			builder.or(qAuthenticationToken.lastAccessDate.before(OffsetDateTime.now().minusHours(expireLimit)));
		}
		if (changeLimit != null)
		{
			builder.or(qAuthenticationToken.createDate.before(OffsetDateTime.now().minusHours(changeLimit)));
		}

		// create query
		return queryFactory.selectFrom(qAuthenticationToken).where(builder).fetch();
	}

	@Override
	public Long removeByIds (List<Long> ids)
	{
		QAuthenticationToken qAuthenticationToken = QAuthenticationToken.authenticationToken;

		return queryFactory.delete(qAuthenticationToken).where(qAuthenticationToken.id.in(ids)).execute();
	}
}
