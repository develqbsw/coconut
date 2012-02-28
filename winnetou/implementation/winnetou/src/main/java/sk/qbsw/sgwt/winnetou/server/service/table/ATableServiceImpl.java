package sk.qbsw.sgwt.winnetou.server.service.table;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import sk.qbsw.sgwt.winnetou.client.model.CFetchResult;
import sk.qbsw.sgwt.winnetou.client.model.table.IFilterCriteria;
import sk.qbsw.sgwt.winnetou.client.model.table.CTableRow;
import sk.qbsw.sgwt.winnetou.client.service.table.ITableService;

/**
 * Table Service base class. This abstract class should be overridden, child
 * should implement method generateData and can override .
 * 
 * @author Dalibor Rak
 * @version 0.1
 * @since 0.1
 */
public abstract class ATableServiceImpl implements ITableService
{

	/**
	 * Method used for reading data from DB.
	 * 
	 * @param startRow
	 *            index of starting row to read
	 * @param endRow
	 *            index of end row to read
	 * @return List of red data
	 */
	@Transactional (isolation = Isolation.READ_COMMITTED, readOnly = true)
	public final CFetchResult<CTableRow> fetch (int startRow, int endRow, IFilterCriteria criteria)
	{
		// int totalRows = getTotalRows();
		List<CTableRow> list = generateData(startRow, endRow - startRow, criteria);
		CFetchResult<CTableRow> ret = new CFetchResult<CTableRow>();
		ArrayList<CTableRow> listaRet = new ArrayList<CTableRow>();
		int i = 0;
		for (; i < endRow - startRow && i < list.size(); i++)
		{
			listaRet.add(list.get(i));
		}
		ret.setFetchedList(listaRet);
		ret.setStartRow(startRow);
		ret.setEndRow(i + startRow);
		ret.setTotalRows(getTotalRows(criteria));
		return ret;
	}

	/**
	 * Method used for generating data for the client side
	 * 
	 * @param startRow
	 *            index of start row to read
	 * @param count
	 *            index of last row to read
	 * @return List of red records
	 */
	protected abstract List<CTableRow> generateData (int startRow, int count, IFilterCriteria criteria);

	/**
	 * Gets count of rows which are available for displaying in the screen
	 * 
	 * @return
	 */
	protected abstract int getTotalRows (IFilterCriteria criteria);

	/**
	 * Does nothing
	 * 
	 * @see ITableService#add(CTableRow)
	 */
	public CTableRow add (CTableRow record)
	{
		return null;
	}

	/**
	 * Does nothing
	 * 
	 * @see ITableService#remove(CTableRow)
	 */
	public void remove (CTableRow record)
	{
		// nothing to do
	}

	/**
	 * Does nothing
	 * 
	 * @see ITableService#update(CTableRow)
	 */
	public CTableRow update (CTableRow record)
	{
		// nothing to do
		return null;
	}
}
