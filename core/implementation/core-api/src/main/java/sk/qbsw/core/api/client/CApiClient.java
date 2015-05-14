package sk.qbsw.core.api.client;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.qbsw.core.api.client.gson.serializer.CDateJSonSerializer;
import sk.qbsw.core.api.client.http.IHttpApiRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * API Client - combination of HTTP request and GSon parser.
 *
 * @author Dalibor Rak
 * @author Michal Lacko
 * 
 * @since 1.2.0
 * @version 1.13.0
 * 
 * @param <I>            input class
 * @param <O>            output class
 */
public class CApiClient<I, O> extends AApiClient<I, O>
{

	/** The Constant LOGGER. */
	protected static final Logger LOGGER = LoggerFactory.getLogger(CApiClient.class);

	/** Gson builder to create gson. */
	private GsonBuilder builder;

	/** The encode api value. */
	boolean encodeApiValue;

	/**
	 * Instantiates a new api client.
	 */
	public CApiClient()
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
	public CApiClient(boolean encodeApiValue)
	{
		this();
		this.encodeApiValue = encodeApiValue;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.api.client.IApiClient#makeCall(sk.qbsw.core.api.client.http.IHttpApiRequest, java.lang.String, I, java.lang.reflect.Type, org.apache.http.entity.ContentType)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public O makeCall(IHttpApiRequest request, String url, I input, Type returnType, ContentType contentType) throws IOException
	{
		// create gson from builder
		Gson gson = this.builder.create();

		// process request
		String response = makeCall(request, url, input, contentType);

		// process response
		O responseObject = (O) gson.fromJson(response, returnType);
		return responseObject;
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.api.client.IApiClient#makeCall(sk.qbsw.core.api.client.http.IHttpApiRequest, java.lang.String, I, java.lang.reflect.Type)
	 */
	@Override
	public O makeCall(IHttpApiRequest request, String url, I input, Type returnType) throws IOException
	{
		return this.makeCall(request, url, input, returnType, ContentType.APPLICATION_JSON);
	}

	/* (non-Javadoc)
	 * @see sk.qbsw.core.api.client.IApiClient#makeCall(sk.qbsw.core.api.client.http.IHttpApiRequest, java.lang.String, I, org.apache.http.entity.ContentType)
	 */
	@Override
	public String makeCall(IHttpApiRequest request, String url, I input, ContentType contentType) throws IOException
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
		return makeCall(request, url, contentType, requestJson);
	}

	/**
	 * Gets the builder.
	 *
	 * @return the builder
	 */
	protected GsonBuilder getBuilder() {
		return builder;
	}

	/**
	 * Sets the builder.
	 *
	 * @param builder the new builder
	 */
	protected void setBuilder(GsonBuilder builder) {
		this.builder = builder;
	}

	/**
	 * Initializes Gson Builder - register standard date
	 * serializer/deserializer.
	 * 
	 * @param builder
	 *            builder to initialize
	 * @return the gson builder
	 */
	protected GsonBuilder prepareGsonBuilder(GsonBuilder builder)
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
	public void registerTypeAdapter(Type type, Object typeAdapter)
	{
		this.builder = builder.registerTypeAdapter(type, typeAdapter);
	}
}
