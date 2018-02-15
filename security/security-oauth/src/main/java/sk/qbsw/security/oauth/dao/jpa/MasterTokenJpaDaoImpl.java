package sk.qbsw.security.oauth.dao.jpa;

import com.querydsl.core.BooleanBuilder;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.security.core.model.domain.QGroup;
import sk.qbsw.security.core.model.domain.QUser;
import sk.qbsw.security.core.model.domain.QUserUnitGroup;
import sk.qbsw.security.oauth.dao.MasterTokenDao;
import sk.qbsw.security.oauth.model.domain.MasterToken;
import sk.qbsw.security.oauth.model.domain.QMasterToken;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * The master token dao.
 * 
 * @author Tomas Lauro
 * @version 1.18.2
 * @since 1.13.1
 */
public class MasterTokenJpaDaoImpl extends AEntityQDslDao<Long, MasterToken> implements MasterTokenDao
{
	/**
	 * Instantiates a new c master token dao.
	 */
	public MasterTokenJpaDaoImpl ()
	{
		super(QMasterToken.masterToken, Long.class);
	}

	@Override
	public MasterToken findByUserAndDevice (Long userId, String deviceId) throws CBusinessException
	{
		if (userId == null || deviceId == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QMasterToken qMasterToken = QMasterToken.masterToken;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterToken.user.id.eq(userId));
		builder.and(qMasterToken.deviceId.eq(deviceId));

		// create query
		return queryFactory.selectFrom(qMasterToken).leftJoin(qMasterToken.user).fetchJoin().where(builder).fetchFirst();
	}

	@Override
	public MasterToken findByUserAndToken (Long userId, String token) throws CBusinessException
	{
		if (userId == null || token == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QMasterToken qMasterToken = QMasterToken.masterToken;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterToken.user.id.eq(userId));
		builder.and(qMasterToken.token.eq(token));

		// create query
		return queryFactory.selectFrom(qMasterToken) //
			.leftJoin(qMasterToken.user).fetchJoin() //
			.where(builder).fetchFirst();
	}

	@Override
	public MasterToken findByUserAndTokenAndDevice (Long userId, String token, String deviceId) throws CBusinessException
	{
		if (userId == null || token == null || deviceId == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QMasterToken qMasterToken = QMasterToken.masterToken;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterToken.user.id.eq(userId));
		builder.and(qMasterToken.token.eq(token));
		builder.and(qMasterToken.deviceId.eq(deviceId));

		// create query
		return queryFactory.selectFrom(qMasterToken) //
			.leftJoin(qMasterToken.user).fetchJoin() //
			.where(builder).fetchFirst();
	}

	@Override
	public MasterToken findByTokenAndDeviceId (String token, String deviceId) throws CBusinessException
	{
		if (token == null || deviceId == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QMasterToken qMasterToken = QMasterToken.masterToken;
		QUser qUser = QUser.user;
		QUserUnitGroup qUserUnitGroup = QUserUnitGroup.userUnitGroup;
		QGroup qGroup = QGroup.group;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterToken.token.eq(token));
		builder.and(qMasterToken.deviceId.eq(deviceId));

		// create query
		return queryFactory.selectFrom(qMasterToken) //
			.leftJoin(qMasterToken.user, qUser).fetchJoin() //
			.leftJoin(qUser.organization).fetchJoin() //
			.leftJoin(qUser.xUserUnitGroups, qUserUnitGroup).fetchJoin() //
			.leftJoin(qUserUnitGroup.group, qGroup).fetchJoin() //
			.leftJoin(qGroup.roles).fetchJoin() //
			.where(builder).fetchFirst();
	}

	@Override
	public void remove (MasterToken token)
	{
		QMasterToken qMasterToken = QMasterToken.masterToken;

		queryFactory.delete(qMasterToken).where(qMasterToken.id.eq(token.getId())).execute();
	}

	@Override
	public List<MasterToken> findByExpireLimitOrChangeLimit (Integer expireLimit, Integer changeLimit)
	{
		QMasterToken qMasterToken = QMasterToken.masterToken;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		if (expireLimit != null)
		{
			builder.or(qMasterToken.lastAccessDate.after(OffsetDateTime.now().minusHours(expireLimit)));
		}
		if (changeLimit != null)
		{
			builder.or(qMasterToken.createDate.after(OffsetDateTime.now().minusHours(changeLimit)));
		}

		// create query
		return queryFactory.selectFrom(qMasterToken).where(builder).fetch();
	}

	@Override
	public Long removeByIds (List<Long> ids)
	{
		QMasterToken qMasterToken = QMasterToken.masterToken;

		return queryFactory.delete(qMasterToken).where(qMasterToken.id.in(ids)).execute();
	}
}
