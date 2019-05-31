package sk.qbsw.et.rquery.brw.client.model.criteria;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.brw.client.model.LogicalOperator;
import sk.qbsw.et.rquery.brw.client.model.Operator;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The filter criterion.
 *
 * @param <F> the property
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
@Getter
@Setter
public class FilterCriterion<F extends Filterable> implements Serializable
{
	private static final long serialVersionUID = -5338691866901178569L;

	@NotNull
	private LogicalOperator logicalOperator;

	@NotNull
	private F property;

	private List<String> values = new ArrayList<>();

	@NotNull
	private Operator operator;

	/**
	 * Instantiates a new Filter criterion.
	 */
	public FilterCriterion ()
	{
		// default constuctor
	}

	/**
	 * Instantiates a new Filter criterion.
	 *
	 * @param logicalOperator the logical operator
	 * @param variable the variable
	 * @param values the values
	 * @param operator the operator
	 */
	public FilterCriterion (LogicalOperator logicalOperator, F variable, List<String> values, Operator operator)
	{
		this.logicalOperator = logicalOperator;
		this.property = variable;
		this.values = values;
		this.operator = operator;
	}
}
