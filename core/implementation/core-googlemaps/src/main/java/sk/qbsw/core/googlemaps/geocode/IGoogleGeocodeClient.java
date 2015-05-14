package sk.qbsw.core.googlemaps.geocode;

import java.io.UnsupportedEncodingException;

import sk.qbsw.core.base.exception.CBusinessException;

/**
 * The Interface IGoogleGeocodeClient.
 */
public interface IGoogleGeocodeClient
{
	
	/**
	 * Find address for GPS coordinates. Get response from google api for latitude and longitude with xml output and slovak language.
	 *
	 * @param latitude latitude search parameter
	 * @param longitude longitude search parameter
	 * @return formatted output for search parameters
	 * @throws CBusinessException the c business exception
	 */
	public String getGeocodeResponseForCoordinates (String latitude, String longitude) throws CBusinessException;

	/**
	 * Find address for GPS coordinates. Get response from google api for latitude and longitude with selected output and slovak language.
	 *
	 * @param output format of output (xml, json)
	 * @param latitude latitude search parameter
	 * @param longitude longitude search parameter
	 * @return formatted output for search parameters
	 * @throws CBusinessException the c business exception
	 */
	public String getGeocodeResponseForCoordinates (String output, String latitude, String longitude) throws CBusinessException;

	/**
	 * Find address for GPS coordinates. Get response from google api for latitude and longitude with selected output and language.
	 *
	 * @param output format of output (xml, json)
	 * @param language language of output
	 * @param latitude latitude search parameter
	 * @param longitude longitude search parameter
	 * @return formatted output for search parameters
	 * @throws CBusinessException the c business exception
	 */
	public String getGeocodeResponseForCoordinates (String output, String language, String latitude, String longitude) throws CBusinessException;

	/**
	 * Find GPS coordiantes for address. Get response from google api for address with xml output and slovak language.
	 *
	 * @param address the address
	 * @return the geocode response for address
	 * @throws CBusinessException the c business exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public String getGeocodeResponseForAddress (String address) throws CBusinessException, UnsupportedEncodingException;

	/**
	 * Find GPS coordiantes for address. Get response from google api for address with selected output and slovak language.
	 *
	 * @param output the output
	 * @param address the address
	 * @return the geocode response for address
	 * @throws CBusinessException the c business exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public String getGeocodeResponseForAddress (String output, String address) throws CBusinessException, UnsupportedEncodingException;

	/**
	 * Find GPS coordiantes for address. Get response from google api for address with selected output and language.
	 *
	 * @param output the output
	 * @param language the language
	 * @param address the address
	 * @return the geocode response for address
	 * @throws CBusinessException the c business exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public String getGeocodeResponseForAddress (String output, String language, String address) throws CBusinessException, UnsupportedEncodingException;

	/**
	 * Find coordinates for selected address and country.
	 *
	 * @param address the address
	 * @param country the country
	 * @param language the language
	 * @return the c location
	 * @throws CBusinessException the c business exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public CLocation findCoordinates (String address, String country, String language) throws CBusinessException, UnsupportedEncodingException;

	/**
	 * Find coordinates for selected address, city and country.
	 *
	 * @param address the address
	 * @param city the city
	 * @param country the country
	 * @param language the language
	 * @return the c location
	 * @throws CBusinessException the c business exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public CLocation findCoordinates (String address, String city, String country, String language) throws CBusinessException, UnsupportedEncodingException;
	
	/**
	 * Get address for GPS position.
	 *
	 * @param latitute the latitute
	 * @param longitude the longitude
	 * @return address
	 * @throws CBusinessException the c business exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	public String getAddressByGPS (Float latitute, Float longitude) throws CBusinessException, UnsupportedEncodingException;
}
