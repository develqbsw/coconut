package sk.qbsw.indy.base.geocode;

import java.io.UnsupportedEncodingException;

import sk.qbsw.indy.base.exceptions.CBusinessException;

public interface IGoogleGeocodeClient
{
	/**
	 * Find address for GPS coordinates. Get response from google api for latitude and longitude with xml output and slovak language. 
	 * 
	 * @param latitude latitude search parameter
	 * @param longitude longitude search parameter
	 * @return formatted output for search parameters
	 * @throws CBusinessException
	 */
	public String getGeocodeResponseForCoordinates (String latitude, String longitude) throws CBusinessException;

	/**
	 * Find address for GPS coordinates. Get response from google api for latitude and longitude with selected output and slovak language.
	 * 
	 * @param output format of output (xml, json)
	 * @param latitude latitude search parameter
	 * @param longitude longitude search parameter
	 * @return formatted output for search parameters
	 * @throws CBusinessException
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
	 * @throws CBusinessException
	 */
	public String getGeocodeResponseForCoordinates (String output, String language, String latitude, String longitude) throws CBusinessException;

	/**
	 * Find GPS coordiantes for address. Get response from google api for address with xml output and slovak language. 
	 * 
	 * @param address
	 * @return
	 * @throws CBusinessException
	 */
	public String getGeocodeResponseForAddress (String address) throws CBusinessException, UnsupportedEncodingException;

	/**
	 * Find GPS coordiantes for address. Get response from google api for address with selected output and slovak language.
	 * 
	 * @param output
	 * @param address
	 * @return
	 * @throws CBusinessException
	 */
	public String getGeocodeResponseForAddress (String output, String address) throws CBusinessException, UnsupportedEncodingException;

	/**
	 * Find GPS coordiantes for address. Get response from google api for address with selected output and language.
	 * 
	 * @param output
	 * @param language
	 * @param address
	 * @return
	 * @throws CBusinessException
	 */
	public String getGeocodeResponseForAddress (String output, String language, String address) throws CBusinessException, UnsupportedEncodingException;
}
