package sk.qbsw.core.persistence.dao.querydsl;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.DslExpression;

/**
 * The query dsl dao helper.
 * 
 * @author Tomas Lauro
 *
 * @version 2.0.0
 * @since 2.0.0
 */
public class CQueryDslDaoHelper
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
	public static <R, Q extends DslExpression<R>>R handleUniqueResultQuery (JPAQuery query, Q queryEntity) throws NonUniqueResultException
	{
		try
		{
			//run query
			return query.uniqueResult(queryEntity);
		}
		catch (com.mysema.query.NonUniqueResultException ex)
		{
			throw new NonUniqueResultException();
		}
	}
}
