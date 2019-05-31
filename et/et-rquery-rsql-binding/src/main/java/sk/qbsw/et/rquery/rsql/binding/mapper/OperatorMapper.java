package sk.qbsw.et.rquery.rsql.binding.mapper;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import sk.qbsw.et.rquery.core.model.CoreOperator;

/**
 * The operator mapper.
 *
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
public interface OperatorMapper
{
	/**
	 * Map to core operator core operator.
	 *
	 * @param operator the operator
	 * @return the core operator
	 */
	CoreOperator mapToCoreOperator (ComparisonOperator operator);
}
