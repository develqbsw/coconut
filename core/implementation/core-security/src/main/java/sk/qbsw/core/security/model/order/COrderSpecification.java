package sk.qbsw.core.security.model.order;

/**
 * The order specification.
 *
 * @author Tomas Lauro
 * @version 1.13.0
 * @param <S> the order by attribute specifier
 * @since 1.13.0
 */
public class COrderSpecification<S extends IOrderByAttributeSpecifier>
{
	/** The order by attribute specifier. */
	private S orderByAttributeSpecifier;

	/** The order specifier. */
	private EOrderSpecifier orderSpecifier;

	/**
	 * Instantiates a new order specification.
	 *
	 * @param orderByAttributeSpecifier the order by attribute specifier
	 * @param orderSpecifier the order specifier
	 */
	public COrderSpecification (S orderByAttributeSpecifier, EOrderSpecifier orderSpecifier)
	{
		this.orderByAttributeSpecifier = orderByAttributeSpecifier;
		this.orderSpecifier = orderSpecifier;
	}

	/**
	 * Gets the order by attribute specifier.
	 *
	 * @return the order by attribute specifier
	 */
	public S getOrderByAttributeSpecifier ()
	{
		return orderByAttributeSpecifier;
	}

	/**
	 * Sets the order by attribute specifier.
	 *
	 * @param orderByAttributeSpecifier the new order by attribute specifier
	 */
	public void setOrderByAttributeSpecifier (S orderByAttributeSpecifier)
	{
		this.orderByAttributeSpecifier = orderByAttributeSpecifier;
	}

	/**
	 * Gets the order specifier.
	 *
	 * @return the order specifier
	 */
	public EOrderSpecifier getOrderSpecifier ()
	{
		return orderSpecifier;
	}

	/**
	 * Sets the order specifier.
	 *
	 * @param orderSpecifier the new order specifier
	 */
	public void setOrderSpecifier (EOrderSpecifier orderSpecifier)
	{
		this.orderSpecifier = orderSpecifier;
	}
}
