/*
 * Developed by QBSW a.s.
 */
package sk.qbsw.security.core.dao;

import sk.qbsw.core.persistence.dao.IEntityDao;
import sk.qbsw.security.core.model.domain.CAuthenticationParams;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

/**
 * The Interface IAuthenticationParamsDao.
 *
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.6.0
 */
public interface IAuthenticationParamsDao extends IEntityDao<Long, CAuthenticationParams>
{
	/**
	 * Find by user id.
	 *
	 * @param userId the user id
	 * @return the authentication params
	 * 
	 * @throws NonUniqueResultException there is no unique result
	 * @throws NoResultException there is no result
	 */
	CAuthenticationParams findOneByUserId (Long userId) throws NonUniqueResultException, NoResultException;

	/**
	 * Find only valid authentication params by user id.
	 *
	 * @param userId the user id
	 * @return the authentication params
	 * 
	 * @throws NonUniqueResultException there is no unique result
	 * @throws NoResultException there is no result
	 */
	CAuthenticationParams findOneValidByUserId (Long userId) throws NonUniqueResultException, NoResultException;
}
