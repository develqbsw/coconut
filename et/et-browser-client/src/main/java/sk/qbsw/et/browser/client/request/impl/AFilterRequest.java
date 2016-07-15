package sk.qbsw.et.browser.client.request.impl;

import javax.validation.Valid;

import sk.qbsw.core.api.model.request.ARequest;
import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.client.model.filter.CFilterCriteriaTransferObject;
import sk.qbsw.et.browser.client.model.filter.CSortingCriteriaTransferObject;
import sk.qbsw.et.browser.client.request.IFilterRequest;

/**
 * The abstract filter request.
 *
 * @author Peter Bozik
 * @author Tomas Lauro
 * 
 * @param <F> the filterable type
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public abstract class AFilterRequest<F extends IFilterable>extends ARequest implements IFilterRequest<F>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7751636869920682118L;

	/** The filter criteria. */
	@Valid
	private CFilterCriteriaTransferObject<F> filterCriteria;

	/** The ordering. */
	@Valid
	private CSortingCriteriaTransferObject<F> sortingCriteria;

	/**
	 * Gets the filter criteria.
	 *
	 * @return the filter criteria
	 */
	@Override
	public CFilterCriteriaTransferObject<F> getFilterCriteria ()
	{
		return filterCriteria;
	}

	/**
	 * Sets the filter criteria.
	 *
	 * @param filterCriteria the new filter criteria
	 */
	@Override
	public void setFilterCriteria (CFilterCriteriaTransferObject<F> filterCriteria)
	{
		this.filterCriteria = filterCriteria;
	}

	/**
	 * Gets the sorting criteria.
	 *
	 * @return the sorting criteria
	 */
	@Override
	public CSortingCriteriaTransferObject<F> getSortingCriteria ()
	{
		return sortingCriteria;
	}

	/**
	 * Sets the sorting criteria.
	 *
	 * @param sortingCriteria the new sorting criteria
	 */
	@Override
	public void setSortingCriteria (CSortingCriteriaTransferObject<F> sortingCriteria)
	{
		this.sortingCriteria = sortingCriteria;
	}
}
