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
 * 
 * @param <F> the filterable model
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CFilterCriteriaTransferObject<F extends IFilterable> implements Serializable
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3042853921547434892L;

	/** The validator. */
	private IFilterCriteriaValidator<F> validator;

	/** The criteria. */
	private final List<CFilterCriterionTransferObject<F>> criteria;

	/**
	 * Instantiates a new c filter criteria.
	 */
	public CFilterCriteriaTransferObject ()
	{
		this.criteria = new ArrayList<>();
	}

	/**
	 * Instantiates a new c filter criteria.
	 *
	 * @param validator the validator
	 */
	public CFilterCriteriaTransferObject (IFilterCriteriaValidator<F> validator)
	{
		this();
		this.validator = validator;
	}

	/**
	 * Adds the filter criterion.
	 *
	 * @param column the column
	 * @param value the value
	 * @param operator the operator
	 */
	public void addFilterCriterion (F column, Serializable value, EOperator operator)
	{
		criteria.add(new CFilterCriterionTransferObject<>(column, value, operator));
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
	 * Checks if is valid.
	 *
	 * @return true, if is valid
	 */
	public boolean isValid ()
	{
		if (validator != null)
		{
			for (CFilterCriterionTransferObject<F> criterion : criteria)
			{
				if (!validator.validateCriterion(criterion))
				{
					return false;
				}
			}
		}

		return true;
	}
}
