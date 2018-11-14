package sk.qbsw.security.core.model.order;

import com.querydsl.core.types.OrderSpecifier;

import java.util.ArrayList;
import java.util.List;

/**
 * The order model.
 *
 * @author Tomas Lauro
 * @version 2.0.0
 * @param <S> the order by attribute specifier
 * @since 1.13.0
 */
public class OrderModel<S extends OrderByAttributeSpecifier>
{
	private List<OrderSpecification<OrderByAttributeSpecifier>> orderSpecification = new ArrayList<>();

	/**
	 * Gets the order specification.
	 *
	 * @return the order specification
	 */
	public List<OrderSpecification<OrderByAttributeSpecifier>> getOrderSpecification ()
	{
		return orderSpecification;
	}

	/**
	 * Sets the order specification.
	 *
	 * @param orderSpecification the new order specification
	 */
	public void setOrderSpecification (List<OrderSpecification<OrderByAttributeSpecifier>> orderSpecification)
	{
		this.orderSpecification = orderSpecification;
	}


	/**
	 * Gets the order specifiers as list.
	 *
	 * @return the order specifiers
	 */
	public OrderSpecifier<?>[] getOrderSpecifiers ()
	{
		OrderSpecifier<?>[] orderSpecifiers = new OrderSpecifier<?>[orderSpecification.size()];

		int i = 0;
		for (OrderSpecification<OrderByAttributeSpecifier> entry : orderSpecification)
		{
			orderSpecifiers[i++] = entry.getOrderByAttributeSpecifier().getOrderSpecifier(entry.getOrderSpecifier());
		}

		return orderSpecifiers;
	}
}
