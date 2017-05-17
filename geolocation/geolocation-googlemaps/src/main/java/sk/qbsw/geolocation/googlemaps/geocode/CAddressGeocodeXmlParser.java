package sk.qbsw.geolocation.googlemaps.geocode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import sk.qbsw.core.base.exception.CBusinessException;


/**
 * The Class CAddressGeocodeXmlParser.
 */
public class CAddressGeocodeXmlParser extends DefaultHandler implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The geocode addresses. */
	private List<CGeocodeAddress> geocodeAddresses;
	
	/** The tmp address. */
	private CGeocodeAddress tmpAddress;
	
	/** The tmp address component. */
	private CAddressComponent tmpAddressComponent;
	
	/** The tmp val. */
	private String tmpVal;
	
	/** The to parse. */
	private String toParse;
	
	/** The result type. */
	private Boolean resultType;

	/**
	 * Gets the geocode addresses.
	 *
	 * @return the geocode addresses
	 */
	public List<CGeocodeAddress> getGeocodeAddresses ()
	{
		return geocodeAddresses;
	}

	/**
	 * Instantiates a new c address geocode xml parser.
	 *
	 * @param toParse the to parse
	 */
	public CAddressGeocodeXmlParser (String toParse)
	{
		this.geocodeAddresses = new ArrayList<CGeocodeAddress>();
		this.toParse = toParse;
		this.resultType = false;
	}

	/**
	 * Parses the.
	 *
	 * @throws CBusinessException the c business exception
	 */
	public void parse () throws CBusinessException
	{
		try
		{
			parseDocument(toParse);
		}
		catch (Exception e)
		{
			throw new CBusinessException("Parsing geocode response", e);
		}
	}

	/**
	 * Parses the document.
	 *
	 * @param fileContent the file content
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the sAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void parseDocument (final String fileContent) throws ParserConfigurationException, SAXException, IOException
	{
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		//get a new instance of parser
		SAXParser sp = spf.newSAXParser();

		//parse the file content and also register this class for call backs
		InputStream is;

		is = new ByteArrayInputStream(fileContent.getBytes("UTF-8"));
		sp.parse(is, this);
	}

	//Event Handlers
	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement (String uri, String localName, String qName, Attributes attributes) throws SAXException
	{
		//reset
		tmpVal = "";
		if (qName.equalsIgnoreCase("result"))
		{
			//create a new instance of geocode address
			tmpAddress = new CGeocodeAddress();
		}
		else if (qName.equalsIgnoreCase("address_component"))
		{
			tmpAddressComponent = new CAddressComponent();
		}
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	public void characters (char[] ch, int start, int length) throws SAXException
	{
		tmpVal = new String(ch, start, length);
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement (String uri, String localName, String qName) throws SAXException
	{

		if (qName.equalsIgnoreCase("result"))
		{
			//add it to the list
			geocodeAddresses.add(tmpAddress);
			resultType = false;
		}
		else if (qName.equalsIgnoreCase("type") && !resultType)
		{
			tmpAddress.setType(tmpVal);
			resultType = true;
		}
		else if (qName.equalsIgnoreCase("long_name"))
		{
			tmpAddressComponent.setLongName(tmpVal);
		}
		else if (qName.equalsIgnoreCase("short_name"))
		{
			tmpAddressComponent.setShortName(tmpVal);
		}
		else if (qName.equalsIgnoreCase("type") && resultType)
		{
			if (tmpAddressComponent != null)
			{
				tmpAddressComponent.getTypes().add(tmpVal);
			}
		}
		else if (qName.equalsIgnoreCase("address_component"))
		{
			tmpAddress.getAddressComponents().add(tmpAddressComponent);
		}

	}
}
