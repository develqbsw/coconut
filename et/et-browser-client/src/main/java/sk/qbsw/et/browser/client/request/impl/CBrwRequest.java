package sk.qbsw.et.browser.client.request.impl;

import javax.validation.Valid;

import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.client.model.filter.CPagingTransferObject;
import sk.qbsw.et.browser.client.request.IBrwRequest;

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
public class CBrwRequest<F extends IFilterable>extends CFilterRequest<F> implements IBrwRequest<F>
{
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8804636840815516951L;

	/** The paging. */
	@Valid
	private CPagingTransferObject paging;

	/**
	 * Gets the paging.
	 *
	 * @return the paging
	 */
	@Override
	public CPagingTransferObject getPaging ()
	{
		return paging;
	}

	/**
	 * Sets the paging.
	 *
	 * @param paging the new paging
	 */
	@Override
	public void setPaging (CPagingTransferObject paging)
	{
		this.paging = paging;
	}
}
