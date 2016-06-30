package sk.qbsw.core.googlemaps.geocode;

import java.io.Serializable;


/**
 * The Class CGeometry.
 */
public class CGeometry implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The location. */
	private CLocation location = new CLocation();
	
	/** The southwest. */
	private CLocation southwest = new CLocation();
	
	/** The northeast. */
	private CLocation northeast = new CLocation();

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public CLocation getLocation ()
	{
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation (CLocation location)
	{
		this.location = location;
	}

	/**
	 * Gets the southwest.
	 *
	 * @return the southwest
	 */
	public CLocation getSouthwest ()
	{
		return southwest;
	}

	/**
	 * Sets the southwest.
	 *
	 * @param southwest the new southwest
	 */
	public void setSouthwest (CLocation southwest)
	{
		this.southwest = southwest;
	}

	/**
	 * Gets the northeast.
	 *
	 * @return the northeast
	 */
	public CLocation getNortheast ()
	{
		return northeast;
	}

	/**
	 * Sets the northeast.
	 *
	 * @param northeast the new northeast
	 */
	public void setNortheast (CLocation northeast)
	{
		this.northeast = northeast;
	}


}
