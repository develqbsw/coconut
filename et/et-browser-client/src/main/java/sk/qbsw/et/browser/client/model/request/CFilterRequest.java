package sk.qbsw.et.browser.client.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import sk.qbsw.core.api.model.request.ARequest;
import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.client.model.filter.CFilterCriteriaTransferObject;

/**
 * The filter request.
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
	private static final long serialVersionUID = 2184850074033502510L;

	/** The filter criteria - the default is empty object. */
	@Valid
	@NotNull
	private CFilterCriteriaTransferObject<F> filterCriteria = new CFilterCriteriaTransferObject<>();

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
}
