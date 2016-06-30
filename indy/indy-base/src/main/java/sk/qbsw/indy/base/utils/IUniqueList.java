package sk.qbsw.indy.base.utils;

import java.util.List;

/**
 * Unique list interface
 * @author Dalibor Rak
 * @version 1.0.0
 * @since 1.0.0
 *
 * @param <T>
 */
public interface IUniqueList<T> extends List<T>
{
	/**
	 * Adds unique record
	 * @param object object to store
	 * @param identifier identifier for providing uniqueness
	 * @return true if record was added
	 */
	public boolean add (T object, Long identifier);
}
