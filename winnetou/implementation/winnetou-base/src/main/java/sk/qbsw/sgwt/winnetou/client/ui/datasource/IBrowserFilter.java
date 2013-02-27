package sk.qbsw.sgwt.winnetou.client.ui.datasource;

import sk.qbsw.sgwt.winnetou.client.model.table.IFilterCriteria;

/**
 * Interface used for adding additional criteria to browser query
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public interface IBrowserFilter
{
	/**
	 * Gets filtering criteria from the component. Used to pass them to RPC call
	 * 
	 * @return criteria
	 */
	public IFilterCriteria getFilterCriteria();
}
