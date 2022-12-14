package sk.qbsw.et.rquery.rsql.binding.mapper;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import sk.qbsw.et.rquery.core.exception.RQUnsupportedOperatorException;
import sk.qbsw.et.rquery.core.model.CoreOperator;
import sk.qbsw.et.rquery.core.model.DefaultCoreOperator;
import sk.qbsw.et.rquery.rsql.binding.operator.ExtendedRSQLOperators;

/**
 * The operator mapper implementation.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public class OperatorMapperImpl implements OperatorMapper
{
	@Override
	public CoreOperator mapToCoreOperator (ComparisonOperator operator)
	{
		if (ExtendedRSQLOperators.EQUAL.equals(operator))
		{
			return DefaultCoreOperator.EQ;
		}
		else if (ExtendedRSQLOperators.NOT_EQUAL.equals(operator))
		{
			return DefaultCoreOperator.NE;
		}
		else if (ExtendedRSQLOperators.GREATER_THAN.equals(operator))
		{
			return DefaultCoreOperator.GT;
		}
		else if (ExtendedRSQLOperators.GREATER_THAN_OR_EQUAL.equals(operator))
		{
			return DefaultCoreOperator.GOE;
		}
		else if (ExtendedRSQLOperators.LESS_THAN.equals(operator))
		{
			return DefaultCoreOperator.LT;
		}
		else if (ExtendedRSQLOperators.LESS_THAN_OR_EQUAL.equals(operator))
		{
			return DefaultCoreOperator.LOE;
		}
		else if (ExtendedRSQLOperators.IN.equals(operator))
		{
			return DefaultCoreOperator.IN;
		}
		else if (ExtendedRSQLOperators.NOT_IN.equals(operator))
		{
			return DefaultCoreOperator.NOT_IN;
		}
		else if (ExtendedRSQLOperators.LIKE.equals(operator))
		{
			return DefaultCoreOperator.LIKE;
		}
		else if (ExtendedRSQLOperators.NOT_LIKE.equals(operator))
		{
			return DefaultCoreOperator.NOT_LIKE;
		}
		else if (ExtendedRSQLOperators.LIKE_IGNORE_CASE.equals(operator))
		{
			return DefaultCoreOperator.LIKE_IGNORE_CASE;
		}
		else
		{
			throw new RQUnsupportedOperatorException();
		}
	}
}
