package sk.qbsw.security.organization.complex.oauth.db.dao;

import com.querydsl.core.types.Predicate;
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
import sk.qbsw.security.organization.complex.core.model.domain.QCXOUnit;
import sk.qbsw.security.organization.complex.core.model.domain.QCXOUser;

import java.util.List;

/**
 * The complex organization master token dao.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public class CXOMasterTokenJpaDaoImpl extends MasterTokenJpaDaoBase<Account, MasterToken> implements MasterTokenDao<Account, MasterToken>
{
	/**
	 * Instantiates a new Master token jpa dao.
	 */
	public CXOMasterTokenJpaDaoImpl ()
	{
		super(QMasterToken.masterToken, Long.class);
	}

	@Override
	public MasterToken findByAccountIdAndDeviceId (Long accountId, String deviceId) throws CBusinessException
	{
		QMasterToken qMasterToken = new QMasterToken(Q_VARIABLE_NAME);

		Predicate predicate = super.findByAccountIdAndDeviceIdPredicate(accountId, deviceId);

		// create query
		return queryFactory.selectFrom(qMasterToken) //
			.leftJoin(qMasterToken.account).fetchJoin() //
			.where(predicate) //
			.fetchFirst();
	}

	@Override
	public MasterToken findByAccountIdAndToken (Long accountId, String token) throws CBusinessException
	{
		QMasterToken qMasterToken = new QMasterToken(Q_VARIABLE_NAME);

		Predicate predicate = super.findByAccountIdAndTokenPredicate(accountId, token);

		// create query
		return queryFactory.selectFrom(qMasterToken) //
			.leftJoin(qMasterToken.account).fetchJoin() //
			.where(predicate) //
			.fetchFirst();
	}

	@Override
	public MasterToken findByAccountIdAndTokenAndDeviceId (Long accountId, String token, String deviceId) throws CBusinessException
	{
		QMasterToken qMasterToken = new QMasterToken(Q_VARIABLE_NAME);

		Predicate predicate = super.findByAccountIdAndTokenAndDeviceIdPredicate(accountId, token, deviceId);

		// create query
		return queryFactory.selectFrom(qMasterToken) //
			.leftJoin(qMasterToken.account).fetchJoin() //
			.where(predicate) //
			.fetchFirst();
	}

	@Override
	public MasterToken findByTokenAndDeviceId (String token, String deviceId) throws CBusinessException
	{
		if (token == null || deviceId == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QMasterToken qMasterToken = new QMasterToken(Q_VARIABLE_NAME);
		QAccount qAccount = QAccount.account;
		QCXOUser qUser = QCXOUser.cXOUser;
		QCXOUnit qUnit = QCXOUnit.cXOUnit;
		QAccountUnitGroup qAccountUnitGroup = QAccountUnitGroup.accountUnitGroup;
		QGroup qGroup = QGroup.group;

		Predicate predicate = super.findByTokenAndDeviceIdPredicate(token, deviceId);

		// create query
		return queryFactory.selectFrom(qMasterToken) //
			.leftJoin(qMasterToken.account, qAccount).fetchJoin() //
			.leftJoin(qAccount.user.as(QCXOUser.class), qUser).fetchJoin() //
			.leftJoin(qUser.units, qUnit).fetchJoin() //
			.leftJoin(qUnit.organization).fetchJoin() //
			.leftJoin(qAccount.accountUnitGroups, qAccountUnitGroup).fetchJoin() //
			.leftJoin(qAccountUnitGroup.group, qGroup).fetchJoin() //
			.leftJoin(qGroup.roles).fetchJoin() //
			.where(predicate) //
			.fetchFirst();
	}

	@Override
	public void remove (MasterToken token)
	{
		QMasterToken qMasterToken = new QMasterToken(Q_VARIABLE_NAME);

		Predicate predicate = super.removePredicate(token);

		queryFactory.delete(qMasterToken) //
			.where(predicate) //
			.execute();
	}

	@Override
	public List<MasterToken> findByExpireLimitOrChangeLimit (Integer expireLimit, Integer changeLimit)
	{
		QMasterToken qMasterToken = new QMasterToken(Q_VARIABLE_NAME);

		Predicate predicate = super.findByExpireLimitOrChangeLimitPredicate(expireLimit, changeLimit);

		// create query
		return queryFactory.selectFrom(qMasterToken) //
			.where(predicate) //
			.fetch();
	}

	@Override
	public Long removeByIds (List<Long> ids)
	{
		QMasterToken qMasterToken = new QMasterToken(Q_VARIABLE_NAME);

		Predicate predicate = super.removeByIdsPredicate(ids);

		return queryFactory.delete(qMasterToken) //
			.where(predicate) //
			.execute();
	}
}
