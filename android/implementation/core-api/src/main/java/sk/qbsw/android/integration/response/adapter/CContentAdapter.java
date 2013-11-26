package sk.qbsw.android.integration.response.adapter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import sk.qbsw.android.exception.CExceptionHandler;

import com.google.api.client.util.Base64;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Adapter for memory optimalization for byte json serialization and deserialization
 * @author Michal Lacko
 * @version 0.1.0
 * @since 0.4.0
 */
public class CContentAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]>
{

	@Override
	public JsonElement serialize (byte[] src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject element = new JsonObject();
		//code bytes to base64 encoding
		byte[] encodedBytes = Base64.encode(src);
		//and add them into json in UTF-8 encoding string
		try
		{
			element.addProperty("content", new String(encodedBytes, "UTF-8"));
		}
		catch (UnsupportedEncodingException e)
		{
			CExceptionHandler.logException(CContentAdapter.class, e);
		}
		return element;
	}

	@Override
	public byte[] deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		byte[] bytesContentToReturn = null;

		//get all string from jsonElement
		String content = json.getAsJsonObject().get("content").getAsString();
		try
		{
			byte[] byteContent = content.getBytes("UTF-8");

			//if is content null then return null
			if (content != null)
			{
				try
				{
					bytesContentToReturn = Base64.decode(byteContent);
				}
				catch (IOException e)
				{
					//when decode from base64 failed
					CExceptionHandler.logException(CContentAdapter.class, e);
					throw new JsonParseException(e);
				}
			}
		}
		catch (UnsupportedEncodingException e)
		{
			CExceptionHandler.logException(CContentAdapter.class, e);
		}

		return bytesContentToReturn;
	}


}
