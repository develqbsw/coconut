package sk.qbsw.security.oauth.db.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.QAccount;
import sk.qbsw.security.core.model.domain.QAccountUnitGroup;
import sk.qbsw.security.core.model.domain.QGroup;
import sk.qbsw.security.oauth.base.dao.MasterTokenDao;
import sk.qbsw.security.oauth.base.dao.MasterTokenJpaDaoBase;
import sk.qbsw.security.oauth.db.model.domain.MasterToken;
import sk.qbsw.security.oauth.db.model.domain.QMasterToken;

import java.util.List;

/**
 * The master token dao.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 1.13.1
 */
public class MasterTokenJpaDaoImpl extends MasterTokenJpaDaoBase<Account, MasterToken> implements MasterTokenDao<Account, MasterToken>
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
		return createQueryWithUser(super.findByAccountIdAndDeviceIdPredicate(accountId, deviceId)).fetchFirst();
	}

	@Override
	public MasterToken findByAccountIdAndToken (Long accountId, String token) throws CBusinessException
	{
		return createQueryWithUser(super.findByAccountIdAndTokenPredicate(accountId, token)).fetchFirst();
	}

	@Override
	public MasterToken findByAccountIdAndTokenAndDeviceId (Long accountId, String token, String deviceId) throws CBusinessException
	{
		return createQueryWithUser(super.findByAccountIdAndTokenAndDeviceIdPredicate(accountId, token, deviceId)).fetchFirst();
	}

	@Override
	public MasterToken findByTokenAndDeviceId (String token, String deviceId) throws CBusinessException
	{
		if (token == null || deviceId == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		return createQueryWithAll(super.findByTokenAndDeviceIdPredicate(token, deviceId)).fetchFirst();
	}

	@Override
	public List<MasterToken> findByExpireLimitOrChangeLimit (Integer expireLimit, Integer changeLimit)
	{
		return createQueryWithAll(super.findByExpireLimitOrChangeLimitPredicate(expireLimit, changeLimit)).fetch();
	}

	@Override
	public void remove (MasterToken token)
	{
		createDeleteClause(super.removePredicate(token)).execute();
	}

	@Override
	public Long removeByIds (List<Long> ids)
	{
		return createDeleteClause(super.removeByIdsPredicate(ids)).execute();
	}

	/**
	 * Create query with user jpa query.
	 *
	 * @param predicate the predicate
	 * @return the jpa query
	 */
	protected JPAQuery<MasterToken> createQueryWithUser (Predicate predicate)
	{
		QMasterToken qMasterToken = new QMasterToken(Q_VARIABLE_NAME);
		QAccount qAccount = QAccount.account;

		// create query
		return queryFactory.selectFrom(qMasterToken) //
			.leftJoin(qMasterToken.account, qAccount).fetchJoin() //
			.leftJoin(qAccount.user).fetchJoin() //
			.where(predicate);
	}

	/**
	 * Create query with all jpa query.
	 *
	 * @param predicate the predicate
	 * @return the jpa query
	 */
	protected JPAQuery<MasterToken> createQueryWithAll (Predicate predicate)
	{
		QMasterToken qMasterToken = new QMasterToken(Q_VARIABLE_NAME);
		QAccount qAccount = QAccount.account;
		QAccountUnitGroup qAccountUnitGroup = QAccountUnitGroup.accountUnitGroup;
		QGroup qGroup = QGroup.group;

		// create query
		return queryFactory.selectFrom(qMasterToken) //
			.leftJoin(qMasterToken.account, qAccount).fetchJoin() //
			.leftJoin(qAccount.organization).fetchJoin() //
			.leftJoin(qAccount.accountUnitGroups, qAccountUnitGroup).fetchJoin() //
			.leftJoin(qAccountUnitGroup.group, qGroup).fetchJoin() //
			.leftJoin(qGroup.roles).fetchJoin() //
			.leftJoin(qAccount.user).fetchJoin() //
			.where(predicate);
	}

	/**
	 * Create delete clause jpa delete clause.
	 *
	 * @param predicate the predicate
	 * @return the jpa delete clause
	 */
	protected JPADeleteClause createDeleteClause (Predicate predicate)
	{
		QMasterToken qMasterToken = new QMasterToken(Q_VARIABLE_NAME);

		return queryFactory.delete(qMasterToken) //
			.where(predicate);
	}
}
