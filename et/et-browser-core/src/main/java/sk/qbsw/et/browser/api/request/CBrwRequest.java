package sk.qbsw.et.browser.api.request;

import javax.validation.Valid;

import sk.qbsw.core.api.model.request.ARequest;
import sk.qbsw.et.browser.api.filter.AFilterCriteria;
import sk.qbsw.et.browser.api.filter.COrdering;
import sk.qbsw.et.browser.api.filter.CPaging;

/**
 * The abstract brw request.
 *
 * @param <T> Type of filter criteria
 *
 * @author Peter Bozik
 * @author Tomas Lauro
 * 
 * @version 1.15.0
 * @since 1.15.0
 */
public class CBrwRequest<T extends AFilterCriteria>extends ARequest
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8804636840815516951L;

	/** The filter criteria. */
	@Valid
	private T filterCriteria;

	/** The ordering. */
	@Valid
	private COrdering ordering;

	/** The paging. */
	@Valid
	private CPaging paging;

	/**
	 * Gets the filter criteria.
	 *
	 * @return the filter criteria
	 */
	public T getFilterCriteria ()
	{
		return filterCriteria;
	}

	/**
	 * Sets the filter criteria.
	 *
	 * @param filterCriteria the new filter criteria
	 */
	public void setFilterCriteria (T filterCriteria)
	{
		this.filterCriteria = filterCriteria;
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
