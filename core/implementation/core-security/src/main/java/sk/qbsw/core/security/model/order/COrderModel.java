package sk.qbsw.core.security.model.order;

import java.util.ArrayList;
import java.util.List;

import com.mysema.query.types.OrderSpecifier;

/**
 * The order model.
 *
 * @author Tomas Lauro
 * @version 1.13.0
 * @param <S> the order by attribute specifier
 * @since 1.13.0
 */
public class COrderModel<S extends IOrderByAttributeSpecifier>
{
	/** The order specification. */
	private List<COrderSpecification<IOrderByAttributeSpecifier>> orderSpecification = new ArrayList<COrderSpecification<IOrderByAttributeSpecifier>>();

	/**
	 * Gets the order specification.
	 *
	 * @return the order specification
	 */
	public List<COrderSpecification<IOrderByAttributeSpecifier>> getOrderSpecification ()
	{
		return orderSpecification;
	}

	/**
	 * Sets the order specification.
	 *
	 * @param orderSpecification the new order specification
	 */
	public void setOrderSpecification (List<COrderSpecification<IOrderByAttributeSpecifier>> orderSpecification)
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
		for (COrderSpecification<IOrderByAttributeSpecifier> entry : orderSpecification)
		{
			orderSpecifiers[i++] = entry.getOrderByAttributeSpecifier().getOrderSpecifier(entry.getOrderSpecifier());
		}

		return orderSpecifiers;
	}
}
