/**
 * 
 */
package sk.qbsw.et.browser.client.model.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sk.qbsw.et.browser.client.model.IFilterable;

/**
 * The filter criteria class.
 *
 * @author Tomas Lauro
 * @version 1.16.0
 * @param <F> the filterable model
 * @since 1.16.0
 */
public class CFilterCriteriaTransferObject<F extends IFilterable> implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3042853921547434892L;

	/** The criteria. */
	private List<CFilterCriterionTransferObject<F>> criteria;

	/**
	 * Instantiates a new c filter criteria.
	 */
	public CFilterCriteriaTransferObject ()
	{
		this.criteria = new ArrayList<>();
	}

	/**
	 * Adds the filter criterion.
	 *
	 * @param column the column
	 * @param stringValue the string value
	 * @param numberValue the number value
	 * @param operator the operator
	 */
	public void addFilterCriterion (ELogicalOperator logicalOperator, F column, String stringValue, Number numberValue, EOperator operator)
	{
		criteria.add(new CFilterCriterionTransferObject<>(logicalOperator, column, stringValue, numberValue, operator));
	}

	/**
	 * Gets the criteria.
	 *
	 * @return the criteria
	 */
	public List<CFilterCriterionTransferObject<F>> getCriteria ()
	{
		return criteria;
	}

	/**
	 * Sets the criteria.
	 *
	 * @param criteria the new criteria
	 */
	public void setCriteria (List<CFilterCriterionTransferObject<F>> criteria)
	{
		this.criteria = criteria;
	}
}
