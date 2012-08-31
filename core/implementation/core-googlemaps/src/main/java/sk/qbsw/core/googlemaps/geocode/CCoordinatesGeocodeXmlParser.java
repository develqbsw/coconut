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

import sk.qbsw.core.security.exception.CBusinessException;


public class CCoordinatesGeocodeXmlParser extends DefaultHandler implements Serializable
{
	private static final long serialVersionUID = 1L;

	private List<CGeocodeCoordinates> geocodeCoordinates;
	private CGeocodeCoordinates tmpCoordinate;
	private CGeometry tmpGeometry;
	private CLocation tmpLocation;
	private String tmpVal;
	private String toParse;
	private Boolean resultType;
	private String tmpShortName;

	public List<CGeocodeCoordinates> getGeocodeCoordinates ()
	{
		return geocodeCoordinates;
	}

	public CCoordinatesGeocodeXmlParser (String toParse)
	{
		this.geocodeCoordinates = new ArrayList<CGeocodeCoordinates>();
		this.toParse = toParse;
		this.resultType = false;
	}

	public void parse () throws CBusinessException
	{
		try
		{
			parseDocument(toParse);
		}
		catch (Throwable e)
		{
			throw new CBusinessException("Parsing geocode response");
		}
	}

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

	public void characters (char[] ch, int start, int length) throws SAXException
	{
		tmpVal = new String(ch, start, length);
	}

	public void endElement (String uri, String localName, String qName) throws SAXException
	{

		if (qName.equalsIgnoreCase("result"))
		{
			//add it to the list
			geocodeCoordinates.add(tmpCoordinate);
			resultType = false;
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
			if (tmpVal.equalsIgnoreCase("locality"))
			{
				tmpCoordinate.setCity(tmpShortName);
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
