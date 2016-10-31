package sk.qbsw.indy.base.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * List which stores only unique data by ID
 * 
 * @author Dalibor Rak
 * @version 1.0
 * @since 1.0
 * 
 * @param <T> holding model type
 */
public class CUniqueArrayList<T> extends ArrayList<T> implements IUniqueList<T>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Map of keys
	 */
	private List<Long> keys = new ArrayList<Long>();

	public boolean add (T object, Long identifier)
	{
		if (keys.contains(identifier))
		{
			return false;
		}

		keys.add(identifier);
			
		return super.add(object);
	}

	/**
	 * Clears all values
	 * 
	 * @see List#clear()
	 */
	public void clear ()
	{
		super.clear();
		keys.clear();
	}
}
