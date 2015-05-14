package sk.qbsw.sgwt.winnetou.client.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Aleksandras Novikovas
 * @author System Tier
 * @version 1.0
 */
public class CFetchResult<T extends IFetchable> implements Serializable
{
	private static final long serialVersionUID = 3956589663371796129L;

	private Integer totalRows;
	private Integer startRow;
	private Integer endRow;
	private List<T> fetchedList;

	public Integer getTotalRows()
	{
		return totalRows;
	}

	public void setTotalRows(Integer totalCount)
	{
		this.totalRows = totalCount;
	}

	public Integer getStartRow()
	{
		return startRow;
	}

	public void setStartRow(Integer startRow)
	{
		this.startRow = startRow;
	}

	public Integer getEndRow()
	{
		return endRow;
	}

	public void setEndRow(Integer endRow)
	{
		this.endRow = endRow;
	}

	public List<T> getFetchedList()
	{
		return fetchedList;
	}

	public void setFetchedList(List<T> fetchedList)
	{
		this.fetchedList = fetchedList;
	}

}
