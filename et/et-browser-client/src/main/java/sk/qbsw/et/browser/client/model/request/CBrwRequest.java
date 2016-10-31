package sk.qbsw.et.browser.client.model.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.client.model.filter.CPagingTransferObject;

/**
 * The abstract brw request.
 *
 * @param <F> the filterable type
 *
 * @author Peter Bozik
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CBrwRequest<F extends IFilterable>extends CFilterRequest<F>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8804636840815516951L;

	/** The paging - with default empty object. */
	@Valid
	@NotNull
	private CPagingTransferObject paging = new CPagingTransferObject();

	/**
	 * Gets the paging.
	 *
	 * @return the paging
	 */
	public CPagingTransferObject getPaging ()
	{
		return paging;
	}

	/**
	 * Sets the paging.
	 *
	 * @param paging the new paging
	 */
	public void setPaging (CPagingTransferObject paging)
	{
		this.paging = paging;
	}
}
