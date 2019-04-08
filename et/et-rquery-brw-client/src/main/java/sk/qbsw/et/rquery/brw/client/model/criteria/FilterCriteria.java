package sk.qbsw.et.rquery.brw.client.model.criteria;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.brw.client.model.LogicalOperator;
import sk.qbsw.et.rquery.brw.client.model.Operator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The filter criteria.
 *
 * @param <F> the filterable model
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
 */
@Getter
@Setter
public class FilterCriteria<F extends Filterable> implements Serializable
{
	private static final long serialVersionUID = -3042853921547434892L;

	private List<FilterCriterion<F>> criteria;

	/**
	 * Instantiates a new c filter criteria.
	 */
	public FilterCriteria ()
	{
		this.criteria = new ArrayList<>();
	}

	/**
	 * Adds the filter criterion.
	 *
	 * @param logicalOperator the logical operator
	 * @param column the column
	 * @param values the values
	 * @param operator the operator
	 */
	public void addFilterCriterion (LogicalOperator logicalOperator, F column, List<String> values, Operator operator)
	{
		criteria.add(new FilterCriterion<>(logicalOperator, column, values, operator));
	}
}
