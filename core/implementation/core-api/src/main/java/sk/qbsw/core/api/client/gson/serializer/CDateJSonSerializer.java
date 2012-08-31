package sk.qbsw.core.api.client.gson.serializer;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Date Serializer for GSON library.
 * 
 * @author Dalibor Rak
 * @version 1.2.0
 * @since 1.2.0
 */
public class CDateJSonSerializer implements JsonSerializer<Date>, JsonDeserializer<Date>
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gson.JsonSerializer#serialize(java.lang.Object,
	 * java.lang.reflect.Type, com.google.gson.JsonSerializationContext)
	 */
	public JsonElement serialize (Date src, Type typeOfSrc, JsonSerializationContext context)
	{
		return src == null ? null : new JsonPrimitive(src.getTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement,
	 * java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	public Date deserialize (JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		return json == null ? null : new Date(json.getAsLong());
	}
}
