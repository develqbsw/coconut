/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.core.security.dao;

import java.io.Serializable;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.core.security.model.domain.CAuthenticationParams;

/**
 * The Interface IAuthenticationParamsDao.
 *
 * @author Tomas Lauro
 * @version 1.12.2
 * @since 1.6.0
 */
public interface IAuthenticationParamsDao extends Serializable, IEntityDao<Long, CAuthenticationParams>
{
	/**
	 * Find by user id.
	 *
	 * @param userId the user id
	 * @return the authentication params
	 */
	public CAuthenticationParams findByUserId (Long userId);

	/**
	 * Find only valid authentication params by user id.
	 *
	 * @param userId the user id
	 * @return the c authentication params
	 */
	public CAuthenticationParams findValidByUserId (Long userId);
}
