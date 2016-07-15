package sk.qbsw.et.browser.core.model;

import com.querydsl.core.JoinType;
import com.querydsl.core.types.EntityPath;

/**
 * The join descriptor.
 *
 * @param <T> the entity type
 *
 * @author Adrian Lopez (http://stackoverflow.com/a/21630123)
 * @author Tomas Lauro
 * 
 * @version 1.16.0
 * @since 1.16.0
 */
public class CJoinDescriptor<T>
{
	/** The path. */
	public final EntityPath<T> path;

	/** The type. */
	public final JoinType type;

	/**
	 * Instantiates a new c join descriptor.
	 *
	 * @param path the path
	 * @param type the type
	 */
	private CJoinDescriptor (EntityPath<T> path, JoinType type)
	{
		this.path = path;
		this.type = type;
	}

	/**
	 * Inner join.
	 *
	 * @param <T> the entity type
	 * @param path the path
	 * @return the c join descriptor
	 */
	public static <T> CJoinDescriptor<T> innerJoin (EntityPath<T> path)
	{
		return new CJoinDescriptor<>(path, JoinType.INNERJOIN);
	}

	/**
	 * Join.
	 *
	 * @param <T> the entity type
	 * @param path the path
	 * @return the c join descriptor
	 */
	public static <T> CJoinDescriptor<T> join (EntityPath<T> path)
	{
		return new CJoinDescriptor<>(path, JoinType.JOIN);
	}

	/**
	 * Left join.
	 *
	 * @param <T> the entity type
	 * @param path the path
	 * @return the c join descriptor
	 */
	public static <T> CJoinDescriptor<T> leftJoin (EntityPath<T> path)
	{
		return new CJoinDescriptor<>(path, JoinType.LEFTJOIN);
	}

	/**
	 * Right join.
	 *
	 * @param <T> the entity type
	 * @param path the path
	 * @return the c join descriptor
	 */
	public static <T> CJoinDescriptor<T> rightJoin (EntityPath<T> path)
	{
		return new CJoinDescriptor<>(path, JoinType.RIGHTJOIN);
	}
}
