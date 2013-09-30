package sk.qbsw.core.api.client;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;

import sk.qbsw.core.api.client.gson.serializer.CDateJSonSerializer;
import sk.qbsw.core.api.client.http.IHttpApiRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * API Client - combination of HTTP request and GSon parser.
 * 
 * @param <I>
 *            input class
 * @param <O>
 *            output class
 * @author Dalibor Rak
 * @version 1.3.0
 * @since 1.2.0
 */
public class CApiClient<I, O>
{
	protected static final Logger LOGGER = Logger.getLogger(CApiClient.class);

	/** Gson builder to create gson. */
	private GsonBuilder builder;

	/** The encode api value. */
	private boolean encodeApiValue;

	/**
	 * Instantiates a new api client.
	 */
	public CApiClient ()
	{
		// create builder
		this.builder = new GsonBuilder();
		// initialize builder and register default adapters
		this.builder = prepareGsonBuilder(builder);
	}

	/**
	 * Instantiates a new c api client.
	 * 
	 * @param encodeApiValue
	 *            the encode api value
	 */
	public CApiClient (boolean encodeApiValue)
	{
		this.encodeApiValue = encodeApiValue;

		// create builder
		this.builder = new GsonBuilder();
		// initialize builder and register default adapters
		this.builder = prepareGsonBuilder(builder);
	}

	/**
	 * Make call to API.
	 * 
	 * @param request
	 *            the request
	 * @param url
	 *            the url
	 * @param input
	 *            the input
	 * @return the string
	 */
	public String makeCall (IHttpApiRequest request, String url, I input) throws IOException
	{
		return this.makeCall(request, url, input, ContentType.APPLICATION_JSON);
	}

	/**
	 * Makes call to API.
	 * 
	 * @param request
	 *            request to use
	 * @param url
	 *            URL to API
	 * @param input
	 *            input parameter
	 * @param returnClass
	 *            Class to return
	 * @return Returned object (instance of returnClass)
	 */
	public O makeCall (IHttpApiRequest request, String url, I input, Class<O> returnClass) throws IOException
	{
		return makeCall(request, url, input, (Type) returnClass);
	}

	/**
	 * Make call with specific content.
	 * 
	 * @param request
	 *            the request
	 * @param url
	 *            the url
	 * @param input
	 *            the input
	 * @param returnClass
	 *            the return class
	 * @param type
	 *            the type
	 * @return the o
	 */
	public O makeCall (IHttpApiRequest request, String url, I input, Class<O> returnClass, ContentType type) throws IOException
	{
		return makeCall(request, url, input, (Type) returnClass, type);
	}

	/**
	 * Makes call with specific content.
	 * 
	 * @param request
	 *            request to use
	 * @param url
	 *            URL to API
	 * @param input
	 *            input parameter
	 * @param contentType
	 *            the content type
	 * @return Returned object (instance of returnClass)
	 */
	public String makeCall (IHttpApiRequest request, String url, I input, ContentType contentType) throws IOException
	{
		// create gson from builder
		Gson gson = this.builder.create();

		// prepare request
		String requestJson = gson.toJson(input);

		if (encodeApiValue)
		{
			requestJson = URLEncoder.encode(requestJson, "UTF8");
		}

		// process request
		String response = request.makeCall(url, contentType, requestJson);

		return response;
	}

	/**
	 * Make call to API.
	 * 
	 * @param request
	 *            the request
	 * @param url
	 *            the url
	 * @param input
	 *            the input
	 * @param returnType
	 *            the return type
	 * @return the o
	 */
	public O makeCall (IHttpApiRequest request, String url, I input, Type returnType) throws IOException
	{
		return this.makeCall(request, url, input, returnType, ContentType.APPLICATION_JSON);
	}

	/**
	 * Makes call with specific content.
	 * 
	 * @param request
	 *            request to use
	 * @param url
	 *            URL to API
	 * @param input
	 *            input parameter
	 * @param returnType
	 *            Type to return
	 * @param contentType
	 *            the content type
	 * @return Returned object (instance of returnClass)
	 */
	@SuppressWarnings ("unchecked")
	public O makeCall (IHttpApiRequest request, String url, I input, Type returnType, ContentType contentType) throws IOException
	{
		// create gson from builder
		Gson gson = this.builder.create();

		// process request
		String response = makeCall(request, url, input, contentType);

		// process response
		O responseObject = (O) gson.fromJson(response, returnType);
		return responseObject;
	}

	/**
	 * Initializes Gson Builder - register standard date
	 * serializer/deserializer.
	 * 
	 * @param builder
	 *            builder to initialize
	 * @return the gson builder
	 */
	protected GsonBuilder prepareGsonBuilder (GsonBuilder builder)
	{
		return this.builder.registerTypeAdapter(Date.class, new CDateJSonSerializer());
	}

	/**
	 * register adapter to request on server(If class implements JsonSerializer)
	 * and for response from server(If class implements JsonDeserializer).
	 * 
	 * @param type
	 *            for which is adapter registered
	 * @param typeAdapter
	 *            adapter to register
	 */
	public void registerTypeAdapter (Type type, Object typeAdapter)
	{
		this.builder = builder.registerTypeAdapter(type, typeAdapter);
	}
}
