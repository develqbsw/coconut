package sk.qbsw.security.management.db.service;

import sk.qbsw.core.base.exception.CSecurityException;
import sk.qbsw.security.core.model.domain.Account;
import sk.qbsw.security.core.model.domain.Unit;

import java.util.List;

/**
 * The unit service.
 *
 * @author Tomas Lauro
 * @version 1.19.0
 * @since 1.6.0
 */
public interface UnitService
{
	/**
	 * Find all list.
	 *
	 * @return the list
	 */
	List<Unit> findAll ();

	/**
	 * Find by account list.
	 *
	 * @param account the account
	 * @return the list
	 * @throws CSecurityException the c security exception
	 */
	List<Unit> findByAccount (Account account) throws CSecurityException;

	/**
	 * Find by account list.
	 *
	 * @param accountId the account id
	 * @return the list
	 * @throws CSecurityException the c security exception
	 */
	List<Unit> findByAccountId (Long accountId) throws CSecurityException;
}
