package sk.qbsw.core.persistence.dao.jpa.qdsl;

import com.querydsl.core.NonUniqueResultException;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.NoResultException;

/**
 * The query dsl dao helper.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 2.0.0
 */
public class CQDslDaoHelper
{
	/**
	 * Hide construction
	 */
	private CQDslDaoHelper ()
	{
	}

	/**
	 * Handle unique result query - if there is no result or no unique result throws an exception.
	 *
	 * @param <R> the return type from query
	 * @param query the query
	 * @return the result from query
	 * @throws NonUniqueResultException there is no unique result
	 */
	public static <R> R handleUniqueResultQuery (JPAQuery<R> query) throws NonUniqueResultException
	{
		try
		{
			R result = query.fetchOne();

			if (result != null)
			{
				return result;
			}
			else
			{
				throw new NoResultException();
			}
		}
		catch (NonUniqueResultException ex)
		{
			throw new javax.persistence.NonUniqueResultException();
		}
	}

	/**
	 * Handle unique result query by null r.
	 *
	 * @param <R> the type parameter
	 * @param query the query
	 * @return the r
	 * @throws NonUniqueResultException the non unique result exception
	 */
	public static <R> R handleUniqueResultQueryByNull (JPAQuery<R> query) throws NonUniqueResultException
	{
		try
		{
			return query.fetchOne();
		}
		catch (NonUniqueResultException ex)
		{
			throw new javax.persistence.NonUniqueResultException();
		}
	}
}
