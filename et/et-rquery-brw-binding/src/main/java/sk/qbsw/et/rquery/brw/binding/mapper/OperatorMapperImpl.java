package sk.qbsw.et.rquery.brw.binding.mapper;


import sk.qbsw.et.rquery.core.exception.RQUnsupportedOperatorException;
import sk.qbsw.et.rquery.core.model.CoreOperator;
import sk.qbsw.et.rquery.core.model.DefaultCoreOperator;

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
	public CoreOperator mapToCoreOperator (String operator)
	{
		try
		{
			return DefaultCoreOperator.valueOf(operator);
		}
		catch (Exception e)
		{
			throw new RQUnsupportedOperatorException("The operator not supported for operator=" + operator);
		}
	}
}
