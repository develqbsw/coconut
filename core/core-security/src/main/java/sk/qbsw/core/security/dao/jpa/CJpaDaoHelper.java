package sk.qbsw.core.security.dao.jpa;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;

/**
 * The helper for dao classes.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.13.0
 */
class CJpaDaoHelper
{
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
	public static <R, Q extends EntityPathBase<R>>R handleUniqueResultQuery (JPAQuery query, Q queryEntity) throws NonUniqueResultException, NoResultException
	{
		try
		{
			//run query
			R result = query.uniqueResult(queryEntity);

			if (result != null)
			{
				return result;
			}
			else
			{
				throw new NoResultException();
			}
		}
		catch (com.mysema.query.NonUniqueResultException ex)
		{
			throw new NonUniqueResultException();
		}
	}
}
