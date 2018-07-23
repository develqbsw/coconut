package sk.qbsw.security.organization.simple.oauth.db.dao;

import com.querydsl.core.types.Predicate;
import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.QAccount;
import sk.qbsw.security.core.model.domain.QAccountUnitGroup;
import sk.qbsw.security.core.model.domain.QGroup;
import sk.qbsw.security.oauth.base.dao.AuthenticationTokenDao;
import sk.qbsw.security.oauth.base.dao.AuthenticationTokenJpaDaoBase;
import sk.qbsw.security.organization.simple.oauth.db.model.domain.AuthenticationToken;
import sk.qbsw.security.organization.simple.oauth.db.model.domain.QAuthenticationToken;

import java.util.List;

/**
 * The simple organization authentication token dao.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.19.0
 */
public class AuthenticationTokenJpaDaoImpl extends AuthenticationTokenJpaDaoBase<Account, AuthenticationToken> implements AuthenticationTokenDao<Account, AuthenticationToken>
{
	/**
	 * Instantiates a new Authentication token jpa dao.
	 */
	public AuthenticationTokenJpaDaoImpl ()
	{
		super(QAuthenticationToken.authenticationToken, Long.class);
	}

	@Override
	public AuthenticationToken findByAccountIdAndDeviceId (Long accountId, String deviceId) throws CBusinessException
	{
		QAuthenticationToken qAuthenticationToken = new QAuthenticationToken(Q_VARIABLE_NAME);

		Predicate predicate = super.findByAccountIdAndDeviceIdPredicate(accountId, deviceId);

		// create query
		return queryFactory.selectFrom(qAuthenticationToken) //
			.leftJoin(qAuthenticationToken.account).fetchJoin() //
			.where(predicate) //
			.fetchFirst();
	}

	@Override
	public AuthenticationToken findByAccountIdAndToken (Long accountId, String token) throws CBusinessException
	{
		QAuthenticationToken qAuthenticationToken = new QAuthenticationToken(Q_VARIABLE_NAME);

		Predicate predicate = super.findByAccountIdAndTokenPredicate(accountId, token);

		// create query
		return queryFactory.selectFrom(qAuthenticationToken) //
			.leftJoin(qAuthenticationToken.account).fetchJoin() //
			.where(predicate) //
			.fetchFirst();
	}

	@Override
	public AuthenticationToken findByTokenAndDeviceId (String token, String deviceId) throws CBusinessException
	{
		QAuthenticationToken qAuthenticationToken = new QAuthenticationToken(Q_VARIABLE_NAME);
		QAccount qAccount = QAccount.account;
		QAccountUnitGroup qAccountUnitGroup = QAccountUnitGroup.accountUnitGroup;
		QGroup qGroup = QGroup.group;

		Predicate predicate = super.findByTokenAndDeviceIdPredicate(token, deviceId);

		// create query
		return queryFactory.selectFrom(qAuthenticationToken) //
			.leftJoin(qAuthenticationToken.account, qAccount).fetchJoin() //
			.leftJoin(qAccount.organization).fetchJoin() //
			.leftJoin(qAccount.accountUnitGroups, qAccountUnitGroup).fetchJoin() //
			.leftJoin(qAccountUnitGroup.group, qGroup).fetchJoin() //
			.leftJoin(qGroup.roles).fetchJoin() //
			.where(predicate) //
			.fetchFirst();
	}

	@Override
	public void remove (AuthenticationToken token)
	{
		QAuthenticationToken qAuthenticationToken = new QAuthenticationToken(Q_VARIABLE_NAME);

		Predicate predicate = super.removePredicate(token);

		queryFactory.delete(qAuthenticationToken) //
			.where(predicate) //
			.execute();
	}

	@Override
	public List<AuthenticationToken> findByExpireLimitOrChangeLimit (Integer expireLimit, Integer changeLimit)
	{
		QAuthenticationToken qAuthenticationToken = new QAuthenticationToken(Q_VARIABLE_NAME);

		Predicate predicate = super.findByExpireLimitOrChangeLimitPredicate(expireLimit, changeLimit);

		// create query
		return queryFactory.selectFrom(qAuthenticationToken) //
			.where(predicate) //
			.fetch();
	}

	@Override
	public Long removeByIds (List<Long> ids)
	{
		QAuthenticationToken qAuthenticationToken = new QAuthenticationToken(Q_VARIABLE_NAME);

		Predicate predicate = super.removeByIdsPredicate(ids);

		return queryFactory.delete(qAuthenticationToken) //
			.where(predicate) //
			.execute();
	}
}
