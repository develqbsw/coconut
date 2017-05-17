package sk.qbsw.geolocation.googlemaps.geocode;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;

import sk.qbsw.core.base.exception.CBusinessException;

/**
 * Google client for API services.
 * if behind proxy you must use in tomcat
 * -Dhttp.proxyHost="192.168.121.31" -Dhttp.proxyPort="3128" 
 * -Dhttps.proxyHost="192.168.121.31" -Dhttps.proxyPort="3128"
 *
 * @author Dalibor Rak
 * @author Marek Martinkovic
 * @version 1.12.1
 * @since 1.2.0
 */
public class CGoogleGeocodeClient implements IGoogleGeocodeClient, Serializable
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant serviceUrl. */
	private final static String serviceUrl = "maps.googleapis.com/maps/api/geocode/";

	/** The Constant OUTPUT_XML. */
	public static final String OUTPUT_XML = "xml";

	/** The protocol to use. */
	protected String protocol;

	/** The protocol HTTPS. */
	public static final String PROTOCOL_HTTPS = "https";

	/** The protocol HTTP. */
	public static final String PROTOCOL_HTTP = "http";

	/** The Constant OUTPUT_JSON. */
	public static final String OUTPUT_JSON = "json";

	/** The Constant LANGUAGE_SK. */
	public static final String LANGUAGE_SK = "sk";

	/** The Constant LANGUAGE_EN. */
	public static final String LANGUAGE_EN = "en";


	/**
	 * specify witch protocol to use.
	 * 
	 * @see PROTOCOL_HTTPS or PROTOCOL_HTTP in this class
	 * @param protocol
	 */
	public CGoogleGeocodeClient (String protocol)
	{
		super();
		this.protocol = protocol;
	}

	/**
	 * using PROTOCOL_HTTP
	 */
	public CGoogleGeocodeClient ()
	{
		super();
		this.protocol = PROTOCOL_HTTP;
	}


	/* (non-Javadoc)
	 * @see sk.qbsw.geolocation.googlemaps.geocode.IGoogleGeocodeClient#getGeocodeResponseForCoordinates(java.lang.String, java.lang.String)
	 */
	@Override
	public String getGeocodeResponseForCoordinates (String latitude, String longitude) throws CBusinessException
	{
		String url = getGoogleURL() + OUTPUT_XML + "?latlng=" + latitude + "," + longitude + "&sensor=false&language=" + LANGUAGE_SK;

		return getFromServer(url);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.geolocation.googlemaps.geocode.IGoogleGeocodeClient#getGeocodeResponseForCoordinates(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getGeocodeResponseForCoordinates (String output, String latitude, String longitude) throws CBusinessException
	{
		String url = getGoogleURL() + output + "?latlng=" + latitude + "," + longitude + "&sensor=false&language=" + LANGUAGE_SK;

		return getFromServer(url);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.geolocation.googlemaps.geocode.IGoogleGeocodeClient#getGeocodeResponseForCoordinates(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getGeocodeResponseForCoordinates (String output, String language, String latitude, String longitude) throws CBusinessException
	{
		String url = getGoogleURL() + OUTPUT_XML + "?latlng=" + latitude + "," + longitude + "&sensor=false&language=" + language;

		return getFromServer(url);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.geolocation.googlemaps.geocode.IGoogleGeocodeClient#getGeocodeResponseForAddress(java.lang.String)
	 */
	@Override
	public String getGeocodeResponseForAddress (String address) throws CBusinessException, UnsupportedEncodingException
	{
		String url = getGoogleURL() + OUTPUT_XML + "?address=" + encodeAddress(address) + "&sensor=false&language=" + LANGUAGE_SK;

		return getFromServer(url);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.geolocation.googlemaps.geocode.IGoogleGeocodeClient#getGeocodeResponseForAddress(java.lang.String, java.lang.String)
	 */
	@Override
	public String getGeocodeResponseForAddress (String output, String address) throws CBusinessException, UnsupportedEncodingException
	{
		String url = getGoogleURL() + output + "?address=" + encodeAddress(address) + "&sensor=false&language=" + LANGUAGE_SK;

		return getFromServer(url);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.geolocation.googlemaps.geocode.IGoogleGeocodeClient#getGeocodeResponseForAddress(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getGeocodeResponseForAddress (String output, String language, String address) throws CBusinessException, UnsupportedEncodingException
	{
		String url = getGoogleURL() + OUTPUT_XML + "?address=" + encodeAddress(address) + "&sensor=false&language=" + language;

		return getFromServer(url);
	}

	/**
	 * Encode address.
	 *
	 * @param address the address
	 * @return the string
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	private String encodeAddress (String address) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(address, "utf-8");
	}

	/**
	 * Encode address.
	 *
	 * @param address the address
	 * @return the string
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	protected String getGoogleURL ()
	{
		return protocol + "://" + serviceUrl;
	}

	/**
	 * Gets response from server.
	 *
	 * @param urlString url to call
	 * @return the from server
	 * @throws CBusinessException the c business exception
	 */
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
			throw new CBusinessException("Problem getting String from stream.", e);
		}
	}

	/**
	 * Searches for Google locality.
	 *
	 * @param address the address
	 * @param country the country
	 * @param language the language
	 * @return the c location
	 * @throws CBusinessException the c business exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @see sk.qbsw.geolocation.googlemaps.geocode.IGoogleGeocodeClient#findCoordinates(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public CLocation findCoordinates (String address, String country, String language) throws CBusinessException, UnsupportedEncodingException
	{
		CLocation location = new CLocation();

		String output = new String(getGeocodeResponseForAddress(CGoogleGeocodeClient.OUTPUT_XML, language, address + "," + country).getBytes(), "utf-8");

		CCoordinatesGeocodeXmlParser parser = new CCoordinatesGeocodeXmlParser(output);
		parser.parse();

		for (CGeocodeCoordinates coordinates : parser.getGeocodeCoordinates())
		{
			if ( ("sublocality".equals(coordinates.getType()) || "locality".equals(coordinates.getType())) && coordinates.getCity().equalsIgnoreCase(address))
			{
				location.setLat(coordinates.getGeometry().getLocation().getLat());
				location.setLng(coordinates.getGeometry().getLocation().getLng());
			}
		}

		return location;
	}

	/**
	 * Searches for google route or street address.
	 *
	 * @param address the address
	 * @param city the city
	 * @param country the country
	 * @param language the language
	 * @return the c location
	 * @throws CBusinessException the c business exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @see sk.qbsw.geolocation.googlemaps.geocode.IGoogleGeocodeClient#findCoordinates(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public CLocation findCoordinates (String address, String city, String country, String language) throws CBusinessException, UnsupportedEncodingException
	{
		if (address == null || address.isEmpty())
		{
			// searches google locality
			return findCoordinates(city, country, language);
		}
		else
		{
			// searches google route or street address
			CLocation location = new CLocation();

			String output = new String(getGeocodeResponseForAddress(CGoogleGeocodeClient.OUTPUT_XML, language, address + "," + city + "," + country).getBytes(), "utf-8");

			CCoordinatesGeocodeXmlParser parser = new CCoordinatesGeocodeXmlParser(output);
			parser.parse();

			for (CGeocodeCoordinates coordinates : parser.getGeocodeCoordinates())
			{
				if ( ("route".equals(coordinates.getType()) || "street_address".equals(coordinates.getType())) && coordinates.getCity().equalsIgnoreCase(city))
				{
					location.setLat(coordinates.getGeometry().getLocation().getLat());
					location.setLng(coordinates.getGeometry().getLocation().getLng());
				}
			}

			return location;
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.geolocation.googlemaps.geocode.IGoogleGeocodeClient#getAddressByGPS(java.lang.Float, java.lang.Float)
	 */
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
			if ("street_address".equals(geocodeAddress.getType()) || "route".equals(geocodeAddress.getType()))
			{
				address = getAddress(geocodeAddress);
			}
		}

		return address;
	}

	/**
	 * Get address for CGeocodeAddress.
	 *
	 * @param geocodeAddress the geocode address
	 * @return the address
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
