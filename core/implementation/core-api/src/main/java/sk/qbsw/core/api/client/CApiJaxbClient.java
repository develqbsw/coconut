package sk.qbsw.core.api.client;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.qbsw.core.api.client.http.IHttpApiRequest;
import sk.qbsw.core.api.exception.CApiHttpException;

/**
 * API Client - combination of HTTP request and GSon parser.
 * 
 * @param <I>
 *            input class
 * @param <O>
 *            output class
 * @author Michal Lacko
 * @version 1.10.0
 * @since 1.10.0
 */
public class CApiJaxbClient<I, O> extends AApiClient<I, O>
{
	protected static final Logger LOGGER = LoggerFactory.getLogger(CApiJaxbClient.class);

	/** Gson builder to create gson. */
	private JAXBContext context;

	/** The encode api value. */
	boolean encodeApiValue;

	/**
	 * Instantiates a new api client.
	 * @throws JAXBException 
	 */
	public CApiJaxbClient (Class... classesToBound) throws JAXBException
	{
		// create builder
		this.context = JAXBContext.newInstance(classesToBound);
	}

	/**
	 * Instantiates a new c api client.
	 * 
	 * @param encodeApiValue
	 *            the encode api value
	 * @throws JAXBException 
	 */
	public CApiJaxbClient (boolean encodeApiValue, Class... classesToBound) throws JAXBException
	{
		this(classesToBound);
		this.encodeApiValue = encodeApiValue;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.api.client.IApiClient#makeCall(sk.qbsw.core.api.client.http.IHttpApiRequest, java.lang.String, I, java.lang.reflect.Type, org.apache.http.entity.ContentType)
	 */
	@Override
	@SuppressWarnings ("unchecked")
	public O makeCall (IHttpApiRequest request, String url, I input, Type returnType, ContentType contentType) throws IOException
	{

		try
		{
			// create gson from builder
			Unmarshaller m = context.createUnmarshaller();

			// process request
			String response = makeCall(request, url, input, contentType);

			// process response

			O responseObject = (O) m.unmarshal(new StringReader(response));
			return responseObject;

		}
		catch (JAXBException e)
		{
			LOGGER.error("Error when deserializing input for CApiJaxbClient", e);
			throw new CApiHttpException("Error when deserializing input for CApiJaxbClient", e, -1);
		}
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.api.client.IApiClient#makeCall(sk.qbsw.core.api.client.http.IHttpApiRequest, java.lang.String, I, java.lang.reflect.Type)
	 */
	@Override
	public O makeCall (IHttpApiRequest request, String url, I input, Type returnType) throws IOException
	{
		return this.makeCall(request, url, input, returnType, ContentType.APPLICATION_XML);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.api.client.IApiClient#makeCall(sk.qbsw.core.api.client.http.IHttpApiRequest, java.lang.String, I, org.apache.http.entity.ContentType)
	 */
	@Override
	public String makeCall (IHttpApiRequest request, String url, I input, ContentType contentType) throws IOException
	{
		try
		{
			// create gson from builder
			Marshaller m = context.createMarshaller();

			StringWriter stringWritter = new StringWriter();

			// prepare request
			m.marshal(input, stringWritter);

			String requestXml = stringWritter.toString();

			if (encodeApiValue)
			{
				requestXml = URLEncoder.encode(requestXml, "UTF8");
			}

			return makeCall(request, url, contentType, requestXml);

		}
		catch (JAXBException e)
		{
			LOGGER.error("Error when serializing input for CApiJaxbClient", e);
			throw new CApiHttpException("Error when serializing input for CApiJaxbClient", e, -1);
		}
	}
}
