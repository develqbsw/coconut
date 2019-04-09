package sk.qbsw.et.rquery.brw.client.model.criteria;

import lombok.Getter;
import lombok.Setter;
import sk.qbsw.et.rquery.brw.client.model.Filterable;
import sk.qbsw.et.rquery.brw.client.model.NullPrecedence;
import sk.qbsw.et.rquery.brw.client.model.SortDirection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The sorting criteria.
 *
 * @param <F> the filterable model
 * @author Tomas Lauro
 * @version 2.2.0
 * @since 2.2.0
 */
@Getter
@Setter
public class SortingCriteria<F extends Filterable> implements Serializable
{
	private static final long serialVersionUID = -4509909409471709097L;

	private List<SortingCriterion<F>> criteria;

	/**
	 * Instantiates a new c filter criteria.
	 */
	public SortingCriteria ()
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
		criteria.add(new SortingCriterion<>(column));
	}

	/**
	 * Adds the filter criterion.
	 *
	 * @param column the column
	 * @param direction the direction
	 */
	public void addSortingCriterion (F column, SortDirection direction)
	{
		criteria.add(new SortingCriterion<>(column, direction));
	}

	/**
	 * Adds the sorting criterion.
	 *
	 * @param column the column
	 * @param direction the direction
	 * @param nullPrecedence the null precedence
	 */
	public void addSortingCriterion (F column, SortDirection direction, NullPrecedence nullPrecedence)
	{
		criteria.add(new SortingCriterion<>(column, direction, nullPrecedence));
	}
}
