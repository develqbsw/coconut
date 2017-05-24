package sk.qbsw.security.model.order;

import com.querydsl.core.types.OrderSpecifier;

/**
 * The order by attribute specifier.
 * 
 * @author Tomas Lauro
 * 
 * @version 1.13.0
 * @since 1.13.0
 */
public interface IOrderByAttributeSpecifier
{
	/**
	 * Gets the order specifier.
	 *
	 * @param orderSpecifier the order specifier
	 * @return the order specifier
	 */
	public OrderSpecifier<?> getOrderSpecifier (EOrderSpecifier orderSpecifier);
}
