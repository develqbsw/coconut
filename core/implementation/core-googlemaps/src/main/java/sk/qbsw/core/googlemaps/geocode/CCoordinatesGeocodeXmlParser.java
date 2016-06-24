package sk.qbsw.core.googlemaps.geocode;

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
 * The Class CCoordinatesGeocodeXmlParser.
 */
public class CCoordinatesGeocodeXmlParser extends DefaultHandler implements Serializable
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The geocode coordinates. */
	private List<CGeocodeCoordinates> geocodeCoordinates;

	/** The tmp coordinate. */
	private CGeocodeCoordinates tmpCoordinate;

	/** The tmp geometry. */
	private CGeometry tmpGeometry;

	/** The tmp location. */
	private CLocation tmpLocation;

	/** The tmp val. */
	private String tmpVal;

	/** The to parse. */
	private String toParse;

	/** The result type. */
	private Boolean resultType;

	/** The result type. */
	private Boolean shortName;

	/** The tmp short name. */
	private String tmpShortName;

	/**
	 * Gets the geocode coordinates.
	 *
	 * @return the geocode coordinates
	 */
	public List<CGeocodeCoordinates> getGeocodeCoordinates ()
	{
		return geocodeCoordinates;
	}

	/**
	 * Instantiates a new c coordinates geocode xml parser.
	 *
	 * @param toParse the to parse
	 */
	public CCoordinatesGeocodeXmlParser (String toParse)
	{
		this.geocodeCoordinates = new ArrayList<CGeocodeCoordinates>();
		this.toParse = toParse;
		this.resultType = false;
		this.shortName = false;
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
		InputStream is = null;

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
			tmpCoordinate = new CGeocodeCoordinates();
		}
		else if (qName.equalsIgnoreCase("geometry"))
		{
			tmpGeometry = new CGeometry();
		}
		else if (qName.equalsIgnoreCase("location") || qName.equalsIgnoreCase("southwest") || qName.equalsIgnoreCase("northeast"))
		{
			tmpLocation = new CLocation();
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
			geocodeCoordinates.add(tmpCoordinate);
			resultType = false;
			shortName = false;
		}
		else if (qName.equalsIgnoreCase("type") && !resultType)
		{
			tmpCoordinate.setType(tmpVal);
			resultType = true;
		}
		else if (qName.equalsIgnoreCase("short_name"))
		{
			tmpShortName = tmpVal;
		}
		else if (qName.equalsIgnoreCase("type") && resultType)
		{
			if (tmpVal.equalsIgnoreCase("locality") && !shortName)
			{
				tmpCoordinate.setCity(tmpShortName);
				shortName = true;
			}
			else if (tmpVal.equalsIgnoreCase("sublocality") && !shortName)
			{
				tmpCoordinate.setCity(tmpShortName);
				shortName = true;
			}
		}
		else if (qName.equalsIgnoreCase("geometry"))
		{
			tmpCoordinate.setGeometry(tmpGeometry);
		}
		else if (qName.equalsIgnoreCase("location"))
		{
			tmpGeometry.setLocation(tmpLocation);
		}
		else if (qName.equalsIgnoreCase("southwest"))
		{
			tmpGeometry.setSouthwest(tmpLocation);
		}
		else if (qName.equalsIgnoreCase("northeast"))
		{
			tmpGeometry.setNortheast(tmpLocation);
		}
		else if (qName.equalsIgnoreCase("lat"))
		{
			tmpLocation.setLat(Double.valueOf(tmpVal).doubleValue());
		}
		else if (qName.equalsIgnoreCase("lng"))
		{
			tmpLocation.setLng(Double.valueOf(tmpVal).doubleValue());
		}
	}
}
