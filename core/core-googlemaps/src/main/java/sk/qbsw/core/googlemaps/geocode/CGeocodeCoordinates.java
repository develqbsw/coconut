package sk.qbsw.core.googlemaps.geocode;

import java.io.Serializable;


/**
 * The Class CGeocodeCoordinates.
 */
public class CGeocodeCoordinates implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The type. */
	private String type;
	
	/** The city. */
	private String city;
	
	/** The geometry. */
	private CGeometry geometry;

	/**
	 * Instantiates a new c geocode coordinates.
	 */
	public CGeocodeCoordinates ()
	{
		super();
		this.setGeometry(new CGeometry());
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType ()
	{
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType (String type)
	{
		this.type = type;
	}

	/**
	 * Gets the geometry.
	 *
	 * @return the geometry
	 */
	public CGeometry getGeometry ()
	{
		return geometry;
	}

	/**
	 * Sets the geometry.
	 *
	 * @param geometry the new geometry
	 */
	public void setGeometry (CGeometry geometry)
	{
		this.geometry = geometry;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity ()
	{
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity (String city)
	{
		this.city = city;
	}

}
