package sk.qbsw.et.browser.client.request;

import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.client.model.filter.CFilterCriteriaTransferObject;
import sk.qbsw.et.browser.client.model.filter.CSortingCriteriaTransferObject;


/**
 * The filter request
 * 
 * @author Tomas Lauro
 *
 * @version 1.16.0
 * @since 1.16.0
 */
public interface IFilterRequest<F extends IFilterable>
{
	/**
	 * Gets the browser code.
	 *
	 * @return the browser code
	 */
	String getBrowserCode ();

	/**
	 * Sets the browser code.
	 *
	 * @param browserCode the new browser code
	 */
	void setBrowserCode (String browserCode);

	/**
	 * Gets the filter criteria.
	 *
	 * @return the filter criteria
	 */
	CFilterCriteriaTransferObject<F> getFilterCriteria ();

	/**
	 * Sets the filter criteria.
	 *
	 * @param filterCriteria the new filter criteria
	 */
	void setFilterCriteria (CFilterCriteriaTransferObject<F> filterCriteria);

	/**
	 * Gets the sorting criteria.
	 *
	 * @return the sorting criteria
	 */
	CSortingCriteriaTransferObject<F> getSortingCriteria ();

	/**
	 * Sets the sorting criteria.
	 *
	 * @param sortingCriteria the new sorting criteria
	 */
	void setSortingCriteria (CSortingCriteriaTransferObject<F> sortingCriteria);
}
