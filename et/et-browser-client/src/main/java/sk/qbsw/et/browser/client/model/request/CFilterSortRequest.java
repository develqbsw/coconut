package sk.qbsw.et.browser.client.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.client.model.filter.CSortingCriteriaTransferObject;

/**
 * The filter and sort request.
 *
 * @param <F> the filterable type
 *
 * @author Peter Bozik
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CFilterSortRequest<F extends IFilterable>extends CFilterRequest<F>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7751636869920682118L;

	/** The ordering - the default is empty object */
	@Valid
	@NotNull
	private CSortingCriteriaTransferObject<F> sortingCriteria = new CSortingCriteriaTransferObject<>();

	/**
	 * Gets the sorting criteria.
	 *
	 * @return the sorting criteria
	 */
	public CSortingCriteriaTransferObject<F> getSortingCriteria ()
	{
		return sortingCriteria;
	}

	/**
	 * Sets the sorting criteria.
	 *
	 * @param sortingCriteria the new sorting criteria
	 */
	public void setSortingCriteria (CSortingCriteriaTransferObject<F> sortingCriteria)
	{
		this.sortingCriteria = sortingCriteria;
	}
}
