package sk.qbsw.indy.base.geocode;

import java.io.Serializable;


public class CGeocodeCoordinates implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String type;
	private String city;
	private CGeometry geometry;

	public CGeocodeCoordinates ()
	{
		super();
		this.setGeometry(new CGeometry());
	}

	public String getType ()
	{
		return type;
	}

	public void setType (String type)
	{
		this.type = type;
	}

	public CGeometry getGeometry ()
	{
		return geometry;
	}

	public void setGeometry (CGeometry geometry)
	{
		this.geometry = geometry;
	}

	public String getCity ()
	{
		return city;
	}

	public void setCity (String city)
	{
		this.city = city;
	}

}
