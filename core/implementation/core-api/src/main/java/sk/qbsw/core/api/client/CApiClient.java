package sk.qbsw.core.api.client;

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
	 * Prepare gson.
	 */
	public void prepareGson ()
	{
		// initialize GSON Builder
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new CDateJSonSerializer());

		this.gson = gsonBuilder.create();
	}

	public O makeCall (IHttpApiRequest request, String url, I input, Class<O> returnClass)
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
		O responseObject = gson.fromJson(response, returnClass);
		return responseObject;
	}
}
