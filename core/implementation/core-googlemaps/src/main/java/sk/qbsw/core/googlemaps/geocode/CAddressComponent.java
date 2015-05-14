package sk.qbsw.core.googlemaps.geocode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class CAddressComponent.
 */
public class CAddressComponent implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The long name. */
	private String longName;
	
	/** The short name. */
	private String shortName;
	
	/** The types. */
	private List<String> types;

	/**
	 * Instantiates a new c address component.
	 */
	public CAddressComponent ()
	{
		super();
		this.types = new ArrayList<String>();
	}

	/**
	 * Gets the types.
	 *
	 * @return the types
	 */
	public List<String> getTypes ()
	{
		return types;
	}

	/**
	 * Gets the long name.
	 *
	 * @return the long name
	 */
	public String getLongName ()
	{
		return longName;
	}

	/**
	 * Sets the long name.
	 *
	 * @param longName the new long name
	 */
	public void setLongName (String longName)
	{
		this.longName = longName;
	}

	/**
	 * Gets the short name.
	 *
	 * @return the short name
	 */
	public String getShortName ()
	{
		return shortName;
	}

	/**
	 * Sets the short name.
	 *
	 * @param shortName the new short name
	 */
	public void setShortName (String shortName)
	{
		this.shortName = shortName;
	}
}
