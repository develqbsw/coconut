package sk.qbsw.et.rquery.brw.client.model.criteria;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.brw.client.model.LogicalOperator;
import sk.qbsw.et.rquery.brw.client.model.Operator;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * The filter criterion.
 *
 * @param <F> the property
 * @author Tomas Lauro
 * @version 2.1.0
 * @since 2.1.0
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

	private String value;

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
	 * @param value the value
	 * @param operator the operator
	 */
	public FilterCriterion (LogicalOperator logicalOperator, F variable, String value, Operator operator)
	{
		this.logicalOperator = logicalOperator;
		this.property = variable;
		this.value = value;
		this.operator = operator;
	}
}
