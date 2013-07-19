package sk.qbsw.core.googlemaps.geocode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class CGeocodeAddress.
 */
public class CGeocodeAddress implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The type. */
	private String type;
	
	/** The address components. */
	private List<CAddressComponent> addressComponents;

	/**
	 * Instantiates a new c geocode address.
	 */
	public CGeocodeAddress ()
	{
		super();
		this.addressComponents = new ArrayList<CAddressComponent>();
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
	 * Gets the address components.
	 *
	 * @return the address components
	 */
	public List<CAddressComponent> getAddressComponents ()
	{
		return addressComponents;
	}

	/**
	 * Sets the address components.
	 *
	 * @param addressComponents the new address components
	 */
	public void setAddressComponents (List<CAddressComponent> addressComponents)
	{
		this.addressComponents = addressComponents;
	}
}
