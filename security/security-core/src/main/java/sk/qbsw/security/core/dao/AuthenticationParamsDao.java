package sk.qbsw.security.core.dao;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.core.model.domain.AuthenticationParams;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

/**
 * The authentication params dao.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.6.0
 */
public interface AuthenticationParamsDao extends IEntityDao<Long, AuthenticationParams>
{
	/**
	 * Find one by account id authentication params.
	 *
	 * @param accountId the account id
	 * @return the authentication params
	 * @throws NonUniqueResultException the non unique result exception
	 * @throws NoResultException the no result exception
	 */
	AuthenticationParams findOneByAccountId (Long accountId) throws NonUniqueResultException, NoResultException;

	/**
	 * Find one valid by account id authentication params.
	 *
	 * @param accountId the account id
	 * @return the authentication params
	 * @throws NonUniqueResultException the non unique result exception
	 * @throws NoResultException the no result exception
	 */
	AuthenticationParams findOneValidByAccountId (Long accountId) throws NonUniqueResultException, NoResultException;
}
