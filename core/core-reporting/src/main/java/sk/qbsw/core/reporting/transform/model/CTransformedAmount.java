package sk.qbsw.core.reporting.transform.model;

/**
 * The transformed amount.
 *
 * @author Tomas Lauro
 * 
 * @version 1.10.2
 * @since 1.10.2
 * 
 * @param <I> the type of integer part of amount
 * @param <F> the type of fractional part of amount
 */
public class CTransformedAmount<I, F>
{
	/** The integer part of amount. */
	private I integerPart;

	/** The fractional part of amount. */
	private F fractionalPart;

	/**
	 * Gets the integer part.
	 *
	 * @return the integer part
	 */
	public I getIntegerPart ()
	{
		return integerPart;
	}

	/**
	 * Sets the integer part.
	 *
	 * @param integerPart the new integer part
	 */
	public void setIntegerPart (I integerPart)
	{
		this.integerPart = integerPart;
	}

	/**
	 * Gets the fractional part.
	 *
	 * @return the fractional part
	 */
	public F getFractionalPart ()
	{
		return fractionalPart;
	}

	/**
	 * Sets the fractional part.
	 *
	 * @param fractionalPart the new fractional part
	 */
	public void setFractionalPart (F fractionalPart)
	{
		this.fractionalPart = fractionalPart;
	}
}
