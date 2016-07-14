package sk.qbsw.core.persistence.dao.jpa.qdsl;

import javax.persistence.NoResultException;

import com.querydsl.core.NonUniqueResultException;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * The query dsl dao helper.
 * 
 * @author Tomas Lauro
 *
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
	 * @param <Q> the query entity type
	 * @param query the query
	 * @param queryEntity the query entity
	 * @return the result from query
	 * @throws NonUniqueResultException there is no unique result
	 * @throws NoResultException there is no result
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
}
