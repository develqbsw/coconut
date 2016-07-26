package sk.qbsw.et.browser.core.model;

import com.querydsl.core.JoinType;
import com.querydsl.core.types.CollectionExpression;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Path;

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
	/** The alias. */
	private final Path<T> alias;

	/** The path. */
	private final EntityPath<T> target;

	/** The collection expression. */
	private final CollectionExpression<?, T> collectionTarget;

	/** The type. */
	private final JoinType type;

	/**
	 * Instantiates a new c join descriptor.
	 *
	 * @param target the target
	 * @param type the type
	 */
	private CJoinDescriptor (EntityPath<T> target, JoinType type)
	{
		this(target, type, null);
	}

	/**
	 * Instantiates a new c join descriptor.
	 *
	 * @param target the target
	 * @param type the type
	 * @param alias the alias
	 */
	private CJoinDescriptor (EntityPath<T> target, JoinType type, Path<T> alias)
	{
		this.target = target;
		this.type = type;
		this.alias = alias;
		this.collectionTarget = null;
	}

	/**
	 * Instantiates a new c join descriptor.
	 *
	 * @param collectionTarget the collection target
	 * @param type the type
	 */
	private CJoinDescriptor (CollectionExpression<?, T> collectionTarget, JoinType type)
	{
		this(collectionTarget, type, null);
	}

	/**
	 * Instantiates a new c join descriptor.
	 *
	 * @param collectionTarget the collection target
	 * @param type the type
	 * @param alias the alias
	 */
	private CJoinDescriptor (CollectionExpression<?, T> collectionTarget, JoinType type, Path<T> alias)
	{
		this.collectionTarget = collectionTarget;
		this.type = type;
		this.alias = alias;
		this.target = null;
	}

	/**
	 * Inner join.
	 *
	 * @param <T> the generic type
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
	 * @param <T> the generic type
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
	 * @param <T> the generic type
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
	 * @param <T> the generic type
	 * @param path the path
	 * @return the c join descriptor
	 */
	public static <T> CJoinDescriptor<T> rightJoin (EntityPath<T> path)
	{
		return new CJoinDescriptor<>(path, JoinType.RIGHTJOIN);
	}

	/**
	 * Inner join.
	 *
	 * @param <T> the generic type
	 * @param path the path
	 * @param alias the alias
	 * @return the c join descriptor
	 */
	public static <T> CJoinDescriptor<T> innerJoin (EntityPath<T> path, Path<T> alias)
	{
		return new CJoinDescriptor<>(path, JoinType.INNERJOIN, alias);
	}

	/**
	 * Join.
	 *
	 * @param <T> the generic type
	 * @param path the path
	 * @param alias the alias
	 * @return the c join descriptor
	 */
	public static <T> CJoinDescriptor<T> join (EntityPath<T> path, Path<T> alias)
	{
		return new CJoinDescriptor<>(path, JoinType.JOIN, alias);
	}

	/**
	 * Left join.
	 *
	 * @param <T> the generic type
	 * @param path the path
	 * @param alias the alias
	 * @return the c join descriptor
	 */
	public static <T> CJoinDescriptor<T> leftJoin (EntityPath<T> path, Path<T> alias)
	{
		return new CJoinDescriptor<>(path, JoinType.LEFTJOIN, alias);
	}

	/**
	 * Right join.
	 *
	 * @param <T> the generic type
	 * @param path the path
	 * @param alias the alias
	 * @return the c join descriptor
	 */
	public static <T> CJoinDescriptor<T> rightJoin (EntityPath<T> path, Path<T> alias)
	{
		return new CJoinDescriptor<>(path, JoinType.RIGHTJOIN, alias);
	}

	/**
	 * Inner join.
	 *
	 * @param <T> the generic type
	 * @param collectionExpression the path
	 * @return the c join descriptor
	 */
	public static <T> CJoinDescriptor<T> innerJoin (CollectionExpression<?, T> collectionExpression)
	{
		return new CJoinDescriptor<>(collectionExpression, JoinType.INNERJOIN);
	}

	/**
	 * Join.
	 *
	 * @param <T> the generic type
	 * @param collectionExpression the path
	 * @return the c join descriptor
	 */
	public static <T> CJoinDescriptor<T> join (CollectionExpression<?, T> collectionExpression)
	{
		return new CJoinDescriptor<>(collectionExpression, JoinType.JOIN);
	}

	/**
	 * Left join.
	 *
	 * @param <T> the generic type
	 * @param collectionExpression the path
	 * @return the c join descriptor
	 */
	public static <T> CJoinDescriptor<T> leftJoin (CollectionExpression<?, T> collectionExpression)
	{
		return new CJoinDescriptor<>(collectionExpression, JoinType.LEFTJOIN);
	}

	/**
	 * Right join.
	 *
	 * @param <T> the generic type
	 * @param collectionExpression the path
	 * @return the c join descriptor
	 */
	public static <T> CJoinDescriptor<T> rightJoin (CollectionExpression<?, T> collectionExpression)
	{
		return new CJoinDescriptor<>(collectionExpression, JoinType.RIGHTJOIN);
	}

	/**
	 * Inner join.
	 *
	 * @param <T> the generic type
	 * @param collectionExpression the path
	 * @param alias the alias
	 * @return the c join descriptor
	 */
	public static <T> CJoinDescriptor<T> innerJoin (CollectionExpression<?, T> collectionExpression, Path<T> alias)
	{
		return new CJoinDescriptor<>(collectionExpression, JoinType.INNERJOIN, alias);
	}

	/**
	 * Join.
	 *
	 * @param <T> the generic type
	 * @param collectionExpression the path
	 * @param alias the alias
	 * @return the c join descriptor
	 */
	public static <T> CJoinDescriptor<T> join (CollectionExpression<?, T> collectionExpression, Path<T> alias)
	{
		return new CJoinDescriptor<>(collectionExpression, JoinType.JOIN, alias);
	}

	/**
	 * Left join.
	 *
	 * @param <T> the generic type
	 * @param collectionExpression the path
	 * @param alias the alias
	 * @return the c join descriptor
	 */
	public static <T> CJoinDescriptor<T> leftJoin (CollectionExpression<?, T> collectionExpression, Path<T> alias)
	{
		return new CJoinDescriptor<>(collectionExpression, JoinType.LEFTJOIN, alias);
	}

	/**
	 * Right join.
	 *
	 * @param <T> the generic type
	 * @param collectionExpression the path
	 * @param alias the alias
	 * @return the c join descriptor
	 */
	public static <T> CJoinDescriptor<T> rightJoin (CollectionExpression<?, T> collectionExpression, Path<T> alias)
	{
		return new CJoinDescriptor<>(collectionExpression, JoinType.RIGHTJOIN, alias);
	}

	/**
	 * Gets the alias.
	 *
	 * @return the alias
	 */
	public Path<T> getAlias ()
	{
		return alias;
	}

	/**
	 * Gets the target.
	 *
	 * @return the target
	 */
	public EntityPath<T> getTarget ()
	{
		return target;
	}

	/**
	 * Gets the collection target.
	 *
	 * @return the collection target
	 */
	public CollectionExpression<?, T> getCollectionTarget ()
	{
		return collectionTarget;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public JoinType getType ()
	{
		return type;
	}
}
