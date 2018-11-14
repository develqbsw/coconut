package sk.qbsw.security.core.model.order;

import com.querydsl.core.types.OrderSpecifier;

/**
 * The order by attribute specifier.
 * 
 * @author Tomas Lauro
 * @version 2.0.0
 * @since 1.13.0
 */
public interface OrderByAttributeSpecifier
{
	/**
	 * Gets the order specifier.
	 *
	 * @param orderSpecifier the order specifier
	 * @return the order specifier
	 */
	OrderSpecifier<?> getOrderSpecifier (OrderSpecifiers orderSpecifier);
}
