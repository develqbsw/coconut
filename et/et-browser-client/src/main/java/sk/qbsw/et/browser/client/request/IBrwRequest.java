package sk.qbsw.et.browser.client.request;

import sk.qbsw.et.browser.client.model.IFilterable;
import sk.qbsw.et.browser.client.model.filter.CPagingTransferObject;


/**
 * The brw request interface.
 * 
 * @author Tomas Lauro
 *
 * @version 1.16.0
 * @since 1.16.0
 */
public interface IBrwRequest<F extends IFilterable>extends IFilterRequest<F>
{
	/**
	 * Gets the paging.
	 *
	 * @return the paging
	 */
	CPagingTransferObject getPaging ();

	/**
	 * Sets the paging.
	 *
	 * @param paging the new paging
	 */
	void setPaging (CPagingTransferObject paging);
}
