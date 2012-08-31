package sk.qbsw.core.googlemaps.geocode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CGeocodeAddress implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String type;
	private List<CAddressComponent> addressComponents;

	public CGeocodeAddress ()
	{
		super();
		this.addressComponents = new ArrayList<CAddressComponent>();
	}

	public String getType ()
	{
		return type;
	}

	public void setType (String type)
	{
		this.type = type;
	}

	public List<CAddressComponent> getAddressComponents ()
	{
		return addressComponents;
	}

	public void setAddressComponents (List<CAddressComponent> addressComponents)
	{
		this.addressComponents = addressComponents;
	}
}
