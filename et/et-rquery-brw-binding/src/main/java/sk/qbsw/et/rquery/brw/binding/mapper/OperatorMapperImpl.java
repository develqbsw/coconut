package sk.qbsw.et.rquery.brw.binding.mapper;

import sk.qbsw.et.rquery.brw.client.model.Operator;
import sk.qbsw.et.rquery.core.exception.RQUnsupportedOperatorException;
import sk.qbsw.et.rquery.core.model.CoreOperator;

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
	public CoreOperator mapToCoreOperator (Operator operator)
	{
		switch (operator)
		{
			case EQ:
				return CoreOperator.EQ;
			case NE:
				return CoreOperator.NE;
			case GT:
				return CoreOperator.GT;
			case GOE:
				return CoreOperator.GOE;
			case LT:
				return CoreOperator.LT;
			case LOE:
				return CoreOperator.LOE;
			case IN:
				return CoreOperator.IN;
			case NOT_IN:
				return CoreOperator.NOT_IN;
			case LIKE:
				return CoreOperator.LIKE;
			case NOT_LIKE:
				return CoreOperator.NOT_LIKE;
			case LIKE_IGNORE_CASE:
				return CoreOperator.LIKE_IGNORE_CASE;
			default:
				throw new RQUnsupportedOperatorException("The operator not supported");
		}
	}
}
