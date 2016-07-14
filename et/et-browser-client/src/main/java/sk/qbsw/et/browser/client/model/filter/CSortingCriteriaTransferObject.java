/**
 * 
 */
package sk.qbsw.et.browser.client.model.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sk.qbsw.et.browser.client.model.IFilterable;

/**
 * The sorting criteria.
 *
 * @author Tomas Lauro
 * 
 * @param <F> the filterable model
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CSortingCriteriaTransferObject<F extends IFilterable> implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4509909409471709097L;

	/** The criteria. */
	private final List<CSortingCriterionTransferObject<F>> criteria;

	/**
	 * Instantiates a new c filter criteria.
	 */
	public CSortingCriteriaTransferObject ()
	{
		this.criteria = new ArrayList<>();
	}

	/**
	 * Adds the filter criterion.
	 *
	 * @param column the column
	 * @param value the value
	 * @param operator the operator
	 */
	public void addSortingCriterion (F column, ESortDirection direction)
	{
		criteria.add(new CSortingCriterionTransferObject<>(column, direction));
	}

	/**
	 * Gets the criteria.
	 *
	 * @return the criteria
	 */
	public List<CSortingCriterionTransferObject<F>> getCriteria ()
	{
		return criteria;
	}
}
