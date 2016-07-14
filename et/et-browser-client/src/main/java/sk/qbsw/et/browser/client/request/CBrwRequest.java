package sk.qbsw.et.browser.client.request;

import javax.validation.Valid;

import sk.qbsw.core.api.model.request.ARequest;
import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.client.model.filter.CFilterCriteriaTransferObject;
import sk.qbsw.et.browser.client.model.filter.CPagingTransferObject;
import sk.qbsw.et.browser.client.model.filter.CSortingCriteriaTransferObject;

/**
 * The abstract brw request.
 *
 * @author Peter Bozik
 * @author Tomas Lauro
 * 
 * @param <F> the generic type
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CBrwRequest<F extends IFilterable>extends ARequest
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8804636840815516951L;

	/** The filter criteria. */
	@Valid
	private CFilterCriteriaTransferObject<F> filterCriteria;

	/** The ordering. */
	@Valid
	private CSortingCriteriaTransferObject<F> sortingCriteria;

	/** The paging. */
	@Valid
	private CPagingTransferObject paging;

	/**
	 * Gets the filter criteria.
	 *
	 * @return the filter criteria
	 */
	public CFilterCriteriaTransferObject<F> getFilterCriteria ()
	{
		return filterCriteria;
	}

	/**
	 * Sets the filter criteria.
	 *
	 * @param filterCriteria the new filter criteria
	 */
	public void setFilterCriteria (CFilterCriteriaTransferObject<F> filterCriteria)
	{
		this.filterCriteria = filterCriteria;
	}

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

	/* (non-Javadoc)
	 * @see sk.qbsw.core.api.model.request.ARequest#isValid()
	 */
	@Override
	public boolean isValid ()
	{
		if (getFilterCriteria() == null)
		{
			return super.isValid();
		}
		if (getFilterCriteria().isValid())
		{
			return super.isValid();
		}
		return false;
	}
}
