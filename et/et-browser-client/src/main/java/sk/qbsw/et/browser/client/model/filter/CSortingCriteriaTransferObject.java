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
 * @param <F> the filterable model
 *
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CSortingCriteriaTransferObject<F extends IFilterable> implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4509909409471709097L;

	/** The criteria. */
	private List<CSortingCriterionTransferObject<F>> criteria;

	/**
	 * Instantiates a new c filter criteria.
	 */
	public CSortingCriteriaTransferObject ()
	{
		this.criteria = new ArrayList<>();
	}

	/**
	 * Adds the sorting criterion.
	 *
	 * @param column the column
	 */
	public void addSortingCriterion (F column)
	{
		criteria.add(new CSortingCriterionTransferObject<>(column));
	}

	/**
	 * Adds the filter criterion.
	 *
	 * @param column the column
	 * @param direction the direction
	 */
	public void addSortingCriterion (F column, ESortDirection direction)
	{
		criteria.add(new CSortingCriterionTransferObject<>(column, direction));
	}

	/**
	 * Adds the sorting criterion.
	 *
	 * @param column the column
	 * @param direction the direction
	 * @param nullPrecedence the null precedence
	 */
	public void addSortingCriterion (F column, ESortDirection direction, ENullPrecedence nullPrecedence)
	{
		criteria.add(new CSortingCriterionTransferObject<>(column, direction, nullPrecedence));
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

	/**
	 * Sets the criteria.
	 *
	 * @param criteria the new criteria
	 */
	public void setCriteria (List<CSortingCriterionTransferObject<F>> criteria)
	{
		this.criteria = criteria;
	}
}
