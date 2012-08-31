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


public class CAddressGeocodeXmlParser extends DefaultHandler implements Serializable
{
	private static final long serialVersionUID = 1L;

	private List<CGeocodeAddress> geocodeAddresses;
	private CGeocodeAddress tmpAddress;
	private CAddressComponent tmpAddressComponent;
	private String tmpVal;
	private String toParse;
	private Boolean resultType;

	public List<CGeocodeAddress> getGeocodeAddresses ()
	{
		return geocodeAddresses;
	}

	public CAddressGeocodeXmlParser (String toParse)
	{
		this.geocodeAddresses = new ArrayList<CGeocodeAddress>();
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
			tmpAddress = new CGeocodeAddress();
		}
		else if (qName.equalsIgnoreCase("address_component"))
		{
			tmpAddressComponent = new CAddressComponent();
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
