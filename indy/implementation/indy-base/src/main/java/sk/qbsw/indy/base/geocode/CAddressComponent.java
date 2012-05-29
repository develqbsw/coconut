package sk.qbsw.indy.base.geocode;

import java.util.ArrayList;
import java.util.List;

public class CAddressComponent
{
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
