package sk.qbsw.core.googlemaps.geocode;

import java.io.Serializable;

/**
 * The Class CLocation.
 */
public class CLocation implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The lat. */
	private double lat;
	
	/** The lng. */
	private double lng;

	/**
	 * Gets the lat.
	 *
	 * @return the lat
	 */
	public double getLat ()
	{
		return lat;
	}

	/**
	 * Sets the lat.
	 *
	 * @param lat the new lat
	 */
	public void setLat (double lat)
	{
		this.lat = lat;
	}

	/**
	 * Gets the lng.
	 *
	 * @return the lng
	 */
	public double getLng ()
	{
		return lng;
	}

	/**
	 * Sets the lng.
	 *
	 * @param lng the new lng
	 */
	public void setLng (double lng)
	{
		this.lng = lng;
	}
}
