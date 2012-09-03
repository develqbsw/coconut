package sk.qbsw.core.api.client;

import java.lang.reflect.Type;
import java.util.Date;

import org.apache.http.entity.ContentType;

import sk.qbsw.core.api.client.gson.serializer.CDateJSonSerializer;
import sk.qbsw.core.api.client.http.IHttpApiRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * API Client - combination of HTTP request and GSon parser
 * 
 * @author Dalibor Rak
 * @version 1.2.0
 * @since 1.2.0
 * 
 * @param <I> input class
 * @param <O> output class
 */
public class CApiClient<I, O>
{

	/**
	 * GSon instance
	 */
	private Gson gson;

	/**
	 * Prepare gson. Calls prepareGsonBuilder.
	 */
	public final void prepareGson ()
	{
		// initialize GSON Builder
		GsonBuilder gsonBuilder = new GsonBuilder();
		prepareGsonBuilder(gsonBuilder);

		this.gson = gsonBuilder.create();
	}

	/**
	 * Initializes Gson Builder.
	 * @param builder builder to initialize
	 */
	protected void prepareGsonBuilder (GsonBuilder builder)
	{
		builder.registerTypeAdapter(Date.class, new CDateJSonSerializer());
	}

	/**
	 * Makes call to API
	 * @param request request to use
	 * @param url URL to API
	 * @param input input parameter
	 * @param returnClass Class to return
	 * @return Returned object (instance of returnClass)
	 */
	public O makeCall (IHttpApiRequest request, String url, I input, Class<O> returnClass)
	{
		return makeCall(request, url, input, (Type) returnClass);
	}

	/**
	 * Makes call to API
	 * @param request request to use
	 * @param url URL to API
	 * @param input input parameter
	 * @param returnType Type to return
	 * @return Returned object (instance of returnClass)
	 */
	@SuppressWarnings ("unchecked")
	public O makeCall (IHttpApiRequest request, String url, I input, Type returnType)
	{
		// if needed, initialize Gson
		if (gson == null)
		{
			prepareGson();
		}

		// prepare request
		String requestJson = gson.toJson(input);

		// process request
		String response = request.makeCall(url, ContentType.APPLICATION_JSON, requestJson);

		// process response
		O responseObject = (O) gson.fromJson(response, returnType);
		return responseObject;
	}

	/**
	 * Makes call to API
	 * @param request request to use
	 * @param url URL to API
	 * @param input input parameter
	 * @param returnType Type to return
	 * @return Returned object (instance of returnClass)
	 */
	public String makeCall (IHttpApiRequest request, String url, I input)
	{
		// if needed, initialize Gson
		if (gson == null)
		{
			prepareGson();
		}

		// prepare request
		String requestJson = gson.toJson(input);

		// process request
		String response = request.makeCall(url, ContentType.APPLICATION_JSON, requestJson);

		return response;
	}

}
