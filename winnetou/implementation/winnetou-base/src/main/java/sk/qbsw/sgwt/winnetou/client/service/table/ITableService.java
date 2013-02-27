package sk.qbsw.sgwt.winnetou.client.service.table;

import sk.qbsw.sgwt.winnetou.client.model.CFetchResult;
import sk.qbsw.sgwt.winnetou.client.model.table.IFilterCriteria;
import sk.qbsw.sgwt.winnetou.client.model.table.CTableRow;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Service interface for table browser component
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 * 
 */
public interface ITableService extends RemoteService
{
	/**
	 * Fetching of table data
	 * 
	 * @param startRow
	 *            starting record
	 * @param endRow
	 *            final record to read
	 * @return list of red data
	 */
	public CFetchResult<CTableRow> fetch(int startRow, int endRow, IFilterCriteria criteria);

	/**
	 * Adding new record
	 * 
	 * @param record
	 *            record to add
	 * @return added record
	 */
	public CTableRow add(CTableRow record);

	/**
	 * Update record
	 * 
	 * @param record
	 *            record to add
	 * @return added record
	 */
	public CTableRow update(CTableRow record);

	/**
	 * delete record
	 * 
	 * @param record
	 *            record to add
	 * @return added record
	 */
	public void remove(CTableRow record);
}
