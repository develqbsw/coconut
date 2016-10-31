package sk.qbsw.core.security.oauth.dao.jpa;

import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;

import sk.qbsw.core.base.exception.CBusinessException;
import sk.qbsw.core.base.exception.ECoreErrorResponse;
import sk.qbsw.core.persistence.dao.jpa.qdsl.AEntityQDslDao;
import sk.qbsw.core.security.oauth.dao.IMasterTokenDao;
import sk.qbsw.core.security.oauth.model.domain.CMasterToken;
import sk.qbsw.core.security.oauth.model.domain.QCMasterToken;

/**
 * The master token dao.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.14.2
 * @since 1.13.1
 *
 */
@Repository ("masterTokenJpaDao")
public class CMasterTokenJpaDao extends AEntityQDslDao<Long, CMasterToken> implements IMasterTokenDao
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6313012649405515609L;

	/**
	 * Instantiates a new c master token dao.
	 */
	public CMasterTokenJpaDao ()
	{
		super(QCMasterToken.cMasterToken, Long.class);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.dao.IMasterTokenDao#findByUserAndDevice(java.lang.Long, java.lang.String)
	 */
	@Override
	public CMasterToken findByUserAndDevice (Long userId, String deviceId) throws CBusinessException
	{
		if (userId == null || deviceId == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QCMasterToken qMasterToken = QCMasterToken.cMasterToken;

		//create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterToken.user.id.eq(userId));
		builder.and(qMasterToken.deviceId.eq(deviceId));

		//create query
		return queryFactory.selectFrom(qMasterToken).leftJoin(qMasterToken.user).fetchJoin().where(builder).fetchFirst();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.dao.IMasterTokenDao#findByUserAndToken(java.lang.Long, java.lang.String)
	 */
	@Override
	public CMasterToken findByUserAndToken (Long userId, String token) throws CBusinessException
	{
		if (userId == null || token == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QCMasterToken qMasterToken = QCMasterToken.cMasterToken;

		//create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterToken.user.id.eq(userId));
		builder.and(qMasterToken.token.eq(token));

		//create query
		return queryFactory.selectFrom(qMasterToken).leftJoin(qMasterToken.user).fetchJoin().where(builder).fetchFirst();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.dao.IMasterTokenDao#findByUserAndTokenAndDevice(java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public CMasterToken findByUserAndTokenAndDevice (Long userId, String token, String deviceId) throws CBusinessException
	{
		if (userId == null || token == null || deviceId == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QCMasterToken qMasterToken = QCMasterToken.cMasterToken;

		//create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterToken.user.id.eq(userId));
		builder.and(qMasterToken.token.eq(token));
		builder.and(qMasterToken.deviceId.eq(deviceId));

		//create query
		return queryFactory.selectFrom(qMasterToken).leftJoin(qMasterToken.user).fetchJoin().where(builder).fetchFirst();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.security.oauth.dao.IMasterTokenDao#findByTokenAndDeviceId(java.lang.String, java.lang.String)
	 */
	@Override
	public CMasterToken findByTokenAndDeviceId (String token, String deviceId) throws CBusinessException
	{
		if (token == null || deviceId == null)
		{
			throw new CBusinessException(ECoreErrorResponse.MISSING_MANDATORY_PARAMETERS);
		}

		QCMasterToken qMasterToken = QCMasterToken.cMasterToken;

		//create where condition
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qMasterToken.token.eq(token));
		builder.and(qMasterToken.deviceId.eq(deviceId));

		//create query
		return queryFactory.selectFrom(qMasterToken).leftJoin(qMasterToken.user).fetchJoin().where(builder).fetchFirst();
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.persistence.dao.querydsl.AEntityQueryDslDao#remove(sk.qbsw.core.persistence.model.domain.IEntity)
	 */
	@Override
	public void remove (CMasterToken token)
	{
		QCMasterToken qMasterToken = QCMasterToken.cMasterToken;

		queryFactory.delete(qMasterToken).where(qMasterToken.id.eq(token.getId())).execute();
	}
}
