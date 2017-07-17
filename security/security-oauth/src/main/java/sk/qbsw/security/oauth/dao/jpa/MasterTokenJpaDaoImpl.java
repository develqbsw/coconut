package sk.qbsw.security.oauth.dao.jpa;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.security.core.model.domain.QUser;
import sk.qbsw.security.oauth.dao.MasterTokenDao;
import sk.qbsw.security.oauth.model.domain.MasterToken;
import sk.qbsw.security.oauth.model.domain.QMasterToken;

/**
 * The master token dao.
 * 
 * @author Tomas Lauro
 * @version 1.14.2
 * @since 1.13.1
 */
@Repository ("masterTokenJpaDao")
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
		QUser qUser = QUser.user;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterToken.user.id.eq(userId));
		builder.and(qMasterToken.token.eq(token));

		// create query
		return queryFactory.selectFrom(qMasterToken) //
			.leftJoin(qMasterToken.user, qUser).fetchJoin() //
			.leftJoin(qUser.organization).fetchJoin() //
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
		QUser qUser = QUser.user;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterToken.user.id.eq(userId));
		builder.and(qMasterToken.token.eq(token));
		builder.and(qMasterToken.deviceId.eq(deviceId));

		// create query
		return queryFactory.selectFrom(qMasterToken) //
			.leftJoin(qMasterToken.user, qUser).fetchJoin() //
			.leftJoin(qUser.organization).fetchJoin() //
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

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterToken.token.eq(token));
		builder.and(qMasterToken.deviceId.eq(deviceId));

		// create query
		return queryFactory.selectFrom(qMasterToken) //
			.leftJoin(qMasterToken.user, qUser).fetchJoin() //
			.leftJoin(qUser.organization).fetchJoin() //
			.where(builder).fetchFirst();
	}

	@Override
	public void remove (MasterToken token)
	{
		QMasterToken qMasterToken = QMasterToken.masterToken;

		queryFactory.delete(qMasterToken).where(qMasterToken.id.eq(token.getId())).execute();
	}
}
