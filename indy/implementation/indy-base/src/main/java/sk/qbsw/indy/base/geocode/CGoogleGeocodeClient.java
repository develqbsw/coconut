package sk.qbsw.indy.base.geocode;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.wicket.util.io.Streams;

import sk.qbsw.indy.base.exceptions.CBusinessException;

public class CGoogleGeocodeClient implements IGoogleGeocodeClient, Serializable
{
	private static final long serialVersionUID = 1L;

	private final String serviceUrl = "http://maps.googleapis.com/maps/api/geocode/";

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

			result = Streams.readString(connection.getInputStream());

			return result;
		}
		catch (final Exception e)
		{
			throw new CBusinessException("geocode");
		}
	}

	/**
	 * Find coordinates for selected address. 
	 * 
	 * @param address address to find coordinates
	 * @return CLocation
	 * @throws CBusinessException
	 */
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
}
