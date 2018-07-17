package sk.qbsw.security.organization.complex.oauth.dao;

import com.querydsl.core.types.Predicate;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.organization.core.model.domain.QUnit;
import sk.qbsw.organization.core.model.domain.QUser;
import sk.qbsw.security.core.model.domain.QAccountUnitGroup;
import sk.qbsw.security.core.model.domain.QGroup;
import sk.qbsw.security.oauth.base.dao.MasterTokenDao;
import sk.qbsw.security.oauth.base.dao.MasterTokenJpaDaoBase;
import sk.qbsw.security.organization.complex.core.model.domain.QUserAccount;
import sk.qbsw.security.organization.complex.core.model.domain.UserAccount;
import sk.qbsw.security.organization.complex.oauth.model.domain.MasterToken;
import sk.qbsw.security.organization.complex.oauth.model.domain.QMasterToken;

import java.util.List;

/**
 * The complex organization master token dao.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class MasterTokenJpaDaoImpl extends MasterTokenJpaDaoBase<UserAccount, MasterToken> implements MasterTokenDao<UserAccount, MasterToken>
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
		QUserAccount qAccount = QUserAccount.userAccount;
		QUser qUser = QUser.user;
		QUnit qUnit = QUnit.unit;
		QAccountUnitGroup qAccountUnitGroup = QAccountUnitGroup.accountUnitGroup;
		QGroup qGroup = QGroup.group;

		Predicate predicate = super.findByTokenAndDeviceIdPredicate(token, deviceId);

		// create query
		return queryFactory.selectFrom(qMasterToken) //
			.leftJoin(qMasterToken.account, qAccount).fetchJoin() //
			.leftJoin(qAccount.user, qUser).fetchJoin() //
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
