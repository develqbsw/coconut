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
	 * Gson builder to create gson
	 */
	private GsonBuilder builder;
	
	public CApiClient ()
	{
		//create builder
		this.builder = new GsonBuilder();
		//initialize builder and register default adapters
		this.builder = prepareGsonBuilder(builder);
	}

	/**
	 * Initializes Gson Builder - register standard date serializer/deserializer
	 * @param builder builder to initialize
	 */
	protected GsonBuilder prepareGsonBuilder (GsonBuilder builder)
	{
		return this.builder.registerTypeAdapter(Date.class, new CDateJSonSerializer());
	}

	/**
	 * register adapter to request on server(If class implements JsonSerializer) and for response from server(If class implements JsonDeserializer)  
	 * @param type for which is adapter registered
	 * @param typeAdapter adapter to register
	 */
	public void registerTypeAdapter (Type type, Object typeAdapter)
	{
		 this.builder = builder.registerTypeAdapter(type, typeAdapter);
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
		// create gson from builder
		Gson gson = this.builder.create();

		// process request
		String response = makeCall(request, url, input);

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
		// create gson from builder
		Gson gson = this.builder.create();

		// prepare request
		String requestJson = gson.toJson(input);

		// process request
		String response = request.makeCall(url, ContentType.APPLICATION_JSON, requestJson);

		return response;
	}

}
