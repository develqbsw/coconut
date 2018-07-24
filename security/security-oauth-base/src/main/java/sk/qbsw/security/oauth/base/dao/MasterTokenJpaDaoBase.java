package sk.qbsw.security.oauth.base.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EntityPathBase;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.oauth.base.model.domain.MasterTokenBase;
import sk.qbsw.security.oauth.base.model.domain.QMasterTokenBase;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * The master token dao.
 *
 * @param <A> the account type
 * @param <T> the token type
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.13.1
 */
public abstract class MasterTokenJpaDaoBase<A extends Account, T extends MasterTokenBase<A>>extends AEntityQDslDao<Long, T>
{
	/**
	 * The constant Q_VARIABLE_NAME.
	 */
	protected static final String Q_VARIABLE_NAME = "masterTokenBase";

	/**
	 * Instantiates a new Master token jpa dao base.
	 *
	 * @param entityPathCRUD the entity path crud
	 * @param primaryKeyClass the primary key class
	 */
	protected MasterTokenJpaDaoBase (EntityPathBase<T> entityPathCRUD, Class<Long> primaryKeyClass)
	{
		super(entityPathCRUD, primaryKeyClass);
	}

	/**
	 * Find by account id and device id predicate predicate.
	 *
	 * @param accountId the account id
	 * @param deviceId the device id
	 * @return the predicate
	 * @throws CBusinessException the c business exception
	 */
	protected Predicate findByAccountIdAndDeviceIdPredicate (Long accountId, String deviceId) throws CBusinessException
	{
		if (accountId == null || deviceId == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QMasterTokenBase qMasterTokenBase = new QMasterTokenBase(Q_VARIABLE_NAME);

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterTokenBase.account.id.eq(accountId));
		builder.and(qMasterTokenBase.deviceId.eq(deviceId));

		return builder;
	}

	/**
	 * Find by account id and token predicate predicate.
	 *
	 * @param accountId the account id
	 * @param token the token
	 * @return the predicate
	 * @throws CBusinessException the c business exception
	 */
	protected Predicate findByAccountIdAndTokenPredicate (Long accountId, String token) throws CBusinessException
	{
		if (accountId == null || token == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QMasterTokenBase qMasterTokenBase = new QMasterTokenBase(Q_VARIABLE_NAME);

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterTokenBase.account.id.eq(accountId));
		builder.and(qMasterTokenBase.token.eq(token));

		return builder;
	}

	/**
	 * Find by account id and token and device id predicate predicate.
	 *
	 * @param accountId the account id
	 * @param token the token
	 * @param deviceId the device id
	 * @return the predicate
	 * @throws CBusinessException the c business exception
	 */
	protected Predicate findByAccountIdAndTokenAndDeviceIdPredicate (Long accountId, String token, String deviceId) throws CBusinessException
	{
		if (accountId == null || token == null || deviceId == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QMasterTokenBase qMasterTokenBase = new QMasterTokenBase(Q_VARIABLE_NAME);

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterTokenBase.account.id.eq(accountId));
		builder.and(qMasterTokenBase.token.eq(token));
		builder.and(qMasterTokenBase.deviceId.eq(deviceId));

		return builder;
	}

	/**
	 * Find by token and device id predicate predicate.
	 *
	 * @param token the token
	 * @param deviceId the device id
	 * @return the predicate
	 * @throws CBusinessException the c business exception
	 */
	protected Predicate findByTokenAndDeviceIdPredicate (String token, String deviceId) throws CBusinessException
	{
		if (token == null || deviceId == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QMasterTokenBase qMasterTokenBase = new QMasterTokenBase(Q_VARIABLE_NAME);

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterTokenBase.token.eq(token));
		builder.and(qMasterTokenBase.deviceId.eq(deviceId));

		return builder;
	}

	/**
	 * Remove predicate predicate.
	 *
	 * @param token the token
	 * @return the predicate
	 */
	protected Predicate removePredicate (T token)
	{
		QMasterTokenBase qMasterTokenBase = new QMasterTokenBase(Q_VARIABLE_NAME);

		return qMasterTokenBase.id.eq(token.getId());
	}

	/**
	 * Find by expire limit or change limit predicate predicate.
	 *
	 * @param expireLimit the expire limit
	 * @param changeLimit the change limit
	 * @return the predicate
	 */
	protected Predicate findByExpireLimitOrChangeLimitPredicate (Integer expireLimit, Integer changeLimit)
	{
		QMasterTokenBase qMasterTokenBase = new QMasterTokenBase(Q_VARIABLE_NAME);

		// create where condition
		BooleanBuilder builder = new BooleanBuilder();
		if (expireLimit != null)
		{
			builder.or(qMasterTokenBase.lastAccessDate.before(OffsetDateTime.now().minusHours(expireLimit)));
		}
		if (changeLimit != null)
		{
			builder.or(qMasterTokenBase.createDate.before(OffsetDateTime.now().minusHours(changeLimit)));
		}

		return builder;
	}

	/**
	 * Remove by ids predicate predicate.
	 *
	 * @param ids the ids
	 * @return the predicate
	 */
	protected Predicate removeByIdsPredicate (List<Long> ids)
	{
		QMasterTokenBase qMasterTokenBase = new QMasterTokenBase(Q_VARIABLE_NAME);

		return qMasterTokenBase.id.in(ids);
	}
}
