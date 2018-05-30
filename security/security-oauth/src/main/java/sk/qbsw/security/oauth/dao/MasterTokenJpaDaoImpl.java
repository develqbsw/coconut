package sk.qbsw.security.oauth.dao;

import com.querydsl.core.BooleanBuilder;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.security.core.model.domain.QAccount;
import sk.qbsw.security.core.model.domain.QAccountUnitGroup;
import sk.qbsw.security.core.model.domain.QGroup;
import sk.qbsw.security.oauth.model.domain.MasterToken;
import sk.qbsw.security.oauth.model.domain.QMasterToken;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * The master token dao.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.1
 */
public class MasterTokenJpaDaoImpl extends AEntityQDslDao<Long, MasterToken> implements MasterTokenDao
{
	/**
	 * Instantiates a new Master token jpa dao.
	 */
	public MasterTokenJpaDaoImpl ()
	{
		super(QMasterToken.masterToken, Long.class);
	}

	@Override
	public MasterToken findByAccountIdAndDeviceId (Long accountId, String deviceId) throws CBusinessException
	{
		if (accountId == null || deviceId == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QMasterToken qMasterToken = QMasterToken.masterToken;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterToken.account.id.eq(accountId));
		builder.and(qMasterToken.deviceId.eq(deviceId));

		// create query
		return queryFactory.selectFrom(qMasterToken).leftJoin(qMasterToken.account).fetchJoin().where(builder).fetchFirst();
	}

	@Override
	public MasterToken findByAccountIdAndToken (Long accountId, String token) throws CBusinessException
	{
		if (accountId == null || token == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QMasterToken qMasterToken = QMasterToken.masterToken;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterToken.account.id.eq(accountId));
		builder.and(qMasterToken.token.eq(token));

		// create query
		return queryFactory.selectFrom(qMasterToken) //
			.leftJoin(qMasterToken.account).fetchJoin() //
			.where(builder).fetchFirst();
	}

	@Override
	public MasterToken findByAccountIdAndTokenAndDeviceId (Long accountId, String token, String deviceId) throws CBusinessException
	{
		if (accountId == null || token == null || deviceId == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QMasterToken qMasterToken = QMasterToken.masterToken;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterToken.account.id.eq(accountId));
		builder.and(qMasterToken.token.eq(token));
		builder.and(qMasterToken.deviceId.eq(deviceId));

		// create query
		return queryFactory.selectFrom(qMasterToken) //
			.leftJoin(qMasterToken.account).fetchJoin() //
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
		QAccount qAccount = QAccount.account;
		QAccountUnitGroup qAccountUnitGroup = QAccountUnitGroup.accountUnitGroup;
		QGroup qGroup = QGroup.group;

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterToken.token.eq(token));
		builder.and(qMasterToken.deviceId.eq(deviceId));

		// create query
		return queryFactory.selectFrom(qMasterToken) //
			.leftJoin(qMasterToken.account, qAccount).fetchJoin() //
			.leftJoin(qAccount.organization).fetchJoin() //
			.leftJoin(qAccount.accountUnitGroups, qAccountUnitGroup).fetchJoin() //
			.leftJoin(qAccountUnitGroup.group, qGroup).fetchJoin() //
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
			builder.or(qMasterToken.lastAccessDate.before(OffsetDateTime.now().minusHours(expireLimit)));
		}
		if (changeLimit != null)
		{
			builder.or(qMasterToken.createDate.before(OffsetDateTime.now().minusHours(changeLimit)));
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
