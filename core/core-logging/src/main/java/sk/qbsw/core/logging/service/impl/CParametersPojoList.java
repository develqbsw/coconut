package sk.qbsw.core.logging.service.impl;

import java.util.List;

/**
 * java class to serialize java List
 * 
 * @author Michal Lacko
 *
 * @version 1.8.0
 * @version 1.8.0
 */
public class CParametersPojoList<T>
{
	private List<T> list;

	/**
	 * @return the list
	 */
	public List<T> getList ()
	{
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList (List<T> list)
	{
		this.list = list;
	}


}