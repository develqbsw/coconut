package sk.qbsw.et.browser.client.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import sk.qbsw.core.api.model.request.ARequest;
import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.client.model.filter.CFilterCriteriaTransferObject;
import sk.qbsw.et.browser.client.model.filter.CSortingCriteriaTransferObject;

/**
 * The abstract filter request.
 *
 * @param <F> the filterable type
 *
 * @author Peter Bozik
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CFilterRequest<F extends IFilterable>extends ARequest
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7751636869920682118L;

	/** The browser code. */
	@NotNull
	private String browserCode;

	/** The filter criteria - the default is empty object. */
	@Valid
	@NotNull
	private CFilterCriteriaTransferObject<F> filterCriteria = new CFilterCriteriaTransferObject<>();

	/** The ordering - the default is empty object */
	@Valid
	@NotNull
	private CSortingCriteriaTransferObject<F> sortingCriteria = new CSortingCriteriaTransferObject<>();

	/**
	 * Gets the browser code.
	 *
	 * @return the browser code
	 */
	public String getBrowserCode ()
	{
		return browserCode;
	}

	/**
	 * Sets the browser code.
	 *
	 * @param browserCode the new browser code
	 */
	public void setBrowserCode (String browserCode)
	{
		this.browserCode = browserCode;
	}

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
}
