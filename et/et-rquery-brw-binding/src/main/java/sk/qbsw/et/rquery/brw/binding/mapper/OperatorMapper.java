package sk.qbsw.et.rquery.brw.binding.mapper;

import sk.qbsw.et.rquery.brw.client.model.Operator;
import sk.qbsw.et.rquery.core.exception.RQUnsupportedOperatorException;
import sk.qbsw.et.rquery.core.model.CoreOperator;

/**
 * The operator mapper.
 *
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
public interface OperatorMapper
{
	/**
	 * Map to core operator core operator.
	 *
	 * @param operator the operator
	 * @return the core operator
	 * @throws RQUnsupportedOperatorException the rq unsupported operator exception
	 */
	CoreOperator mapToCoreOperator (Operator operator) throws RQUnsupportedOperatorException;
}
