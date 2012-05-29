package sk.qbsw.indy.base.geocode;

import java.util.ArrayList;
import java.util.List;

public class CGeocodeAddress
{
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
