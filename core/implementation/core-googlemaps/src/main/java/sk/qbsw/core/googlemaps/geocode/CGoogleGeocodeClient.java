package sk.qbsw.core.googlemaps.geocode;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;

import sk.qbsw.core.security.exception.CBusinessException;

public class CGoogleGeocodeClient implements IGoogleGeocodeClient, Serializable
{
	private static final long serialVersionUID = 1L;

	private final static String serviceUrl = "http://maps.googleapis.com/maps/api/geocode/";

	public static final String OUTPUT_XML = "xml";
	public static final String OUTPUT_JSON = "json";

	public static final String LANGUAGE_SK = "sk";
	public static final String LANGUAGE_EN = "en";

	@Override
	public String getGeocodeResponseForCoordinates (String latitude, String longitude) throws CBusinessException
	{
		String url = serviceUrl + OUTPUT_XML + "?latlng=" + latitude + "," + longitude + "&sensor=false&language=" + LANGUAGE_SK;

		return getFromServer(url);
	}

	@Override
	public String getGeocodeResponseForCoordinates (String output, String latitude, String longitude) throws CBusinessException
	{
		String url = serviceUrl + output + "?latlng=" + latitude + "," + longitude + "&sensor=false&language=" + LANGUAGE_SK;

		return getFromServer(url);
	}

	@Override
	public String getGeocodeResponseForCoordinates (String output, String language, String latitude, String longitude) throws CBusinessException
	{
		String url = serviceUrl + OUTPUT_XML + "?latlng=" + latitude + "," + longitude + "&sensor=false&language=" + language;

		return getFromServer(url);
	}

	@Override
	public String getGeocodeResponseForAddress (String address) throws CBusinessException, UnsupportedEncodingException
	{
		String url = serviceUrl + OUTPUT_XML + "?address=" + encodeAddress(address) + "&sensor=false&language=" + LANGUAGE_SK;

		return getFromServer(url);
	}

	@Override
	public String getGeocodeResponseForAddress (String output, String address) throws CBusinessException, UnsupportedEncodingException
	{
		String url = serviceUrl + output + "?address=" + encodeAddress(address) + "&sensor=false&language=" + LANGUAGE_SK;

		return getFromServer(url);
	}

	@Override
	public String getGeocodeResponseForAddress (String output, String language, String address) throws CBusinessException, UnsupportedEncodingException
	{
		String url = serviceUrl + OUTPUT_XML + "?address=" + encodeAddress(address) + "&sensor=false&language=" + language;

		return getFromServer(url);
	}

	private String encodeAddress (String address) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(address, "utf-8");
	}

	private String getFromServer (String urlString) throws CBusinessException
	{
		String result = "";
		try
		{
			final URL url = new URL(urlString);
			URLConnection connection = url.openConnection();

			result = IOUtils.toString(connection.getInputStream());

			return result;
		}
		catch (final Exception e)
		{
			throw new CBusinessException("geocode");
		}
	}

	@Override
	public CLocation findCoordinates (String address, String country, String language) throws CBusinessException, UnsupportedEncodingException
	{
		CLocation location = new CLocation();

		String output = new String(getGeocodeResponseForAddress(CGoogleGeocodeClient.OUTPUT_XML, language, address + "," + country).getBytes(), "utf-8");

		CCoordinatesGeocodeXmlParser parser = new CCoordinatesGeocodeXmlParser(output);
		parser.parse();

		for (CGeocodeCoordinates coordinates : parser.getGeocodeCoordinates())
		{
			if (coordinates.getType().equals("locality") && coordinates.getCity().equalsIgnoreCase(address))
			{
				location.setLat(coordinates.getGeometry().getLocation().getLat());
				location.setLng(coordinates.getGeometry().getLocation().getLng());
			}
		}

		return location;
	}

	@Override
	public String getAddressByGPS (Float latitute, Float longitude) throws CBusinessException, UnsupportedEncodingException
	{
		String address = "";

		CGoogleGeocodeClient geocode = new CGoogleGeocodeClient();

		String output = new String(geocode.getGeocodeResponseForCoordinates(CGoogleGeocodeClient.OUTPUT_XML, latitute.toString(), longitude.toString()).getBytes(), "utf-8");

		CAddressGeocodeXmlParser parser = new CAddressGeocodeXmlParser(output);
		parser.parse();

		for (CGeocodeAddress geocodeAddress : parser.getGeocodeAddresses())
		{
			if (geocodeAddress.getType().equals("street_address") || geocodeAddress.getType().equals("route"))
			{
				address = getAddress(geocodeAddress);
			}
		}

		return address;
	}

	/**
	 * Get address for CGeocodeAddress
	 * 
	 * @param address CGeocodeAddress to find a address
	 * @return
	 */
	private String getAddress (CGeocodeAddress geocodeAddress)
	{
		String number = null;
		String street = null;
		String city = null;
		String address = "";

		for (CAddressComponent addressComponent : geocodeAddress.getAddressComponents())
		{
			if (addressComponent.getTypes().contains("route"))
			{
				street = addressComponent.getLongName();
			}
			if (addressComponent.getTypes().contains("street_number"))
			{
				number = addressComponent.getLongName();
			}
			if (addressComponent.getTypes().contains("locality"))
			{
				city = addressComponent.getLongName();
			}
		}

		if (street != null)
		{
			address = address.concat(street);
		}
		if (street != null && number != null)
		{
			address = address.concat(" ");
		}
		if (number != null)
		{
			address = address.concat(number);
		}
		if (address.length() > 0)
		{
			address = address.concat(", ");
		}
		if (city != null)
		{
			address = address.concat(city);
		}

		return address;
	}
}
