package sk.qbsw.core.googlemaps.geocode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CAddressComponent implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String longName;
	private String shortName;
	private List<String> types;

	public CAddressComponent ()
	{
		super();
		this.types = new ArrayList<String>();
	}

	public List<String> getTypes ()
	{
		return types;
	}

	public String getLongName ()
	{
		return longName;
	}

	public void setLongName (String longName)
	{
		this.longName = longName;
	}

	public String getShortName ()
	{
		return shortName;
	}

	public void setShortName (String shortName)
	{
		this.shortName = shortName;
	}
}
