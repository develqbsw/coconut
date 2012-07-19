package sk.qbsw.indy.base.geocode;

import java.io.Serializable;



public class CGeometry implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CLocation location = new CLocation();
	private CLocation southwest = new CLocation();
	private CLocation northeast = new CLocation();

	public CLocation getLocation ()
	{
		return location;
	}

	public void setLocation (CLocation location)
	{
		this.location = location;
	}

	public CLocation getSouthwest ()
	{
		return southwest;
	}

	public void setSouthwest (CLocation southwest)
	{
		this.southwest = southwest;
	}

	public CLocation getNortheast ()
	{
		return northeast;
	}

	public void setNortheast (CLocation northeast)
	{
		this.northeast = northeast;
	}


}
